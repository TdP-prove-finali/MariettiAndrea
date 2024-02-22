package it.polito.tdp.loadoutBuilder;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.loadoutBuilder.model.Emblem;
import it.polito.tdp.loadoutBuilder.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class FXMLController {
    
	private Model model;
	private String[] arrParam = {"Attack", "Sp.Atk", "Defense", "Sp.Def","HP","Crit.Rate","Mov.Speed"};

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCreaAuto;

    @FXML
    private Button btnCreaManual;

    @FXML
    private ComboBox<Emblem> cmb1;

    @FXML
    private ComboBox<Emblem> cmb10;

    @FXML
    private ComboBox<Emblem> cmb2;

    @FXML
    private ComboBox<Emblem> cmb3;

    @FXML
    private ComboBox<Emblem> cmb4;

    @FXML
    private ComboBox<Emblem> cmb5;

    @FXML
    private ComboBox<Emblem> cmb6;

    @FXML
    private ComboBox<Emblem> cmb7;

    @FXML
    private ComboBox<Emblem> cmb8;

    @FXML
    private ComboBox<Emblem> cmb9;

    @FXML
    private ComboBox<String> cmbP0;

    @FXML
    private ComboBox<String> cmbP1;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCreaBuildAuto(ActionEvent event) {

    }

    @FXML
    void doCreaBuildManual(ActionEvent event) {

    }



    @FXML
    void initialize() {
        //assert btnCrea != null : "fx:id=\"btnCrea\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmb1 != null : "fx:id=\"cmb1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmb10 != null : "fx:id=\"cmb10\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmb2 != null : "fx:id=\"cmb2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmb3 != null : "fx:id=\"cmb3\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmb4 != null : "fx:id=\"cmb4\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmb5 != null : "fx:id=\"cmb5\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmb6 != null : "fx:id=\"cmb6\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmb7 != null : "fx:id=\"cmb7\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmb8 != null : "fx:id=\"cmb8\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmb9 != null : "fx:id=\"cmb9\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbP0 != null : "fx:id=\"cmbP0\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbP1 != null : "fx:id=\"cmbP1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }

    public void setModel(Model model) {
		this.model = new Model();
		int i=0;
		while(i <this.arrParam.length) {
			this.cmbP0.getItems().add(arrParam[i]);
			this.cmbP1.getItems().add(arrParam[i]);
			i++;
		}
		
		List<Emblem> emblemi = new ArrayList<>(this.model.getEmblemi());
		this.cmb1.getItems().addAll(emblemi);
		this.cmb2.getItems().addAll(emblemi);
		this.cmb3.getItems().addAll(emblemi);
		this.cmb4.getItems().addAll(emblemi);
		this.cmb5.getItems().addAll(emblemi);
		this.cmb6.getItems().addAll(emblemi);
		this.cmb7.getItems().addAll(emblemi);
		this.cmb8.getItems().addAll(emblemi);
		this.cmb9.getItems().addAll(emblemi);
		this.cmb10.getItems().addAll(emblemi);		
		
	}
    
    private void reset() {
    	this.cmbP0.setValue(null);
    	this.cmbP1.setValue(null);
    	this.cmb1.setValue(null);
    	this.cmb2.setValue(null);
    	this.cmb3.setValue(null);
    	this.cmb4.setValue(null);
    	this.cmb5.setValue(null);
    	this.cmb6.setValue(null);
    	this.cmb7.setValue(null);
    	this.cmb8.setValue(null);
    	this.cmb9.setValue(null);
    	this.cmb10.setValue(null);
	}
}
