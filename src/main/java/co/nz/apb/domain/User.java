package co.nz.apb.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class User {

	@Id
	private String userId;
	private String userName;
	private String deviceId;
	private String phone;
	private String address;	
	private String medicalDetails;
	@OneToMany(cascade=CascadeType.ALL,orphanRemoval=true)
	@JoinColumn(name="userId")
	private Set<EmergencyContact> emergencyContact;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMedicalDetails() {
		return medicalDetails;
	}
	public void setMedicalDetails(String medicalDetails) {
		this.medicalDetails = medicalDetails;
	}
	public Set<EmergencyContact> getEmergencyContact() {
		return emergencyContact;
	}
	public void setEmergencyContact(Set<EmergencyContact> emergencyContact) {
		this.emergencyContact = emergencyContact;
	}
	
	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", deviceId=" + deviceId + ", phone=" + phone
				+ ", address=" + address + ", medicalDetails=" + medicalDetails + ", emergencyContact="
				+ emergencyContact + "]";
	}

}
