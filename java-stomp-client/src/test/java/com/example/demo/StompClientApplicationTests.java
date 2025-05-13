package com.example.demo;

import java.lang.reflect.Type;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.lang.Nullable;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.util.concurrent.CompletableToListenableFutureAdapter;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

@SpringBootTest
class StompClientApplicationTests {

	private final static String URL = "ws://localhost:61616/stomp";
	private final static String TOPIC = "/topic/myTopic";
	private static final String QUEUE_NAME = "roboti";
	private static final String USER_NAME = "admin";
	private static final String USER_PASSWORD = "admin";

	@Test
	void contextLoads() throws Exception {
		CountDownLatch latch = new CountDownLatch(1);

		WebSocketStompClient stompClient = new WebSocketStompClient(
				new StandardWebSocketClient());
		stompClient.setMessageConverter(new StringMessageConverter());

		StompSessionHandler sessionHandler = new StompSessionHandlerAdapter() {
			@Override
			public void afterConnected(@Nullable StompSession session,
					@Nullable StompHeaders connectedHeaders) {
				System.out.println("Connected");

				session.subscribe(TOPIC, new StompFrameHandler() {
					@Override
					public Type getPayloadType(@Nullable StompHeaders headers) {
						return String.class;
					}

					@Override
					public void handleFrame(@Nullable StompHeaders headers,
							@Nullable Object payload) {
						System.out.println("Received: " + payload);
						latch.countDown();
					}
				});

				session.send(TOPIC, "Hello from Spring STOMP!");
			}
		};


		final StompHeaders connectHeaders = new StompHeaders();
		connectHeaders.setAcceptVersion("1.2"); 
		connectHeaders.add("login", USER_NAME);
		connectHeaders.add("passcode", USER_PASSWORD);

		// ListenableFuture<StompSession> connect = stompClient.connect(URL,
		// new WebSocketHttpHeaders(), connectHeaders, sessionHandler);

		CompletableFuture<StompSession> connect = stompClient.connectAsync(URL,
				new WebSocketHttpHeaders(), connectHeaders, sessionHandler);

		connect.get();

		latch.await(10, TimeUnit.SECONDS);

		stompClient.stop();

	}

}
