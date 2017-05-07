package co.nz.apb.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
public class User {

	@Id
	private String userId;
	private String userName;
	private String deviceId;
	private String phone;
	private String address;
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name="userId")
	private Set<MedicalDetail> medicalDetails;
	@OneToOne(cascade=CascadeType.ALL,orphanRemoval=true)	
	private EmergencyContact emergencyContact;
	
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
	public Set<MedicalDetail> getMedicalDetails() {
		return medicalDetails;
	}
	public void setMedicalDetails(Set<MedicalDetail> medicalDetails) {
		this.medicalDetails = medicalDetails;
	}
	public EmergencyContact getEmergencyContact() {
		return emergencyContact;
	}
	public void setEmergencyContact(EmergencyContact emergencyContact) {
		this.emergencyContact = emergencyContact;
	}
	
	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", deviceId=" + deviceId + ", phone=" + phone
				+ ", address=" + address + ", medicalDetails=" + medicalDetails + ", emergencyContact="
				+ emergencyContact + "]";
	}

}
