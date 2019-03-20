package in.co.online.exam.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.co.online.exam.models.Question;
import in.co.online.exam.repositories.QuestionRepository;
import in.co.online.exam.utils.Constants.QuestionCategory;

public class QuestionStore {

	private QuestionRepository questionRepo;

	public QuestionStore() {

	}

	public QuestionStore(QuestionRepository questionRepo) {
		this.questionRepo = questionRepo;
		loadQuestions();
	}

	private Map<QuestionCategory, List<Question>> questionByCategory = new HashMap<Constants.QuestionCategory, List<Question>>();

	public void put(QuestionCategory category, List<Question> questions) {
		questionByCategory.put(category, questions);
	}

	public List<Question> getQuestionsForCategory(QuestionCategory category) {
		List<Question> list = questionByCategory.get(category);
		Collections.shuffle(list);
		if (list.isEmpty()) {
			return new ArrayList<Question>();
		}
		return list.subList(0, category.count < list.size() ? category.count : list.size());
	}

	public void loadQuestions() {
		questionByCategory.clear();
		for (QuestionCategory questionCategory : Constants.QuestionCategory.values()) {
			this.put(questionCategory, questionRepo.findByCategory(questionCategory));
		}
	}
}
