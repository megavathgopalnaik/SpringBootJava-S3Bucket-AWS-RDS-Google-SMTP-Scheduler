package rosahealthcarebackend.service;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.JsonNode;

import jakarta.transaction.Transactional;

import rosahealthcarebackend.common.CommonResponse;
import rosahealthcarebackend.entity.AwsCloudUtil;
import rosahealthcarebackend.entity.BookingAppointment;
import rosahealthcarebackend.entity.Department;
import rosahealthcarebackend.entity.Doctor;
import rosahealthcarebackend.entity.PatientEntity;
import rosahealthcarebackend.entity.Role;
import rosahealthcarebackend.entity.UserD0;
import rosahealthcarebackend.repository.BookingRepository;
import rosahealthcarebackend.repository.DepartmentRepository;
import rosahealthcarebackend.repository.DoctorRepository;
import rosahealthcarebackend.repository.PatientRepository;
import rosahealthcarebackend.repository.RoleRepository;
import rosahealthcarebackend.repository.UserRepository;
@Service
@Transactional
public class DoctorServiceImpl implements DoctorService {
	
	


	@Value("${aws.access.key}")
	private String AWS_ACCESS_KEY;

	@Value("${aws.secret.key}")
	private String AWS_SECRET_KEY;
	
	@Value("${aws.s3.bucket}")
	private String AWS_BUCKET;	

	@Autowired
	private DownloadImageFunction fun;
	
	@Autowired
	private DoctorRepository doctorRepository;
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private RoleRepository roleRepository;
	
	
	

	
	@Override
	public CommonResponse addDoctor(JsonNode userData) {
		
		
		CommonResponse response=new CommonResponse();
		
		try {
		
			Optional<UserD0>user=userRepository.findByEmail(userData.get("rs_doctor_email").asText());
			if(user.isPresent()) {
				
				response.setResponseCode(0);
				response.setResponseMessage("Email alraedy present");
				response.setResponseStatus("Failed");
			}else {
				  UserD0 newUser = new UserD0();
			        newUser.setEmail(userData.get("rs_doctor_email").asText());
			        newUser.setFirst_name(userData.get("rs_doctor_first_name").asText());
			        newUser.setLast_name(userData.get("rs_doctor_last_name").asText());
			        newUser.setPassword(encryptThisString(userData.get("rs_doctor_password").asText())); 
			        newUser.setPhone_no(userData.get("rs_doctor_mobile").asText());
			        newUser.setStatus(userData.get("rs_doctor_status").asText());
			       newUser.setCreated_user_id(userData.get("rs_created_user_id").asText());
				
			   	Doctor d=new Doctor();
				d.setAddress(userData.get("rs_doctor_address").asText());
				d.setAvailable_end_time(userData.get("rs_doctor_available_end_time").asText());
				d.setAvailable_start_time(userData.get("rs_doctor_available_start_time").asText());
				d.setAvailable_status(userData.get("rs_doctor_available_status").asText());
		     	d.setBirth_date(userData.get("rs_doctor_birth_date").asText());
				d.setDescription(userData.get("rs_doctor_profile_description").asText());
				d.setDesignation(userData.get("rs_doctor_designation").asText());
				d.setEducation(userData.get("rs_doctor_education").asText());
				d.setExp_years(userData.get("rs_doctor_exp_years").asInt());
				d.setFirst_name(userData.get("rs_doctor_first_name").asText());
				d.setGender(userData.get("rs_doctor_gender").asText());
				d.setImage(userData.get("rs_doctor_image").asText());
				d.setLast_name(userData.get("rs_doctor_last_name").asText());
			
				Optional<Department> depart= departmentRepository.findById(userData.get("rs_doctor_department").asInt());
				Optional<Role> role= roleRepository.findByName("Doctor");
			    newUser.setRole(role.get());
				
			     d.setDepartment(depart.get());

				d.setUser(newUser);
				Doctor cate=doctorRepository.save(d);
				  if(cate==null) 
				   {
					   response.setResponseCode(1);
						response.setResponseMessage("Exception Occured While Saving.");
						response.setResponseStatus("Failure");
						return response;
				   }else {
					   response.setResponseData(String.valueOf(cate.getId()));
					   response.setResponseCode(1);
						response.setResponseMessage("Doctor Added Successfully");
						response.setResponseStatus("Success");

				
			}
			
			}
			
		}
			catch(Exception e) {
				e.printStackTrace ();
				response. setResponseCode (1) ;
				response. setResponseMessage ("Exception Occured while User Creation.");
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
	public String uploadFileToS3(MultipartFile data) throws IOException {
		  
		try {
		AwsCloudUtil util= new AwsCloudUtil();
		System.out.println(AWS_ACCESS_KEY);
		
		util.uploadFileTos3(data.getOriginalFilename(), data.getBytes(),AWS_ACCESS_KEY,AWS_SECRET_KEY,AWS_BUCKET);
	  
		return String.format(" File is Uploaded ", data.getOriginalFilename());
		
		}catch(Exception e){
			e.printStackTrace ();
			
			
			
		}
		return "failed";
	}
	@Override
	public byte[] downloadS3File(String filename) throws IOException {
		
		

		AwsCloudUtil util=new AwsCloudUtil();

		return util.downloadFileFromS3(filename,AWS_ACCESS_KEY,AWS_SECRET_KEY,AWS_BUCKET).readAllBytes();
	
	
	}
	
	
	@Override
	public CommonResponse getDoctor(JsonNode userData) {
		// TODO Auto-generated method stub
		CommonResponse response =new CommonResponse();
		try {
			
			List<Doctor> doc= doctorRepository.findAllOderById();
			if(doc.isEmpty()) {
				
			response.setResponseCode(0);
			response.setResponseMessage("No doctors found.");
			response.setResponseStatus("Success");
			return response;
			
			
			}else {
				
				List<Doctor>doctor=new ArrayList<>();
				for(Doctor d:doc) {
					
					String i=fun.imageBase64(d.getImage());
					Doctor doct=new Doctor();
					doct.setAddress(d.getAddress());
					doct.setAvailable_end_time(d.getAvailable_end_time());
					doct.setAvailable_start_time(d.getAvailable_start_time());
					doct.setAvailable_status(d.getAvailable_status());
					doct.setBirth_date(d.getBirth_date());
					doct.setDepartment(d.getDepartment());
					doct.setDescription(d.getDescription());
					doct.setDesignation(d.getDesignation());
					doct.setEducation(d.getEducation());
					doct.setExp_years(d.getExp_years());
					doct.setFirst_name("Dr."+d.getFirst_name());
					doct.setGender(d.getGender());
					doct.setId(d.getId());

					doct.setImage("data:image/png;base64,"+i);

					doct.setLast_name(d.getLast_name());
					doct.setUser(d.getUser());
					doctor.add(doct);
					response.setResponseData(doctor);
					response.setResponseCode(0);
					response.setResponseMessage("Doctor Data Fetched Successfully.");
					response.setResponseStatus("Success");
				}
				
			} 
				
						
			
		}catch(Exception e) {
			e.printStackTrace();
			response.setResponseCode(1);
			response.setResponseMessage("Exception Occured while Fetching Doctor");
			response.setResponseStatus("Failure");
		}
		return response;
	}

   @Override
	public CommonResponse addImage(MultipartFile data) {
		CommonResponse response=new CommonResponse();
		try {  
			
			
			String I=uploadFileToS3(data);
		if("failed".equals(I)) {
			response.setResponseCode(1);
			response.setResponseMessage("Image Uploaded Failed");
			response.setResponseStatus("Success");
			return response;
			}
		else {
			
			response.setResponseCode(0);
			response.setResponseMessage("Image  Uploaded Successfully");
			response.setResponseStatus("Success");
		}
		}catch(Exception e) {
			e.printStackTrace();
			response.setResponseCode(1);
			response.setResponseMessage("Exception Occured While Adding image");
			response.setResponseStatus("Failure");
		}
		return response;
	}
@Override
public CommonResponse updateDoctor(JsonNode userData) {
	// TODO Auto-generated method stub
	CommonResponse response=new CommonResponse();
	try {
		
		
		if(!userData.get("rs_doctor_id").asText().isEmpty()) {
			
			 UserD0 user=new UserD0();
			 Optional<Doctor> DoctorExisting=doctorRepository.findById(userData.get("rs_doctor_id").asInt());
			 Doctor doctor=new Doctor();
			 if(DoctorExisting.isPresent()) {
				 
				    
				// if(DoctorExisting.get().getUser().getEmail().matches(userData.get("rs_user_email").asText())) { 
					 if(userData.get("rs_doctor_email").asText()!=null&& !userData.get("rs_doctor_email").asText().isEmpty()) {
						  Optional<UserD0>useremail=userRepository.findByEmail(userData.get("rs_doctor_email").asText());
						 if(useremail.isPresent()&&DoctorExisting.get().getUser().getId()!=useremail.get().getId()) {
					 response.setResponseCode(0);
					 response.setResponseMessage("Email Already Present");
					 response.setResponseStatus("Success");
					 return response;
						 }
					    
						
					else {
						 doctor=DoctorExisting.get();
						 user=doctor.getUser();
						 
						 if(userData.get("rs_doctor_email").asText()!=null&& !userData.get("rs_doctor_email").asText().isEmpty()) {
						 user.setEmail(userData.get("rs_doctor_email").asText());
						 }
						 if(userData.get("rs_doctor_first_name").asText()!=null&& !userData.get("rs_doctor_first_name").asText().isEmpty()) {
						  user.setFirst_name(userData.get("rs_doctor_first_name").asText());
						  }
						 if(userData.get("rs_doctor_last_name").asText()!=null&& !userData.get("rs_doctor_last_name").asText().isEmpty()) {
				          user.setLast_name(userData.get("rs_doctor_last_name").asText()); 	}
						 
						 if(userData.get("rs_doctor_mobile").asText()!=null&& !userData.get("rs_doctor_mobile").asText().isEmpty()) {
				          user.setPhone_no(userData.get("rs_doctor_mobile").asText());
				          }
						 if(userData.get("rs_doctor_password").asText()!=null&& !userData.get("rs_doctor_password").asText().isEmpty()) {
						 user.setPassword(encryptThisString(userData.get("rs_doctor_password").asText()));
						 }
						 
						 if(userData.get("rs_doctor_status").asText()!=null&& !userData.get("rs_doctor_status").asText().isEmpty()) {
				          user.setStatus(userData.get("rs_doctor_status").asText());}
				          
						 if(userData.get("rs_doctor_address").asText()!=null&& !userData.get("rs_doctor_address").asText().isEmpty()) {
						 doctor.setAddress(userData.get("rs_doctor_address").asText());}
						 
						 if(userData.get("rs_doctor_available_end_time").asText()!=null&& !userData.get("rs_doctor_available_end_time").asText().isEmpty()) {
						 doctor.setAvailable_end_time(userData.get("rs_doctor_available_end_time").asText());}
						 
						 if(userData.get("rs_doctor_available_start_time").asText()!=null&& !userData.get("rs_doctor_available_start_time").asText().isEmpty()) {
						 doctor.setAvailable_start_time(userData.get("rs_doctor_available_start_time").asText());}
						 
						 if(userData.get("rs_doctor_available_status").asText()!=null&& !userData.get("rs_doctor_available_status").asText().isEmpty()) {
						 doctor.setAvailable_status(userData.get("rs_doctor_available_status").asText());}
						 
						 if(userData.get("rs_doctor_birth_date").asText()!=null&& !userData.get("rs_doctor_birth_date").asText().isEmpty()) {
						 doctor.setBirth_date(userData.get("rs_doctor_birth_date").asText());}
						 
						 if(userData.get("rs_doctor_profile_description").asText()!=null&& !userData.get("rs_doctor_profile_description").asText().isEmpty()) {
						 doctor.setDescription(userData.get("rs_doctor_profile_description").asText());}
						 
						 if(userData.get("rs_doctor_designation").asText()!=null&& !userData.get("rs_doctor_designation").asText().isEmpty()) {
						 doctor.setDesignation(userData.get("rs_doctor_designation").asText());}
						 
						 if(userData.get("rs_doctor_exp_years").asText()!=null&& !userData.get("rs_doctor_exp_years").asText().isEmpty()) {
						 doctor.setExp_years(userData.get("rs_doctor_exp_years").asInt());}
						 
						 if(userData.get("rs_doctor_education").asText()!=null&& !userData.get("rs_doctor_education").asText().isEmpty()) {
						 doctor.setEducation(userData.get("rs_doctor_education").asText());}
						 
						 if(userData.get("rs_doctor_first_name").asText()!=null&& !userData.get("rs_doctor_first_name").asText().isEmpty()) {
						 doctor.setFirst_name(userData.get("rs_doctor_first_name").asText());}
						  
						 if(userData.get("rs_doctor_gender").asText()!=null&& !userData.get("rs_doctor_gender").asText().isEmpty()) {
						 doctor.setGender(userData.get("rs_doctor_gender").asText());}
						 
						 if(userData.get("rs_doctor_image").asText()!=null&& !userData.get("rs_doctor_image").asText().isEmpty()) {
						 doctor.setImage(userData.get("rs_doctor_image").asText());}
						 
						 if(userData.get("rs_doctor_last_name").asText()!=null&& !userData.get("rs_doctor_last_name").asText().isEmpty()) {
						 doctor.setLast_name(userData.get("rs_doctor_last_name").asText());
						 }
						 if(userData.get("rs_doctor_department").asText()!=null&& !userData.get("rs_doctor_department").asText().isEmpty()) {
						 Optional<Department> depart= departmentRepository.findById(userData.get("rs_doctor_department").asInt());
						 doctor.setDepartment(depart.get());
						 }
						 
						 doctor.setUser(user);
						Doctor D= doctorRepository.save(doctor);
						 
						 response.setResponseCode(0);
						 response.setResponseData(D);
						 response.setResponseMessage("Updated Successfully");
						 response.setResponseStatus("Success");
						 return response;
					 }
			 }else {
				 response.setResponseCode(0);
				 response.setResponseMessage("No Doctor found");
				 response.setResponseStatus("Success");
				 return response;
			 }
				  
		}
		}
	}catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
		response.setResponseCode(1);
		response.setResponseMessage("Exception Occured While Updating");
		response.setResponseStatus("Failure");
	}
	return response;
}
@Override
public CommonResponse getActiveDoctor(JsonNode userData) {
	// TODO Auto-generated method stub
	CommonResponse response= new CommonResponse();
	try {
		 List<Doctor> doctorList = new ArrayList<>();
		List<UserD0> doctors=userRepository.findByStatusAndRole();

      if(!doctors.isEmpty()) {
		    

		    for (UserD0 user : doctors) {
		       // Doctor doctor = new Doctor();
		        int user_id = user.getId();
		        List<Doctor> activeDoctors = doctorRepository.findByUserIdLi(user_id);
		        for (Doctor d : activeDoctors) {
		

					String i=fun.imageBase64(d.getImage());
					Doctor doct=new Doctor();
					doct.setAddress(d.getAddress());
					doct.setAvailable_end_time(d.getAvailable_end_time());
					doct.setAvailable_start_time(d.getAvailable_start_time());
					doct.setAvailable_status(d.getAvailable_status());
					doct.setBirth_date(d.getBirth_date());
					doct.setDepartment(d.getDepartment());
					doct.setDescription(d.getDescription());
					doct.setDesignation(d.getDesignation());
					doct.setEducation(d.getEducation());
					doct.setExp_years(d.getExp_years());
					doct.setFirst_name("Dr."+d.getFirst_name());
					doct.setGender(d.getGender());
					doct.setId(d.getId());

					doct.setImage("data:image/png;base64,"+i);
					doct.setLast_name(d.getLast_name());
					doct.setUser(d.getUser());
		            doctorList.add(doct);
		            
		        	response.setResponseCode(0);
					response.setResponseData(doctorList);
					response.setResponseMessage("Active Doctor Data Fetched successfully");
					response.setResponseStatus("Success");
					
		    }

		    }
			
		}else {
			
			response.setResponseCode(1);
			response.setResponseMessage("No Doctor Found");
			response.setResponseStatus("Success");
		}
		    
	} catch (Exception e) {
		e.printStackTrace();
		response.setResponseCode(1);
		response.setResponseMessage("Exception Occured while Fetching");
		response.setResponseStatus("Failure");
	}
	return response;
}
@Override
public CommonResponse getDoctorDashboardData(JsonNode userData) {
	  CommonResponse response=new CommonResponse();
	  try {
		 Optional<Doctor>doctor=doctorRepository.findByUserId(userData.get("rs_user_id").asInt());
		 if(doctor.isEmpty()) {
			  
			    response.setResponseCode(0);
			    response.setResponseMessage("No Doctor Found");
			    response.setResponseStatus("Success");
		 }else {
		int id=doctor.get().getId();
	  List<PatientEntity> patient = patientRepository.findByDoctorId(id);

     List<PatientEntity> activePatientList = patient.stream()
    .filter(activePatient -> "Active".equals(activePatient.getUser().getStatus()))
    .collect(Collectors.toList());
     
	   List<Doctor>doctorall=doctorRepository.findAll();
	   List<Doctor> activeDoctorList = doctorall.stream()
			    .filter(activeDoctor -> "Active".equals(activeDoctor.getUser().getStatus()))
			    .collect(Collectors.toList());
	   
     Integer booking = bookingRepository.findByDoctorIdCount(id);
	 List<BookingAppointment> recentAppointments = bookingRepository.getRecentAppointmentsOfDoctor(id);
  	 int maxAppointmentsToShow = Math.min(6, recentAppointments.size());
     List<BookingAppointment> recentSixAppointments = recentAppointments.subList(0, maxAppointmentsToShow);
      
     List<Object[]>male=bookingRepository.findByDaysAndMale(id);
     Map<String, Long> bookingsByWeekdaysMapMale = new LinkedHashMap<>();
     List<String> allWeekdays = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday","Sunday");

  for (String weekday : allWeekdays) {
      bookingsByWeekdaysMapMale.put(weekday, 0L);
  }

     for (Object[] result : male) {
         String weekday = (String) result[0];
  	   Long count = (Long) result[1];
  	   
       bookingsByWeekdaysMapMale.put(weekday, count);
      
     }
     List<Object[]>female=bookingRepository.findByDaysAndFemale(id);
     Map<String, Long> bookingsByWeekdaysMapFemale = new LinkedHashMap<>();
     List<String> allWeekdaysfemale = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday","Sunday");

  for (String weekday : allWeekdaysfemale) {
      bookingsByWeekdaysMapFemale.put(weekday, 0L);
  }

     for (Object[] result : female) {
         String weekday = (String) result[0];
  	   Long count = (Long) result[1];
  	   
       bookingsByWeekdaysMapFemale.put(weekday, count);
      
     }
     Integer activePatientCount = activePatientList.size();
     Integer activeBookingCount=booking;
     Integer activeDoctorCount=activeDoctorList.size();
     Map<String, Object> res = new HashMap<>();
	    res.put("activeBookingCount", activeBookingCount);
	    res.put("activePatientCount", activePatientCount);
        res.put("upcomingAppointments", recentSixAppointments);
        res.put("MaleAppointments", bookingsByWeekdaysMapMale);
        res.put("femaleAppointments", bookingsByWeekdaysMapFemale);
        res.put("ActiveDoctorsCount", activeDoctorCount);
        
	    response.setResponseData(res);
	    response.setResponseCode(0);
	    response.setResponseMessage("Doctor Dashboard Data Fetched Successfully");
	    response.setResponseStatus("Success");
		 }
       }catch (Exception e) {
    	   response.setResponseCode(1);
      	    response.setResponseMessage("Exception Occurred While Fetching Dashboard Data");
      	    response.setResponseStatus("Failure");
	}
	return response;
}
@Override
public CommonResponse getDoctorWithOutImage(JsonNode userData) {
	CommonResponse response= new CommonResponse();
	try {
		 List<Doctor> doctorList = new ArrayList<>();
		List<UserD0> doctors=userRepository.findByStatusAndRole();

       if(!doctors.isEmpty()) {
		    

		    for (UserD0 user : doctors) {
		       // Doctor doctor = new Doctor();
		        int user_id = user.getId();
		        List<Doctor> activeDoctors = doctorRepository.findByUserIdLi(user_id);
		        for (Doctor d : activeDoctors) {
		

					
					Doctor doct=new Doctor();
					doct.setAddress(d.getAddress());
					doct.setAvailable_end_time(d.getAvailable_end_time());
					doct.setAvailable_start_time(d.getAvailable_start_time());
					doct.setAvailable_status(d.getAvailable_status());
					doct.setBirth_date(d.getBirth_date());
					doct.setDepartment(d.getDepartment());
					doct.setDescription(d.getDescription());
					doct.setDesignation(d.getDesignation());
					doct.setEducation(d.getEducation());
					doct.setExp_years(d.getExp_years());
					doct.setFirst_name("Dr."+d.getFirst_name());
					doct.setGender(d.getGender());
					doct.setId(d.getId());

					doct.setImage(d.getImage());
					doct.setLast_name(d.getLast_name());
					doct.setUser(d.getUser());
		            doctorList.add(doct);
		            response.setResponseCode(0);
					response.setResponseData(doctorList);
					response.setResponseMessage("Active Doctor Data Fetched successfully");
					response.setResponseStatus("Success");
		    }

		    }
			
		}else {
			
			response.setResponseCode(1);
			response.setResponseMessage("No Doctor Found");
			response.setResponseStatus("Success");
			
		}
		    
	} catch (Exception e) {
		e.printStackTrace();
		response.setResponseCode(1);
		response.setResponseMessage("Exception Occured while Fetching");
		response.setResponseStatus("Failure");
	}
	return response;

}
}