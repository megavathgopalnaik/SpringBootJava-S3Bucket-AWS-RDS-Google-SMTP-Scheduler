package rosahealthcarebackend.service;


import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;

import rosahealthcarebackend.common.CommonResponse;

@Component
public interface UserService {

	CommonResponse authenticateUser(String email, String password);

	CommonResponse addUser(JsonNode userData);

	CommonResponse getUser(JsonNode userData);

	 CommonResponse getDashboardData();

	CommonResponse updatePassword(JsonNode userData);

	CommonResponse updateUser(JsonNode userData);

	CommonResponse getUserById(JsonNode userData);

	CommonResponse updateTime(JsonNode userData);

	

}
