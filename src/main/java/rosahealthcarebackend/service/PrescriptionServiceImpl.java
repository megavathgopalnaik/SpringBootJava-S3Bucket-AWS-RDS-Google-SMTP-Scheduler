package rosahealthcarebackend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;

import jakarta.transaction.Transactional;
import rosahealthcarebackend.appointmentenum.AppointmentStatus;
import rosahealthcarebackend.common.CommonResponse;
import rosahealthcarebackend.entity.BookingAppointment;
import rosahealthcarebackend.entity.Doctor;
import rosahealthcarebackend.entity.PatientEntity;
import rosahealthcarebackend.entity.Prescription;
import rosahealthcarebackend.entity.UserD0;
import rosahealthcarebackend.repository.BookingRepository;
import rosahealthcarebackend.repository.DoctorRepository;
import rosahealthcarebackend.repository.PatientRepository;
import rosahealthcarebackend.repository.PrescriptionRepository;
import rosahealthcarebackend.repository.UserRepository;

@Service
@Transactional
public class PrescriptionServiceImpl implements PrescriptionService {

	
	@Autowired
	private PrescriptionRepository prescriptionRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private DoctorRepository doctorRepository;
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private BookingRepository bookingRepository;
	
	@Override
	public CommonResponse addPrescription(JsonNode userData) {
		CommonResponse response=new CommonResponse();
		try {
			
			Prescription prescription=new Prescription();
			prescription.setDosageInstructions(userData.get("rs_user_dosage_instruction").asText());
			prescription.setPrescriptionDate(userData.get("rs_user_prescription_date").asText());
			prescription.setPrescriptionTitle(userData.get("rs_prescription_title").asText());
			Optional<Doctor> doctor= doctorRepository.findById(userData.get("rs_doctor_id").asInt());
			Optional<PatientEntity> patient=patientRepository.findById(userData.get("rs_patient_id").asInt());
			
//			BookingAppointment b=new BookingAppointment();
//			Optional<BookingAppointment>bo=bookingRepository.findByPatientIdANDDate(userData.get("rs_patient_id").asInt());
//			b=bo.get();
//			b.setAppointmentStatus(AppointmentStatus.Visited);
//			bookingRepository.save(b);
			prescription.setDoctor(doctor.get());
			prescription.setPatient(patient.get());
			Prescription p=prescriptionRepository.save(prescription);
			if(p==null) {
				response.setResponseCode (1) ;
				response.setResponseMessage ("Exception occured while saving.");
				response.setResponseStatus ("Failure") ;
			}else {
			response.setResponseCode(0);
			response.setResponseData(p.getId());
			response.setResponseMessage("Prescription Added Successfully");
			response.setResponseStatus("Success");
			return response;
			}
			
		} catch (Exception e) {
			e.printStackTrace ();
			response. setResponseCode (1) ;
			response. setResponseMessage ("Exception Occured while Prescription Creation.");
			response.setResponseStatus("Failure");
		}
		return response;
	}

	@Override
	public CommonResponse getPrescription(JsonNode userData) {
		CommonResponse response= new CommonResponse();
		try {
			Optional<UserD0>user=userRepository.findById(userData.get("rs_user_id").asInt());
			if(user.isEmpty()) {
				response.setResponseCode(1);
				response.setResponseMessage("No prescription Found");
				response.setResponseStatus("Success");
			}else if(user.get().getRole().getId()==3){
				
			
			Optional<PatientEntity> patient=patientRepository.findByUserId(userData.get("rs_user_id").asInt());
			List<Prescription>prescription=prescriptionRepository.findByPatientId(patient.get().getId());
			if(prescription.isEmpty()) {
				response.setResponseCode(1);
				response.setResponseMessage("No prescription Found");
				response.setResponseStatus("Success");
			}
			else {
				response.setResponseCode(0);
				response.setResponseData(prescription);
				response.setResponseMessage("Prescription Data Fetched Successfully");
				response.setResponseStatus("Success");
			}
			}else if(user.get().getRole().getId()==1 ||user.get().getRole().getId()==2) {
				
				List<Prescription> prescription=prescriptionRepository.findAll();
				if(prescription.isEmpty()) {
					response.setResponseCode(1);
					response.setResponseMessage("No prescription Found");
					response.setResponseStatus("Success");
				}
				else {
					response.setResponseCode(0);
					response.setResponseData(prescription);
					response.setResponseMessage("Prescription Data Fetched Successfully");
					response.setResponseStatus("Success");
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace ();
			response. setResponseCode (1) ;
			response. setResponseMessage ("Exception Occured while Fetching Prescription");
			response.setResponseStatus("Failure");
		}
		return response;
	}

	@Override
	public CommonResponse getPrescriptionbyPatientId(JsonNode userData) {
		CommonResponse response=new CommonResponse();
		try {
			List<Prescription>prescription=prescriptionRepository.findByPatientId(userData.get("rs_patient_id").asInt());
			if(prescription.isEmpty()) {
				response.setResponseCode(1);
				response.setResponseMessage("No prescription Found");
				response.setResponseStatus("Success");
			}else {
				response.setResponseCode(0);
				response.setResponseData(prescription);
				response.setResponseMessage("Prescription Data Fetched Successfully");
				response.setResponseStatus("Success");
			}
		} catch (Exception e) {
			e.printStackTrace ();
			response. setResponseCode (1) ;
			response. setResponseMessage ("Exception Occured while Fetching Prescription");
			response.setResponseStatus("Failure");
			
		}
		return response;
	}

	@Override
	public CommonResponse getprescriptionByBookingId(JsonNode userData) {
		CommonResponse response= new CommonResponse();
		try {
			
			List<Prescription>prescription=prescriptionRepository.findByBookingId(userData.get("rs_booking_id").asInt());
			if(!prescription.isEmpty()) {
				response.setResponseCode(0);
				response.setResponseData(prescription);
				response.setResponseMessage("Prescription Fetched Successfully");
				response.setResponseStatus("Success");
			}else {
				response.setResponseCode(1);
				response.setResponseMessage("Prescription Not Found");
				response.setResponseStatus("Success");
			}
			
		} catch (Exception e) {
			e.printStackTrace ();
			response. setResponseCode (1) ;
			response. setResponseMessage ("Exception Occured while Fetching Prescription");
			response.setResponseStatus("Failure");
		}
		return response;
	}
	

	
}
