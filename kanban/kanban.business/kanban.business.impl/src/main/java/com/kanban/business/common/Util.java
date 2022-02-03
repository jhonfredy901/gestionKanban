/**
 * 
 */
package com.kanban.business.common;

import java.util.Map;

/**
 * @author jhernandez
 *
 */
public class Util {
	private Util() {
	}

	public static final String PARAM = "#param#";

	/**
	 * Validar si un objeto esta vacio
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean empty(Object obj) {
		if (obj instanceof Map) {
			return ((Map<?, ?>) obj).isEmpty();
		} else if (obj instanceof String) {
			return ((String) obj).isEmpty();
		} else if (obj == null) {
			return true;
		}

		return false;
	}

	/**
	 * 
	 * @param obj
	 * @param param
	 * @throws BusinessException
	 */
	public static void isEmpty(Object obj, String param) throws BusinessException {
		if (empty(obj)) {
			String msg = EnumError.ERR_101.getValue();
			msg = msg.replace(PARAM, param);
			throw new BusinessException(EnumError.ERR_101.getNum(), msg);
		}
	}

}
