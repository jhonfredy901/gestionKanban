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
import com.kanban.business.dto.BoardDTO;
import com.kanban.business.dto.UserDTO;
import com.kanban.data.impl.model.Board;
import com.kanban.data.impl.model.User;

/**
 * @author jhernandez
 *
 */
public class BoardCommand extends Command<BoardDTO, Object> {

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
			Util.isEmpty(input.getName(), EnumText.NAME.getValue());
			Util.isEmpty(input.getCode(), EnumText.CODE.getValue());
			Util.isEmpty(input.getIdtask(), EnumText.IDTASK.getValue());
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
			getBoardById();
			break;
		case GETFULL:
			getBoardAll();
			break;
		case POST:
			createBoard();
			break;
		case PUT:
			updateBoard();
			break;
		case DELETE:
			deleteBoard();
			break;
		default:
			break;
		}

	}

	private void deleteBoard() throws BusinessException {
		try {
			Board entity = getEm().find(Board.class, input.getId());
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

	private void updateBoard() throws BusinessException {
		try {
			Board entity = getEm().find(Board.class, input.getId());
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

	private void createBoard() throws BusinessException {
		try {
			Board userFind = getUserByIdentification();
			if (userFind != null) {
				throw new BusinessException(EnumError.ERR_106.getNum(), EnumError.ERR_106.getValue());
			}
			Board entity = input.fromDTO(null);
			getEm().persist(entity);
			result = entity;
		} catch (PersistenceException e) {
			LOG.error(EnumError.ERR_102.getValue(), e);
			throw new BusinessException(EnumError.ERR_102.getNum(), EnumError.ERR_102.getValue());
		}
	}

	private void getBoardById() throws BusinessException {
		try {
			TypedQuery<Board> findByIdQuery = getEm()
					.createQuery("SELECT DISTINCT b FROM Board b WHERE b.id = :entityId ORDER BY u.id", Board.class);
			findByIdQuery.setParameter("entityId", input.getId());
			Board entity;
			entity = findByIdQuery.getSingleResult();
			if (entity == null) {
				throw new BusinessException(EnumError.ERR_105.getValue());
			}
			BoardDTO dto = new BoardDTO(entity);
			result = dto;
		} catch (PersistenceException e) {
			LOG.error(EnumError.ERR_105.getValue(), e);
			throw new BusinessException(EnumError.ERR_105.getNum(), EnumError.ERR_105.getValue());
		}
	}

	private void getBoardAll() {
		TypedQuery<User> findAllQuery = getEm().createQuery("SELECT DISTINCT b FROM Board b ORDER BY b.id", User.class);
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

	private Board getUserByIdentification() {
		TypedQuery<Board> query = getEm().createQuery("SELECT DISTINCT b FROM Board b WHERE b.code = :code",
				Board.class);
		query.setParameter("code", input.getCode());
		Board u = null;
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
