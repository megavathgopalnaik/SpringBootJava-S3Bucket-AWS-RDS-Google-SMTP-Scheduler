package rosahealthcarebackend.repository;




import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rosahealthcarebackend.entity.Admin;


public interface AdminRepository extends JpaRepository<Admin, Integer>{

	
	@Query(value="SELECT * FROM tbl_admin u ORDER BY u.id DESC;", nativeQuery = true)
	List<Admin> findAllByOrderDesc();

	 @Query(value="SELECT * FROM tbl_admin d WHERE d.user_id = :user_id", nativeQuery = true)
	 Optional<Admin> findByUserId(int user_id);
	
	

	

}
