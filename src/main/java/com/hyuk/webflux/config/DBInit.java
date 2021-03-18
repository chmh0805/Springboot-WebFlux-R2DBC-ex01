package com.hyuk.webflux.config;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

import com.hyuk.webflux.domain.Customer;
import com.hyuk.webflux.domain.CustomerRepository;

import io.r2dbc.spi.ConnectionFactory;

@Configuration
public class DBInit {
	
	@Bean
	public ConnectionFactoryInitializer dbInit(ConnectionFactory connectionFactory) {
		ConnectionFactoryInitializer init = new ConnectionFactoryInitializer();
		init.setConnectionFactory(connectionFactory);
		init.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));
		return init;
	}

	// CommandLineRunner를 리턴하는 함수에서, CommandLineRunner는 리턴하는 값 또는 라인을 실행시킨다.
	@Bean
	public CommandLineRunner dbinit(CustomerRepository customerRepository) {
		return (args) -> {
			// 데이터 초기화하기
			customerRepository.saveAll(Arrays.asList(
				new Customer("Jack", "Bauer"),
				new Customer("Jung", "Bauer"),
				new Customer("Kim", "Bauer"),
				new Customer("Song", "Bauer"),
				new Customer("Shin", "Bauer")
			)).blockLast(); // blockLast를 걸어줘야한다 !
		};
	}
}
