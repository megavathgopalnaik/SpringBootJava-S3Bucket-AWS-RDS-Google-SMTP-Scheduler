package rosahealthcarebackend.entity;


import java.util.Date;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="tbl_patient")

public class PatientEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "admitted_date", updatable = false, nullable = false)
    @CreationTimestamp 
	private Date admitted_date;
   
    private String gender;
    private String marital_status;
    private String address;
    private String blood_group;
    private int blood_presure;
    private int sugger;
    private String injury;
	
	private String birth_date;
	
	private int age;
	
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="doctor_id",referencedColumnName = "id")
    private Doctor doctor;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="user_id",referencedColumnName = "id")
	private UserD0 user;
	
	public Doctor getDoctor() {
		return doctor;
	}
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getMarital_status() {
		return marital_status;
	}
	public void setMarital_status(String marital_status) {
		this.marital_status = marital_status;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBlood_group() {
		return blood_group;
	}
	public void setBlood_group(String blood_group) {
		this.blood_group = blood_group;
	}
	public int getBlood_presure() {
		return blood_presure;
	}
	public void setBlood_presure(int blood_presure) {
		this.blood_presure = blood_presure;
	}
	public int getSugger() {
		return sugger;
	}
	public void setSugger(int sugger) {
		this.sugger = sugger;
	}
	public String getInjury() {
		return injury;
	}
	public void setInjury(String injury) {
		this.injury = injury;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getBirth_date() {
		return birth_date;
	}
	public void setBirth_date(String birth_date) {
		this.birth_date = birth_date;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public UserD0 getUser() {
		return user;
	}
	public void setUser(UserD0 user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "PatientEntity [id=" + id + ", gender=" + gender + ", marital_status=" + marital_status + ", address="
				+ address + ", blood_group=" + blood_group + ", blood_presure=" + blood_presure + ", sugger=" + sugger
				+ ", injury=" + injury + ", birth_date=" + birth_date + ", age=" + age + ", doctor=" + doctor
				+ ", user=" + user + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(address, age, birth_date, blood_group, blood_presure, doctor, gender, id, injury,
				marital_status, sugger, user);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PatientEntity other = (PatientEntity) obj;
		return Objects.equals(address, other.address) && age == other.age
				&& Objects.equals(birth_date, other.birth_date) && Objects.equals(blood_group, other.blood_group)
				&& blood_presure == other.blood_presure && Objects.equals(doctor, other.doctor)
				&& Objects.equals(gender, other.gender) && id == other.id && Objects.equals(injury, other.injury)
				&& Objects.equals(marital_status, other.marital_status) && sugger == other.sugger
				&& Objects.equals(user, other.user);
	}
	
	
}
