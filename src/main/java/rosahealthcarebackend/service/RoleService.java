package rosahealthcarebackend.service;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;

import rosahealthcarebackend.common.CommonResponse;

@Component
public interface RoleService {

	CommonResponse addRole(JsonNode userData);

	CommonResponse getRole(JsonNode userData);

}
