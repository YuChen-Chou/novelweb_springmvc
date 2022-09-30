package model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import validation.ValidAdd;
import validation.ValidUpdate;

@Entity
@Table(name = "slideshow")
public class Slideshow {
	@Id
	@Column(name = "slideshow_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "index_num")
	@NotNull(message = "編號不能為空", groups = { ValidAdd.class, ValidUpdate.class })
	private Integer indexNum;

	@NotBlank(message = "名字不能為空", groups = { ValidAdd.class, ValidUpdate.class })
	private String name;

	private String picture="resources/img/slideshow/noIMG.jpg";

	private boolean disable = true;

	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	@Column(name = "createtime", nullable = false, updatable = false) // 加入此設定，避免被update影響
	private Calendar createtime;

	@Temporal(TemporalType.TIMESTAMP)
	@UpdateTimestamp
	private Calendar updatetime;

	public Slideshow() {
		super();
	}

	public Slideshow(Integer id) {
		this.id = id;
	}

	public Slideshow(@NotBlank(message = "編號不能為空", groups = { ValidAdd.class, ValidUpdate.class }) Integer indexNum,
			@NotBlank(message = "名字不能為空", groups = { ValidAdd.class, ValidUpdate.class }) String name, String picture) {
		this.indexNum = indexNum;
		this.name = name;
		this.picture = picture;
	}

	public Slideshow(Integer id,
			@NotBlank(message = "編號不能為空", groups = { ValidAdd.class, ValidUpdate.class }) Integer indexNum,
			@NotBlank(message = "名字不能為空", groups = { ValidAdd.class, ValidUpdate.class }) String name, String picture) {
		this.id = id;
		this.indexNum = indexNum;
		this.name = name;
		this.picture = picture;
	}

	public Slideshow(Integer indexNum,
			@NotBlank(message = "名字不能為空", groups = { ValidAdd.class, ValidUpdate.class }) String name, String picture,
			boolean disable) {
		this.indexNum = indexNum;
		this.name = name;
		this.picture = picture;
		this.disable = disable;
	}

	public Slideshow(Integer id, Integer indexNum,
			@NotBlank(message = "名字不能為空", groups = { ValidAdd.class, ValidUpdate.class }) String name, String picture,
			boolean disable) {
		this.id = id;
		this.indexNum = indexNum;
		this.name = name;
		this.picture = picture;
		this.disable = disable;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIndexNum() {
		return indexNum;
	}

	public void setIndexNum(Integer indexNum) {
		this.indexNum = indexNum;
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

	public boolean isDisable() {
		return disable;
	}

	public void setDisable(boolean disable) {
		this.disable = disable;
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

}
