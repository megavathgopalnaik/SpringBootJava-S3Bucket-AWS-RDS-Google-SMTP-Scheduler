package rosahealthcarebackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;

import jakarta.transaction.Transactional;
import rosahealthcarebackend.common.CommonResponse;
import rosahealthcarebackend.entity.Department;
import rosahealthcarebackend.repository.DepartmentRepository;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService{
	
	@Autowired
	private DepartmentRepository departmentRepository;

	@Override
	public CommonResponse getDepartment(JsonNode userData) {
		// TODO Auto-generated method stub
		CommonResponse response= new CommonResponse();
		try {
		List<Department> department= departmentRepository.findAllOrderedByIdDesc();
		if(department.isEmpty()) {
			response.setResponseCode(1);
			response.setResponseMessage("No Department Found");
			response.setResponseStatus("Success");
			return response;
		}else {
			response.setResponseCode(0);
			response.setResponseData(department);
			response.setResponseMessage("Department Data Fetched");
			response.setResponseStatus("Success");
			return response;
		}
		
		
	}catch(Exception e) {
		e.printStackTrace();
		response.setResponseCode(1);
		response.setResponseMessage("Exception Occured While Fetching Department");
		response.setResponseStatus("Failure");

	}
  return response;
}
}