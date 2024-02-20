package rosahealthcarebackend.service;

import java.io.IOException;


import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;

import rosahealthcarebackend.common.CommonResponse;

@Service
public interface DoctorService {

	

	CommonResponse addDoctor(JsonNode userData);

	String uploadFileToS3(MultipartFile data) throws IOException;

	byte[] downloadS3File(String filename) throws IOException;

	CommonResponse getDoctor(JsonNode userData);

	CommonResponse addImage(MultipartFile data);

	CommonResponse updateDoctor(JsonNode userData);

	CommonResponse getActiveDoctor(JsonNode userData);

	CommonResponse getDoctorDashboardData(JsonNode userData);

	CommonResponse getDoctorWithOutImage(JsonNode userData);




}
