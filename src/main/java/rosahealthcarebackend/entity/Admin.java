package rosahealthcarebackend.entity;

import java.util.Objects;

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

@Entity
@Table(name="tbl_admin")
public class Admin {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="first_name")
    private String firstName;
	
	@Column(name="last_name")
    private String lastName;
	
    private String gender;

    private String designation;
    
    
    private String address;
   
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="department_id",referencedColumnName = "id")
    private Department department;
    
    @Column(name="available_status")
    private String available_status;
    
    @Column(name="available_start_time")
    private String availableStartTime;
    
    @Column(name="available_end_time")
    private String availableEndTime;
    
    @Column(name="birth_date")
    private String birthDate;
    
    private String education;
    
    @Column(name="exp_years")
    private int expYears;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id",referencedColumnName = "id")
    private UserD0 userDO;
 
    
    public UserD0 getUserDO() {
		return userDO;
	}
	public void setUserDO(UserD0 userDO) {
		this.userDO = userDO;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	

	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAvailable_status() {
		return available_status;
	}
	public void setAvailable_status(String available_status) {
		this.available_status = available_status;
	}
	public String getAvailableStartTime() {
		return availableStartTime;
	}
	public void setAvailableStartTime(String availableStartTime) {
		this.availableStartTime = availableStartTime;
	}
	public String getAvailableEndTime() {
		return availableEndTime;
	}
	public void setAvailableEndTime(String availableEndTime) {
		this.availableEndTime = availableEndTime;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public int getExpYears() {
		return expYears;
	}
	public void setExpYears(int expYears) {
		this.expYears = expYears;
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(address, availableEndTime, availableStartTime, available_status, birthDate, department,
				designation, education, expYears, firstName, gender, id, lastName, userDO);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Admin other = (Admin) obj;
		return Objects.equals(address, other.address) && Objects.equals(availableEndTime, other.availableEndTime)
				&& Objects.equals(availableStartTime, other.availableStartTime)
				&& Objects.equals(available_status, other.available_status)
				&& Objects.equals(birthDate, other.birthDate) && Objects.equals(department, other.department)
				&& Objects.equals(designation, other.designation) && Objects.equals(education, other.education)
				&& expYears == other.expYears && Objects.equals(firstName, other.firstName)
				&& Objects.equals(gender, other.gender) && id == other.id && Objects.equals(lastName, other.lastName)
				&& Objects.equals(userDO, other.userDO);
	}
	@Override
	public String toString() {
		return "Admin [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", gender=" + gender
				+ ", designation=" + designation + ", address=" + address + ", department=" + department
				+ ", available_status=" + available_status + ", availableStartTime=" + availableStartTime
				+ ", availableEndTime=" + availableEndTime + ", birthDate=" + birthDate + ", education=" + education
				+ ", expYears=" + expYears + ", userDO=" + userDO + "]";
	}
}
	