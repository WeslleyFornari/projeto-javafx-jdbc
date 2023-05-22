package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

public class MainViewController implements Initializable{
	
	@FXML
	private MenuItem menuItemSeller;
	@FXML
	private MenuItem menuItemDeparment;
	@FXML
	private MenuItem menuItemAbout;
	
	public void onMenuItemSellerAction() {
		System.out.println("MenuItemSeller OK!");
	}
	
	public void onMenuItemSellerDepartment() {
		System.out.println("MenuItemDeparment OK!");
	}
	
	public void onMenuItemSellerAbout() {
		System.out.println("MenuItemAbout OK!");
	}
	
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		
		
	}

}
