package rosahealthcarebackend.service;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;

import rosahealthcarebackend.common.CommonResponse;

@Component
public interface AdminService {


	CommonResponse getAdmin(JsonNode userData);

	CommonResponse addAdmin(JsonNode userData);

	CommonResponse updateAdmin(JsonNode userData);



}
