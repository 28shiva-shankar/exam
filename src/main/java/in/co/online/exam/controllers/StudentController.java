package in.co.online.exam.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.co.online.exam.models.Answer;
import in.co.online.exam.models.Student;
import in.co.online.exam.services.StudentService;

@RestController
public class StudentController {

	@Autowired
	StudentService studentService;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public Student registerStudent(@RequestBody Student student) {
		return studentService.registerStudent(student);
	}

	@RequestMapping(value = "/registration-info", method = RequestMethod.GET)
	public Student getRegistartionInfo(@RequestParam("id") Long id) {
		return studentService.getRegistartionInfo(id);
	}

	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public Student submit(@RequestParam("id") Long id) {
		return studentService.submit(id);
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void saveAnswer(@RequestBody Answer answer) {
		studentService.saveAnswer(answer);
	}

	@RequestMapping(value = "/getLoggedInUser", method = RequestMethod.GET)
	public Object getLoggedInUser() {
		return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	@RequestMapping(value = "/getAnswersByCategory", method = RequestMethod.GET)
	public List<Answer> getAnswersByQCategory(@RequestParam("id") Long id, @RequestParam("category") String category) {
		return studentService.getAnswersByQCategory(id, category);
	}

	@RequestMapping(value = "/start", method = RequestMethod.POST)
	public void startExam(@RequestParam("id") Long id) {
		studentService.startExam(id);
	}

}
