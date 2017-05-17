package co.nz.apb.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.nz.apb.ws.GeoLocationHandler;

@RestController
@RequestMapping("/api/geolocation")
public class GeoLocationController {

	Logger log = LoggerFactory.getLogger(getClass());
	private final GeoLocationHandler geoLocationHandler;
	
	@Autowired
	public GeoLocationController(GeoLocationHandler geoLocationHandler){
		this.geoLocationHandler = geoLocationHandler;
	}
	
	
	@PostMapping("/{userId}")
	public void tagLocation(@PathVariable String userId,
				@RequestParam String lat,@RequestParam String lng,@RequestParam String role){
		log.info("tagLocation - userId={} lat={} lng={} role={}",userId,lat,lng,role);
		StringBuilder message = new StringBuilder(100);
		message.append(userId).append(",").append(lat).append(",").append(lng).append(",").append(role);
		geoLocationHandler.updateAlert(message.toString());
	}
	
	@PostMapping("/tag/{responseId}")
	public void tagResponser(@PathVariable String responseId,@RequestParam String userId,
				@RequestParam String lat,@RequestParam String lng){
		log.info("tagResponser - userId={} lat={} lng={} ",userId,lat);
		StringBuilder message = new StringBuilder(100);
		message.append(userId).append(",").append(lat).append(",").append(lng);
		geoLocationHandler.updateResponser(message.toString(),responseId);
	}
	
}
