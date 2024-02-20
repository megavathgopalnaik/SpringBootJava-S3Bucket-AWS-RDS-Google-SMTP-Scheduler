package rosahealthcarebackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

import rosahealthcarebackend.common.CommonResponse;
import rosahealthcarebackend.service.RoleService;

@RestController
public class RoleApi {
 
	@Autowired
	private RoleService roleService;
	
	@PostMapping("/RSAddRole")
	public CommonResponse addRole (@RequestBody JsonNode reqObj) {
	CommonResponse commonResponse = null;
	try {
	JsonNode userData = reqObj.get("ADDRole").get("rs_add_recin");
	commonResponse = roleService.addRole(userData) ;
	}catch (Exception e) {
	e.printStackTrace() ;
	commonResponse = new CommonResponse() ;
	commonResponse. setResponseCode (1) ;
	commonResponse.setResponseMessage("Exception occured in UserController"); 
	commonResponse. setResponseStatus ("Failure") ;
	}
	return commonResponse;

	}
     
	
	@PostMapping("/RSGetRole")
	public CommonResponse getRole (@RequestBody JsonNode reqObj) {
	CommonResponse commonResponse = null;
	try {
	JsonNode userData = reqObj.get("GetRole").get("rs_add_recin");
	commonResponse = roleService.getRole(userData) ;
	}catch (Exception e) {
	e.printStackTrace() ;
	commonResponse = new CommonResponse() ;
	commonResponse. setResponseCode (1) ;
	commonResponse.setResponseMessage("Exception occured in UserController"); 
	commonResponse. setResponseStatus ("Failure") ;
	}
	return commonResponse;

	}
}
