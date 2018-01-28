package top.kylewang.taotao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * @author Kyle.Wang
 * 2018/1/23 0023 20:26
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class ManagerWebApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(ManagerWebApplication.class,args);
    }
}
