package rosahealthcarebackend.entity;


import java.util.Date;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="tbl_prescription") 
public class Prescription {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="prescription_title")
	private String prescriptionTitle;
	
	private String prescriptionDate;
	
	@Column(columnDefinition = "Text")
	private String DosageInstructions;
	

	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="doctor_id",referencedColumnName = "id")
    private Doctor doctor;
	
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="patient_id",referencedColumnName = "id")
    private PatientEntity patient;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date", updatable = false, nullable = false)
    @CreationTimestamp 
	private Date createdDate;
	


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_date", insertable = false, updatable = false)
    @UpdateTimestamp 
	private Date updatedDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="booking_id",referencedColumnName = "id")
    private BookingAppointment booking;
    
	public String getPrescriptionTitle() {
		return prescriptionTitle;
	}


	public void setPrescriptionTitle(String prescriptionTitle) {
		this.prescriptionTitle = prescriptionTitle;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getPrescriptionDate() {
		return prescriptionDate;
	}


	public void setPrescriptionDate(String prescriptionDate) {
		this.prescriptionDate = prescriptionDate;
	}


	public String getDosageInstructions() {
		return DosageInstructions;
	}


	public void setDosageInstructions(String dosageInstructions) {
		DosageInstructions = dosageInstructions;
	}


	public Doctor getDoctor() {
		return doctor;
	}


	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}


	public PatientEntity getPatient() {
		return patient;
	}


	public void setPatient(PatientEntity patient) {
		this.patient = patient;
	}


	public Date getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


	public Date getUpdatedDate() {
		return updatedDate;
	}


	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}


	public BookingAppointment getBooking() {
		return booking;
	}


	public void setBooking(BookingAppointment booking) {
		this.booking = booking;
	}


	@Override
	public int hashCode() {
		return Objects.hash(DosageInstructions, booking, createdDate, doctor, id, patient, prescriptionDate,
				prescriptionTitle, updatedDate);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Prescription other = (Prescription) obj;
		return Objects.equals(DosageInstructions, other.DosageInstructions) && Objects.equals(booking, other.booking)
				&& Objects.equals(createdDate, other.createdDate) && Objects.equals(doctor, other.doctor)
				&& id == other.id && Objects.equals(patient, other.patient)
				&& Objects.equals(prescriptionDate, other.prescriptionDate)
				&& Objects.equals(prescriptionTitle, other.prescriptionTitle)
				&& Objects.equals(updatedDate, other.updatedDate);
	}


	@Override
	public String toString() {
		return "Prescription [id=" + id + ", prescriptionTitle=" + prescriptionTitle + ", prescriptionDate="
				+ prescriptionDate + ", DosageInstructions=" + DosageInstructions + ", doctor=" + doctor + ", patient="
				+ patient + ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + ", booking=" + booking
				+ "]";
	}

}