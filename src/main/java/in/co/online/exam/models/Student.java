package in.co.online.exam.models;


import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import in.co.online.exam.utils.Constants;

@Entity
@Table(name = "students")
public class Student implements UserDetails {

	private static final long serialVersionUID = 1L;

	public enum ExamStatus {
		REGISTERED, ONGOING, SUBMITTED
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "student_name")
	private String studentName;

	@Column(name = "email_Id")
	private String emailId;

	@Column(name = "mobileNo")
	private Long mobileNo;

	@Column(name = "roll_no", unique = true, nullable = false)
	private String rollNo;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private ExamStatus status;

	@Column(name = "start_time")
	private Date startTime;

	@Column(name = "end_time")
	private Date endTime;

	@Column(name = "password")
	private String password;

	@JsonIgnore
	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
	private List<Answer> answers;

	@Column(name = "score")
	private Integer score;

	@Column(name ="is_registration_done")
	private boolean isRegistrationDone;
	
	public boolean isRegistrationDone() {
		return isRegistrationDone;
	}

	public void setRegistrationDone(boolean isRegistrationDone) {
		this.isRegistrationDone = isRegistrationDone;
	}

	@JsonIgnore
	public boolean isTimeExpired() {
		return new Date().after(endTime);
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getRollNo() {
		return rollNo;
	}

	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}

	public ExamStatus getStatus() {
		return status;
	}

	public void setStatus(ExamStatus status) {
		this.status = status;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date date) {
		this.startTime = date;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date date) {
		this.endTime = date;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Long getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(Long mobileNo) {
		this.mobileNo = mobileNo;
	}

	@JsonIgnore
	public void calculateScore() {
		int score = 0;

		for (Answer answer : getAnswers()) {
			if (answer.getAnswer() != null) {
				if (answer.getQuestion().getSolution().equalsIgnoreCase(answer.getAnswer())) {
					score += Constants.CORRECT_ANSWER_SCORE;
				} else {
					score += Constants.INCORRECT_ANSWER_SCORE;
				}
			}
		}
		this.score = score;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@Override
	@JsonIgnore
	public String getUsername() {
		return rollNo;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
