package com.kanban.business.dto;

import javax.xml.bind.annotation.XmlRootElement;

import com.kanban.business.common.EnumAction;
import com.kanban.data.impl.model.Task;

@XmlRootElement
public class TaskDTO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9103028713192105524L;
	private int id;
	private String code;
	private String description;
	private int duration;
	private String status; //EN PROGRESO, ABIERTA, CERRADA
	private int iduser;
	private EnumAction action;
	private Integer startPosition;

	public TaskDTO(final Task entity) {
		if (entity != null) {
			this.id = entity.getId();
			this.code = entity.getCode();
			this.description = entity.getDescription();
			this.duration = entity.getDuration();
			this.status = entity.getStatus();
			this.iduser = entity.getIduser();
		}
	}

	public Task fromDTO(Task entity) {
		if (entity == null) {
			entity = new Task();
		}
		entity.setCode(this.code);
		entity.setDescription(this.description);
		entity.setDuration(this.duration);
		entity.setStatus(this.status);
		entity.setIduser(this.iduser);
		return entity;
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

	private Integer maxResult;

	public TaskDTO() {
	}

	public TaskDTO(String code, String description, int duration, String status, int iduser) {
		this.code = code;
		this.description = description;
		this.duration = duration;
		this.status = status;
		this.iduser = iduser;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDuration() {
		return this.duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getIduser() {
		return this.iduser;
	}

	public void setIduser(int iduser) {
		this.iduser = iduser;
	}

}
