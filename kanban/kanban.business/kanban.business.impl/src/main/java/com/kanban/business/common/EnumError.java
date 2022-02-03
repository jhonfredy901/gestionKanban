package com.kanban.business.common;

public enum EnumError {

	ERR_100(100, "Error en la ejecucion del servicio ->> {}  parámetros->> {}  error ->> {}"),
	ERR_101(101, "El parámetro #param# no tiene valor, revise por favor los datos de entrada!."),
	ERR_102(102, "No fue posible almacenar el registro, verifíque los datos en intente nuevamente. {}"),
	ERR_103(103, "No fue posible actualizar el registro, verifíque los datos en intente nuevamente. {}"),
	ERR_104(104, "No fue posible borrar el registro, verifíque los datos en intente nuevamente. {}"),
	ERR_105(105, "No fue posible obtener el registro, verifíque los datos en intente nuevamente."),
	ERR_106(106, "El registro ya existe, verifíque los datos en intente nuevamente."),
	ERR_107(107, "Verifique la información ingresada, no se encontró usuario con identificación enviada."),
	ERR_108(108, "Contraseña incorrecta por favor valide y vuelva a intentar.")
	
	
	;

	private final int num;
	private final String value;

	private EnumError(int num, String value) {
		this.num = num;
		this.value = value;
	}

	/**
	 * @return the num
	 */
	public int getNum() {
		return num;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Se concatena el numero del errro y sumensaje
	 * 
	 * @return
	 */
	public String getMessage() {
		StringBuilder message = new StringBuilder();
		message.append(num).append(": ").append(value);
		return message.toString();
	}

}
