package rosahealthcarebackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rosahealthcarebackend.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

	Doctor save(List<Doctor> doc);
    
	@Query(value="SELECT * FROM tbl_doctor u ORDER BY u.id DESC;", nativeQuery = true)
	List<Doctor> findAllOderById();
//	
//	@Query(value="SELECT COUNT(*) FROM tbl_doctor d WHERE d.status = 'Active';",nativeQuery = true)
//	Integer getActiveDoctorCount();

    @Query(value="SELECT * FROM tbl_doctor d WHERE d.user_id = :user_id", nativeQuery = true)
	Optional<Doctor> findByUserId( int user_id);
    
    
    @Query(value="SELECT * FROM tbl_doctor d WHERE d.user_id = :user_id", nativeQuery = true)
    List<Doctor> findByUserIdLi( int user_id);
    
    
   
   

}
