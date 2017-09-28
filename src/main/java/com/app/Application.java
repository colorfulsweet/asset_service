package com.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//直接注册到spring容器当中的bean
@SpringBootApplication(scanBasePackages = {"com.web", "com.utils"})
//针对实体类创建的Repository接口进行的扫描
@EnableJpaRepositories(basePackages = {"com.web.dao"})
//针对实体类进行的扫描 ( 扫描的是@Entity注解 )
@EntityScan(basePackages = {"com.web.entity"})
public class Application {
	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
