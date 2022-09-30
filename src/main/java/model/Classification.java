package model;

import java.util.List;

//import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import validation.ValidAdd;
import validation.ValidUpdate;

@Entity
@Table(name = "classification")
public class Classification {
	@Id
	@Column(name = "classification_id")
	private Integer id;
	
	@NotBlank(message = "類別名稱不能為空\n", groups = { ValidAdd.class, ValidUpdate.class })
	private String name;
	
	//給小說表用的資料
	@OneToMany(mappedBy = "classification")
	@Cascade({CascadeType.ALL})
	private List<Novels> novels;
	
	public Classification() {
	}

	public Classification(Integer id) {
		this.id = id;
	}

	public Classification(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Novels> getNovels() {
		return novels;
	}

	public void setNovels(List<Novels> novels) {
		this.novels = novels;
	}

}
