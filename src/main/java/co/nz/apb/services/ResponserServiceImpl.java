package co.nz.apb.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import co.nz.apb.domain.Responser;
import co.nz.apb.repository.ResponserRepository;

@Component
public class ResponserServiceImpl implements ResponserService{

	private final Logger log = LoggerFactory.getLogger(ResponserServiceImpl.class);
	private final ResponserRepository responserRepository;
	
	public ResponserServiceImpl(ResponserRepository responserRepository){
		this.responserRepository = responserRepository;
	}
	
	@Override
	public Responser createUpdateResponser(Responser responser) {
		log.info("Creating / updating responser {}",responser);
		return responserRepository.save(responser);
	}

	@Override
	public Responser getResponserByDeviceId(String deviceId) {
		log.info("retriving responser having device id = {}",deviceId);
		return responserRepository.findByDeviceId(deviceId);
	}
	
	@Override
	public Responser getResponserById(String responserId) {
		log.info("retriving responser having responser id = {}",responserId);
		return responserRepository.findOne(responserId);
	}

}
