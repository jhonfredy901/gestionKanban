package com.kanban.service.impl.services;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;

import com.kanban.business.command.LoginCommand;
import com.kanban.business.common.BusinessException;
import com.kanban.business.common.EnumError;
import com.kanban.business.common.GenericResponse;
import com.kanban.business.dto.LoginDto;

/**
 * Servicio que permite crear,actualizar, borrar y obtener Usuarios
 */
@Path("/auth")
public class LoginEndpoint {
	/**
	 * Instancia del Log
	 */
	protected static final Logger LOG = org.slf4j.LoggerFactory.getLogger(LoginEndpoint.class);
	/**
	 * comando para gestionar CRUD de Usuario
	 */
	@Inject
	private LoginCommand loginCmd;
	/**
	 * Objeto para manejo general de respuestas
	 */
	private GenericResponse response;

	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response create(LoginDto dto) {
		response = new GenericResponse();
		try {
			loginCmd.setInput(dto);
			loginCmd.execute();
			response.setData(loginCmd.getOut());
		} catch (BusinessException e) {
			LOG.error(EnumError.ERR_100.getValue(), this.getClass(), e.getCause(), e);
			response.setData(e.getCode());
			response.setMessage(e.getMessage());
		}
		return Response.ok(response).build();
	}

}
