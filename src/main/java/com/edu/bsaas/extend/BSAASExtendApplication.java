package com.edu.bsaas.extend;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.apache.catalina.Context;
import org.apache.tomcat.util.scan.StandardJarScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;

import java.net.InetAddress;
import java.net.UnknownHostException;

@EnableAsync
//@EnableCreateCacheAnnotation
@SpringBootApplication(scanBasePackages = {"com.edu.bsaas"}, exclude = {DruidDataSourceAutoConfigure.class, GsonAutoConfiguration.class})
public class BSAASExtendApplication extends SpringBootServletInitializer {
    static {
        // 设置子线程读取MDC变量
        System.setProperty("log4j2.isThreadContextMapInheritable", Boolean.toString(true));
        System.setProperty("application.name", "b_extend");
    }

    private static Logger log = LoggerFactory.getLogger(BSAASExtendApplication.class.getName());

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BSAASExtendApplication.class);
    }

    public static void main(String[] args) throws UnknownHostException {
        SpringApplicationBuilder applicationBuilder = new SpringApplicationBuilder(BSAASExtendApplication.class);
        ConfigurableApplicationContext application = applicationBuilder.web(WebApplicationType.SERVLET).run(args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String path = env.getProperty("server.servlet.context-path");
        log.info("\n----------------------------------------------------------\n\t" +
                "Application Jeecg-Boot is running! Access URLs:\n\t" +
                "Local: \t\thttp://localhost:" + port + path + "/\n\t" +
                "External: \thttp://" + ip + ":" + port + path + "/\n\t" +
                "Doc文档: \thttp://" + ip + ":" + port + path + "/doc.html\n" +
                "----------------------------------------------------------");
    }

    /**
     * tomcat-embed-jasper引用后提示jar找不到的问题
     */
    @Bean
    public TomcatServletWebServerFactory tomcatFactory() {
        return new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                ((StandardJarScanner) context.getJarScanner()).setScanManifest(false);
            }
        };
    }
}
