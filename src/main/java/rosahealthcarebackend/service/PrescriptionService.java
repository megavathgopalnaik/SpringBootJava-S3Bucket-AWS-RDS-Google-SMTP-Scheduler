package rosahealthcarebackend.service;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;

import rosahealthcarebackend.common.CommonResponse;

@Component
public interface PrescriptionService {

	CommonResponse addPrescription(JsonNode userData);

	CommonResponse getPrescription(JsonNode userData);

	CommonResponse getPrescriptionbyPatientId(JsonNode userData);

	CommonResponse getprescriptionByBookingId(JsonNode userData);

}
