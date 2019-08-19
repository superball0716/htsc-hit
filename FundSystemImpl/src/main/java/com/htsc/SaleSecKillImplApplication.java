package com.htsc;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;

@ComponentScan(value = { "com.htsc.redis","com.htsc.kafka" })
@MapperScan("com.htsc.mapper")
@SpringBootApplication
@DubboComponentScan("com.htsc.impl")
public class SaleSecKillImplApplication extends SpringBootServletInitializer{
	
	/**
	 * 当前应用配置
	 */
	@Bean("dubbo-annotation-provider")
	public ApplicationConfig applicationConfig() {
		ApplicationConfig applicationConfig = new ApplicationConfig();
		applicationConfig.setName("dubbo-server");
		return applicationConfig;
	}

	/**
	 * 当前连接注册中心配置
	 */
	@Bean("my-registry")
	public RegistryConfig registryConfig() {
		RegistryConfig registryConfig = new RegistryConfig();
		registryConfig.setAddress("zookeeper://106.15.224.136:2181");
		return registryConfig;
	}

	/**
	 * 当前连接注册中心配置
	 */
	@Bean("dubbo")
	public ProtocolConfig protocolConfig() {
		ProtocolConfig protocolConfig = new ProtocolConfig();
		protocolConfig.setName("dubbo");
		protocolConfig.setPort(12345);
		return protocolConfig;
	}

	public static void main(String[] args) {
		SpringApplication.run(SaleSecKillImplApplication.class, args);
	}
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SaleSecKillImplApplication.class);
    }
	

}
