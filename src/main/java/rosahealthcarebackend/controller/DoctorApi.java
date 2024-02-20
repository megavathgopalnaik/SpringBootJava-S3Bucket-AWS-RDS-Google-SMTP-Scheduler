package rosahealthcarebackend.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.JsonNode;

import rosahealthcarebackend.common.CommonResponse;
import rosahealthcarebackend.service.DoctorService;





@RestController
public class DoctorApi {

	
	
	
	@Autowired
	private DoctorService doctorService;
	
	@PostMapping("/RSDoctorAdd")
     	public CommonResponse addDoctor (@RequestBody JsonNode reqObj) {
   	CommonResponse commonResponse = null;
	try {
	
		JsonNode userData = reqObj.get("RSDOCADDOP").get("rs_ad_recin");
		if(!reqObj.get("RSDOCADDOP").get("rs_ad_recin").isEmpty()) {
	if( !reqObj.get("RSDOCADDOP").get("rs_ad_recin").get("rs_doctor_id").asText().isEmpty()) {
		
	commonResponse=doctorService.updateDoctor(userData);
	}else {
		
		commonResponse = doctorService.addDoctor(userData) ;
	
		}
		}
	}catch (Exception e) {
	e.printStackTrace() ;
	commonResponse = new CommonResponse() ;
	commonResponse. setResponseCode (1) ;
	commonResponse.setResponseMessage("Exception occured in UserController"); 
	commonResponse. setResponseStatus ("Failure") ;
	}
     	return commonResponse;

	}
   
   
   @GetMapping("/filefroms3/{filename}")
    public ResponseEntity<byte[]> downloads3File(@PathVariable String filename) throws IOException{
    	byte[] resp=doctorService.downloadS3File(filename);
    	return ResponseEntity.ok(resp);
    
    	
   }
   

   @PostMapping(value="/RSGetDoctor", consumes = MediaType.APPLICATION_JSON_VALUE)
	public CommonResponse getDoctor(@RequestBody JsonNode reqObj) {
		CommonResponse commonResponse=null;
		try {
			JsonNode userData = reqObj.get("GetDoctorOperation").get("rs_add_recin");
		    System.out.println(System.getenv("AWS_BUCKET"));
			commonResponse=doctorService.getDoctor(userData);
			
		}catch(Exception e) {
			e.printStackTrace() ;
			commonResponse = new CommonResponse() ;
			commonResponse. setResponseCode (1) ;
			commonResponse.setResponseMessage("Exception occured in DoctorController"); 
			commonResponse. setResponseStatus ("Failure") ;
		}
		return commonResponse;

   }
 @CrossOrigin(origins = "*")
 @PostMapping("/RDIimageUpload")
 public CommonResponse  uploadFileToS3(@RequestParam("imageFile") MultipartFile data) throws
	  IOException{  
	CommonResponse commonResponse = null;
     try {

     commonResponse  = doctorService.addImage(data) ;
 }catch (Exception e) {
 e.printStackTrace() ;
 commonResponse = new CommonResponse() ;
 commonResponse. setResponseCode (1) ;
 commonResponse.setResponseMessage("Exception occured in Controller"); 
 commonResponse. setResponseStatus ("Failure") ;
  }
	return commonResponse;

	  
 }

 @PostMapping(value="/RSGetActiveDoctor", consumes = MediaType.APPLICATION_JSON_VALUE)
	public CommonResponse getActiveDoctor(@RequestBody JsonNode reqObj) {
		CommonResponse commonResponse=null;
		try {
			JsonNode userData = reqObj.get("GetDoctorOperation").get("rs_add_recin");
			commonResponse=doctorService.getActiveDoctor(userData);
			
		}catch(Exception e) {
			e.printStackTrace() ;
			commonResponse = new CommonResponse() ;
			commonResponse. setResponseCode (1) ;
			commonResponse.setResponseMessage("Exception occured in Doctor Controller"); 
			commonResponse. setResponseStatus ("Failure") ;
		}
		return commonResponse;
      }
 
 @PostMapping("/RSgetDoctorDashBoardData")
	public CommonResponse getDoctorDashboardData(@RequestBody JsonNode reqObj) {
		CommonResponse commonResponse=null;
		try {
			
			
			JsonNode userData = reqObj.get("GetDoctorDashBoard").get("rs_add_recin");
			
			commonResponse=doctorService.getDoctorDashboardData(userData);
			
		}catch(Exception e) {
			e.printStackTrace() ;
			commonResponse = new CommonResponse() ;
			commonResponse. setResponseCode (1) ;
			commonResponse.setResponseMessage("Exception occured in DashBoard Controller"); 
			commonResponse. setResponseStatus ("Failure") ;
		}
		return commonResponse;
    }
 
 @GetMapping("/testing")
 public String test() {
	String b=	System.getenv("RDS_USER");
	String c=	"Testing the App";
		return b+"    "+c;
	

 }

 @PostMapping("/RSgetDoctorWithoutImage")
	public CommonResponse getDoctorWithOutImage(@RequestBody JsonNode reqObj) {
		CommonResponse commonResponse=null;
		try {
			
			
			JsonNode userData = reqObj.get("GetDoctorOperation").get("rs_add_recin");
			
			commonResponse=doctorService.getDoctorWithOutImage(userData);
			
		}catch(Exception e) {
			e.printStackTrace() ;
			commonResponse = new CommonResponse() ;
			commonResponse. setResponseCode (1) ;
			commonResponse.setResponseMessage("Exception occured in Controller"); 
			commonResponse. setResponseStatus ("Failure") ;
		}
		return commonResponse;
    }
 
 @CrossOrigin(origins = "*")
 @PostMapping(value="/Upload",consumes = "multipart/form-data")
 public CommonResponse  TestinguploadFileToS3(@RequestPart("imageFile") MultipartFile data) throws
	  IOException{  
	CommonResponse commonResponse = null;
     try {

     commonResponse  = doctorService.addImage(data) ;
 }catch (Exception e) {
 e.printStackTrace() ;
 commonResponse = new CommonResponse() ;
 commonResponse. setResponseCode (1) ;
 commonResponse.setResponseMessage("Exception occured in Controller"); 
 commonResponse. setResponseStatus ("Failure") ;
  }
	return commonResponse;

	  
 }
}