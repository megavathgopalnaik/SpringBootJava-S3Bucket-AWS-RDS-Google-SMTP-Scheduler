package rosahealthcarebackend.entity;


import java.io.IOException;
import java.io.InputStream;
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
@Table(name="tbl_doctor")
public class Doctor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
    private String first_name;
   private String last_name;
    private String gender;

    private String designation;
    
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="department_id",referencedColumnName = "id")
    private Department department;
    
   // @Column(length = 2555)
    @Column(columnDefinition ="Text")
    private String description;
    
    private String address;
   
    private String available_status;
    private String available_start_time;
    
    private String available_end_time;
    private String birth_date;
    private String education;
    private int exp_years;
    
   
    
    private String image;


    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="user_id",referencedColumnName = "id")
    private UserD0 user;
    
	public UserD0 getUser() {
		return user;
	}
	public void setUser(UserD0 user) {
		this.user = user;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
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
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	
	
	public String getAvailable_start_time() {
		return available_start_time;
	}
	public void setAvailable_start_time(String available_start_time) {
		this.available_start_time = available_start_time;
	}
	public String getAvailable_end_time() {
		return available_end_time;
	}
	public void setAvailable_end_time(String available_end_time) {
		this.available_end_time = available_end_time;
	}
	public String getBirth_date() {
		return birth_date;
	}
	public void setBirth_date(String birth_date) {
		this.birth_date = birth_date;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public int getExp_years() {
		return exp_years;
	}
	public void setExp_years(int exp_years) {
		this.exp_years = exp_years;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
@Override
public String toString() {
	return "Doctor [id=" + id + ", first_name=" + first_name + ", last_name=" + last_name + ", gender=" + gender
			+ ", designation=" + designation + ", department=" + department + ", description=" + description
			+ ", address=" + address + ", available_status=" + available_status + ", available_start_time="
			+ available_start_time + ", available_end_time=" + available_end_time + ", birth_date=" + birth_date
			+ ", education=" + education + ", exp_years=" + exp_years + ", image=" + image + ", user=" + user + "]";
}
	@Override
	public int hashCode() {
		return Objects.hash(address, available_end_time, available_start_time, available_status, birth_date, department,
				description, designation, education, exp_years, first_name, gender, id, image, last_name, user);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Doctor other = (Doctor) obj;
		return Objects.equals(address, other.address) && Objects.equals(available_end_time, other.available_end_time)
				&& Objects.equals(available_start_time, other.available_start_time)
				&& Objects.equals(available_status, other.available_status)
				&& Objects.equals(birth_date, other.birth_date) && Objects.equals(department, other.department)
				&& Objects.equals(description, other.description) && Objects.equals(designation, other.designation)
				&& Objects.equals(education, other.education) && exp_years == other.exp_years
				&& Objects.equals(first_name, other.first_name) && Objects.equals(gender, other.gender)
				&& id == other.id && Objects.equals(image, other.image) && Objects.equals(last_name, other.last_name)
				&& Objects.equals(user, other.user);
	}
    
    
}



