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
		String userId = message.getPayload().toLowerCase();
		log.info("Connected to {} as user -  {} ",addr,userId);
		userSessions.put(addr,userId);
		sessions.put(userId,session);	
		log.info("Total sessions after connection of {}/{} - {}",addr,userId,sessions.size());
    }
	
	public void updateLocation(String msg){
		//log.info("updateLocation - {} , Sessions {} , userSessions {}",msg,sessions.size(),userSessions.size());
		try {			
			log.info("Update Location - {}",msg);			
			WebSocketSession ws = sessions.get("admin");
			if(ws!=null  && ws.isOpen()){
				ws.sendMessage(new TextMessage(msg));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void updateResponser(String msg,String responserId){
		log.info("Update Responser {} - {} ",responserId,msg);
		try {			
				log.info("sessions {} ",sessions);
				WebSocketSession ws = sessions.get(responserId.toLowerCase()); 
				if(ws!=null && ws.isOpen()){
					log.info("Updating responser");			
					ws.sendMessage(new TextMessage(msg));
				}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		String addr = session.getRemoteAddress().toString();
		log.info("Connection Established with {} ",addr);		
		userSessions.put(addr,"");
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		String addr = session.getRemoteAddress().toString();		
		String userId = userSessions.get(addr);		
		log.info("Connection closed for session {}/{}",addr,userId);
		sessions.remove(userId);
		userSessions.remove(addr);
		log.info("Total sessions after closer of {}/{} - {}",addr,userId,sessions.size());
	}
	
	
}
