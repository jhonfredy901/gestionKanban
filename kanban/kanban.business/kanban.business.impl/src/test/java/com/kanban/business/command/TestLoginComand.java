package com.kanban.business.command;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import com.kanban.business.common.BusinessException;
import com.kanban.business.common.EnumError;
import com.kanban.business.common.GenericResponse;
import com.kanban.business.dto.LoginDto;

class TestLoginComand {

	private LoginCommand login;

	public LoginDto asignData(String id, String pass) {
		LoginDto log = new LoginDto();
		log.setIdentification(id);
		log.setPassword(pass);
		return log;
	}

	public GenericResponse response(String msg, Object data) {
		GenericResponse resp = new GenericResponse();
		resp.setMessage(msg);
		resp.setData(data);
		return resp;
	}

	@Test
	void testLoginOk() throws BusinessException {
		login = new LoginCommand();
		login.setInput(asignData("1049622967", "abc123"));
		login.validateLogin();
		assertEquals(response("ok", true), login.getOut());
	}

	@Test
	void testLoginErrPass() throws BusinessException {
		login = new LoginCommand();
		login.setInput(asignData("1049622967", "abc123sss"));
		login.validateLogin();
		assertEquals(response(EnumError.ERR_108.getValue(), EnumError.ERR_108.getNum()), login.getOut());
	}

	@Test
	void testLoginErrUser() throws BusinessException {
		login = new LoginCommand();
		login.setInput(asignData("10496278", "abc123"));
		login.validateLogin();
		assertEquals(response(EnumError.ERR_107.getValue(), EnumError.ERR_107.getNum()), login.getOut());
	}

}
