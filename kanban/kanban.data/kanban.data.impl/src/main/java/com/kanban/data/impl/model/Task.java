package com.kanban.data.impl.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Task generated by hbm2java
 */
@Entity
@Table(name = "task", schema = "public")
public class Task implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7609067381319957942L;
	private int id;
	private User user;
	private String code;
	private String description;
	private String status;
	private int duration;
	private Set<Board> boards = new HashSet<Board>(0);

	public Task() {
	}

	public Task(User user, String code, String description, String status, int duration) {
		this.user = user;
		this.code = code;
		this.description = description;
		this.status = status;
		this.duration = duration;
	}

	public Task(User user, String code, String description, String status, int duration, Set<Board> boards) {
		this.user = user;
		this.code = code;
		this.description = description;
		this.status = status;
		this.duration = duration;
		this.boards = boards;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "iduser", nullable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "code", nullable = false, length = 5)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "description", nullable = false, length = 200)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "status", nullable = false, length = 20)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "duration", nullable = false, precision = 8, scale = 0)
	public int getDuration() {
		return this.duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "task")
	public Set<Board> getBoards() {
		return this.boards;
	}

	public void setBoards(Set<Board> boards) {
		this.boards = boards;
	}

}