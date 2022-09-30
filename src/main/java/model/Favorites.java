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

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "favorites")
public class Favorites {
	@Id
	@Column(name = "favorites_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	@Column(name = "createtime", nullable = false, updatable = false) // 加入此設定，避免被update影響
	private Calendar createtime;

	@Temporal(TemporalType.TIMESTAMP)
	@UpdateTimestamp
	private Calendar updatetime;

	// 外鍵小說id
	@ManyToOne
	@Cascade({CascadeType.SAVE_UPDATE,CascadeType.MERGE,CascadeType.PERSIST})
	@JoinColumn(name = "novel_id")
	private Novels novels;

	// 外鍵會員id
	@ManyToOne
	@Cascade({CascadeType.SAVE_UPDATE,CascadeType.MERGE,CascadeType.PERSIST})
	@JoinColumn(name = "member_id")
	private Member member;

	public Favorites() {
	}

	public Favorites(Integer id) {
		this.id = id;
	}

	public Favorites(Novels novels, Member member) {
		this.novels = novels;
		this.member = member;
	}

	public Favorites(Integer id, Novels novels, Member member) {
		this.id = id;
		this.novels = novels;
		this.member = member;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

}
