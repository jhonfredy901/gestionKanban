/**
 * 
 */
package com.kanban.business.command;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.kanban.business.common.BusinessException;
import com.kanban.business.common.Command;
import com.kanban.business.common.EnumError;
import com.kanban.business.common.EnumText;
import com.kanban.business.common.Util;
import com.kanban.business.dto.LoginDto;
import com.kanban.data.impl.model.User;

/**
 * @author jhernandez
 *
 */
public class LoginCommand extends Command<LoginDto, Object> {

	private boolean isAutenticado;

	@Override
	protected void preValidate() throws BusinessException {
		Util.isEmpty(input.getIdentification(), EnumText.IDENTIFICATION.getValue());
		Util.isEmpty(input.getPassword(), EnumText.PASS.getValue());
		isValid = true;
	}

	@Override
	protected void executeCommand() throws BusinessException {
		validateLogin();
	}

	public void validateLogin() throws BusinessException {
		TypedQuery<User> query = getEm().createQuery("SELECT u FROM User u WHERE u.identification = :identification",
				User.class);
		query.setParameter("identification", input.getIdentification());
		User u = null;
		try {
			u = query.getSingleResult();
		} catch (NoResultException e) {
			LOG.debug(EnumError.ERR_106.getValue());
		}

		if (u == null) {
			LOG.info(EnumError.ERR_107.getValue());
			throw new BusinessException(EnumError.ERR_107.getNum(), EnumError.ERR_107.getValue());
		}

		if (input.getPassword().compareTo(u.getPass()) == 0) {
			setAutenticado(true);
		} else {
			throw new BusinessException(EnumError.ERR_108.getNum(), EnumError.ERR_108.getValue());
		}
		result = isAutenticado;
	}

	@Override
	public Object getOut() {
		return result;
	}

	/**
	 * @return the isAutenticado
	 */
	public boolean isAutenticado() {
		return isAutenticado;
	}

	/**
	 * @param isAutenticado the isAutenticado to set
	 */
	public void setAutenticado(boolean isAutenticado) {
		this.isAutenticado = isAutenticado;
	}

}
