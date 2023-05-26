package model.exceptions;

import java.util.HashMap;
import java.util.Map;

//CLASSE RESPONSAVEL POR VALIDAÇÃO DO FORM

public class ValidationException extends RuntimeException{

		private static final long serialVersionUID = 1L;
		
		private Map<String, String> errors = new HashMap<>(); //INSTANCIA UM OBJ DE ERROS
		
		public ValidationException(String msg) {
			super(msg);
		}

		public Map<String, String> getErrors() {
			return errors;
		}
		
		public void addErrors (String fieldName, String errorMessage) {
			errors.put(fieldName, errorMessage);
		}
	
	

	
	
}
