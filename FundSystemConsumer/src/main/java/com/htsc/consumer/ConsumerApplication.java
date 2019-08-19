package com.htsc.consumer;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ConsumerApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(ConsumerApplication.class, args);
	}
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ConsumerApplication.class);
    }
//
//	@Bean
//	private static ApplicationContext startConsumerContext() {
//		// 创建服务消费方 Annotation 配置上下文
//		AnnotationConfigApplicationContext consumerContext = new AnnotationConfigApplicationContext();
//		// 注册服务消费方配置 Bean
//		consumerContext.register(ConsumerConfiguration.class);
//		// 启动服务消费方上下文
//		consumerContext.refresh();
//		// 返回服务消费方 Annotation 配置上下文
//		return consumerContext;
//	}
}