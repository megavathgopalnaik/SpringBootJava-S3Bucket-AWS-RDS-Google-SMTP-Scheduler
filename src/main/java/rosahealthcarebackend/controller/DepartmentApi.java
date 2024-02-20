package rosahealthcarebackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

import rosahealthcarebackend.common.CommonResponse;
import rosahealthcarebackend.service.DepartmentService;

@RestController
public class DepartmentApi {
	
	
	@Autowired
	private DepartmentService departmentService;

	   @PostMapping(value="/RSGetDepartment", consumes = MediaType.APPLICATION_JSON_VALUE)
		public CommonResponse getDepartment(@RequestBody JsonNode reqObj) {
			CommonResponse commonResponse=null;
			try {
				JsonNode userData = reqObj.get("GetDepartmentOperation").get("rs_add_recin");
				commonResponse=departmentService.getDepartment(userData);
				
			}catch(Exception e) {
				e.printStackTrace() ;
				commonResponse = new CommonResponse() ;
				commonResponse. setResponseCode (1) ;
				commonResponse.setResponseMessage("Exception occured in Department Controller"); 
				commonResponse. setResponseStatus ("Failure") ;
			}
			return commonResponse;

}
}