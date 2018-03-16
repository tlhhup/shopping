package org.tlh.shopping.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@Order(Integer.MAX_VALUE-1)
@ComponentScan(basePackages="org.tlh.shopping.search")
public class ESConfig {
	
	@Value("${es.host}")
	private String host;
	
	@Value("${es.port}")
	private int port;
	
	@Bean(destroyMethod="close")
	public RestHighLevelClient restHighLevelClient(){
		RestHighLevelClient client=new RestHighLevelClient(RestClient.builder(new HttpHost(host, port)));
		return client;
	}
	
	@Bean
	public ObjectMapper objectMapper(){
		ObjectMapper objectMapper=new ObjectMapper();
		return objectMapper;
	}
	
}
