 package rosahealthcarebackend.service;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;

import jakarta.transaction.Transactional;
import rosahealthcarebackend.appointmentenum.AppointmentStatus;
import rosahealthcarebackend.common.CommonResponse;
import rosahealthcarebackend.entity.BookingAppointment;
import rosahealthcarebackend.entity.Doctor;
import rosahealthcarebackend.entity.PatientEntity;
import rosahealthcarebackend.entity.Role;
import rosahealthcarebackend.entity.UserD0;
import rosahealthcarebackend.repository.BookingRepository;
import rosahealthcarebackend.repository.DoctorRepository;
import rosahealthcarebackend.repository.PatientRepository;
import rosahealthcarebackend.repository.UserRepository;

@Service
@Transactional
public class BookingServiceImpl implements BookingService{
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private BookingRepository bookingRepo;
	
	@Autowired
	private DoctorRepository doctorRepo;
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private DoctorRepository doctorRepository;
	
	@Override
	public CommonResponse addBooking(JsonNode userData) {
		// TODO Auto-generated method stub
		CommonResponse response= new CommonResponse();
		try {
			
		List<BookingAppointment> apoint=bookingRepo.findByEmailAndAppointmentDate(userData.get("rs_user_email").asText(), userData.get("rs_appointment_date").asText());
		if(!apoint.isEmpty()) {
			response.setResponseCode(0);
			response.setResponseMessage("Appointment Has Been Booked Already.");
			response.setResponseStatus("Success");
		}else { //Booking Repo Line 135
			List<BookingAppointment> apo=bookingRepo.findByDateTimeANDDoctorId(userData.get("rs_doctor_id").asInt(),
					userData.get("rs_appointment_time").asText(),userData.get("rs_appointment_date").asText());
		   if(!apo.isEmpty()) {
			   response.setResponseCode(0);
				response.setResponseMessage("Yor Selected Slot is Not Available");
				response.setResponseStatus("Success");
		   
		   }
		   else {
			BookingAppointment booking= new BookingAppointment();
			 booking.setAddress(userData.get("rs_user_address").asText());
			 booking.setAppointmentDate(userData.get("rs_appointment_date").asText());
			  booking.setAppointmentStatus(AppointmentStatus.Pending);
			   booking.setAppointmentTime(userData.get("rs_appointment_time").asText());
			   booking.setBirthDate(userData.get("rs_user_birth_date").asText());
			   booking.setEmail(userData.get("rs_user_email").asText());
			  
			   booking.setFirstName(userData.get("rs_user_first_name").asText());
			   booking.setGender(userData.get("rs_user_gender").asText());
			   booking.setLastName(userData.get("rs_user_last_name").asText());
			   booking.setInjury(userData.get("rs_user_injury").asText());
			   booking.setMobile(userData.get("rs_user_mobile").asText());
		
			   if(userData.get("rs_user_id").asText()!=null&& !userData.get("rs_user_id").asText().isEmpty()) {
					 Optional<PatientEntity> pat=patientRepository.findByUserId(userData.get("rs_user_id").asInt());
					    booking.setPatient(pat.get());
			   }
			    Optional<Doctor> doc=doctorRepo.findById(userData.get("rs_doctor_id").asInt());
			    booking.setDoctor(doc.get());
			    
			   // String i=sendMail(userData.get("rs_user_email").asText(), "RosaHealthCare Appointment", "Your Appointment has been confirmed");
		
			    	  
			    BookingAppointment b=  bookingRepo.save(booking);
			    response.setResponseData(b.getId());
				response.setResponseCode(0);
				response.setResponseMessage("Hi, your appointment has been confirmed");
				response.setResponseStatus("Success");
			   
			}
		
	
		}
			   
			
		}catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode(1);
			response.setResponseMessage("Exception Occured while Booking Appointment");
			response.setResponseStatus("Failure");
			
		}
		
		return response;
	}
	@SuppressWarnings("unused")
	@Override
	public CommonResponse getBooking(JsonNode userData) {

	
			CommonResponse response=new CommonResponse();
			try {
			   Optional<UserD0> user = userRepository.findById(userData.get("rs_user_id").asInt());

			    if (user.isPresent()) {
			        UserD0 u = user.get();
			        
			        // Assuming Role is a property in UserD0 entity
			        Role role = u.getRole();


			        if (role.getId() == 2) {
			        	Optional<Doctor>d=doctorRepository.findByUserId(userData.get("rs_user_id").asInt());
			            List<BookingAppointment> booking = bookingRepo.findByDoctorId(d.get().getId());

			            if (booking.isEmpty()) {
			                response.setResponseCode(1);
			                response.setResponseMessage("Booking not found");
			                response.setResponseStatus("Success");
			            } else {
			                response.setResponseCode(0);
			                response.setResponseData(booking);
			                response.setResponseMessage("Booking Data Fetched");
			                response.setResponseStatus("Success");
			            }
			        } else if (user.get().getRole().getId() == 1) {
			            List<BookingAppointment> bookingAll = bookingRepo.findAllOrderByIdDesc();

			            if (bookingAll.isEmpty()) {
			                response.setResponseCode(1);
			                response.setResponseMessage("Patients not found");
			                response.setResponseStatus("Success");
			            } else {
			                response.setResponseCode(0);
			                response.setResponseData(bookingAll);
			                response.setResponseMessage("Patients Data Fetched");
			                response.setResponseStatus("Success");
			            } 
			        }else if (role.getId() == 3) {
			                Optional<PatientEntity> patientOptional = patientRepository.findByUserId(userData.get("rs_user_id").asInt());
			                if (patientOptional.isPresent()) {
			                    List<BookingAppointment> patientBookings = bookingRepo.findByPatientId(patientOptional.get().getId());

			                    if (patientBookings.isEmpty()) {
			                        response.setResponseCode(1);
			                        response.setResponseMessage("Bookings not found");
			                        response.setResponseStatus("Success");
			                    } else {
			                        response.setResponseCode(0);
			                        response.setResponseData(patientBookings);
			                        response.setResponseMessage("Patient's Bookings Data Fetched");
			                        response.setResponseStatus("Success");
			                    }
			                } else {
			                    response.setResponseCode(1);
			                    response.setResponseMessage("Patient not found for the given user");
			                    response.setResponseStatus("Failure");
			                }
			        } else {
			            response.setResponseCode(1);
			            response.setResponseMessage("Invalid Role ID");
			            response.setResponseStatus("Failure");
			        }
			    } else {
			        response.setResponseCode(1);
			        response.setResponseMessage("User not found");
			        response.setResponseStatus("Failure");
			    }
			} catch (Exception e) {
			    e.printStackTrace();
			    response.setResponseCode(1);
			    response.setResponseMessage("Exception Occurred while Fetching Patients");
			    response.setResponseStatus("Failure");
			}

			return response;
		}
	@Scheduled(initialDelay=3000,fixedDelay = 60000)
	@Override
public CommonResponse mailSender() {
		
		CommonResponse response=new CommonResponse();
		
		try {
			System.out.print("running");
			List<BookingAppointment> booking=bookingRepo.finByAppointmentDate(); 
			System.out.println(booking);
			 for(BookingAppointment b:booking) {
				 
				 
				 System.out.println(b);
				 
	                	 SimpleMailMessage message=new SimpleMailMessage();
	                	 message.setFrom("gopalnayak422@gmail.com");
	                	 message.setTo(b.getEmail());
	            	     message.setText("Hi "+b.getFirstName()+" "+b.getLastName() +","+ "\n"
	            	     		+ "Your appointment has been confirmed.\n"
	            	    	        + "Booking Date: " + b.getAppointmentDate() + "\n"
	            	    	        + "Booking Time: " + b.getAppointmentTime());
	                	 message.setSubject("Rosa Healthcare Appointment Confirmation");
	                	 mailSender.send (message);
	                	 response.setResponseCode(1);
	            		 response.setResponseMessage("Mail  is Sent");
	            		 response.setResponseStatus("Success");
	            		 
	            		 System.out.println("End of method");
	                }	
			 
			
}catch (Exception e) {
	e.printStackTrace();
	response.setResponseCode(1);
	response.setResponseMessage("Exception Occured while Sending Mail");
	response.setResponseStatus("Failure");
}
		return response;
}

	@Override
	public CommonResponse updateBooking(JsonNode userData) {
		// TODO Auto-generated method stub
		CommonResponse response = new CommonResponse();
		try {
			Optional<BookingAppointment> appointmentExisting=bookingRepo.findById(userData.get("rs_appointment_id").asInt());
			BookingAppointment booking= new BookingAppointment();
			if(appointmentExisting.isEmpty()) {
				
				response.setResponseCode(1);
				response.setResponseMessage("Booking  not found");
				response.setResponseStatus("Success");
				return response;
				
				
			}else {
				
				booking=appointmentExisting.get();
				
				 if(userData.get("rs_user_address").asText()!=null&& !userData.get("rs_user_address").asText().isEmpty()) {
				 booking.setAddress(userData.get("rs_user_address").asText());
				 }
				 if(userData.get("rs_appointment_date").asText()!=null&& !userData.get("rs_appointment_date").asText().isEmpty()) {
				 booking.setAppointmentDate(userData.get("rs_appointment_date").asText());
				 }
				
				 if(userData.get("rs_appointment_time").asText()!=null&& !userData.get("rs_appointment_time").asText().isEmpty()) {
				   booking.setAppointmentTime(userData.get("rs_appointment_time").asText());
				 }
				 if(userData.get("rs_user_birth_date").asText()!=null&& !userData.get("rs_user_birth_date").asText().isEmpty()) {
				   booking.setBirthDate(userData.get("rs_user_birth_date").asText());
				 }
				 if(userData.get("rs_user_email").asText()!=null&& !userData.get("rs_user_email").asText().isEmpty()) {
				   booking.setEmail(userData.get("rs_user_email").asText());
				   }
				 if(userData.get("rs_user_first_name").asText()!=null&& !userData.get("rs_user_first_name").asText().isEmpty()) {
				   booking.setFirstName(userData.get("rs_user_first_name").asText());
				 }
				 if(userData.get("rs_user_gender").asText()!=null&& !userData.get("rs_user_gender").asText().isEmpty()) {
				   booking.setGender(userData.get("rs_user_gender").asText());
				 }
				 if(userData.get("rs_user_last_name").asText()!=null&& !userData.get("rs_user_last_name").asText().isEmpty()) {
				   booking.setLastName(userData.get("rs_user_last_name").asText());
				 }
				 if(userData.get("rs_user_injury").asText()!=null&& !userData.get("rs_user_injury").asText().isEmpty()) {
				   booking.setInjury(userData.get("rs_user_injury").asText());
				 }
				 if(userData.get("rs_user_mobile").asText()!=null&& !userData.get("rs_user_mobile").asText().isEmpty()) {
				   booking.setMobile(userData.get("rs_user_mobile").asText());
				 }
				 if(userData.get("rs_doctor_id").asText()!=null&& !userData.get("rs_doctor_id").asText().isEmpty()) {
				    Optional<Doctor> doc=doctorRepo.findById(userData.get("rs_doctor_id").asInt());
				    booking.setDoctor(doc.get());
				 }
				 if(userData.get("rs_user_id").asText()!=null&& !userData.get("rs_user_id").asText().isEmpty()) {
					 Optional<PatientEntity> pat=patientRepository.findByUserId(userData.get("rs_user_id").asInt());
					    booking.setPatient(pat.get());
					 }
              
                BookingAppointment b=  bookingRepo.save(booking);
			    response.setResponseData(b);
				response.setResponseCode(0);
				response.setResponseMessage("Hi, your appointment has been Updated");
				response.setResponseStatus("Success");
			   
			}
			
			
			    
		} catch (Exception e) {
	       
			e.printStackTrace();
			response.setResponseCode(1);
			response.setResponseMessage("Exception Occured Updating Appointment");
			response.setResponseStatus("Failure");
		}
		return response;
	}
	@Override
	public CommonResponse bookingStatusUpdate(JsonNode userData) {
		// TODO Auto-generated method stub
		CommonResponse response=new CommonResponse();
		try {
		Optional<BookingAppointment>appointment=bookingRepo.findById(userData.get("rs_booking_id").asInt());
		if(appointment.isPresent()) {
			
		BookingAppointment b= new BookingAppointment();
		b=appointment.get();
		b.setAppointmentStatus(AppointmentStatus.Visited);
		b.setBill(5);
		bookingRepo.save(b);
		
		response.setResponseCode(0);
		response.setResponseMessage("Status Has Been Updated ");
		response.setResponseStatus("Success");
		
		}else {
			response.setResponseCode(1);
			response.setResponseMessage("Booking not found ");
			response.setResponseStatus("Success");
		}
		}catch (Exception e) {

		       
			e.printStackTrace();
			response.setResponseCode(1);
			response.setResponseMessage("Exception Occured Updating Appointment Status");
			response.setResponseStatus("Failure");
		
		}
		return response;
	}
	@Scheduled(initialDelay=3000,fixedDelay = 90000)
	@Override
	public CommonResponse visitedStatusUpdate() {
		// TODO Auto-generated method stub
		CommonResponse response=new CommonResponse();
		try {
			
			List<BookingAppointment>booking=bookingRepo.findyByEndTime();
			booking.stream()
            .filter(book -> book.getAppointmentStatus() == AppointmentStatus.Pending)
            .forEach(b -> {
                b.setAppointmentStatus(AppointmentStatus.Notvisited);
                bookingRepo.save(b);
            });

			
				response.setResponseCode(0);
				response.setResponseMessage("Status marked has not Visited");
				response.setResponseStatus("Success");
			
		} catch (Exception e) {
			// TODO: handle exception
		      
					e.printStackTrace();
					response.setResponseCode(1);
					response.setResponseMessage("Exception Occured Updating Appointment Status");
					response.setResponseStatus("Failure");
				
		}
		return response;
	}
}