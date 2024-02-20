package rosahealthcarebackend.service;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;

import rosahealthcarebackend.common.CommonResponse;

@Component
public interface PatientService {

	CommonResponse addPatient(JsonNode userData);


	CommonResponse getPatient(JsonNode userData);

	
	CommonResponse getPatientById(JsonNode userData);

	
	CommonResponse updatePatient(JsonNode userData);


	CommonResponse patientDashBoard(JsonNode userData);

}
