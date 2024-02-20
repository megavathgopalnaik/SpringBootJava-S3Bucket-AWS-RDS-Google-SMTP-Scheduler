package rosahealthcarebackend.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.databind.JsonNode;

import jakarta.transaction.Transactional;
import rosahealthcarebackend.common.CommonResponse;
import rosahealthcarebackend.entity.Admin;
import rosahealthcarebackend.entity.BookingAppointment;
import rosahealthcarebackend.entity.Doctor;
import rosahealthcarebackend.entity.PatientEntity;
import rosahealthcarebackend.entity.Role;
import rosahealthcarebackend.entity.UserD0;
import rosahealthcarebackend.repository.AdminRepository;
import rosahealthcarebackend.repository.BookingRepository;
import rosahealthcarebackend.repository.DoctorRepository;
import rosahealthcarebackend.repository.PatientRepository;
import rosahealthcarebackend.repository.RoleRepository;
import rosahealthcarebackend.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService{
    @Autowired
    private AdminRepository adminRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private DoctorRepository doctorRepository;
	@Autowired
	private BookingRepository bookingRepository;
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	@Override
	public CommonResponse authenticateUser(String email, String password) {
		// TODO Auto-generated method stub
		CommonResponse response= new CommonResponse();
		response.setResponseCode(1);
		response.setResponseMessage("Incorrect Username or Password");
		response.setResponseStatus("Failure");
		try {
			List<Object[]> opUser=userRepository.authenticate(email.toLowerCase(), encryptThisString(password));
			
			 Map<String, String> userData=new HashMap<>();
			 if(!CollectionUtils.isEmpty(opUser)) {
				 for(Object[] rserDO :opUser) {
					 if(null !=rserDO) {
					if(rserDO[8].toString().equalsIgnoreCase("Active")){
						  Integer roleId = (Integer) rserDO[5];
						  Integer user_id=(Integer) rserDO[0];
						  if(roleId==1) {
							  Optional<Admin> admin=adminRepository.findByUserId(user_id);
							  String address=admin.get().getAddress();
							  String gender=admin.get().getGender();
							  String dob=admin.get().getBirthDate();
							 userData.put("id", rserDO[0].toString());
							 userData.put("first_name", rserDO[1].toString());
							 userData.put("last_name", rserDO[2].toString().toLowerCase());
							 userData.put("email", rserDO[3].toString());
							 userData.put("mobile_no", rserDO[4].toString());
							 userData.put("role_id", rserDO[5].toString());
							 userData.put("role_name",rserDO[6].toString());
							 userData.put("Address",address);
							 userData.put("Gender", gender);
							 userData.put("DateOfBirth",dob);
							 
						  }
						  if(roleId==2) {
							  Optional<Doctor> doctor=doctorRepository.findByUserId(user_id);
							  String address=doctor.get().getAddress();
							  String gender=doctor.get().getGender();
							  String dob=doctor.get().getBirth_date();
								 userData.put("id", rserDO[0].toString());
								 userData.put("first_name", rserDO[1].toString());
								 userData.put("last_name", rserDO[2].toString().toLowerCase());
								 userData.put("email", rserDO[3].toString());
								 userData.put("mobile_no", rserDO[4].toString());
								 userData.put("role_id", rserDO[5].toString());
								 userData.put("role_name",rserDO[6].toString());
								 userData.put("Address",address);
								 userData.put("Gender", gender);
								 userData.put("DateOfBirth",dob);
						  }
						  if(roleId==3) {
							  Optional<PatientEntity> patient=patientRepository.findByUserId(user_id);
							  String address=patient.get().getAddress();
							  String gender=patient.get().getGender();
							  String dob=patient.get().getBirth_date();
							  
								 userData.put("id", rserDO[0].toString());
								 userData.put("first_name", rserDO[1].toString());
								 userData.put("last_name", rserDO[2].toString().toLowerCase());
								 userData.put("email", rserDO[3].toString());
								 userData.put("mobile_no", rserDO[4].toString());
								 userData.put("role_id", rserDO[5].toString());
								 userData.put("role_name",rserDO[6].toString());
								 userData.put("Address",address);
								 userData.put("Gender", gender);
								 userData.put("DateOfBirth",dob);
						  }
							 
							 
							 response.setResponseData(userData);
							 response.setResponseCode(0);
							 response.setResponseMessage("Authentication Successfull");
							 response.setResponseStatus("Sucess");
						 }else {
							 response.setResponseCode(1);
							 response.setResponseMessage("User is Deactive");
							 response.setResponseStatus("Failure");
						 }
					 }
				 }
				 
			 }
			
		}
		catch(Exception e){
			e.printStackTrace();
			response.setResponseCode(1);
			 response.setResponseMessage("Exception Occured While User Authentication");
			 response.setResponseStatus("Failure");
		}
		return response;
	}

	public static String encryptThisString(String input) {
		//
		try {
			
			// getInstance () method is called with algorithm SHA-1
			  MessageDigest md = MessageDigest. getInstance ("SHA-1");
			
			// digest () method is called
			// to calculate message digest of the input string
			// returned as array of byte
			byte[] messageDigest=md.digest(input.getBytes());
		
			// Convert byte array into signum representation
			BigInteger no = new BigInteger (1, messageDigest);
			
			// Convert message digest into hex value
			String hashtext=no.toString(16);
			
			// Add preceding Os to make it 32 bit
			while (hashtext. length () < 32) {
			hashtext = "0" + hashtext;
			}
			// return the HashText
			return hashtext;
		
			// For specifying wrong message digest algorithms
		}catch (NoSuchAlgorithmException e) {
			throw new RuntimeException (e);
		}
		
		

}

	@Override
	public CommonResponse addUser(JsonNode userData) {
		// TODO Auto-generated method stub
		CommonResponse response = new CommonResponse();
		try {
	
			Optional<UserD0> userexist = userRepository.findByEmail(userData.get("rs_user_email_id").asText().toLowerCase ());
			
			UserD0 userDO = new UserD0() ;
			userDO.setId(0) ;
			if (userexist.isPresent ()) {
			UserD0 userDOl = userexist.get();
			if (((String) userDOl.getStatus()).equals ("Active")) {
			response. setResponseCode (1) ;
			response. setResponseMessage ("Email Id Already Exist.");
			response. setResponseStatus ("Failure");
			
			return response;
			} else {
			userDO= userDOl;
			}
			}
			userDO.setFirst_name(userData.get("rs_first_name").asText());
			userDO.setPhone_no(userData.get ("rs_user_mob_no").asText ());
			userDO.setEmail(userData.get("rs_user_email_id").asText().toLowerCase());
			userDO.setPassword(encryptThisString(userData.get("rs_user_password").asText())); 
		    userDO.setStatus(userData.get("rs_user_status").asText());
		    userDO.setLast_name(userData.get("rs_last_name").asText());
		    userDO.setCreated_user_id(userData.get("rs_created_user_id").asText());
			Optional<Role> role = roleRepository.findByName(userData.get("rs_user_role").asText());
			
			     userDO.setRole(role.get());
					UserD0 User = userRepository.save(userDO) ;
					if (User == null) {
					response.setResponseCode (1) ;
					response.setResponseMessage ("Exception occured while saving.");
					response.setResponseStatus ("Failure") ;
					} else {
			
					try {
					//	userRepository.save(act);
					
					} catch (Exception e) {
					e.printStackTrace () ;
					}
					response.setResponseData (String.valueOf(User.getId())) ;
					response.setResponseCode(0);
					response.setResponseMessage("User Created Successfully.");
					response.setResponseStatus ("Success");
					}
					}catch (Exception e) {
					e.printStackTrace ();
					response. setResponseCode (1) ;
					response. setResponseMessage ("Exception Occured while User Creation.");
					response.setResponseStatus("Failure");
					}
		return response;
	}

	@Override
	public CommonResponse getUser(JsonNode userData) {

		CommonResponse response=new CommonResponse();
		try {
			 
			List<UserD0> user=userRepository.findAll();
			if(user.isEmpty()) {
				response.setResponseCode(1);
				response.setResponseMessage("Users  not found");
				response.setResponseStatus("Success");
				return response;

				
			}else {
				response.setResponseCode(0);
				response.setResponseData(user);
				response.setResponseMessage("Users Data Fetched");
				response.setResponseStatus("Success");
				return response;
							}
			
			
		}catch(Exception e) {
		e.printStackTrace();
		response.setResponseCode(1);
		response.setResponseMessage("Exception Occured while Fetching Users");
		response.setResponseStatus("Failure");
		}
		return response;
	
	}

	@Override
	public CommonResponse getDashboardData() {
		  CommonResponse response=new CommonResponse();
   	   try {
   		   
   		 Integer activeDoctorCount = userRepository.getActiveDoctorCount();
   	    Integer activePatientCount = userRepository.getActivePatientCount();
        Integer appointmentCount= bookingRepository.getAppointment();
   	   // Integer allaappointmentCount=bookingRepository.getAppointmentCount();
  	 List<BookingAppointment> recentAppointments = bookingRepository.getRecentAppointments();
  	 int maxAppointmentsToShow = Math.min(6, recentAppointments.size());
     List<BookingAppointment> recentSixAppointments = recentAppointments.subList(0, maxAppointmentsToShow);
     
     List<Object[]> bookingsByWeekday = bookingRepository.countBookingsByWeekday();
     
     Integer totalbill=bookingRepository.SumOfBill();
     
     Map<String, Long> bookingsByWeekdaysMap = new LinkedHashMap<>();
     List<String> allWeekdays = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday","Sunday");

  for (String weekday : allWeekdays) {
      bookingsByWeekdaysMap.put(weekday, 0L);
  }

     for (Object[] result : bookingsByWeekday) {
         String weekday = (String) result[0];
  	   Long count = (Long) result[1];
  	   
       bookingsByWeekdaysMap.put(weekday, count);
      
     }
   	    Map<String, Object> res = new HashMap<>();
   	    res.put("activeDoctorCount", activeDoctorCount);
   	    res.put("activePatientCount", activePatientCount);
        res.put("next6monthsAppointments", appointmentCount);
        res.put("recentAppointments", recentSixAppointments);
        res.put("bookingsByWeekdays", bookingsByWeekdaysMap);
        res.put("bill", totalbill);
   	    response.setResponseData(res);
   	    response.setResponseCode(0);
   	    response.setResponseMessage("Dashboard Data Fetched Successfully");
   	    response.setResponseStatus("Success");
   	   }catch (Exception e) {
   		 e.printStackTrace();
   	    response.setResponseCode(1);
   	    response.setResponseMessage("Exception Occurred While Fetching Dashboard Data");
   	    response.setResponseStatus("Failure");
   	   }
		return response;
	} 
   	   
   		  
	@Override
	public CommonResponse updatePassword(JsonNode userData) {
		// TODO Auto-generated method stub
		CommonResponse response=new CommonResponse();
		try {
			UserD0 user=new UserD0();
		Optional<UserD0>userExists=userRepository.findById(userData.get("rs_user_id").asInt());
		if(userData.get("rs_user_pswd").asText().isBlank()) {
			response.setResponseCode(1);
			response.setResponseMessage("Password Is Blank");
			response.setResponseStatus("Failure");
			return response;
			
		}
		if(userExists.isPresent()) {
			user=userExists.get();
			user.setPassword(encryptThisString(userData.get("rs_user_pswd").asText()));
			userRepository.save(user);
			 response.setResponseCode(0);
			 response.setResponseMessage("Password Is Updated");
				response.setResponseStatus("Success");
				return response;
			
		}else {
			response.setResponseCode(1);
			response.setResponseMessage("No User Found");
			response.setResponseStatus("Failure");
			return response;
			
		}

	}	catch(Exception e) {
		e.printStackTrace();
		response.setResponseCode(1);
		response.setResponseMessage("Exception Occured while Updating Password");
		response.setResponseStatus("Failure");
	}
		return response;
	}

	@Override
	public CommonResponse updateUser(JsonNode userData) {
		// TODO Auto-generated method stub
		CommonResponse response=new CommonResponse();
		try {
		UserD0 user=new UserD0();
		Optional<UserD0>userExists=userRepository.findById(userData.get("rs_user_id").asInt());
		if(userExists.isEmpty()) {
			response.setResponseCode(1);
			response.setResponseMessage("No User Found");
			response.setResponseStatus("Failure");
			return response;
		}
		
		if(userExists.isPresent()) {
			user=userExists.get();
			user.setFirst_name(userData.get("rs_user_first_name").asText());
			user.setLast_name(userData.get("rs_user_last_name").asText());
			userRepository.save(user);
		}
		Optional<Doctor> doctorExisting=doctorRepository.findByUserId(userData.get("rs_user_id").asInt());
		     Doctor doctor=new Doctor();
				
		     if(doctorExisting.isPresent()) {
				doctor=doctorExisting.get();
					doctor.setFirst_name(userData.get("rs_user_first_name").asText());
					doctor.setLast_name(userData.get("rs_user_last_name").asText());
					doctor.setAddress(userData.get("rs_user_address").asText());
					doctorRepository.save(doctor);
					}
		     Optional<PatientEntity> patientExisting=patientRepository.findByUserId(userData.get("rs_user_id").asInt());
		     PatientEntity patient=new PatientEntity();
		     if(patientExisting.isPresent()) {
					patient=patientExisting.get();
					
					patient.setAddress(userData.get("rs_user_address").asText());
						patientRepository.save(patient);
						}
		     
		     Optional<Admin> adminExisting=adminRepository.findByUserId(userData.get("rs_user_id").asInt());
		     Admin admin=new Admin();
		     if(adminExisting.isPresent()) {
					admin=adminExisting.get();
						admin.setFirstName(userData.get("rs_user_first_name").asText());
						admin.setLastName(userData.get("rs_user_last_name").asText());
						admin.setAddress(userData.get("rs_user_address").asText());
						adminRepository.save(admin);
						
						}
		              response.setResponseCode(0);
		              response.setResponseMessage("User Updated Successfully");
		              response.setResponseStatus("Success");
		
		}catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode(1);
			response.setResponseMessage("Exception Occured while Updating User");
			response.setResponseStatus("Failure");
			
		}
		return response;
	}

	@Override
	public CommonResponse getUserById(JsonNode userData) {
   CommonResponse response=new CommonResponse();
		try {
	        Optional<UserD0> user = userRepository.findById(userData.get("rs_user_id").asInt());
	        
	        if (user.isEmpty()) {
	            response.setResponseCode(1);
	            response.setResponseMessage("No User Found");
	            response.setResponseStatus("Failure");
	        } else {
	            if (user.get().getRole().getId() == 2) {
	                Optional<Doctor> doctor = doctorRepository.findByUserId(userData.get("rs_user_id").asInt());
	                
	                if (doctor.isPresent()) {
	                  
	                    String firstname=doctor.get().getFirst_name();
	                    String lastname=doctor.get().getLast_name();
	                    String status=doctor.get().getAvailable_status();
	                    String address=doctor.get().getAddress();
	                    String AvailableStartTime=doctor.get().getAvailable_start_time();
	                    String AvailableEndTime=doctor.get().getAvailable_end_time();
	                    HashMap<String, Object>li=new HashMap<>();
	            		li.put("firstName",firstname);
	            		li.put("lastName", lastname);
	            		li.put("Status",status);
	            		li.put("addres",address);
	            		li.put("AvailableStartTime", AvailableStartTime);
	            		li.put("AvailableEndTime", AvailableEndTime);
	                    response.setResponseCode(0);
	                    response.setResponseData(li);
	                    response.setResponseMessage("User Fetched Successfully");
	                    response.setResponseStatus("Success");
	                } else {
	                    response.setResponseCode(1);
	                    response.setResponseMessage("No Doctor Found");
	                    response.setResponseStatus("Failure");
	                }
	            } else if (user.get().getRole().getId() == 1) {
	                Optional<Admin> admin = adminRepository.findByUserId(userData.get("rs_user_id").asInt());
	                
	                if (admin.isPresent()) {
	                   
	                   String firstname=admin.get().getFirstName();
	                    String lastname=admin.get().getLastName();
	                    String status=admin.get().getAvailable_status();
	                    String address=admin.get().getAddress();
	                    String AvailableStartTime=admin.get().getAvailableStartTime();
	                    String AvailableEndTime=admin.get().getAvailableEndTime();
	                    HashMap<String, Object>li=new HashMap<>();
	            		li.put("firstName",firstname);
	            		li.put("lastName", lastname);
	            		li.put("Status",status);
	            		li.put("addres",address);
	            		li.put("AvailableStartTime", AvailableStartTime);
	            		li.put("AvailableEndTime", AvailableEndTime);
	            		
	                    response.setResponseCode(0); 
	                    response.setResponseData(li);
	                    response.setResponseMessage("User Fetched Successfully");
	                    response.setResponseStatus("Success");
	                } else {
	                    response.setResponseCode(1);
	                    response.setResponseMessage("No User Found");
	                  response.setResponseStatus("Failure");
	                }
	            }else if(user.get().getRole().getId() == 3) {
	            	Optional<PatientEntity>patient=patientRepository.findByUserId(userData.get("rs_user_id").asInt());
	            	if(patient.isPresent()) {
	            		String firstname=user.get().getFirst_name();
	            		String lastname=user.get().getLast_name();
	            		String status=user.get().getStatus();
	            		String address=patient.get().getAddress();
	            		HashMap<String, Object>li=new HashMap<>();
	            		li.put("firstName",firstname);
	            		li.put("lastName", lastname);
	            		li.put("Status",status);
	            		li.put("addres",address);
	            		 
	                    response.setResponseCode(0); 
	                    response.setResponseData(li);
	                    response.setResponseMessage("User Fetched Successfully");
	                    response.setResponseStatus("Success");
	            	}else {
	            		 response.setResponseCode(1);
		                    response.setResponseMessage("No User Found");
		                  response.setResponseStatus("Failure");
	            	}
	            	}
	            }
    }catch(Exception e) {
	e.printStackTrace();
	response.setResponseCode(1);
	response.setResponseMessage("Exception Occured while Fetching Users");
	response.setResponseStatus("Failure");
	}
	return response;
}

	@Override
	public CommonResponse updateTime(JsonNode userData) {
		CommonResponse response= new CommonResponse();
		try {
			
			 Optional<UserD0> user = userRepository.findById(userData.get("rs_user_id").asInt());
		        
		        if (user.isEmpty()) {
		            response.setResponseCode(1);
		            response.setResponseMessage("No User Found");
		            response.setResponseStatus("Failure");
		        } else {
		            if (user.get().getRole().getId() == 2) {
		                Optional<Doctor> doctor = doctorRepository.findByUserId(userData.get("rs_user_id").asInt());
		                
		                if (doctor.isPresent()) {
		                  
		                  Doctor d=new Doctor();
		                  d=doctor.get();
		                  d.setAvailable_start_time(userData.get("rs_user_available_start_time").asText());
		                  d.setAvailable_end_time(userData.get("rs_user_available_end_time").asText());
		                  d.setAvailable_status(userData.get("rs_user_available_status").asText());
		                  doctorRepository.save(d);
		                    response.setResponseCode(0);
		                    response.setResponseMessage("Doctor Timinings Updated Successfully");
		                    response.setResponseStatus("Success");
		                } else {
		                    response.setResponseCode(1);
		                    response.setResponseMessage("No Doctor Found");
		                    response.setResponseStatus("Failure");
		                }
		            } else if (user.get().getRole().getId() == 1) {
		                Optional<Admin> admin = adminRepository.findByUserId(userData.get("rs_user_id").asInt());
		                
		                if (admin.isPresent()) {
		                   
		                	Admin a= new Admin();
		                	a=admin.get();
		                	a.setAvailableStartTime(userData.get("rs_user_available_start_time").asText());
		                	a.setAvailableEndTime(userData.get("rs_user_available_end_time").asText());
		                	a.setAvailable_status(userData.get("rs_user_available_status").asText());
		                	adminRepository.save(a);
		                    response.setResponseCode(0); 
		                    response.setResponseMessage("User Timings Updated Successfully");
		                    response.setResponseStatus("Success");
		                } else {
		                    response.setResponseCode(1);
		                    response.setResponseMessage("No User Found");
		                  response.setResponseStatus("Failure");
		                }
		            }
		        }
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode(1);
			response.setResponseMessage("Exception Occured while Updating Timings");
			response.setResponseStatus("Failure");
		}
		return response;
	}
}
