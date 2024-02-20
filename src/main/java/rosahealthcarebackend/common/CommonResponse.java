package rosahealthcarebackend.common;

import java.util.Objects;

public class CommonResponse {

	Integer responseCode;
	String responseStatus;
	Object responseData;
	String responseMessage;
	public Integer getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseStatus() {
		return responseStatus;
	}
	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}
	public Object getResponseData() {
		return responseData;
	}
	public void setResponseData(Object responseData) {
		this.responseData = responseData;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	@Override
	public String toString() {
		return "CommonResponse [responseCode=" + responseCode + ", responseStatus=" + responseStatus + ", responseData="
				+ responseData + ", responseMessage=" + responseMessage + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(responseCode, responseData, responseMessage, responseStatus);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CommonResponse other = (CommonResponse) obj;
		return Objects.equals(responseCode, other.responseCode) && Objects.equals(responseData, other.responseData)
				&& Objects.equals(responseMessage, other.responseMessage)
				&& Objects.equals(responseStatus, other.responseStatus);
	}

}
