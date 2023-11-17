package com.inspur.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
@ComponentScan(basePackages = {"com.inspur"})
public class InspurDemoEduApplication {
    public static void main(String[] args) {
        SpringApplication.run(InspurDemoEduApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  教育模块启动成功   ლ(´ڡ`ლ)ﾞ" );
    }
}
