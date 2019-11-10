package org.why.studio.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableEurekaClient
@EnableZuulProxy
@SpringBootApplication
@EnableCaching
public class WhyGatewayApp {

    public static void main(String[] args) {
        SpringApplication.run(WhyGatewayApp.class, args);
    }

}
