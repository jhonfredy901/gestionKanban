package com.kanban.data.impl.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Board generated by hbm2java
 */
@Entity
@Table(name = "board", schema = "public")
@XmlRootElement
public class Board implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8805193362571383030L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;
	@Column(name = "code", nullable = false, length = 5)
	private String code;
	@Column(name = "name", nullable = false, length = 100)
	private String name;
	@Column(name = "idtask", nullable = false)
	private long idtask;

	public Board() {
	}

	public Board(String code, String name, int idtask) {
		this.code = code;
		this.name = name;
		this.idtask = idtask;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getIdtask() {
		return this.idtask;
	}

	public void setIdtask(long idtask) {
		this.idtask = idtask;
	}

}
