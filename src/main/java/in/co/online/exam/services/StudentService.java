package in.co.online.exam.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import in.co.online.exam.models.Answer;
import in.co.online.exam.models.Question;
import in.co.online.exam.models.Student;
import in.co.online.exam.models.Student.ExamStatus;
import in.co.online.exam.repositories.AnswerRepository;
import in.co.online.exam.repositories.QuestionRepository;
import in.co.online.exam.repositories.StudentRepository;
import in.co.online.exam.utils.Constants;
import in.co.online.exam.utils.Email;

@Service
public class StudentService {

	@Autowired
	private StudentRepository studentRepo;

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@Autowired
	EmailService emailService;

	@Autowired
	private AnswerRepository answerRepo;

	@Autowired
	private QuestionRepository questionRepo;

	public Student registerStudent(Student student) {
		if (studentRepo.getByRollNo(student.getRollNo()) != null) {
			throw new RuntimeException("Duplicate Roll No");
		}
		String pass = RandomStringUtils.randomAlphanumeric(6);
		student.setPassword(passwordEncoder.encode(pass));
		if (isValid(student.getEmailId())) {
			String textMsg = "Hi " + student.getStudentName() + ",\n\nYour credentials for logging are: \nUserID : "
					+ student.getRollNo() + "\nLogin Password : " + pass
					+ "\nPlease use these credentials for logging.\nAll the Best!!!!\n\nDH-Team";
			emailService.sendPlainTextMail(
					new Email(Arrays.asList(student.getEmailId()), Arrays.asList(), "Student Login Password", textMsg));
		} else {
			throw new RuntimeException("Invalid Email Id");
		}
		student.setStatus(ExamStatus.REGISTERED);
		studentRepo.save(student);
		return student;
	}

	public static boolean isValid(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
				+ "A-Z]{2,7}$";
		Pattern pat = Pattern.compile(emailRegex);
		if (email == null)
			return false;
		return pat.matcher(email).matches();
	}

	public boolean isSubmitted(Student student) {
		if (student.isTimeExpired()) {
			submit(student);
			return true;
		}
		if (student.getStatus().equals(ExamStatus.SUBMITTED)) {
			throw new RuntimeException("Paper is already submitted");
		}
		return false;
	}

	public Student submit(Long id) {
		Student student = studentRepo.findOne(id);
		if (student.getStatus().equals(ExamStatus.SUBMITTED)) {
			throw new RuntimeException("Paper is already submitted");
		}
		return submit(student);
	}

	public Student submit(Student student) {
		isSubmitted(student);
		student.calculateScore();
		student.setStatus(ExamStatus.SUBMITTED);
		String textMsg = null;
		int score = 15;
		if (student.getScore() > score) {
			textMsg = "Hi " + student.getStudentName() + ",\n\n Thanks for taking the online-exam!!!!"
					+ "\n Your total score is: " + student.getScore()
					+ "\n Congratulations, you are shortlisted for round two.\n\n DH-Team.";
		} else {
			textMsg = "Hi " + student.getStudentName() + ",\n\n Thanks for taking the online-exam!!!!"
					+ "\n Your total score is: " + student.getScore()
					+ "\n Sorry, you are not shortlisted for round two.\n\n DH-Team.";
		}
		emailService.sendPlainTextMail(
				new Email(Arrays.asList(student.getEmailId()), Arrays.asList(), "Student Result", textMsg));
		studentRepo.save(student);
		return student;
	}

	public void saveAnswer(Answer answer) {
		Answer originalAnswer = answerRepo.findOne(answer.getId());
		if (originalAnswer == null) {
			throw new RuntimeException("Invalid Answer");
		}
		isSubmitted(originalAnswer.getStudent());
		originalAnswer.setAnswer(answer.getAnswer());
		answerRepo.save(originalAnswer);
	}

	public Student getRegistartionInfo(Long id) {
		Student student = studentRepo.findOne(id);
		if (student.getStatus().equals(ExamStatus.ONGOING) && new Date().after(student.getEndTime())) {
			student.calculateScore();
			student.setStatus(ExamStatus.SUBMITTED);
			return studentRepo.save(student);
		}
		return studentRepo.findOne(id);
	}

	public void startExam(Long id) {
		Student student = studentRepo.findOne(id);
		if (student.getStatus().equals(ExamStatus.SUBMITTED)) {
			throw new RuntimeException("Paper is already submitted");
		}
		if (!student.getStatus().equals(ExamStatus.ONGOING)) {
			student.setStartTime(new Date());
			student.setEndTime(DateUtils.addMinutes(new Date(), Constants.EXAM_DURATION_IN_MINUTES));
			student.setStatus(ExamStatus.ONGOING);
			student.setRegistrationDone(true);
			List<Answer> answers = new ArrayList<>();
			List<Question> questions = questionRepo.findAll();
			questions.forEach(q -> {
				Answer answer = new Answer();
				answer.setQuestion(q);
				answer.setStudent(student);
				answers.add(answer);
			});
			student.setAnswers(answers);
			studentRepo.save(student);
		}
	}

	public List<Answer> getAnswersByQCategory(Long id, String category) {
		if (!isSubmitted(studentRepo.findOne(id))) {
			List<Answer> answers = null;
			try {
				answers = answerRepo.findByStudentIdAndQuestionCategory(id,
						Constants.QuestionCategory.valueOf(category));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return answers;
		} else {
			throw new RuntimeException("Time Expired");
		}
	}

}
