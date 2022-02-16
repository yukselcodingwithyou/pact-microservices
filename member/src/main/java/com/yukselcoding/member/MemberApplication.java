package com.yukselcoding.member;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class MemberApplication {

	private static final String BASE_URL = "http://localhost:9999";
	public static final int TIMEOUT = 1000;

	public static void main(String[] args) {
		SpringApplication.run(MemberApplication.class, args);
	}

	@Bean
	public WebClient webClientWithTimeout() {
		final var tcpClient = TcpClient
				.create()
				.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, TIMEOUT)
				.doOnConnected(connection -> {
					connection.addHandlerLast(new ReadTimeoutHandler(TIMEOUT, TimeUnit.MILLISECONDS));
					connection.addHandlerLast(new WriteTimeoutHandler(TIMEOUT, TimeUnit.MILLISECONDS));
				});

		return WebClient.builder()
				.baseUrl(BASE_URL)
				.clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
				.build();
	}

}

