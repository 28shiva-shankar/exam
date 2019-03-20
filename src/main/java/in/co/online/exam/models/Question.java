package in.co.online.exam.models;

import java.io.IOException;
import java.io.InputStream;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Base64Utils;

import in.co.online.exam.config.DefaultAppConfig;
import in.co.online.exam.config.InjectionHelper;
import in.co.online.exam.utils.Constants.QuestionCategory;
import in.co.online.exam.utils.Constants.QuestionType;

@Entity
@Table(name = "questions")
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "path")
	private String path;

	@Column(name = "solution")
	private String solution;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "type")
	private QuestionType type;
	
	@Column(name = "option_count")
	private Integer optionCount;

	@Enumerated(EnumType.STRING)
	@Column(name = "category")
	private QuestionCategory category;
	
	@Column(name = "info_path")
	private String infoPath;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getOptionCount() {
		return optionCount;
	}

	public void setOptionCount(Integer optionCount) {
		this.optionCount = optionCount;
	}

	public String getInfoPath() {
		return infoPath;
	}

	public void setInfoPath(String infoPath) {
		this.infoPath = infoPath;
	}

	public boolean isMulti() {
		return type.equals(QuestionType.MULTI);
	}
	
	public boolean isText() {
		return type.equals(QuestionType.TEXT);
	}

	public QuestionType getType() {
		return type;
	}

	public QuestionCategory getCategory() {
		return category;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCategory(QuestionCategory category) {
		this.category = category;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public String getImage() throws IOException {
		InputStream ris = null;
		String imageData = null;
		try {
			ResourceLoader resourceLoader = InjectionHelper.getBean(DefaultAppConfig.class).resourceLoader;
			Resource resource = resourceLoader.getResource("classpath:" + path);
			ris = resource.getInputStream();
			imageData = Base64Utils.encodeToString(IOUtils.toByteArray(ris));
		} catch (Exception e) {
			return null;
		} finally {
			if (ris != null) {
				ris.close();
			}
		}
		return imageData;
	}

	public String getInfoImage() throws IOException {
		InputStream ris = null;
		String imageData = null;
		try {
			if (infoPath == null) {
				return null;
			}
			ResourceLoader resourceLoader = InjectionHelper.getBean(DefaultAppConfig.class).resourceLoader;
			Resource resource = resourceLoader.getResource("classpath:" + infoPath);
			ris = resource.getInputStream();
			imageData = Base64Utils.encodeToString(IOUtils.toByteArray(ris));
		} catch (Exception e) {
			return null;
		} finally {
			if (ris != null) {
				ris.close();
			}
		}
		return imageData;
	}

}
