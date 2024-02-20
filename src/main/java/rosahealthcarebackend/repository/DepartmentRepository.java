package rosahealthcarebackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rosahealthcarebackend.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

	Optional<Department> findByName(String name);

	Optional<Department> findById(Integer id);
	
	@Query(value="SELECT * FROM tbl_department u ORDER BY u.id DESC;", nativeQuery = true)
	List<Department>findAllOrderedByIdDesc();
	


}
