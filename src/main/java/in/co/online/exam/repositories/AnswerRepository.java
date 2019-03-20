package in.co.online.exam.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.co.online.exam.models.Answer;
import in.co.online.exam.models.Student;
import in.co.online.exam.utils.Constants.QuestionCategory;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long>{

	public List<Answer> findByStudent(Student student);
	
	public List<Answer> findByStudentIdAndQuestionCategory(Long id, QuestionCategory category);
}
