package rosahealthcarebackend.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;

import jakarta.transaction.Transactional;
import rosahealthcarebackend.common.CommonResponse;
import rosahealthcarebackend.entity.BookingAppointment;
import rosahealthcarebackend.entity.Doctor;
import rosahealthcarebackend.entity.PatientEntity;
import rosahealthcarebackend.entity.Role;
import rosahealthcarebackend.entity.UserD0;
import rosahealthcarebackend.repository.BookingRepository;
import rosahealthcarebackend.repository.DoctorRepository;
import rosahealthcarebackend.repository.PatientRepository;
import rosahealthcarebackend.repository.RoleRepository;
import rosahealthcarebackend.repository.UserRepository;

@Service
@Transactional
public class PatientServiceImpl  implements PatientService{
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private DoctorRepository doctorRepository;

	@Autowired
	private BookingRepository bookingRepository;
	
	@Override
	public CommonResponse addPatient(JsonNode userData) {
		// TODO Auto-generated method stub
		CommonResponse response = new CommonResponse();
		try {
			Optional<UserD0>user=userRepository.findByEmail(userData.get("rs_user_email").asText());
			if(user.isPresent()) {
				
				response.setResponseCode(0);
				response.setResponseMessage("Email alraedy present");
				response.setResponseStatus("Failed");
			}else {
				  UserD0 newUser = new UserD0();
			        newUser.setEmail(userData.get("rs_user_email").asText());
			        newUser.setFirst_name(userData.get("rs_user_first_name").asText());
			        newUser.setLast_name(userData.get("rs_user_last_name").asText());
			        newUser.setPassword(encryptThisString(userData.get("rs_user_password").asText())); 
			        newUser.setPhone_no(userData.get("rs_user_mobile").asText());
			        newUser.setStatus(userData.get("rs_user_status").asText());
			        newUser.setCreated_user_id(userData.get("rs_created_user_id").asText());
			        
			        PatientEntity p=new PatientEntity();
			        p.setUser(newUser);
			        p.setAddress(userData.get("rs_user_address").asText());
			        p.setAge(userData.get("rs_user_age").asInt());
			        p.setBirth_date(userData.get("rs_user_birth_date").asText());
			        p.setBlood_group(userData.get("rs_user_blood_group").asText());
			        p.setBlood_presure(userData.get("rs_user_blood_presure").asInt());
			        p.setGender(userData.get("rs_user_gender").asText());
			        p.setInjury(userData.get("rs_user_injury").asText());
			        p.setMarital_status(userData.get("rs_user_marital_status").asText());
			        p.setSugger(userData.get("rs_user_sugger").asInt());
			        Optional<Doctor> doc=doctorRepository.findById(userData.get("rs_doctor_id").asInt());
				    p.setDoctor(doc.get());
			        Optional<Role> role= roleRepository.findByName("Patient");
				    newUser.setRole(role.get());
			
					 PatientEntity pat= patientRepository.save(p);
					 if(pat==null) {

						   response.setResponseCode(1);
							response.setResponseMessage("Exception Occured While Saving.");
							response.setResponseStatus("Failure");
							return response;
					   }else {
						   response.setResponseData(String.valueOf(pat.getId()));
						   response.setResponseCode(1);
							response.setResponseMessage("Patient Added Successfully");
							response.setResponseStatus("Success");

					 
					 
					 } 
			        
			}
		}catch(Exception e){
			e.printStackTrace ();
			response. setResponseCode (1) ;
			response. setResponseMessage ("Exception Occured while patient Creation.");
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
	public CommonResponse getPatient(JsonNode userData) {

		CommonResponse response=new CommonResponse();
		try {
		   Optional<UserD0> user = userRepository.findById(userData.get("rs_user_id").asInt());

		    if (user.isPresent()) {
		        UserD0 u = user.get();
		        
		        // Assuming Role is a property in UserD0 entity
		        Role role = u.getRole();


		        if (role.getId() == 2) {
		        	Optional<Doctor>d=doctorRepository.findByUserId(userData.get("rs_user_id").asInt());
		            List<PatientEntity> patient = patientRepository.findByDoctorId(d.get().getId());

		            if (patient.isEmpty()) {
		                response.setResponseCode(1);
		                response.setResponseMessage("Patients not found");
		                response.setResponseStatus("Success");
		            } else {
		                response.setResponseCode(0);
		                response.setResponseData(patient);
		                response.setResponseMessage("Patients Data Fetched");
		                response.setResponseStatus("Success");
		            }
		        } else if (user.get().getRole().getId() == 1) {
		            List<PatientEntity> patientAll = patientRepository.findAllOrderByIdDesc();

		            if (patientAll.isEmpty()) {
		                response.setResponseCode(1);
		                response.setResponseMessage("Patients not found");
		                response.setResponseStatus("Success");
		            } else {
		                response.setResponseCode(0);
		                response.setResponseData(patientAll);
		                response.setResponseMessage("Patients Data Fetched");
		                response.setResponseStatus("Success");
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
	@Override
	public CommonResponse updatePatient(JsonNode userData) {
		// TODO Auto-generated method stub
		CommonResponse response=new CommonResponse();
		
		try {
			
			
			if(!userData.get("rs_patient_id").asText().isEmpty()) {
				
				 UserD0 user=new UserD0();
				 Optional<PatientEntity> patientExisting=patientRepository.findById(userData.get("rs_patient_id").asInt());
				 PatientEntity p=new PatientEntity();
				if(patientExisting.isPresent()) {
					
					if(patientExisting.isPresent()) {
						//if(patientExisting.get().getUser().getEmail().matches(userData.get("rs_user_email").asText())) { 
							 if(userData.get("rs_user_email").asText()!=null&& !userData.get("rs_user_email").asText().isEmpty()) {
								  Optional<UserD0>useremail=userRepository.findByEmail(userData.get("rs_user_email").asText());
								 if(useremail.isPresent()&&patientExisting.get().getUser().getId()!=useremail.get().getId()) {
							 response.setResponseCode(0);
							 response.setResponseMessage("Email Already Present");
							 response.setResponseStatus("Success");
							 return response;
								 
							 
						}else {
							p=patientExisting.get();
							 user=p.getUser();
							 
							 if(userData.get("rs_user_email").asText()!=null&& !userData.get("rs_user_email").asText().isEmpty()) {
								 user.setEmail(userData.get("rs_user_email").asText());
								 }
								 if(userData.get("rs_user_first_name").asText()!=null&& !userData.get("rs_user_first_name").asText().isEmpty()) {
								  user.setFirst_name(userData.get("rs_user_first_name").asText());
								  }
								 if(userData.get("rs_user_last_name").asText()!=null&& !userData.get("rs_user_last_name").asText().isEmpty()) {
						          user.setLast_name(userData.get("rs_user_last_name").asText()); 	}
								 
								 if(userData.get("rs_user_mobile").asText()!=null&& !userData.get("rs_user_mobile").asText().isEmpty()) {
						          user.setPhone_no(userData.get("rs_user_mobile").asText());}
								 if(userData.get("rs_user_password").asText()!=null&& !userData.get("rs_user_password").asText().isEmpty()) {
									 user.setPassword(encryptThisString(userData.get("rs_user_password").asText()));
									 }
								 
								 if(userData.get("rs_user_status").asText()!=null&& !userData.get("rs_user_status").asText().isEmpty()) {
						          user.setStatus(userData.get("rs_user_status").asText());
						          }
								 p.setUser(user);
								 if(userData.get("rs_user_address").asText()!=null&& !userData.get("rs_user_address").asText().isEmpty()) {
								 p.setAddress(userData.get("rs_user_address").asText());
								 }
								 if(userData.get("rs_user_age").asText()!=null&& !userData.get("rs_user_age").asText().isEmpty()) {
								 p.setAge(userData.get("rs_user_age").asInt());
								 }
								 if(userData.get("rs_user_birth_date").asText()!=null&& !userData.get("rs_user_birth_date").asText().isEmpty()) {
								 p.setBirth_date(userData.get("rs_user_birth_date").asText());
								 }
								 if(userData.get("rs_user_blood_group").asText()!=null&& !userData.get("rs_user_blood_group").asText().isEmpty()) {
								 p.setBlood_group(userData.get("rs_user_blood_group").asText());
								 }
								 if(userData.get("rs_user_blood_presure").asText()!=null&& !userData.get("rs_user_blood_presure").asText().isEmpty()) {
								 p.setBlood_presure(userData.get("rs_user_blood_presure").asInt());
								 }
								 if(userData.get("rs_user_gender").asText()!=null&& !userData.get("rs_user_gender").asText().isEmpty()) {
								 p.setGender(userData.get("rs_user_gender").asText());
								 }
								 if(userData.get("rs_user_injury").asText()!=null&& !userData.get("rs_user_injury").asText().isEmpty()) {
								 p.setInjury(userData.get("rs_user_injury").asText());
								 }
								 if(userData.get("rs_user_marital_status").asText()!=null&& !userData.get("rs_user_marital_status").asText().isEmpty()) {
								 p.setMarital_status(userData.get("rs_user_marital_status").asText());
								 }
								 if(userData.get("rs_user_sugger").asText()!=null&& !userData.get("rs_user_sugger").asText().isEmpty()) {
								 p.setSugger(userData.get("rs_user_sugger").asInt());
								 }
								 
								 
								 if(userData.get("rs_doctor_id").asText()!=null&& !userData.get("rs_doctor_id").asText().isEmpty()) {
								 Optional<Doctor> doct= doctorRepository.findById(userData.get("rs_doctor_id").asInt());
								p.setDoctor(doct.get());
								 }
						 }
						 
						p .setUser(user);
						PatientEntity D= patientRepository.save(p);
						 
						 response.setResponseCode(0);
						 response.setResponseData(D);
						 response.setResponseMessage("Updated Successfully");
						 response.setResponseStatus("Success");
						 return response;
						}
						
				}else {
					 response.setResponseCode(0);
					 response.setResponseMessage("No Patient found");
					 response.setResponseStatus("Success");
					 return response;
				}
			}

			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			response.setResponseCode(1);
			response.setResponseMessage("Exception Occured while Updating Patient");
			response.setResponseStatus("Failure");
		}
		return response;
	}

	@Override
	public CommonResponse getPatientById(JsonNode userData) {
		CommonResponse response= new CommonResponse();
		try {
			Optional<PatientEntity> patient=patientRepository.findById(userData.get("rs_user_id").asInt());
			if(patient.isPresent()) {
				
				response.setResponseCode(0);
				response.setResponseData(patient);
				response.setResponseMessage("Patient Data Fetched Successfully");
				response.setResponseStatus("Success");
			}else {
				response.setResponseCode(1);
				response.setResponseMessage("Patient Data Not Found");
				response.setResponseStatus("Success");
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			response.setResponseCode(1);
			response.setResponseMessage("Exception Occured while Fetching Patient");
			response.setResponseStatus("Failure");
		}
		return response;
	}

	@Override
	public CommonResponse patientDashBoard(JsonNode userData) {
		// TODO Auto-generated method stub
		CommonResponse response= new CommonResponse();
		try {
			Optional<PatientEntity> patient=patientRepository.findByUserId(userData.get("rs_user_id").asInt());
			Integer id=patient.get().getId();
			List<BookingAppointment> appointments=bookingRepository.findByPatientId(id);
		 	 int maxAppointmentsToShow = Math.min(6, appointments.size());
		     List<BookingAppointment> recentSixAppointments = appointments.subList(0, maxAppointmentsToShow);
		      

			Integer appointmentCount=  appointments.size();
			Map<String, Object> res = new HashMap<>();
			   res.put("AppointmentCount", appointmentCount);
			   res.put("Appointments",recentSixAppointments );
			   res.put("BloodGroup", patient.get().getBlood_group());
			   res.put("Sugger", patient.get().getSugger());
			   res.put("BloodPressure", patient.get().getBlood_presure());
			   
			   
			   response.setResponseCode(0);
			   response.setResponseData(res);
			   response.setResponseMessage("Patient DashBoard Fetched");
			   response.setResponseStatus("Success");
		} catch (Exception e) {
			response.setResponseCode(1);
			response.setResponseMessage("Exception Occured while Getting Patient DashBoard");
			response.setResponseStatus("Failure");
		}
		
		return response;
	}

}
