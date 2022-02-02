package com.kanban.business.dto;

import com.kanban.business.common.EnumAction;
import com.kanban.data.impl.model.Board;

public class BoardDTO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8805193362571383030L;
	private long id;
	private String code;
	private String name;
	private long idtask;
	private EnumAction action;
	private Integer startPosition;
	private Integer maxResult;

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

	public BoardDTO(final Board entity) {
		if (entity != null) {
			this.id = entity.getId();
			this.code = entity.getCode();
			this.name = entity.getName();
			this.idtask = entity.getIdtask();
		}
	}

	public Board fromDTO(Board entity) {
		if (entity == null) {
			entity = new Board();
		}
		entity.setCode(this.code);
		entity.setName(this.name);
		entity.setIdtask(this.idtask);
		return entity;
	}

	public BoardDTO() {
	}

	public BoardDTO(String code, String name, long idtask) {
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
