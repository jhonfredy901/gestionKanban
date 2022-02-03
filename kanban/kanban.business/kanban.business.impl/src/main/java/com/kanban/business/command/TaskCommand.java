/**
 * 
 */
package com.kanban.business.command;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.kanban.business.common.BusinessException;
import com.kanban.business.common.Command;
import com.kanban.business.common.EnumError;
import com.kanban.business.common.EnumText;
import com.kanban.business.common.Util;
import com.kanban.business.dto.TaskDTO;
import com.kanban.data.impl.model.Task;

/**
 * @author jhernandez
 *
 */
public class TaskCommand extends Command<TaskDTO, Object> {

	@Override
	protected void preValidate() throws BusinessException {
		Util.isEmpty(input.getAction(), EnumText.ACTION.getValue());
		switch (input.getAction()) {
		case GETID:
			Util.isEmpty(input.getId(), EnumText.ID.getValue());
			break;
		case GETFULL:
			Util.isEmpty(input.getStartPosition(), EnumText.STARTPOSITION.getValue());
			Util.isEmpty(input.getMaxResult(), EnumText.MAXRESULT.getValue());
			break;
		case POST:
			Util.isEmpty(input.getAction(), EnumText.CODE.getValue());
			Util.isEmpty(input.getAction(), EnumText.DESCRIPTION.getValue());
			Util.isEmpty(input.getAction(), EnumText.DURATION.getValue());
			Util.isEmpty(input.getAction(), EnumText.STATE.getValue());
			Util.isEmpty(input.getAction(), EnumText.IDUSER.getValue());
			break;
		case PUT:
			Util.isEmpty(input.getId(), EnumText.ID.getValue());
			break;
		case DELETE:
			Util.isEmpty(input.getId(), EnumText.ID.getValue());
			break;
		default:
			break;
		}

		isValid = true;
	}

	@Override
	protected void executeCommand() throws BusinessException {
		switch (input.getAction()) {
		case GETID:
			getTaskById();
			break;
		case GETFULL:
			getTaskAll();
			break;
		case POST:
			createTask();
			break;
		case PUT:
			updateTask();
			break;
		case DELETE:
			deleteTask();
			break;
		default:
			break;
		}

	}

	private void deleteTask() throws BusinessException {
		try {
			Task entity = getEm().find(Task.class, input.getId());
			if (entity == null) {
				throw new BusinessException(EnumError.ERR_105.getValue());
			}
			getEm().remove(entity);
			result = true;
		} catch (PersistenceException e) {
			LOG.error(EnumError.ERR_104.getValue(), e);
			throw new BusinessException(EnumError.ERR_104.getNum(), EnumError.ERR_104.getValue());
		}
	}

	private void updateTask() throws BusinessException {
		try {
			Task entity = getEm().find(Task.class, input.getId());
			if (entity == null) {
				throw new BusinessException(EnumError.ERR_105.getValue());
			}
			entity = input.fromDTO(entity);
			getEm().merge(entity);
			result = entity;
		} catch (PersistenceException e) {
			LOG.error(EnumError.ERR_103.getValue(), e);
			throw new BusinessException(EnumError.ERR_103.getNum(), EnumError.ERR_103.getValue());
		}
	}

	private void createTask() throws BusinessException {
		try {
			Task userFind = getTaskByCode();
			if (userFind != null) {
				throw new BusinessException(EnumError.ERR_106.getNum(), EnumError.ERR_106.getValue());
			}
			Task entity = input.fromDTO(null);
			getEm().persist(entity);
			result = entity;
		} catch (PersistenceException e) {
			LOG.error(EnumError.ERR_102.getValue(), e);
			throw new BusinessException(EnumError.ERR_102.getNum(), EnumError.ERR_102.getValue());
		}
	}

	private void getTaskById() throws BusinessException {
		try {
			TypedQuery<Task> findByIdQuery = getEm()
					.createQuery("SELECT DISTINCT t FROM Task t WHERE t.id = :entityId ORDER BY t.id", Task.class);
			findByIdQuery.setParameter("entityId", input.getId());
			Task entity;
			entity = findByIdQuery.getSingleResult();
			if (entity == null) {
				throw new BusinessException(EnumError.ERR_105.getValue());
			}
			result = entity;
		} catch (PersistenceException e) {
			LOG.error(EnumError.ERR_105.getValue(), e);
			throw new BusinessException(EnumError.ERR_105.getNum(), EnumError.ERR_105.getValue());
		}
	}

	private void getTaskAll() {
		TypedQuery<Task> findAllQuery = getEm().createQuery("SELECT DISTINCT t FROM Task t ORDER BY t.id", Task.class);
		if (input.getStartPosition() != null) {
			findAllQuery.setFirstResult(input.getStartPosition());
		}
		if (input.getMaxResult() != null) {
			findAllQuery.setMaxResults(input.getMaxResult());
		}
		List<Task> searchResults = findAllQuery.getResultList();
//		List<TaskDTO> results = new ArrayList<>();
//		for (Task searchResult : searchResults) {
//			TaskDTO dto = new TaskDTO(searchResult);
//			results.add(dto);
//		}
		result = searchResults;
	}

	private Task getTaskByCode() {
		TypedQuery<Task> query = getEm().createQuery("SELECT t FROM Task t WHERE t.code = :code", Task.class);
		query.setParameter("code", input.getCode());
		Task t = null;
		try {
			t = query.getSingleResult();
		} catch (NoResultException e) {
			LOG.debug(EnumError.ERR_106.getValue());
		}
		return t;
	}

	@Override
	public Object getOut() {
		return result;
	}

}
