package my.utils;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

public class MybatisPlusCodeGenerator {
	/**
	 * 配置信息
	 */
	private static String outputDir = System.getProperty("user.dir") + "/src/test/java";
	private static String author = "xuezw";
	private static boolean swagger2Flag = true;
	
	private static String datasource_url = "jdbc:mysql://localhost:3306/meetingfilm?useUnicode=true&useSSL=false&characterEncoding=utf8";
	private static String datasource_user = "root";
	private static String datasource_pwd = "root";
	private static String datasource_driver = "com.mysql.jdbc.Driver";
	
	private static String moduleName = "user";
	private static String parentPackage = "backend.common";
	
	private static String tableName = "mooc_backend_user_t";

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        // 文件生成位置
        gc.setOutputDir(outputDir);
        gc.setAuthor(author);
        // 实体属性 Swagger2 注解 
        gc.setSwagger2(swagger2Flag); 
        gc.setFileOverride(true);
        gc.setBaseResultMap(true);
        gc.setOpen(false);
        gc.setBaseColumnList(true);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(datasource_url);
        dsc.setUsername(datasource_user);
        dsc.setPassword(datasource_pwd);
        dsc.setDriverName(datasource_driver);
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(moduleName);
        pc.setParent(parentPackage);
        mpg.setPackageInfo(pc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setRestControllerStyle(true);
        strategy.setInclude(tableName);
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        strategy.setEntityTableFieldAnnotationEnable(true);
        
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

}
