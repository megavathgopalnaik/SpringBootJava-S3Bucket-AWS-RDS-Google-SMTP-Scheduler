package rosahealthcarebackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

import rosahealthcarebackend.common.CommonResponse;
import rosahealthcarebackend.service.AdminService;



@RestController
public class AdminApi {

	@Autowired
	private AdminService adminService;
	
	@PostMapping("/RSAdminAdd")  
	public CommonResponse adminAdd(@RequestBody JsonNode reqObj ) {
		
	 	CommonResponse commonResponse = null;
		try {
		
		JsonNode userData = reqObj.get("RSADMINAPPADDOP").get("rs_ad_recin");
		if(! reqObj.get("RSADMINAPPADDOP").get("rs_ad_recin").isEmpty()) {
		if(!reqObj.get("RSADMINAPPADDOP").get("rs_ad_recin").get("rs_admin_id").asText().isEmpty()) {
			commonResponse=adminService.updateAdmin(userData);
		}else {
		commonResponse = adminService.addAdmin(userData) ;
		}
		}
		}catch (Exception e) {
		e.printStackTrace() ;
		commonResponse = new CommonResponse() ;
		commonResponse. setResponseCode (1) ;
		commonResponse.setResponseMessage("Exception occured in Controller"); 
		commonResponse. setResponseStatus ("Failure") ;
		}
	     	return commonResponse;

		
	}



	   @PostMapping(value="/RSGetAdmin", consumes = MediaType.APPLICATION_JSON_VALUE)
		public CommonResponse getAdmin(@RequestBody JsonNode reqObj) {
			CommonResponse commonResponse=null;
			try {
				JsonNode userData = reqObj.get("GetAdminOperation").get("rs_add_recin");
				commonResponse=adminService.getAdmin(userData);
				
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