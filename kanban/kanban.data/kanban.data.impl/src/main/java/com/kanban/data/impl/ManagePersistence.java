package com.kanban.data.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class ManagePersistence {

	@PersistenceContext(unitName = "kanban.persistence-unit")
	private EntityManager em;

	/**
	 * @return the em
	 */
	public EntityManager getEm() {
		return em;
	}

	/**
	 * @param em the em to set
	 */
	public void setEm(EntityManager em) {
		this.em = em;
	}

}
