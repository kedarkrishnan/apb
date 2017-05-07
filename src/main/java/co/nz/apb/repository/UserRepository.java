package co.nz.apb.repository;

import org.springframework.data.repository.CrudRepository;

import co.nz.apb.domain.User;

public interface UserRepository extends CrudRepository<User,String>{

	public User findUserByDeviceId(String deviceId);
}
