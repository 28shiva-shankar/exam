package in.co.online.exam.utils;

import java.util.List;

public class Email {

	private List<String> to;

	private List<String> cc;

	private String subject;

	private String message;

	public Email(List<String> to, List<String> cc, String subject, String message) {
		super();
		this.to = to;
		this.cc = cc;
		this.subject = subject;
		this.message = message;
	}

	public List<String> getTo() {
		return to;
	}

	public void setTo(List<String> to) {
		this.to = to;
	}

	public List<String> getCc() {
		return cc;
	}

	public void setCc(List<String> cc) {
		this.cc = cc;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
