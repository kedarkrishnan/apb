package co.nz.apb.services;

import co.nz.apb.domain.User;

public interface UserService {

	public User createUpdateUser(User user);
	public User getUserByUserId(String userId);
}
