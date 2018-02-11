package top.kylewang.taotao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author Kyle.Wang
 * 2018/1/23 0023 20:26
 */

@SpringBootApplication
@MapperScan("top.kylewang.taotao.mapper")
@EnableEurekaClient
public class SSOServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(SSOServiceApplication.class, args);
    }

}
