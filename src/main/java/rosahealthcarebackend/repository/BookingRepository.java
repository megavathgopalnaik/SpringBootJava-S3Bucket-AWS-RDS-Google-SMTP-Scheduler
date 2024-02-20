package rosahealthcarebackend.repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import rosahealthcarebackend.entity.BookingAppointment;


public interface BookingRepository extends JpaRepository<BookingAppointment, Integer > {
	
	
	//@Query(value="SELECT  b.email as email,b.appointment_date as appointment_date FROM tbl_appointment b WHERE b.email = :email AND b.appointment_date = :appointment_date",nativeQuery = true)
	
	
	@Query(value = "SELECT * FROM tbl_appointment b WHERE b.email = :email AND b.appointment_date = :appointment_date", nativeQuery = true)
	List<BookingAppointment> findByEmailAndAppointmentDate( String email, String appointment_date);
	
	
	@Query(value = "SELECT * FROM tbl_appointment u " +
            "WHERE STR_TO_DATE(u.appointment_date, '%Y-%m-%d') BETWEEN CURDATE() - INTERVAL 2 MONTH AND CURDATE() + INTERVAL 6 MONTH " +
            "ORDER BY " +
            "  STR_TO_DATE(u.appointment_date, '%Y-%m-%d') >= CURDATE() DESC, " +
            "  CASE WHEN STR_TO_DATE(u.appointment_date, '%Y-%m-%d') >= CURDATE() THEN STR_TO_DATE(u.appointment_date, '%Y-%m-%d') END ASC, " +
            "  CASE WHEN STR_TO_DATE(u.appointment_date, '%Y-%m-%d') < CURDATE() THEN STR_TO_DATE(u.appointment_date, '%Y-%m-%d') END DESC, " +
            "  STR_TO_DATE(u.appointment_date, '%Y-%m-%d') ASC, " +
            "  CASE WHEN STR_TO_DATE(u.appointment_date, '%Y-%m-%d') < CURDATE() THEN STR_TO_DATE(u.appointment_time, '%H:%i') END DESC, " +
            "  STR_TO_DATE(u.appointment_time, '%H:%i') ASC", nativeQuery = true)
       	List<BookingAppointment> findAllBetweenMonths();
    
	
	@Query(value = "SELECT * FROM tbl_appointment b " +
	        "WHERE STR_TO_DATE(b.appointment_date, '%Y-%m-%d') >= CURRENT_DATE " +
	        "ORDER BY b.appointment_date ASC, STR_TO_DATE(b.appointment_time, '%H:%i:%s') ASC;", nativeQuery = true)
	List<BookingAppointment> getRecentAppointments();


	@Query(value = "SELECT COUNT(*) FROM tbl_appointment b " +
            "WHERE STR_TO_DATE( b.appointment_date, '%Y-%m-%d') >= CURRENT_DATE " +
            "  AND STR_TO_DATE( b.appointment_date, '%Y-%m-%d') <= DATE_ADD(CURRENT_DATE, INTERVAL 6 MONTH) " , nativeQuery = true)
	Integer getAppointment();
	
	
	
//	@Query(value="SELECT * FROM tbl_appointment e WHERE "+
//             "STR_TO_DATE(e.appointment_date, '%Y-%m-%d') = CURDATE() AND"+
//	   " TIMESTAMPDIFF(MINUTE,  STR_TO_DATE(SUBSTRING_INDEX(e.appointment_time, '-', 1), '%H:%i'), "+
//	       " NOW()   ) = -1;",nativeQuery = true)
//	   List<BookingAppointment> finByAppointmentDate();


	@Query(value="SELECT * FROM tbl_appointment e WHERE "+
            "STR_TO_DATE(e.appointment_date, '%Y-%m-%d') = CURDATE() AND"+
	  " TIMESTAMPDIFF(MINUTE,  STR_TO_DATE(SUBSTRING_INDEX(e.appointment_time, '-', 1), '%H:%i'),"+ 
	      " NOW()   ) = -1",nativeQuery = true)
	List<BookingAppointment> finByAppointmentDate();
   
	
	  @Query(value = "SELECT DAYNAME(STR_TO_DATE(b.appointment_date, '%Y-%m-%d')) AS weekday, COUNT(STR_TO_DATE(b.appointment_date, '%Y-%m-%d')) AS total " +
	            "FROM tbl_appointment b " +
	            "WHERE STR_TO_DATE(b.appointment_date, '%Y-%m-%d') BETWEEN DATE_SUB(NOW(), INTERVAL 7 DAY) AND NOW() " +
	            "GROUP BY weekday", nativeQuery = true)
	             List<Object[]> countBookingsByWeekday();


	   List<BookingAppointment> findByDoctorId(int doctor_id);

	   
	   @Query(value = "SELECT * FROM tbl_appointment b " +
               "WHERE b.doctor_id = :doctor_id  " +  
               "ORDER BY b.appointment_date ASC, " +
               " STR_TO_DATE(CONCAT(b.appointment_date, ' ', SUBSTRING_INDEX(b.appointment_time, '-', 1)), '%Y-%m-%d %H:%i') ASC",
       nativeQuery = true)
List<BookingAppointment> getRecentAppointmentsOfDoctor(@Param("doctor_id") int doctorId);
   
	   
	   
	   @Query(value =
		        "SELECT DAYNAME(STR_TO_DATE(b.appointment_date, '%Y-%m-%d')) AS weekday, " +
		        "       COUNT(STR_TO_DATE(b.appointment_date, '%Y-%m-%d')) AS total " +
		        "FROM tbl_appointment b " +
		        "WHERE STR_TO_DATE(b.appointment_date, '%Y-%m-%d') BETWEEN DATE_SUB(NOW(), INTERVAL 7 DAY) AND NOW() " +
		        "      AND b.doctor_id = :doctor_id " +
		        "      AND b.gender = 'Male' GROUP BY weekday", nativeQuery = true)
	List<Object[]> findByDaysAndMale(@Param("doctor_id") int doctorId);

	 @Query(value =
		        "SELECT DAYNAME(STR_TO_DATE(b.appointment_date, '%Y-%m-%d')) AS weekday, " +
		        "       COUNT(STR_TO_DATE(b.appointment_date, '%Y-%m-%d')) AS total " +
		        "FROM tbl_appointment b " +
		        "WHERE STR_TO_DATE(b.appointment_date, '%Y-%m-%d') BETWEEN DATE_SUB(NOW(), INTERVAL 7 DAY) AND NOW() " +
		        "      AND b.doctor_id = :doctor_id " +
		        "      AND b.gender = 'Female' GROUP BY weekday", nativeQuery = true)
	List<Object[]> findByDaysAndFemale(@Param("doctor_id") int doctorId);


	
	@Query(value="SELECT * FROM tbl_appointment  u ORDER BY STR_TO_DATE(u.appointment_date, '%Y-%m-%d')DESC,"
			+ "STR_TO_DATE(u.appointment_time, '%H:%i')ASC ", nativeQuery = true)
	List<BookingAppointment> findAllOrderByIdDesc();


	List<BookingAppointment> findByPatientId(int patient_id);


	@Query(value="SELECT * FROM tbl_appointment u WHERE STR_TO_DATE(u.appointment_date, '%Y-%m-%d')="
			+ "CURRENT_DATE AND u.patient_id=:patient_id",nativeQuery = true)
	Optional<BookingAppointment> findByPatientIdANDDate(int patient_id);

	@Query(value="SELECT COUNT(*) FROM tbl_appointment u;",nativeQuery = true)
	Integer getAppointmentCount();
  
	
	
	@Query(value = "SELECT COUNT(*) FROM tbl_appointment b " +
            "WHERE STR_TO_DATE( b.appointment_date, '%Y-%m-%d') >= CURRENT_DATE " +
            "  AND STR_TO_DATE( b.appointment_date, '%Y-%m-%d') <= DATE_ADD(CURRENT_DATE, INTERVAL 6 MONTH) "+
           "   AND b.doctor_id = :doctor_id" , nativeQuery = true)
	Integer findByDoctorIdCount(@Param("doctor_id") int doctorId);


	
	
	
	@Query (value="SELECT *FROM tbl_appointment e WHERE "+
	   " STR_TO_DATE(e.appointment_date, '%Y-%m-%d') = CURDATE() "+
	    "AND STR_TO_DATE(SUBSTRING_INDEX(e.appointment_time, '-', -1), '%H:%i') < NOW()",nativeQuery = true)
	List<BookingAppointment> findyByEndTime();


	

	@Query(value = "SELECT * FROM tbl_appointment e WHERE " +
	        "e.appointment_date = :appointment_date AND e.doctor_id = :doctor_id " +
	        "AND e.appointment_time = :appointment_time", nativeQuery = true)
	List<BookingAppointment> findByDateTimeANDDoctorId(
	        @Param("doctor_id") int doctor_id,
	        @Param("appointment_time") String appointment_time,
	        @Param("appointment_date") String appointment_date);

	
	
	@Query(value="SELECT SUM(b.bill)  FROM tbl_appointment b",nativeQuery = true) 
	Integer SumOfBill();


	
	
//	@Query(value = "SELECT DAYNAME(STR_TO_DATE(b.appointment_date, '%Y-%m-%d')) AS weekday, " +
//	        "COUNT(STR_TO_DATE(b.appointment_date, '%Y-%m-%d')) AS total " +
//	        "FROM tbl_appointment b " +
//	        "WHERE STR_TO_DATE(b.appointment_date, '%Y-%m-%d') BETWEEN " +
//	        "CURDATE() - INTERVAL (DAYOFWEEK(CURDATE()) - 1) DAY AND " +
//	        "CURDATE() + INTERVAL (7 - DAYOFWEEK(CURDATE())) DAY " +
//	        "GROUP BY weekday", nativeQuery = true)
	
   

//	List<BookingAppointment> getRecentAppointmentsInWeek();
 
	
}
