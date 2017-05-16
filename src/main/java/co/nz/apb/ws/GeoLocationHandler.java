package co.nz.apb.ws;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class GeoLocationHandler extends TextWebSocketHandler {
	private final Logger log = LoggerFactory.getLogger(getClass());
	private List<WebSocketSession> sessions = new ArrayList<>();

	@Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
		log.info("handleTextMessage - ",message.getPayload());
		try {
			session.sendMessage(new TextMessage("Hi"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
    }
	
	public void updateAlert(String msg){
		log.info("updateAlert - {} , {}",msg,sessions.size());
		try {
			for(WebSocketSession ws : sessions) {
				log.info("sendMessage - {}",msg);
				ws.sendMessage(new TextMessage(msg));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.info("afterConnectionEstablished");
		sessions.add(session);
		log.info("sessions {}",sessions.size());
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		log.info("afterConnectionClosed");
		sessions.remove(session);
	}
	
	
}
