package com.safziy.generate;

import com.mybatisflex.codegen.Generator;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.zaxxer.hikari.HikariDataSource;


/**
 * 生成Mybatis-Flex代码
 */
public class MybatisFlexCodegen {

    public static void main(String[] args) {
        //配置数据源
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/all?characterEncoding=utf-8");
        dataSource.setUsername("root");
        dataSource.setPassword("admin");

        //创建配置内容
        GlobalConfig globalConfig = createGlobalConfig();
        //通过 datasource 和 globalConfig 创建代码生成器
        Generator generator = new Generator(dataSource, globalConfig);
        //生成代码
        generator.generate();
    }

    public static GlobalConfig createGlobalConfig() {
        //创建配置内容
        GlobalConfig globalConfig = new GlobalConfig();

        //设置根包
        globalConfig.setBasePackage("com.safziy");

        //设置表前缀和只生成哪些表
        globalConfig.setTablePrefix("t_");
        globalConfig.setGenerateTable("t_user_sudoku", "t_user_sudoku_history");

        //设置生成 entity 并启用 Lombok
        globalConfig.setEntityGenerateEnable(true);
        globalConfig.setEntityWithLombok(true);
        //设置生成 mapper
        globalConfig.enableMapper();

//        globalConfig.enableService();
//        globalConfig.enableServiceImpl();
//        globalConfig.enableController();
        globalConfig.enableMapperXml();
        globalConfig.enablePackageInfo();

        //可以单独配置某个列
//        ColumnConfig columnConfig = new ColumnConfig();
//        columnConfig.setColumnName("tenant_id");
//        columnConfig.setLarge(true);
//        columnConfig.setVersion(true);
//        globalConfig.setColumnConfig("tb_account", columnConfig);

        return globalConfig;
    }

}
