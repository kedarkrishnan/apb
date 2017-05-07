package co.nz.apb.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import co.nz.apb.domain.User;
import co.nz.apb.repository.UserRepository;

@Component
public class UserServiceImpl implements UserService{

	private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	private final UserRepository userRepository;
	
	public UserServiceImpl(UserRepository userRepository){
		this.userRepository = userRepository;
	}
	
	@Override
	public User createUpdateUser(User user) {
		log.info("Creating / updating user {}",user);
		return userRepository.save(user);
	}

	@Override
	public User getUserFromDeviceId(String deviceId) {
		log.info("retriving user having device id = {}",deviceId);
		return userRepository.findUserByDeviceId(deviceId);
	}

}
