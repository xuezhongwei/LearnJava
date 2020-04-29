package api.freemarker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import freemarker.cache.StringTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreemarkerDemo {

	private static final String TEMPLATE_PATH = "src/main/java/com/freemark/hello/templates";
    private static final String CLASS_PATH = "src/main/java/com/freemark/hello";
    
    public static void main(String[] args) {
       
    }
    
    /**
     * 通过模板配置文件使用freemarker
     */
    public static void demo1() {
    	 // step1 创建freeMarker配置实例。要设置版本号，选最新的就好。
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
        Writer out = null;
        try {
            // step2 获取模版路径
            configuration.setDirectoryForTemplateLoading(new File(TEMPLATE_PATH));
            
            // step3 创建数据模型
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("classPath", "com.freemark.hello");
            dataMap.put("className", "AutoCodeDemo");
            dataMap.put("helloWorld", "通过简单的 <代码自动生产程序> 演示 FreeMarker的HelloWorld！");
            
            // step4 加载模版文件
            Template template = configuration.getTemplate("hello.ftl");
            
            // step5 生成数据
            File docFile = new File(CLASS_PATH + "\\" + "AutoCodeDemo.java");
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile)));
            
            // step6 输出文件
            template.process(dataMap, out);
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^AutoCodeDemo.java 文件创建成功 !");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.flush();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
    /**
     * 通过模板字符串使用freemarker
     */
    public static void demo2() {
    	// step1 创建freeMarker配置实例。要设置版本号，选最新的就好。
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
        Writer out = null;
        try {
            // step2 创建数据模型
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("classPath", "com.freemark.hello");
            dataMap.put("className", "AutoCodeDemo");
            dataMap.put("helloWorld", "通过简单的 <代码自动生产程序> 演示 FreeMarker的HelloWorld！");
            
            // step3 设置模板加载器，用于加载模板。
            // 根据模板的来源，会有不同的模板加载器。常用StringTemplateLoader,FileTemplateLoader
            StringTemplateLoader templateLoader = new StringTemplateLoader();
            String templateKey = "mytemplate";
            String templateSource = "这是${value}模板字符串";
            templateLoader.putTemplate(templateKey, templateSource);
            configuration.setTemplateLoader(templateLoader);
            
            // step4 加载模版文件
            Template template = configuration.getTemplate(templateKey);
            
            // step5 生成数据
            File docFile = new File(CLASS_PATH + "\\" + "AutoCodeDemo.java");
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile)));
            
            // step6 输出文件
            template.process(dataMap, out);
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^AutoCodeDemo.java 文件创建成功 !");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.flush();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
    /**
     * 通过模板文件使用freemarker
     */
    public static void demo3() {
    	// TODO
    }
}


