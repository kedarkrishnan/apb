package co.nz.apb.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class EmergencyContact {

	@Id
	@GeneratedValue
	private long contactId;
	private String userId;
	private String contactName;
	private String phone;
	private String relation;

	public long getContactId() {
		return contactId;
	}
	public void setContactId(long contactId) {
		this.contactId = contactId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	@Override
	public String toString() {
		return "EmergencyContact [contactId=" + contactId + ", userId=" + userId + ", contactName=" + contactName
				+ ", phone=" + phone + ", relation=" + relation + "]";
	}	
	
	
}
