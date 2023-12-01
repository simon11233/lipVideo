package com.inspur.gateway;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
@EnableDiscoveryClient
public class GateWayApplication {
    public static void main(String[] args) {
        //去除nacos日志
        System.setProperty("nacos.logging.default.config.enabled", "false");
        ApplicationContext applicationContext = SpringApplication.run(GateWayApplication.class, args);
        Environment env = applicationContext.getEnvironment();
        System.out.println("----run env："+env.getProperty("spring.profiles.active")+"----");
    }
}
