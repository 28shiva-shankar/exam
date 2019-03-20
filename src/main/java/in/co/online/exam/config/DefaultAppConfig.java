package in.co.online.exam.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.context.annotation.Configuration;
import in.co.online.exam.repositories.QuestionRepository;
import in.co.online.exam.utils.QuestionStore;

@Configuration
public class DefaultAppConfig {

	@Autowired
	public ResourceLoader resourceLoader;

	@Autowired
	private QuestionRepository questionRepo;

	@Bean
	public InjectionHelper autowirehelper() {
		return new InjectionHelper();
	}

	@Bean
	public QuestionStore loadQuestions() {
		QuestionStore questionStore = new QuestionStore(questionRepo);
		return questionStore;
	}
}
