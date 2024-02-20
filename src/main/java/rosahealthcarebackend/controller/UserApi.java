package rosahealthcarebackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

import rosahealthcarebackend.common.CommonResponse;
import rosahealthcarebackend.service.UserService;

@RestController

public class UserApi {
	@Autowired
	private UserService userService;
	
	@PostMapping("/RSLogin")
	public CommonResponse authenticateUser (@RequestBody JsonNode reqObj) {
		CommonResponse commonResponse = null;
		try {
		if (reqObj.get("RSLoginOperation").get("rs_log_recin").get("rs_user_email").asText()==null) {
		commonResponse = new CommonResponse () ;
		commonResponse. setResponseCode (1) ;
		commonResponse. setResponseMessage ("Invalid Username");
		commonResponse. setResponseStatus ("'Failure") ;
		}else if (reqObj.get("RSLoginOperation").get("rs_log_recin").get("rs_user_pswd").asText()==null) {
		commonResponse = new CommonResponse () ;
		commonResponse. setResponseCode (1) ;
		commonResponse. setResponseMessage ("Invalid Password");
		commonResponse. setResponseStatus ("Failure");
		}else {
		String email = reqObj.get("RSLoginOperation").get("rs_log_recin").get("rs_user_email") .asText () ;
		String password = reqObj.get("RSLoginOperation").get("rs_log_recin").get("rs_user_pswd").asText();
		commonResponse=userService.authenticateUser (email, password);
		}
		}catch (Exception e) {
		e.printStackTrace () ;
		commonResponse = new CommonResponse () ;
		commonResponse.setResponseCode (1) ;
		commonResponse.setResponseMessage("Exception occured in UserController"); 
		commonResponse.setResponseStatus ("Failure") ;
		}
		return commonResponse;
	}
	
	@PostMapping("/RSUserAdd")
	public CommonResponse addUser (@RequestBody JsonNode reqObj) {
	CommonResponse commonResponse = null;
	try {
	JsonNode userData = reqObj.get("UserADDOperation").get("rs_add_recin");
	commonResponse = userService.addUser(userData) ;
	}catch (Exception e) {
	e.printStackTrace() ;
	commonResponse = new CommonResponse() ;
	commonResponse. setResponseCode (1) ;
	commonResponse.setResponseMessage("Exception occured in UserController"); 
	commonResponse. setResponseStatus ("Failure") ;
	}
	return commonResponse;

	}

	

	@PostMapping("/RSUGetAdd")
	public CommonResponse getUser (@RequestBody JsonNode reqObj) {
	CommonResponse commonResponse = null;
	try {
	JsonNode userData = reqObj.get("UserADDOperation").get("rs_add_recin");
	commonResponse = userService.getUser(userData) ;
	}catch (Exception e) {
	e.printStackTrace() ;
	commonResponse = new CommonResponse() ;
	commonResponse. setResponseCode (1) ;
	commonResponse.setResponseMessage("Exception occured in UserController"); 
	commonResponse. setResponseStatus ("Failure") ;
	}
	return commonResponse;
}
	
	@PostMapping("/RSgetDashBoardData")
	public CommonResponse getDashboardData() {
		CommonResponse commonResponse=null;
		try {
			commonResponse=userService.getDashboardData();
			
		}catch(Exception e) {
			e.printStackTrace() ;
			commonResponse = new CommonResponse() ;
			commonResponse. setResponseCode (1) ;
			commonResponse.setResponseMessage("Exception occured in DashBoard Controller"); 
			commonResponse. setResponseStatus ("Failure") ;
		}
		return commonResponse;
}
     @PostMapping("/RSUpdatePassword")
     public CommonResponse updatePassword(@RequestBody JsonNode reqObj) {
    	 CommonResponse commonResponse=null;
    	 try {
    		 JsonNode userData=reqObj.get("RSDOCADDOP").get("rs_ad_recin");
    		 commonResponse=userService.updatePassword(userData);
    	 }catch (Exception e) {
    		 e.printStackTrace() ;
 			commonResponse = new CommonResponse() ;
 			commonResponse. setResponseCode (1) ;
 			commonResponse.setResponseMessage("Exception occured in Update Password Controller"); 
 			commonResponse. setResponseStatus ("Failure") ;
		}
    	 return commonResponse;
     }
     

     @PostMapping("/RSUpdateUser")
     public CommonResponse updateUser(@RequestBody JsonNode reqObj) {
    	 CommonResponse commonResponse=null;
    	 try {
    		 JsonNode userData=reqObj.get("RSDOCADDOP").get("rs_ad_recin");
    		 commonResponse=userService.updateUser(userData);
    	 }catch (Exception e) {
    		 e.printStackTrace() ;
 			commonResponse = new CommonResponse() ;
 			commonResponse. setResponseCode (1) ;
 			commonResponse.setResponseMessage("Exception occured in Update Controller"); 
 			commonResponse. setResponseStatus ("Failure") ;
		}
    	 return commonResponse;
     }

     @PostMapping("/RSGetUserById")
     public CommonResponse getUserById(@RequestBody JsonNode reqObj) {
    	 CommonResponse commonResponse=null;
    	 try {
    		 JsonNode userData=reqObj.get("RSDOCGETOP").get("rs_add_recin");
    		 commonResponse=userService.getUserById(userData);
    	 }catch (Exception e) {
    		 e.printStackTrace() ;
 			commonResponse = new CommonResponse() ;
 			commonResponse. setResponseCode (1) ;
 			commonResponse.setResponseMessage("Exception occured in  Controller"); 
 			commonResponse. setResponseStatus ("Failure") ;
		}
    	 return commonResponse;
     }
     @PostMapping("/RSUpdateTime")
     public CommonResponse updateTime(@RequestBody JsonNode reqObj) {
    	 CommonResponse commonResponse=null;
    	 try {
    		 JsonNode userData=reqObj.get("RSUPDATEOP").get("rs_ad_recin");
    		 commonResponse=userService.updateTime(userData);
    	 }catch (Exception e) {
    		 e.printStackTrace() ;
 			commonResponse = new CommonResponse() ;
 			commonResponse. setResponseCode (1) ;
 			commonResponse.setResponseMessage("Exception occured in  Controller"); 
 			commonResponse. setResponseStatus ("Failure") ;
		}
    	 return commonResponse;
}

}