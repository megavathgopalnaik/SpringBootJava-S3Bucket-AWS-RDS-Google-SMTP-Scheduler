package rosahealthcarebackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;

import jakarta.transaction.Transactional;
import rosahealthcarebackend.common.CommonResponse;
import rosahealthcarebackend.entity.Admin;
import rosahealthcarebackend.entity.Role;
import rosahealthcarebackend.repository.RoleRepository;

@Service
@Transactional
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public CommonResponse addRole(JsonNode userData) {
		// TODO Auto-generated method stub
		CommonResponse response=new CommonResponse();
		try {
			Role role=new Role();
			role.setName(userData.get("rs_user_role").asText()); 
			Role r=roleRepository.save(role);
			response.setResponseCode(0);
			response.setResponseData(r.getId());
			response.setResponseMessage("role added successfully");
			response.setResponseStatus("Success");
			
			
			
		}catch(Exception e) {
		e.printStackTrace();
		response.setResponseCode(1);
		response.setResponseMessage("Exception Occured while Adding role");
		response.setResponseStatus("Failure");
		}
		return response;
	}

	@Override
	public CommonResponse getRole(JsonNode userData) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
