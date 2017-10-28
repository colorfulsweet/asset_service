package com.app;



import javax.annotation.Resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@SpringBootApplication(scanBasePackages = {"com.web", "com.utils"})//直接注册到spring容器当中的bean
@EnableJpaRepositories(basePackages = {"com.web.dao"})//针对实体类创建的Repository接口进行的扫描
@EntityScan(basePackages = {"com.web.entity"})//针对实体类进行的扫描 ( 扫描的是@Entity注解 )
@EnableScheduling //执行@Scheduled注解扫描, 加载定时任务
public class Application extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
	
	@Resource(name="tokenInterceptor")
	private HandlerInterceptor tokenInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//配置拦截器
		registry.addInterceptor(tokenInterceptor) //token拦截器
			.addPathPatterns("/**")
			.excludePathPatterns("/login") //登陆API
			.excludePathPatterns("/lz/outputQrcode/*") //二维码图片输出
			.excludePathPatterns("/lz/readPhoto") //上传的资产照片输出
			.excludePathPatterns("/views/**/*") //PC页面视图相关
			.excludePathPatterns("/views/*") 
			.excludePathPatterns("/views")
			.excludePathPatterns("/error")
			;
		super.addInterceptors(registry);
	}
	
	@Override  
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*") //允许跨域请求
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .maxAge(3600);
    }
}
