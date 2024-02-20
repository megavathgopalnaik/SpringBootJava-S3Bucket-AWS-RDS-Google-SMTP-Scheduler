package rosahealthcarebackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rosahealthcarebackend.entity.PatientEntity;

public interface PatientRepository extends JpaRepository<PatientEntity, Integer>{
    
	@Query(value="SELECT * FROM tbl_patient u ORDER BY u.id DESC;", nativeQuery = true)
	List<PatientEntity> findAllOrderByIdDesc();
     
	 @Query(value="SELECT * FROM tbl_patient d WHERE d.user_id = :user_id", nativeQuery = true)
	Optional<PatientEntity> findByUserId(int user_id);




	List<PatientEntity> findByDoctorId(int doctor_id);


}
