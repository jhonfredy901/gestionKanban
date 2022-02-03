package com.kanban.business.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.kanban.business.common.EnumAction;
import com.kanban.data.impl.model.User;

@XmlRootElement
public class UserDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6853466744729234653L;
	private long id;
	private String pass;
	private String identification;
	private String name;
	private String lastname;
	private EnumAction action;
	private Integer startPosition;
	private Integer maxResult;

	public UserDTO() {
	}

	public UserDTO(User entity) {
		if (entity != null) {
			this.id = entity.getId();
			this.pass = entity.getPass();
			this.identification = entity.getIdentification();
			this.name = entity.getName();
			this.lastname = entity.getLastname();
		}
	}

	public User fromDTO(User entity) {
		if (entity == null) {
			entity = new User();
		}
		entity.setPass(this.pass);
		entity.setIdentification(this.identification);
		entity.setName(this.name);
		entity.setLastname(this.lastname);
		return entity;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPass() {
		return this.pass;
	}

	public void setPass(final String pass) {
		this.pass = pass;
	}

	public String getIdentification() {
		return this.identification;
	}

	public void setIdentification(final String identification) {
		this.identification = identification;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(final String lastname) {
		this.lastname = lastname;
	}

	/**
	 * @return the action
	 */
	public EnumAction getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(EnumAction action) {
		this.action = action;
	}

	/**
	 * @return the startPosition
	 */
	public Integer getStartPosition() {
		return startPosition;
	}

	/**
	 * @param startPosition the startPosition to set
	 */
	public void setStartPosition(Integer startPosition) {
		this.startPosition = startPosition;
	}

	/**
	 * @return the maxResult
	 */
	public Integer getMaxResult() {
		return maxResult;
	}

	/**
	 * @param maxResult the maxResult to set
	 */
	public void setMaxResult(Integer maxResult) {
		this.maxResult = maxResult;
	}
}