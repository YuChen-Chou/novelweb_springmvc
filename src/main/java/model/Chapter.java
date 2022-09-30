package model;

import java.util.Calendar;

//import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "chapter")
public class Chapter {
	@Id
	@Column(name = "chapter_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotBlank(message = "章節名字不能為空\n", groups = { ValidAdd.class, ValidUpdate.class })
	private String name;
	@NotBlank(message = "章節內容不能為空\n", groups = { ValidAdd.class, ValidUpdate.class })
	private String content;
	private String contentpath;

	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	@Column(name = "createtime", nullable = false, updatable = false) // 加入此設定，避免被update影響
	private Calendar createtime;

	@Temporal(TemporalType.TIMESTAMP)
	@UpdateTimestamp
	private Calendar updatetime;

	@ManyToOne
	@Cascade({CascadeType.SAVE_UPDATE})//改為使用org.hibernate.annotations.Cascade來設定關聯物件之間更新/刪除的關係
	@JoinColumn(name = "novel_id")
	private Novels novels;

	public Chapter() {
	}

	public Chapter(Integer id) {
		this.id = id;
	}
	
	public Chapter(String name, String content, String contentpath, Novels novels) {
		this.name = name;
		this.content = content;
		this.contentpath = contentpath;
		this.novels = novels;
	}

	public Chapter(Integer id, String name, String content, Novels novels) {
		this.id = id;
		this.name = name;
		this.content = content;
		this.novels = novels;
	}
	
	

	public Chapter(Integer id, String name, String content, String contentpath, Novels novels) {
		this.id = id;
		this.name = name;
		this.content = content;
		this.contentpath = contentpath;
		this.novels = novels;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public Novels getNovels() {
		return novels;
	}

	public void setNovels(Novels novels) {
		this.novels = novels;
	}

	public String getContentpath() {
		return contentpath;
	}

	public void setContentpath(String contentpath) {
		this.contentpath = contentpath;
	}
	

}
