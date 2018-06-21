/**
 * 
 */
package com.garlane.relation.common.socket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @author lixingfa
 * @date 2018年6月21日上午10:50:55
 * 
 */
@Configuration
@EnableWebSocket
public class WebsocketConfig implements WebSocketConfigurer {
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(myHandler(), "/ws").addInterceptors(new MyHandShake());

		registry.addHandler(myHandler(), "/ws/sockjs").addInterceptors(new MyHandShake()).withSockJS();
	}
	
	@Bean
    public WebsocketHandler myHandler() {
        return new WebsocketHandler();
    }
}