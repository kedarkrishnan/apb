package co.nz.apb.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.nz.apb.domain.Responser;
import co.nz.apb.domain.User;

@RestController
@RequestMapping("/api/responser")
public class ResponserController {

	Logger log = LoggerFactory.getLogger(getClass());
	
	@PostMapping
	public User CreateUpdateUser(@RequestBody Responser responser){
		log.info("Create / update responser - {}",responser);
		return null;
	}
	
	@GetMapping("/{deviceId}")
	public User getResponserDetails(@PathVariable String deviceId){
		log.info("Get responser details having device Id = {}",deviceId);
		return null;
	}
	
	
}
