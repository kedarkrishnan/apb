package co.nz.apb.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/geo")
public class GeoLocationController {

	Logger log = LoggerFactory.getLogger(getClass());
	
	@GetMapping("/tag/{deviceId}")
	public void tagLocation(@PathVariable String deviceId,
				@RequestParam String lat,@RequestParam String lng){
		log.info("tagLocation - deviceId={} lat={} lng={}",deviceId,lat,lng);
	}
	
}
