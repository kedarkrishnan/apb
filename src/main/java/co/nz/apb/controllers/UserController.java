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

import co.nz.apb.domain.User;
import co.nz.apb.services.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	private final Logger log = LoggerFactory.getLogger(getClass());
	
	private final UserService userService;
	
	@Autowired
	UserController(UserService userService){
		this.userService = userService;
	}
	
	@PostMapping
	public User CreateUpdateUser(@RequestBody User user){
		log.info("Create / update user - {}",user);
		return userService.createUpdateUser(user);
	}
	
	@GetMapping("/{userId}")
	public User getUserDetails(@PathVariable String userId){
		log.info("Get user details having user Id = {}",userId);
		return userService.getUserByUserId(userId);		
	}
	
	
}
