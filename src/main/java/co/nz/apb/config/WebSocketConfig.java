package co.nz.apb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import co.nz.apb.ws.GeoLocationHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer{
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(geoLocationHandler(), "/GeoLocationHandler").setAllowedOrigins("*");	
	}		
	
	@Bean
	public GeoLocationHandler geoLocationHandler(){
		return new GeoLocationHandler();
	}

}
