package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;
import model.exceptions.ValidationException;
import model.services.DepartmentService;

public class DepartmentFormController implements Initializable {
	
	
	private Department depEntity; // INJECAO DE DEPENCIA DA CLASSE DEPARTMENT
	
	private DepartmentService depService;
	
	private List<DataChangeListener> dataChangeListDep = new ArrayList<>();
	

	@FXML
	private TextField txtId; // CRIA OS OBJETOS CONTROLLERS DA VIEW
	@FXML
	private TextField txtName;
	@FXML
	private Label labelErrorName;
	@FXML
	private Button btSave;
	@FXML
	private Button btCancel;
	
	// METODO SETTER DO DEPARTMENT
	public void setDepartment(Department depEntity) {
		this.depEntity = depEntity;
	}
	
	// METODO SETTER DEPARTMENT SERVICE
	public void setDepService(DepartmentService depService) {
		this.depService = depService;
	}
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListDep.add(listener);
	}
	
		
    // CRIA OS METODOS SAVE E CANCEL
	@FXML
	public void onBtSaveAction(ActionEvent event) {
		if (depEntity == null) {
			throw new IllegalStateException("Entity was null");
		}
		if (depService == null) {
			throw new IllegalStateException("Service was null");
		}
		try {
		depEntity = getFormData(); // SALVA A ENTIDADE
		depService.saveOrUpdate(depEntity); // SALVA A ENTIDADE
		notifyDataChangeListeners();
		Utils.currentStage(event).close();; // FECHA A JANELA ATUAL VIEW
		}
		catch (ValidationException e) {
			setErrorMessages(e.getErrors());
		}
		catch (DbException e) {
			Alerts.showAlert("Error save object",null, e.getMessage(), AlertType.ERROR);
		}
	}
	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListDep) {
			listener.onDataChanged();
		}
		
	}

	//PEGA OS DADOS ID E NAME DO FORMULARIO -> ENTITY
	private Department getFormData() {
		Department obj = new Department(); // CRIA OBJETO DEPARTMENT VAZIO
		
		ValidationException exception = new ValidationException("Erro de vallidação"); //INSTANCIA UMA EXCEÇÃO
		
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		
		//CONDICÃO - NOME NÃO PODE SER NULO OU VAZIO
		if (txtName.getText() == null || txtName.getText().trim().equals("")) {
		exception.addErrors("name", "Campo não pode ser vazio"); // LANÇA A EXCEÇÃO
		}
		obj.setName(txtName.getText());
		
		if (exception.getErrors().size() > 0) {
			throw exception; // LANÇA EXCEÇÃO
		}
		
		return obj;
	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();; // FECHA A JANELA ATUAL VIEW
	}
	
	
	//METODO PUBLICO INITIALIZE
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes(); //CHAMA O METODO ESPECIFICO
	}

	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId); //CHAMA A RESTRIÇÃO CONTRAINTS ID NUMERO
		Constraints.setTextFieldMaxLength(txtName, 30); // CHAMA A CLASSE DE RESTRIÇÃO CONSTRAINTS
	}
	
	//UPDATE METODO DE ADIÇÃO
	
	public void updateFormData() {
		if(depEntity == null) {
			throw new IllegalStateException("DepEntity estava nula"); //TRATAMENTO DA EXCEÇÃO
		}
		txtId.setText(String.valueOf(depEntity.getId())); //UPDATE ID
		txtName.setText(depEntity.getName());             //UPDATE NAME
	}
	
	// CRIA O METODO MENSAGEM DE ERRO
	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet(); // INSTANCIA UMA LISTA DO TIPO SET
		
		if (fields.contains("name")) {
			labelErrorName.setText(errors.get("name"));
		}
	}

		
	
}
