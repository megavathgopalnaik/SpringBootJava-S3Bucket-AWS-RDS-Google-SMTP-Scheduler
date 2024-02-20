package rosahealthcarebackend.entity;

import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import rosahealthcarebackend.appointmentenum.AppointmentStatus;

@Entity
@Table(name="tbl_appointment")
public class BookingAppointment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	 
	 @Column(name="first_name")
	  private String firstName;
	 
	  private Integer bill;
	  
	 @Column(name="last_name")
      private String  lastName;
      private String gender;
      private String mobile;
      @Column(name="address")
      private String address;
      private String email;
      
      @Column(name="birth_date")
      private String birthDate;
    
	@ManyToOne(cascade = CascadeType.ALL)
     @JoinColumn(name="doctor_id",referencedColumnName = "id")
     private Doctor doctor;
	
     @Column(name="appointment_date")
     private String appointmentDate;
     
     @Column(name="appointment_time")
     private String   appointmentTime;
     
     
     @ManyToOne(cascade = CascadeType.ALL)
     @JoinColumn(name="patient_id",referencedColumnName = "id")
     private PatientEntity patient;
     public PatientEntity getPatient() {
		return patient;
	}
	public void setPatient(PatientEntity patient) {
		this.patient = patient;
	}


	private String injury;
     @Enumerated(EnumType.STRING)
     @Column(name="appointment_status")
     private AppointmentStatus appointmentStatus;
     
     
	public Integer getBill() {
		return bill;
	}
	public void setBill(Integer bill) {
		this.bill = bill;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getAppointmentTime() {
		return appointmentTime;
	}
	public void setAppointmentTime(String appointmentTime) {
		this.appointmentTime = appointmentTime;
	}
	@Override
	public int hashCode() {
		return Objects.hash(address, appointmentDate, appointmentStatus, appointmentTime, bill, birthDate, doctor,
				email, firstName, gender, id, injury, lastName, mobile, patient);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookingAppointment other = (BookingAppointment) obj;
		return Objects.equals(address, other.address) && Objects.equals(appointmentDate, other.appointmentDate)
				&& appointmentStatus == other.appointmentStatus
				&& Objects.equals(appointmentTime, other.appointmentTime) && Objects.equals(bill, other.bill)
				&& Objects.equals(birthDate, other.birthDate) && Objects.equals(doctor, other.doctor)
				&& Objects.equals(email, other.email) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(gender, other.gender) && id == other.id && Objects.equals(injury, other.injury)
				&& Objects.equals(lastName, other.lastName) && Objects.equals(mobile, other.mobile)
				&& Objects.equals(patient, other.patient);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public String getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(String appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public String getInjury() {
		return injury;
	}

	public void setInjury(String injury) {
		this.injury = injury;
	}

	public AppointmentStatus getAppointmentStatus() {
		return appointmentStatus;
	}

	public void setAppointmentStatus(AppointmentStatus appointmentStatus) {
		this.appointmentStatus = appointmentStatus;
	}


	@Override
	public String toString() {
		return "BookingAppointment [id=" + id + ", firstName=" + firstName + ", bill=" + bill + ", lastName=" + lastName
				+ ", gender=" + gender + ", mobile=" + mobile + ", address=" + address + ", email=" + email
				+ ", birthDate=" + birthDate + ", doctor=" + doctor + ", appointmentDate=" + appointmentDate
				+ ", appointmentTime=" + appointmentTime + ", patient=" + patient + ", injury=" + injury
				+ ", appointmentStatus=" + appointmentStatus + "]";
	}
}