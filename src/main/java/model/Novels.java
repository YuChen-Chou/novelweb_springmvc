package model;

import java.util.Calendar;
import java.util.List;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import validation.ValidAdd;
import validation.ValidUpdate;

@Entity
@Table(name = "novels")
public class Novels {
	@Id
	@Column(name = "novel_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank(message = "小說名字不能為空", groups = { ValidAdd.class, ValidUpdate.class })
	private String name;

	private String picture="resources/img/cover/default.jpg";
	private String introduction;

	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	@Column(name = "createtime", nullable = false, updatable = false) // 加入此設定，避免被update影響
	private Calendar createtime;

	@Temporal(TemporalType.TIMESTAMP)
	@UpdateTimestamp
	private Calendar updatetime;

	@ManyToOne
	@Cascade({CascadeType.SAVE_UPDATE,CascadeType.MERGE,CascadeType.PERSIST})
	@JoinColumn(name = "author_id")
	private Author author;

	@ManyToOne
	@JoinColumn(name = "status_id")
	@Cascade({CascadeType.SAVE_UPDATE,CascadeType.MERGE,CascadeType.PERSIST})
	private Status status=new Status(0,"連載中");

	@ManyToOne
	@Cascade({CascadeType.SAVE_UPDATE,CascadeType.MERGE,CascadeType.PERSIST})
	@JoinColumn(name = "classification_id")
	private Classification classification;

	//給章節表用
	@OneToMany(mappedBy = "novels")
	@Cascade(CascadeType.ALL)
	private List<Chapter> chapter;
	
	//給喜愛表用
	@OneToMany(mappedBy = "novels")
	@Cascade(CascadeType.ALL)
	private List<Favorites> favorites;
	
	
	public Novels() {
	}

	public Novels(Integer id) {
		this.id = id;
	}

	public Novels(String name, String introduction, Author author, Classification classification) {
		this.name = name;
		this.introduction = introduction;
		this.author = author;
		this.classification = classification;
	}

	public Novels(String name, String picture, String introduction, Author author) {
		this.name = name;
		this.picture = picture;
		this.introduction = introduction;
		this.author = author;
	}

	public Novels(Integer id, String name, String picture, String introduction, Author author) {
		this.id = id;
		this.name = name;
		this.picture = picture;
		this.introduction = introduction;
		this.author = author;
	}

	public Novels(String name, String picture, String introduction, Author author, Status status,
			Classification classification) {
		this.name = name;
		this.picture = picture;
		this.introduction = introduction;
		this.author = author;
		this.status = status;
		this.classification = classification;
	}

	public Novels(Integer id, String name, String picture, String introduction, Author author, Status status,
			Classification classification) {
		this.id = id;
		this.name = name;
		this.picture = picture;
		this.introduction = introduction;
		this.author = author;
		this.status = status;
		this.classification = classification;
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

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public Calendar getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Calendar createtime) {
		this.createtime = createtime;
	}

	public Calendar getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Calendar updatetime) {
		this.updatetime = updatetime;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Classification getClassification() {
		return classification;
	}

	public void setClassification(Classification classification) {
		this.classification = classification;
	}

	public List<Chapter> getChapter() {
		return chapter;
	}

	public void setChapter(List<Chapter> chapter) {
		this.chapter = chapter;
	}

	public List<Favorites> getFavorites() {
		return favorites;
	}

	public void setFavorites(List<Favorites> favorites) {
		this.favorites = favorites;
	}
	
}
