package com.safziy.controller;

import com.safziy.constants.MpConstants;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/wx/mp")
public class MpController {
    @Resource
    private WxMpService wxMpService;
    @Resource
    private WxMpMessageRouter wxMpMessageRouter;

    @GetMapping("portal/{appId}")
    public String wxMpVerify(@PathVariable String appId, String signature, String timestamp, String nonce,
                             String echostr) {
        log.info("\n接收到来自微信公众号服务器的认证消息：[{}, {}, {}, {}]", signature, timestamp, nonce, echostr);
        if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
            throw new IllegalArgumentException("请求参数非法，请核实!");
        }
        if (!wxMpService.switchover(appId)) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appId));
        }
        if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
            return "非法请求";
        }
        return echostr;
    }

    @PostMapping("portal/{appId}")
    public String wxMpMessage(@PathVariable String appId,
                               String signature, String timestamp, String nonce, String openid,
                               @RequestParam(name = "encrypt_type", defaultValue = "raw") String encryptType,
                               @RequestParam(name = "msg_signature") String msgSignature,
                               @RequestBody String message) {
        log.info("\n接收微信公众号请求：[openid=[{}], [signature=[{}], encryptType=[{}], msgSignature=[{}], " +
                        "timestamp=[{}], nonce=[{}], message=[{}]",
                openid, signature, encryptType, msgSignature, timestamp, nonce, message);
        if (!wxMpService.switchover(appId)) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appId));
        }
        if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
            throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
        }
        switch (encryptType) {
            // 明文消息
            case MpConstants.EncryptTypeRaw -> {
                WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(message);
                WxMpXmlOutMessage outMessage = wxMpMessageRouter.route(inMessage);
                if (outMessage == null) {
                    log.info("outMessage is null");
                    return "success";
                }
                return outMessage.toXml();
            }
            // AES加密消息
            case MpConstants.EncryptTypeAes -> {
                WxMpConfigStorage config = wxMpService.getWxMpConfigStorage();
                WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(message, config, timestamp, nonce, msgSignature);
                WxMpXmlOutMessage outMessage = wxMpMessageRouter.route(inMessage);
                if (outMessage == null) {
                    log.info("outMessage is null");
                    return "success";
                }
                return outMessage.toEncryptedXml(config);
            }
            default -> {
                log.info("unknown message type");
                return "success";
            }
        }
    }
}
