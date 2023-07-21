package it.polito.tpd.loadoutBuilder;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.loadoutBuilder.model.Emblem;
import it.polito.tdp.loadoutBuilder.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class FXMLController {

	private Model model;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private CheckBox CheckDefSp;

    @FXML
    private Button btnCrea;

    @FXML
    private CheckBox checkAtt;

    @FXML
    private CheckBox checkAttSp;

    @FXML
    private CheckBox checkCrit;

    @FXML
    private CheckBox checkDef;

    @FXML
    private CheckBox checkHP;

    @FXML
    private CheckBox checkSpeed;

    @FXML
    private ComboBox<String> cmbP1;

    @FXML
    private ComboBox<String> cmbP2;

    @FXML
    private ListView<Emblem> listPicker;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCreaBuild(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert CheckDefSp != null : "fx:id=\"CheckDefSp\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCrea != null : "fx:id=\"btnCrea\" was not injected: check your FXML file 'Scene.fxml'.";
        assert checkAtt != null : "fx:id=\"checkAtt\" was not injected: check your FXML file 'Scene.fxml'.";
        assert checkAttSp != null : "fx:id=\"checkAttSp\" was not injected: check your FXML file 'Scene.fxml'.";
        assert checkCrit != null : "fx:id=\"checkCrit\" was not injected: check your FXML file 'Scene.fxml'.";
        assert checkDef != null : "fx:id=\"checkDef\" was not injected: check your FXML file 'Scene.fxml'.";
        assert checkHP != null : "fx:id=\"checkHP\" was not injected: check your FXML file 'Scene.fxml'.";
        assert checkSpeed != null : "fx:id=\"checkSpeed\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbP1 != null : "fx:id=\"cmbP1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbP2 != null : "fx:id=\"cmbP2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert listPicker != null : "fx:id=\"listPicker\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }

	public void setModel(Model model) {
		this.model = new Model();
		this.listPicker.getItems().addAll(this.model.getAllEmblems());
		
	}

}
