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

import com.kanban.business.command.TaskCommand;
import com.kanban.business.common.BusinessException;
import com.kanban.business.common.EnumAction;
import com.kanban.business.common.EnumError;
import com.kanban.business.common.GenericResponse;
import com.kanban.business.dto.TaskDTO;

/**
 * Servicio que permite crear,actualizar, borrar y obtener tareas
 */
@Path("/tasks")
public class TaskEndpoint {
	/**
	 * Instancia del Log
	 */
	protected static final Logger LOG = org.slf4j.LoggerFactory.getLogger(TaskEndpoint.class);
	/**
	 * comando para gestionar CRUD de Usuario
	 */
	@Inject
	private TaskCommand taskCmd;
	/**
	 * Objeto para manejo general de respuestas
	 */
	private GenericResponse response;

	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response create(TaskDTO dto) {
		response = new GenericResponse();
		try {
			dto.setAction(EnumAction.POST);
			taskCmd.setInput(dto);
			taskCmd.execute();
			response.setData(taskCmd.getOut());
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
			TaskDTO dto = new TaskDTO();
			dto.setId(id);
			dto.setAction(EnumAction.DELETE);
			taskCmd.setInput(dto);
			taskCmd.execute();
			response.setData(taskCmd.getOut());
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
			TaskDTO dto = new TaskDTO();
			dto.setId(id);
			dto.setAction(EnumAction.GETID);
			taskCmd.setInput(dto);
			taskCmd.execute();
			response.setData(taskCmd.getOut());
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
			TaskDTO dto = new TaskDTO();
			dto.setStartPosition(startPosition);
			dto.setMaxResult(maxResult);
			dto.setAction(EnumAction.GETFULL);
			taskCmd.setInput(dto);
			taskCmd.execute();
			response.setData(taskCmd.getOut());
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
	public Response update(@PathParam("id") int id, TaskDTO dto) {
		response = new GenericResponse();
		try {
			dto.setAction(EnumAction.PUT);
			dto.setId(id);
			taskCmd.setInput(dto);
			taskCmd.execute();
			response.setData(taskCmd.getOut());
		} catch (BusinessException e) {
			LOG.error(EnumError.ERR_100.getValue(), e);
			response.setData(e.getCode());
			response.setMessage(e.getMessage());
		}
		return Response.ok(response).build();
	}
}
