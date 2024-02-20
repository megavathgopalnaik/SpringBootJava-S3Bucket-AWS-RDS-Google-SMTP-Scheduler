package rosahealthcarebackend.service;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;

import jakarta.transaction.Transactional;
import rosahealthcarebackend.common.CommonResponse;
import rosahealthcarebackend.entity.Admin;
import rosahealthcarebackend.entity.Department;
import rosahealthcarebackend.entity.Role;
import rosahealthcarebackend.entity.UserD0;
import rosahealthcarebackend.repository.AdminRepository;
import rosahealthcarebackend.repository.DepartmentRepository;
import rosahealthcarebackend.repository.RoleRepository;
import rosahealthcarebackend.repository.UserRepository;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
@Autowired
private AdminRepository adminRepository; 

@Autowired
private UserRepository userRepository; 

@Autowired
private RoleRepository roleRepository;
	@Autowired
	private DepartmentRepository departmentRepository;
	@Override
	public CommonResponse addAdmin(JsonNode userData) {
		// TODO Auto-generated method stub
		CommonResponse response=new CommonResponse();
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
			
			Admin admin=new Admin();
			admin.setUserDO(newUser);
			admin.setAddress(userData.get("rs_user_address").asText());
			admin.setAvailableEndTime(userData.get("rs_user_end_time").asText()); 
			admin.setAvailableStartTime(userData.get("rs_user_start_time").asText());
			admin.setAvailable_status(userData.get("rs_user_available_status").asText());
			admin.setBirthDate(userData.get("rs_user_birth_date").asText());
			Optional<Department> dep=departmentRepository.findById(userData.get("rs_user_department").asInt());
			admin.setDepartment(dep.get());
			admin.setDesignation(userData.get("rs_user_designation").asText());
			admin.setEducation(userData.get("rs_user_education").asText());
			admin.setExpYears(userData.get("rs_user_exp_years").asInt());
			admin.setFirstName(userData.get("rs_user_first_name").asText());
			admin.setGender(userData.get("rs_user_gender").asText());
			admin.setLastName(userData.get("rs_user_last_name").asText());
			Optional<Role> role= roleRepository.findByName(userData.get("rs_user_designation").asText());
			newUser.setRole(role.get());
			Admin admins=adminRepository.save(admin);
		
			response.setResponseCode(0);
			response.setResponseData(admins.getId());
			response.setResponseMessage("Staff added successfully");
			response.setResponseStatus("Success");
			
			}
			
		}catch(Exception e) {
		e.printStackTrace();
		response.setResponseCode(1);
		response.setResponseMessage("Exception Occured while Adding Staff");
		response.setResponseStatus("Failure");
		}
		return response;
	}

	@Override
	public CommonResponse getAdmin(JsonNode userData) {
		
		CommonResponse response=new CommonResponse();
		try {
			 
			List<Admin> admin=adminRepository.findAllByOrderDesc();
			if(admin.isEmpty()) {
				
				response.setResponseCode(1);
				response.setResponseMessage("Staff  not found");
				response.setResponseStatus("Success");
				return response;
				
				
			}else {
				
				response.setResponseCode(0);
				response.setResponseData(admin);
				response.setResponseMessage("Staff Data Fetched");
				response.setResponseStatus("Success");
				return response;
				
			}
			
			
		}catch(Exception e) {
		e.printStackTrace();
		response.setResponseCode(1);
		response.setResponseMessage("Exception Occured while Fetching Staff");
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
	public CommonResponse updateAdmin(JsonNode userData) {
		// TODO Auto-generated method stub
		CommonResponse response=new CommonResponse();
		try {
			
			if(!userData.get("rs_admin_id").asText().isEmpty()) {
				
				
				 UserD0 user=new UserD0();
				 Optional<Admin> adminExisting=adminRepository.findById(userData.get("rs_admin_id").asInt());
				 Admin a=new Admin();
				if(adminExisting.isPresent()) {
					//if(adminExisting.get().getUserDO().getEmail().matches(userData.get("rs_user_email").asText())) { 
						 if(userData.get("rs_user_email").asText()!=null&& !userData.get("rs_user_email").asText().isEmpty()) {
							  Optional<UserD0>useremail=userRepository.findByEmail(userData.get("rs_user_email").asText());
							 if(useremail.isPresent()&&adminExisting.get().getUserDO().getId()!=useremail.get().getId()) {
						 response.setResponseCode(0);
						 response.setResponseMessage("Email Already Present");
						 response.setResponseStatus("Success");
						 return response;
							 }
						 
					else {

						a=adminExisting.get();
						 user=a.getUserDO();
						 
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
							 a.setUserDO(user);
							 if(userData.get("rs_user_address").asText()!=null&& !userData.get("rs_user_address").asText().isEmpty()) {
							  a.setAddress(userData.get("rs_user_address").asText());
							 }
							 
							 if(userData.get("rs_user_available_status").asText()!=null&& !userData.get("rs_user_available_status").asText().isEmpty()) {
							 a.setAvailable_status(userData.get("rs_user_available_status").asText());
							 }
							 if(userData.get("rs_user_end_time").asText()!=null&& !userData.get("rs_user_end_time").asText().isEmpty()) {
							 a.setAvailableEndTime(userData.get("rs_user_end_time").asText());
							 }
							 if(userData.get("rs_user_start_time").asText()!=null&& !userData.get("rs_user_start_time").asText().isEmpty()) {
							 a.setAvailableStartTime(userData.get("rs_user_start_time").asText());
							 }
							 if(userData.get("rs_user_birth_date").asText()!=null&& !userData.get("rs_user_birth_date").asText().isEmpty()) {
							 a.setBirthDate(userData.get("rs_user_birth_date").asText());
							 }
							 if(userData.get("rs_user_designation").asText()!=null&& !userData.get("rs_user_designation").asText().isEmpty()) {
							 a.setDesignation(userData.get("rs_user_designation").asText());
							 }
							 if(userData.get("rs_user_education").asText()!=null&& !userData.get("rs_user_education").asText().isEmpty()) {
							 a.setEducation(userData.get("rs_user_education").asText());
							 }
							 if(userData.get("rs_user_exp_years").asText()!=null&& !userData.get("rs_user_exp_years").asText().isEmpty()) {
							 a.setExpYears(userData.get("rs_user_exp_years").asInt());
							 }
							 if(userData.get("rs_user_first_name").asText()!=null&& !userData.get("rs_user_first_name").asText().isEmpty()) {
							 a.setFirstName(userData.get("rs_user_first_name").asText());
							 }
							 if(userData.get("rs_user_gender").asText()!=null&& !userData.get("rs_user_gender").asText().isEmpty()) {
							 a.setGender(userData.get("rs_user_gender").asText());
							 }
							 if(userData.get("rs_user_last_name").asText()!=null&& !userData.get("rs_user_last_name").asText().isEmpty()) {
							 a.setLastName(userData.get("rs_user_last_name").asText());
							 }
							 if(userData.get("rs_user_department").asText()!=null&& !userData.get("rs_user_department").asText().isEmpty()) {
								 Optional<Department> dep=departmentRepository.findById(userData.get("rs_user_department").asInt());
									a.setDepartment(dep.get());
							
							 }
					 
					 
				
					Admin D= adminRepository.save(a);
					 
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
   
}