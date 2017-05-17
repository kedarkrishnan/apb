package co.nz.apb.ws;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class GeoLocationHandler extends TextWebSocketHandler {
	private final Logger log = LoggerFactory.getLogger(getClass());
	private Map<String,WebSocketSession> sessions = new HashMap<>();
	private Map<String,String> userSessions = new HashMap<>();
	
	@Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
		String addr = session.getRemoteAddress().toString();
		log.info("user for session  {} -  {} ",addr,message.getPayload());
		userSessions.put(addr,message.getPayload());		
    }
	
	public void updateAlert(String msg){
		log.info("updateAlert - {} , {}",msg,sessions.size());
		try {			
			for(String key : sessions.keySet()) {
				log.info("sendMessage - {}",msg);
				WebSocketSession ws = sessions.get(key); 
				ws.sendMessage(new TextMessage(msg));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void updateResponser(String msg,String userId){
		log.info("updateResponser - {} , {}",msg,sessions.size());
		try {			
				WebSocketSession ws = sessions.get(userSessions.get(userId)); 
				ws.sendMessage(new TextMessage(msg));			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		String addr = session.getRemoteAddress().toString();
		log.info("ConnectionEstablished with {} ",addr);		
		sessions.put(addr,session);
		userSessions.put(addr,"");
		log.info("sessions {}",sessions.size());
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		log.info("afterConnectionClosed");
		sessions.remove(session);
	}
	
	
}
