package com.kanban.business.dto;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.kanban.data.impl.model.Task;

public class NestedTaskDTO implements Serializable {

	private int id;
	private String code;
	private String description;
	private String status;
	private int duration;

	public NestedTaskDTO() {
	}

	public NestedTaskDTO(final Task entity) {
		if (entity != null) {
			this.id = entity.getId();
			this.code = entity.getCode();
			this.description = entity.getDescription();
			this.status = entity.getStatus();
			this.duration = entity.getDuration();
		}
	}

	public Task fromDTO(Task entity, EntityManager em) {
		if (entity == null) {
			entity = new Task();
		}
		if (((Integer) this.id) != null) {
			TypedQuery<Task> findByIdQuery = em.createQuery("SELECT DISTINCT t FROM Task t WHERE t.id = :entityId",
					Task.class);
			findByIdQuery.setParameter("entityId", this.id);
			try {
				entity = findByIdQuery.getSingleResult();
			} catch (javax.persistence.NoResultException nre) {
				entity = null;
			}
			return entity;
		}
		entity.setCode(this.code);
		entity.setDescription(this.description);
		entity.setStatus(this.status);
		entity.setDuration(this.duration);
		entity = em.merge(entity);
		return entity;
	}

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(final String code) {
		this.code = code;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public int getDuration() {
		return this.duration;
	}

	public void setDuration(final int duration) {
		this.duration = duration;
	}
}