package com.htsc.consumer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;

@Configuration
@DubboComponentScan
public class ConsumerConfiguration {
	/**
	 * 当前应用配置
	 */
	@Bean
	public ApplicationConfig applicationConfig() {
		ApplicationConfig applicationConfig = new ApplicationConfig();
		applicationConfig.setName("dubbo-consumer");
		return applicationConfig;
	}

	/**
	 * 当前连接注册中心配置
	 */
	@Bean
	public RegistryConfig registryConfig() {
		RegistryConfig registryConfig = new RegistryConfig();
		registryConfig.setAddress("zookeeper://106.15.224.136:2181");
		return registryConfig;
	}

//	@Bean(name = "multipartResolver")
//	public MultipartResolver multipartResolver() {
//		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
//		resolver.setDefaultEncoding("UTF-8");
//		resolver.setResolveLazily(true);// resolveLazily属性启用是为了推迟文件解析，以在在UploadAction中捕获文件大小异常
//		resolver.setMaxInMemorySize(40960);
//		resolver.setMaxUploadSize(50 * 1024 * 1024);// 上传文件大小 50M 50*1024*1024
//		return resolver;
//	}

	@Bean
	public ProductInfoConsumer ProductInfoConsumer() {
		return new ProductInfoConsumer();
	}

	@Bean
	public SecKillConsumer secKillConsumer() {
		return new SecKillConsumer();
	}

	@Bean
	public UserServiceConsumer userServiceConsumer() {
		return new UserServiceConsumer();
	}

	@Bean
	public ManagerServiceConsumer managerServiceConsumer() {
		return new ManagerServiceConsumer();
	}

	@Bean
	FundManagerConsumer fundManagerConsumer() {
		return new FundManagerConsumer();
	}

	@Bean
	SecKillResultConsumer secKillResultConsumer() {
		return new SecKillResultConsumer();
	}
}