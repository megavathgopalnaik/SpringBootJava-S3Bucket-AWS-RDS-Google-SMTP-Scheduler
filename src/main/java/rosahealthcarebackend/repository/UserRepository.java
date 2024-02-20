package rosahealthcarebackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import rosahealthcarebackend.entity.UserD0;

public interface UserRepository extends JpaRepository<UserD0, Integer>{

	

	@Query(value = "select u.id as id, u.first_name as first_name,u.last_name as last_name, u.email as email, u.phone_no as mobile_no, u.role_id as role_id,"
	+ "r.name as role_name, u.password as password ,u.status as status from tbl_user u left join tbl_role r on u.role_id= r.id where email=:email and " 
			+ " password=:password	", nativeQuery = true)
List<Object[]> authenticate(String email, String password);

	Optional<UserD0> findByEmail(String email);

	
	 @Query(value="select count(*),status from tbl_user group by status order by status;",nativeQuery = true)
	List<Object[]> getUserDataCount();
    
	 @Query(value="SELECT COUNT(*) FROM tbl_user u WHERE u.status = 'Active' AND u.role_id = 2;",nativeQuery = true)
	 Integer getActiveDoctorCount();
 
	@Query(value="SELECT COUNT(*) FROM tbl_user u WHERE u.status = 'Active' AND u.role_id = 3;",nativeQuery = true)
	Integer getActivePatientCount();

    @Query(value="SELECT  * FROM tbl_user u WHERE u.status = 'Active' AND u.role_id = 2;",nativeQuery = true)
	List<UserD0> findByStatusAndRole();

}
