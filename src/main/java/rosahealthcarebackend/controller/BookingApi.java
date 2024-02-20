package rosahealthcarebackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

import rosahealthcarebackend.common.CommonResponse;
import rosahealthcarebackend.service.BookingService;

@RestController
public class BookingApi {
	
	
	@Autowired
	private BookingService bookingService;
	

	@PostMapping("/RSBooking")
    public CommonResponse addBooking (@RequestBody JsonNode reqObj) {
   	CommonResponse commonResponse = null;
	try {

		JsonNode userData = reqObj.get("RSBOOKAPPADDOP").get("rs_ad_recin");
		if(!reqObj.get("RSBOOKAPPADDOP").get("rs_ad_recin").isEmpty()) {
	if( !reqObj.get("RSBOOKAPPADDOP").get("rs_ad_recin").get("rs_appointment_id").asText().isEmpty()) {
		
	commonResponse=bookingService.updateBooking(userData);
	}else {
		
		commonResponse = bookingService.addBooking(userData) ;
	
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
     

	   @PostMapping(value="/RSGetBooking", consumes = MediaType.APPLICATION_JSON_VALUE)
		public CommonResponse getBooking(@RequestBody JsonNode reqObj) {
			CommonResponse commonResponse=null;
			try {
				JsonNode userData = reqObj.get("GetBookingOperation").get("rs_add_recin");
				commonResponse=bookingService.getBooking(userData);
				
			}catch(Exception e) {
				e.printStackTrace() ;
				commonResponse = new CommonResponse() ;
				commonResponse. setResponseCode (1) ;
				commonResponse.setResponseMessage("Exception occured in Controller"); 
				commonResponse. setResponseStatus ("Failure") ;
			}
			return commonResponse;
	}
	   @PostMapping(value="/RSSendMail")
	   public CommonResponse mailSender(@RequestBody JsonNode reqObj) {
		   CommonResponse commonResponse=null;
		   try {
			   
			   commonResponse=bookingService.mailSender();
		   }catch (Exception e) {
				commonResponse = new CommonResponse() ;
				commonResponse. setResponseCode (1) ;
				commonResponse.setResponseMessage("Exception occured in Controller"); 
				commonResponse. setResponseStatus ("Failure") ;
		}
		   return commonResponse;
	   }
	   @PostMapping(value="/RSUpdateStatus")
	   public CommonResponse bookingStatusUpdate(@RequestBody JsonNode reqObj) {
		   CommonResponse commonResponse=null;
		   try {
			   JsonNode userData = reqObj.get("BookingStatusOperation").get("rs_add_recin");
			   commonResponse=bookingService.bookingStatusUpdate(userData);
		   }catch (Exception e) {
				commonResponse = new CommonResponse() ;
				commonResponse. setResponseCode (1) ;
				commonResponse.setResponseMessage("Exception occured in Controller"); 
				commonResponse. setResponseStatus ("Failure") ;
		}
		   return commonResponse;
	   }
	 
	 
	 
	   @PostMapping(value="/RSUpdateVisitedStatus")
	   public CommonResponse visitedStatusUpdate(@RequestBody JsonNode reqObj) {
		   CommonResponse commonResponse=null;
		   try {
			  // JsonNode userData = reqObj.get("BookingStatusOperation").get("rs_add_recin");
			   commonResponse=bookingService.visitedStatusUpdate();
		   }catch (Exception e) {
				commonResponse = new CommonResponse() ;
				commonResponse. setResponseCode (1) ;
				commonResponse.setResponseMessage("Exception occured in Controller"); 
				commonResponse. setResponseStatus ("Failure") ;
		}
		   return commonResponse;
}
}