package co.nz.apb.services;

import co.nz.apb.domain.Responser;

public interface ResponserService {

	public Responser createUpdateResponser(Responser responser);
	public Responser getResponserByDeviceId(String deviceId);
	public Responser getResponserById(String responserId);
}
