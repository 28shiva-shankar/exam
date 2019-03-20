package in.co.online.exam.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.co.online.exam.models.Student;
import in.co.online.exam.models.Student.ExamStatus;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

	public Student findByStudentName(String studentName);

	public Student getByRollNo(String rollNo);

	public List<Student> getByStatus(ExamStatus status);
	
}
