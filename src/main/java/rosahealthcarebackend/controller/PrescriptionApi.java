package rosahealthcarebackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

import rosahealthcarebackend.common.CommonResponse;
import rosahealthcarebackend.service.PrescriptionService;

@RestController
public class PrescriptionApi {

	@Autowired
	private PrescriptionService prescriptionService;
	
	@PostMapping("/RSAddPrescription")
 	public CommonResponse addPrescription (@RequestBody JsonNode reqObj) {
	CommonResponse commonResponse = null;
      try {

	JsonNode userData = reqObj.get("RSPRESADDOP").get("rs_ad_recin");
	
              commonResponse=prescriptionService.addPrescription(userData);

      }catch (Exception e) {
        e.printStackTrace() ;
          commonResponse = new CommonResponse() ;
          commonResponse. setResponseCode (1) ;
          commonResponse.setResponseMessage("Exception occured in Controller"); 
          commonResponse. setResponseStatus ("Failure") ;
    }
 	return commonResponse;

}
	@PostMapping("/RSGetPrescription")
 	public CommonResponse getPrescription (@RequestBody JsonNode reqObj) {
	CommonResponse commonResponse = null;
           try {

	      JsonNode userData = reqObj.get("RSPRESGETOP").get("rs_add_recin");
	
         commonResponse=prescriptionService.getPrescription(userData);

            }catch (Exception e) {
        e.printStackTrace() ;
          commonResponse = new CommonResponse() ;
         commonResponse. setResponseCode (1) ;
        commonResponse.setResponseMessage("Exception occured in Controller");  
         commonResponse. setResponseStatus ("Failure") ;
      }
 	    return commonResponse;

     }

	@PostMapping("/RSGetPrescriptionById")
 	public CommonResponse getPrescriptionbyPatientId (@RequestBody JsonNode reqObj) {
	CommonResponse commonResponse = null;
           try {

	      JsonNode userData = reqObj.get("RSPRESGETOP").get("rs_add_recin");
	
         commonResponse=prescriptionService.getPrescriptionbyPatientId(userData);

            }catch (Exception e) {
        e.printStackTrace() ;
          commonResponse = new CommonResponse() ;
         commonResponse. setResponseCode (1) ;
        commonResponse.setResponseMessage("Exception occured in Controller");  
         commonResponse. setResponseStatus ("Failure") ;
      }
 	    return commonResponse;

     }
	@PostMapping("/RSBGetByBookingId")
	public CommonResponse getprescriptionByBookingId(@RequestBody JsonNode reqObj) {
		CommonResponse commonResponse=null;
		try {
			 JsonNode userData = reqObj.get("RSPRESGETOP").get("rs_add_recin");
				
	         commonResponse=prescriptionService.getprescriptionByBookingId(userData);

		} catch (Exception e) {
			 e.printStackTrace() ;
	          commonResponse = new CommonResponse() ;
	         commonResponse. setResponseCode (1) ;
	        commonResponse.setResponseMessage("Exception occured in Controller");  
	         commonResponse. setResponseStatus ("Failure") ;
	      }
	 	    return commonResponse;
	}
}
