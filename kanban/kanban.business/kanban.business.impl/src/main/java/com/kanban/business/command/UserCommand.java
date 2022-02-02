/**
 * 
 */
package com.kanban.business.command;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.kanban.business.common.BusinessException;
import com.kanban.business.common.Command;
import com.kanban.business.common.EnumError;
import com.kanban.business.common.EnumText;
import com.kanban.business.common.Util;
import com.kanban.business.dto.UserDTO;
import com.kanban.data.impl.model.User;

/**
 * @author jhernandez
 *
 */
public class UserCommand extends Command<UserDTO, Object> {

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
			Util.isEmpty(input.getAction(), EnumText.PASS.getValue());
			Util.isEmpty(input.getAction(), EnumText.IDENTIFICATION.getValue());
			Util.isEmpty(input.getAction(), EnumText.NAME.getValue());
			Util.isEmpty(input.getAction(), EnumText.LASTNAME.getValue());
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
			getUserById();
			break;
		case GETFULL:
			getUserAll();
			break;
		case POST:
			createUser();
			break;
		case PUT:
			updateUser();
			break;
		case DELETE:
			deleteUser();
			break;
		default:
			break;
		}

	}

	private void deleteUser() throws BusinessException {
		try {
			User entity = getEm().find(User.class, input.getId());
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

	private void updateUser() throws BusinessException {
		try {
			User entity = getEm().find(User.class, input.getId());
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

	private void createUser() throws BusinessException {
		try {
			User userFind = getUserByIdentification();
			if (userFind != null) {
				throw new BusinessException(EnumError.ERR_106.getNum(), EnumError.ERR_106.getValue());
			}
			User entity = input.fromDTO(null);
			getEm().persist(entity);
			result = entity;
		} catch (PersistenceException e) {
			LOG.error(EnumError.ERR_102.getValue(), e);
			throw new BusinessException(EnumError.ERR_102.getNum(), EnumError.ERR_102.getValue());
		}
	}

	private void getUserById() throws BusinessException {
		try {
			TypedQuery<User> findByIdQuery = getEm().createQuery(
					"SELECT DISTINCT u FROM User u WHERE u.id = :entityId ORDER BY u.id",
					User.class);
			findByIdQuery.setParameter("entityId", input.getId());
			User entity;
			entity = findByIdQuery.getSingleResult();
			if (entity == null) {
				throw new BusinessException(EnumError.ERR_105.getValue());
			}
			UserDTO dto = new UserDTO(entity);
			result = dto;
		} catch (PersistenceException e) {
			LOG.error(EnumError.ERR_105.getValue(), e);
			throw new BusinessException(EnumError.ERR_105.getNum(), EnumError.ERR_105.getValue());
		}
	}

	private void getUserAll() {
		TypedQuery<User> findAllQuery = getEm()
				.createQuery("SELECT DISTINCT u FROM User u ORDER BY u.id", User.class);
		if (input.getStartPosition() != null) {
			findAllQuery.setFirstResult(input.getStartPosition());
		}
		if (input.getMaxResult() != null) {
			findAllQuery.setMaxResults(input.getMaxResult());
		}
		List<User> searchResults = findAllQuery.getResultList();
		final List<UserDTO> results = new ArrayList<>();
		for (User searchResult : searchResults) {
			UserDTO dto = new UserDTO(searchResult);
			results.add(dto);
		}
		result = results;
	}

	private User getUserByIdentification() {
		TypedQuery<User> query = getEm().createQuery("SELECT u FROM User u WHERE u.identification = :identification",
				User.class);
		query.setParameter("identification", input.getIdentification());
		User u = null;
		try {
			u = query.getSingleResult();
		} catch (NoResultException e) {
			LOG.debug(EnumError.ERR_106.getValue());
		}
		return u;
	}

	@Override
	public Object getOut() {
		return result;
	}

}
