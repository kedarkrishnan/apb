package co.nz.apb.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class MedicalDetail {

	@Id
	@GeneratedValue
	private long detailId;
	private String userId;
	private String detailType;
	private String detailValue;	

	public long getDetailId() {
		return detailId;
	}


	public void setDetailId(long detailId) {
		this.detailId = detailId;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getDetailType() {
		return detailType;
	}


	public void setDetailType(String detailType) {
		this.detailType = detailType;
	}


	public String getDetailValue() {
		return detailValue;
	}


	public void setDetailValue(String detailValue) {
		this.detailValue = detailValue;
	}
	

	
	@Override
	public String toString() {
		return "MedicalDetail [detailId=" + detailId + ", userId=" + userId + ", detailType=" + detailType
				+ ", detailValue=" + detailValue + "]";
	}
}
