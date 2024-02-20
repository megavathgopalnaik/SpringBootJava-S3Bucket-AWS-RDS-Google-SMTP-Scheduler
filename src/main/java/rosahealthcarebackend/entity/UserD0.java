package rosahealthcarebackend.entity;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name="tbl_user")
public class UserD0 {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String first_name;
	
	private String last_name;
	
	@Column(name = "email", unique = true)
	private String email;
	
	private String phone_no;
	
	private String password;
	
	private String status;
	

	public String getStatus() {
		return status;
	}
	


	public void setStatus(String status) {
		this.status = status;
	}
    


	@Column(name="created_user_id")
	private String created_user_id;
	

	public String getCreated_user_id() {
		return created_user_id;
	}



	public void setCreated_user_id(String created_user_id) {
		this.created_user_id = created_user_id;
	}


	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="role_id",referencedColumnName = "id")
	private Role role;



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


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPhone_no() {
		return phone_no;
	}


	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}





	public Role getRole() {
		return role;
	}


	public void setRole(Role role) {
		this.role = role;
	}


	@Override
	public String toString() {
		return "UserD0 [id=" + id + ", first_name=" + first_name + ", last_name=" + last_name + ", email=" + email
				+ ", phone_no=" + phone_no + ", password=" + password + ", status=" + status + ", created_user_id="
				+ created_user_id + ", role=" + role + "]";
	}


	@Override
	public int hashCode() {
		return Objects.hash(created_user_id, email, first_name, id, last_name, password, phone_no, role, status);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserD0 other = (UserD0) obj;
		return Objects.equals(created_user_id, other.created_user_id) && Objects.equals(email, other.email)
				&& Objects.equals(first_name, other.first_name) && id == other.id
				&& Objects.equals(last_name, other.last_name) && Objects.equals(password, other.password)
				&& Objects.equals(phone_no, other.phone_no) && Objects.equals(role, other.role)
				&& Objects.equals(status, other.status);
	}


	
}
