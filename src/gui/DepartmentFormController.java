package gui;

import java.net.URL;
import java.util.ResourceBundle;

import db.DbException;
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
import model.services.DepartmentService;

public class DepartmentFormController implements Initializable {
	
	
	private Department depEntity; // INJECAO DE DEPENCIA DA CLASSE DEPARTMENT
	
	private DepartmentService depService;
	

	@FXML
	private TextField txtId; // CRIA OS OBJETOS CONTROLLERS DA VIEW
	@FXML
	private TextField txtName;
	@FXML
	private Label labelERROR;
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
		Utils.currentStage(event).close();; // FECHA A JANELA ATUAL VIEW
		}
		catch (DbException e) {
			Alerts.showAlert("Error save object",null, e.getMessage(), AlertType.ERROR);
		}
	}
	//PEGA OS DADOS ID E NAME DO FORMULARIO -> ENTITY
	private Department getFormData() {
		Department obj = new Department(); // CRIA OBJETO DEPARTMENT VAZIO
		
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		obj.setName(txtName.getText());
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

		
	
}
