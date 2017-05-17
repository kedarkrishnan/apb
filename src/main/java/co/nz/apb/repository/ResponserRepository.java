package co.nz.apb.repository;

import org.springframework.data.repository.CrudRepository;

import co.nz.apb.domain.Responser;

public interface ResponserRepository extends CrudRepository<Responser,String>{

		public Responser findByDeviceId(String deviceId); 
}
