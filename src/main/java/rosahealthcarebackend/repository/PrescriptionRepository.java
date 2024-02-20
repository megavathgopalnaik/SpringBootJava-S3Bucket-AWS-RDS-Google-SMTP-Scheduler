package rosahealthcarebackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import rosahealthcarebackend.entity.Prescription;

public interface PrescriptionRepository extends JpaRepository<Prescription, Integer> {
	@Query(value="SELECT * FROM tbl_prescription a  ORDER BY a.id DESC;",nativeQuery = true)
	List<Prescription> findByPatientId(int patient_id);
    
	@Query(value="SELECT * FROM tbl_prescription a WHERE a.booking_id = :bookingId ORDER BY a.id DESC",nativeQuery = true)
	List<Prescription> findByBookingId(@Param("bookingId") int bookingId);


}
