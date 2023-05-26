package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentListController implements Initializable, DataChangeListener {

	private DepartmentService depService; // INJEÇÃO DE DEPENDENCIA

	@FXML
	private TableView<Department> tableViewDeparment;
	@FXML
	private TableColumn<Department, Integer> tableColumnId;
	@FXML
	private TableColumn<Department, String> tableColumnName;

	@FXML
	private TableColumn<Department, Department> tableColumnEDIT; // OBJETO PARA EDITAR / UPDATE

	@FXML
	private Button btNew;

	private ObservableList<Department> obsLista;

	@FXML
	public void onBtAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Department obj = new Department(); // CRIA UM DEPARTAMENTO VAZIO
		createDialogForm(obj, "/gui/DepartmentForm.fxml", parentStage); // CHAMA O METODO CREATE
	}

	// METODO DE DEPENDENCIA DO SERVIÇO
	public void setDepartmentService(DepartmentService depService) {
		this.depService = depService;
	}

	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		initializeNodes();

	}

	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));

		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewDeparment.prefHeightProperty().bind(stage.heightProperty());

	}

	public void upDateTableView() {
		if (depService == null) {
			throw new IllegalStateException(" Serviço está nulo");
		}

		List<Department> lista2 = depService.findAll(); // PUXA A LISTA DO SERVIÇO
		obsLista = FXCollections.observableArrayList(lista2); // TRANSFORMA PARA LISTA GRAFICA (objLista)
		tableViewDeparment.setItems(obsLista); // JOGA A LISTA NA TABELA GRAFICA
		
		initEditButtons(); // EDITAR AS LINHAS / FORMS DA TABELA PARA UPDATE
	}

	// METODO QUE CRIA UM NOVO DEPARTAMENTO
	private void createDialogForm(Department obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			DepartmentFormController controller = loader.getController(); // INSTANCIA CONTROLLER
			controller.setDepartment(obj);
			controller.setDepService(new DepartmentService());
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Entre Department data");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();

		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "ERROR", e.getMessage(), AlertType.ERROR);
		}

	}

	@Override
	public void onDataChanged() {
		upDateTableView();

	}

	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Department, Department>() {

			private final Button button = new Button("edit");

			@Override
			protected void updateItem(Department obj, boolean empty) {

				super.updateItem(obj, empty);

				if (obj == null) {
					setGraphic(null);
					return;

				}

				setGraphic(button);
				button.setOnAction(event -> createDialogForm(

						obj, "/gui/DepartmentForm.fxml", Utils.currentStage(event)));

			}
		});
	}

}
