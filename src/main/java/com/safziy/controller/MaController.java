package com.safziy.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaMessage;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.binarywang.wx.miniapp.constant.WxMaConstants;
import cn.binarywang.wx.miniapp.message.WxMaMessageRouter;
import cn.binarywang.wx.miniapp.util.WxMaConfigHolder;
import com.safziy.controller.base.WebSupport;
import com.safziy.controller.response.WxMaLogin;
import com.safziy.entity.WxUser;
import com.safziy.controller.base.Resp;
import com.safziy.service.LoginService;
import com.safziy.utils.JsonUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/wx/ma")
public class MaController implements WebSupport {
    @Resource
    private WxMaService wxMaService;
    @Resource
    private WxMaMessageRouter wxMaMessageRouter;
    @Resource
    private LoginService loginService;

    @GetMapping("portal/{appId}")
    public String wxMaVerify(@PathVariable String appId, String signature, String timestamp, String nonce,
                             String echoStr) {
        log.info("\n接收到来自微信小程序服务器的认证消息：[{}, {}, {}, {}]", signature, timestamp, nonce, echoStr);
        if (StringUtils.isAnyBlank(signature, timestamp, nonce, echoStr)) {
            throw new IllegalArgumentException("请求参数非法，请核实!");
        }
        if (!wxMaService.switchover(appId)) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appId));
        }
        if (!wxMaService.checkSignature(timestamp, nonce, signature)) {
            WxMaConfigHolder.remove(); // 清理ThreadLocal
            return "非法请求";
        }
        WxMaConfigHolder.remove(); // 清理ThreadLocal
        return echoStr;
    }

    @PostMapping("portal/{appId}")
    public String wxMaMessage(@PathVariable String appId,
                               String signature, String timestamp, String nonce, String openid,
                               @RequestParam(name = "encrypt_type", defaultValue = "raw") String encryptType,
                               @RequestParam(name = "msg_signature") String msgSignature,
                               @RequestBody String message) {
        log.info("\n接收微信小程序请求：[openid=[{}], [signature=[{}], encryptType=[{}], msgSignature=[{}], " +
                        "timestamp=[{}], nonce=[{}], message=[{}]",
                openid, signature, encryptType, msgSignature, timestamp, nonce, message);
        if (!wxMaService.switchover(appId)) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appId));
        }
        if (!wxMaService.checkSignature(timestamp, nonce, signature)) {
            throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
        }
        final boolean isJson = Objects.equals(wxMaService.getWxMaConfig().getMsgDataFormat(),
                WxMaConstants.MsgDataFormat.JSON);
        if (StringUtils.isBlank(encryptType)) {
            // 明文传输的消息
            WxMaMessage inMessage;
            if (isJson) {
                inMessage = WxMaMessage.fromJson(message);
            } else {//xml
                inMessage = WxMaMessage.fromXml(message);
            }

            this.route(inMessage);
            WxMaConfigHolder.remove();//清理ThreadLocal
            return "success";
        }

        if ("aes".equals(encryptType)) {
            // 是aes加密的消息
            WxMaMessage inMessage;
            if (isJson) {
                inMessage = WxMaMessage.fromEncryptedJson(message, wxMaService.getWxMaConfig());
            } else {//xml
                inMessage = WxMaMessage.fromEncryptedXml(message, wxMaService.getWxMaConfig(),
                        timestamp, nonce, msgSignature);
            }

            this.route(inMessage);
            WxMaConfigHolder.remove();//清理ThreadLocal
            return "success";
        }
        WxMaConfigHolder.remove();//清理ThreadLocal
        throw new RuntimeException("不可识别的加密类型：" + encryptType);
    }

    private void route(WxMaMessage message) {
        try {
            wxMaMessageRouter.route(message);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 登陆接口
     */
    @PostMapping("/login/{appId}")
    public Resp<WxMaLogin> login(@PathVariable String appId, String code) {
        if (StringUtils.isBlank(code)) {
            throw new RuntimeException();
        }
        if (!wxMaService.switchover(appId)) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appId));
        }
        try {
            WxMaJscode2SessionResult session = wxMaService.getUserService().getSessionInfo(code);
            log.info(session.getSessionKey());
            log.info(session.getOpenid());
            WxUser wxUser = loginService.wxMaLogin(session.getOpenid());
            WxMaLogin loginResult = new WxMaLogin();
            loginResult.setUserId(wxUser.getUserId());
            loginResult.setOpenId(wxUser.getOpenId());
            return success(loginResult);
        } catch (WxErrorException e) {
            log.error(e.getMessage(), e);
            return error("error", "error");
        } finally {
            WxMaConfigHolder.remove();//清理ThreadLocal
        }
    }

    /**
     * <pre>
     * 获取用户信息接口
     * </pre>
     */
    @GetMapping("/info/{appId}")
    public String info(@PathVariable String appId, String sessionKey,
                       String signature, String rawData, String encryptedData, String iv) {
        if (!wxMaService.switchover(appId)) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appId));
        }

        // 用户信息校验
        if (!wxMaService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            WxMaConfigHolder.remove();//清理ThreadLocal
            return "user check failed";
        }

        // 解密用户信息
        WxMaUserInfo userInfo = wxMaService.getUserService().getUserInfo(sessionKey, encryptedData, iv);
        WxMaConfigHolder.remove();//清理ThreadLocal
        return JsonUtil.toJson(userInfo);
    }

    /**
     * <pre>
     * 获取用户绑定手机号信息
     * </pre>
     */
    @GetMapping("/phone/{appId}")
    public String phone(@PathVariable String appId, String sessionKey, String signature,
                        String rawData, String encryptedData, String iv) {
        if (!wxMaService.switchover(appId)) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appId));
        }

        // 用户信息校验
        if (!wxMaService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            WxMaConfigHolder.remove();//清理ThreadLocal
            return "user check failed";
        }

        // 解密
        WxMaPhoneNumberInfo phoneNoInfo = wxMaService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);
        WxMaConfigHolder.remove();//清理ThreadLocal
        return JsonUtil.toJson(phoneNoInfo);
    }
}
