package top.kylewang.taotao.item;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * @author Kyle.Wang
 * 2018/1/23 0023 20:26
 */
@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
public class ItemWebApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ItemWebApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ItemWebApplication.class, args);
    }
}
