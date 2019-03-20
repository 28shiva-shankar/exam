package in.co.online.exam.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.co.online.exam.models.Question;
import in.co.online.exam.utils.Constants.QuestionCategory;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long>{

	public List<Question> findByCategory(QuestionCategory category);
}
