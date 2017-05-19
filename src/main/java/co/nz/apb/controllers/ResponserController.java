package co.nz.apb.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.nz.apb.domain.Responser;
import co.nz.apb.services.ResponserService;

@RestController
@RequestMapping("/api/responser")
public class ResponserController {

	Logger log = LoggerFactory.getLogger(getClass());
	
	private final ResponserService responserService;
	
	@Autowired
	ResponserController(ResponserService responserService){
		this.responserService = responserService;
	}
	
	@PostMapping
	public Responser CreateUpdateUser(@RequestBody Responser responser){
		log.info("Create / update responser - {}",responser);
		return responserService.createUpdateResponser(responser);
	}
	
	@GetMapping("/{deviceId}")	
	public Responser getResponserDetails(@PathVariable String deviceId){
		log.info("Get responser details for device Id = {}",deviceId);
		return responserService.getResponserByDeviceId(deviceId);
	}
	
	@GetMapping("/id/{responserId}")	
	public Responser getResponserDetailsById(@PathVariable String responserId){
		log.info("Get responser details for Id = {}",responserId);
		return responserService.getResponserById(responserId);
	}
	
	
}
