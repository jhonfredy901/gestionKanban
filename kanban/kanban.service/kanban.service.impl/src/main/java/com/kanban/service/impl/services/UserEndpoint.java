package com.kanban.service.impl.services;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;

import com.kanban.business.command.UserCommand;
import com.kanban.business.common.BusinessException;
import com.kanban.business.common.EnumAction;
import com.kanban.business.common.EnumError;
import com.kanban.business.common.GenericResponse;
import com.kanban.business.dto.UserDTO;

/**
 * Servicio que permite crear,actualizar, borrar y obtener Usuarios
 */
@Path("/users")
public class UserEndpoint {
	/**
	 * Instancia del Log
	 */
	protected static final Logger LOG = org.slf4j.LoggerFactory.getLogger(UserEndpoint.class);
	/**
	 * comando para gestionar CRUD de Usuario
	 */
	@Inject
	private UserCommand userCmd;
	/**
	 * Objeto para manejo general de respuestas
	 */
	private GenericResponse response;

	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response create(UserDTO dto) {
		response = new GenericResponse();
		try {
			dto.setAction(EnumAction.POST);
			userCmd.setInput(dto);
			userCmd.execute();
			response.setData(userCmd.getOut());
		} catch (BusinessException e) {
			LOG.error(EnumError.ERR_100.getValue(), this.getClass(), e.getCause(), e);
			response.setData(e.getCode());
			response.setMessage(e.getMessage());
		}
		return Response.ok(response).build();
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	@Produces("application/json")
	public Response deleteById(@PathParam("id") int id) {
		response = new GenericResponse();
		try {
			UserDTO dto = new UserDTO();
			dto.setId(id);
			dto.setAction(EnumAction.DELETE);
			userCmd.setInput(dto);
			userCmd.execute();
			response.setData(userCmd.getOut());
		} catch (BusinessException e) {
			LOG.error(EnumError.ERR_100.getValue(), this.getClass(), e.getCause(), e);
			response.setData(e.getCode());
			response.setMessage(e.getMessage());
		}
		return Response.ok(response).build();
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces("application/json")
	public Response findById(@PathParam("id") int id) {
		response = new GenericResponse();
		try {
			UserDTO dto = new UserDTO();
			dto.setId(id);
			dto.setAction(EnumAction.GETID);
			userCmd.setInput(dto);
			userCmd.execute();
			response.setData(userCmd.getOut());
		} catch (BusinessException e) {
			LOG.error(EnumError.ERR_100.getValue(), this.getClass(), e.getCause(), e);
			response.setData(e.getCode());
			response.setMessage(e.getMessage());
		}
		return Response.ok(response).build();
	}

	@GET
	@Produces("application/json")
	public Response listAll(@QueryParam("start") Integer startPosition, @QueryParam("max") Integer maxResult) {
		response = new GenericResponse();
		try {
			UserDTO dto = new UserDTO();
			dto.setStartPosition(startPosition);
			dto.setMaxResult(maxResult);
			dto.setAction(EnumAction.GETFULL);
			userCmd.setInput(dto);
			userCmd.execute();
			response.setData(userCmd.getOut());
		} catch (BusinessException e) {
			LOG.error(EnumError.ERR_100.getValue(), this.getClass(), e.getCause(), e);
			response.setData(e.getCode());
			response.setMessage(e.getMessage());
		}
		return Response.ok(response).build();
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response update(@PathParam("id") int id, UserDTO dto) {
		response = new GenericResponse();
		try {
			dto.setAction(EnumAction.PUT);
			dto.setId(id);
			userCmd.setInput(dto);
			userCmd.execute();
			response.setData(userCmd.getOut());
		} catch (BusinessException e) {
			LOG.error(EnumError.ERR_100.getValue(), e);
			response.setData(e.getCode());
			response.setMessage(e.getMessage());
		}
		return Response.ok(response).build();
	}
}
