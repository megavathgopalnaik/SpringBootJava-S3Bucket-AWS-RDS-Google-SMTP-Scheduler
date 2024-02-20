package rosahealthcarebackend.service;

import com.fasterxml.jackson.databind.JsonNode;

import rosahealthcarebackend.common.CommonResponse;

public interface BookingService {

	CommonResponse addBooking(JsonNode userData);

	CommonResponse getBooking(JsonNode userData);

	CommonResponse mailSender();

	CommonResponse updateBooking(JsonNode userData);


	CommonResponse bookingStatusUpdate(JsonNode userData);

	

	CommonResponse visitedStatusUpdate();

}
