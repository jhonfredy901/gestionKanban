package com.kanban.business.common;

import javax.inject.Inject;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

import org.slf4j.Logger;

import com.kanban.data.impl.ManagePersistence;

/**
 * Abstración de patrón comando para permitir realizar uso en comandos concretos
 * para ejecución
 * 
 * @author jhon hernandez
 *
 */
public abstract class Command<I, O> extends ManagePersistence {
	/**
	 * Instancia del Log
	 */
	protected static final Logger LOG = org.slf4j.LoggerFactory.getLogger(Command.class);
	/**
	 * Propiedad para manejar datos de entrada para ejecución
	 */
	protected I input;
	/**
	 * Propiedad para retornan el resultado de la ejecucion del comando
	 */
	protected O result;
	/**
	 * Propiedad para manejar si es valido o no el comando a ejecutar
	 */
	protected boolean isValid;
	/**
	 * Manejo de transaccion
	 */
	@Inject
	protected UserTransaction ut;

	/**
	 * Metodo que permite prevalidar datos antes de realizar operación
	 * 
	 * @throws BusinessException
	 */
	protected abstract void preValidate() throws BusinessException;

	/**
	 * Método que permite realizar la ejecución del correspondiente comando enviado
	 * 
	 * @throws BusinessException
	 */
	protected abstract void executeCommand() throws BusinessException;

	/**
	 * Retorna resultado esperado de salida
	 * 
	 * @return Objeto de tipo P
	 */
	public abstract O getOut();

	public void execute() throws BusinessException {
		LOG.info("Inicia ejecución de comando");
		boolean tx = false;
		try {
			if (ut.getStatus() == Status.STATUS_NO_TRANSACTION) {
				ut.begin();
				tx = true;
			}
			preValidate();
			if (isValid) {
				executeCommand();
			}
			if (tx) {
				ut.commit();
			}
			LOG.info("Finalizó ejecución de comando");
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e1) {
			throw new BusinessException(e1);
		}
	}

	public void setInput(I input) {
		this.input = input;
	}

}
