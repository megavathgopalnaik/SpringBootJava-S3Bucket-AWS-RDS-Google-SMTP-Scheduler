package rosahealthcarebackend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

import rosahealthcarebackend.common.CommonResponse;
import rosahealthcarebackend.service.PatientService;

@RestController
public class PatientApi {
	
	@Autowired
	private PatientService patientService;
	
	@PostMapping("/RSAddPatient")
	public CommonResponse addPatient(@RequestBody JsonNode reqObj) {
		CommonResponse commonResponse = null;
		try {
		JsonNode userData = reqObj.get("RSPATIENTADDOP").get("rs_ad_recin");
		if(! reqObj.get("RSPATIENTADDOP").get("rs_ad_recin").isEmpty()) {
			
		if(! reqObj.get("RSPATIENTADDOP").get("rs_ad_recin").get("rs_patient_id").asText().isEmpty()) {
			commonResponse=patientService.updatePatient(userData);
		}else {
			
	
		commonResponse =patientService.addPatient(userData) ;
		}
		}
		}catch (Exception e) {
		e.printStackTrace() ;
		commonResponse = new CommonResponse() ;
		commonResponse. setResponseCode (1) ;
		commonResponse.setResponseMessage("Exception occured in UserController"); 
		commonResponse. setResponseStatus ("Failure") ;
		}
		return commonResponse;

	}
	
	
	   @PostMapping(value="/RSGetPatient", consumes = MediaType.APPLICATION_JSON_VALUE)
		public CommonResponse getPatient(@RequestBody JsonNode reqObj) {
			CommonResponse commonResponse=null;
			try {
				JsonNode userData = reqObj.get("GetPatientOperation").get("rs_add_recin");
				
				commonResponse=patientService.getPatient(userData);
					
					
			}catch(Exception e) {
				e.printStackTrace() ;
				commonResponse = new CommonResponse() ;
				commonResponse. setResponseCode (1) ;
				commonResponse.setResponseMessage("Exception occured in Controller"); 
				commonResponse. setResponseStatus ("Failure") ;
			}
			return commonResponse;
	
}
	   @PostMapping(value="/RSGetPatientById", consumes = MediaType.APPLICATION_JSON_VALUE)
		public CommonResponse getPatientById(@RequestBody JsonNode reqObj) {
			CommonResponse commonResponse=null;
			try {
				JsonNode userData = reqObj.get("GetPatientByIdOperation").get("rs_add_recin");
				commonResponse=patientService.getPatientById(userData);
				
			}catch(Exception e) {
				e.printStackTrace() ;
				commonResponse = new CommonResponse() ;
				commonResponse. setResponseCode (1) ;
				commonResponse.setResponseMessage("Exception occured in Controller"); 
				commonResponse. setResponseStatus ("Failure") ;
			}
			return commonResponse;
}

	   @PostMapping(value="/RSPatientDashBoard", consumes = MediaType.APPLICATION_JSON_VALUE)
		public CommonResponse patientDashBoard(@RequestBody JsonNode reqObj) {
			CommonResponse commonResponse=null;
			try {
				JsonNode userData = reqObj.get("GetPatientOperation").get("rs_add_recin");
				commonResponse=patientService.patientDashBoard(userData);
				
			}catch(Exception e) {
				e.printStackTrace() ;
				commonResponse = new CommonResponse() ;
				commonResponse. setResponseCode (1) ;
				commonResponse.setResponseMessage("Exception occured in Controller"); 
				commonResponse. setResponseStatus ("Failure") ;
			}
			return commonResponse;
}
}