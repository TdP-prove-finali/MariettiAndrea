package it.polito.tpd.loadoutBuilder;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

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
	private String[] arrParam = {"Attack", "Sp.Atk", "Defense", "Sp.Def","HP","Crit.Rate","Mov.Speed"};
	
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
    private ListView<Emblem> listPicker;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCreaBuild(ActionEvent event) {
    	
    	this.txtResult.clear();
    	
    	
    	//TODO mancano i controlli e tutto
    	
    	String paramPrincipale = this.cmbP1.getValue();
    	// ad ogni posizione dell'array corrisponde un valore booleano che indica se il parametro può essere diminuto o meno
    	// le posizioni sono corrispondenti all'array arrParam
    	Boolean[] arrChecked = new Boolean[this.arrParam.length];
    	arrChecked[0] = this.checkAtt.isSelected();
    	arrChecked[1] = this.checkAttSp.isSelected();
    	arrChecked[2] = this.checkDef.isSelected();
    	arrChecked[3] = this.CheckDefSp.isSelected();
    	arrChecked[4] = this.checkHP.isSelected();
    	arrChecked[5] = this.checkCrit.isSelected();
    	arrChecked[6] = this.checkSpeed.isSelected();
    	
    	//lista placeholder nel caso l'utente avesse scelto alcuni valori manualmente. saranno quelli di partenza per il parziale
    	Set<Emblem> scelti = new HashSet<>();
    	
    	this.model.creaBuild(paramPrincipale,arrChecked, scelti);
    	
    	paramPrincipale = null;
    	List<Emblem> result = new ArrayList<>(this.model.getBuildFinale());
    	Double[] valori = {0.0,0.0,0.0,0.0,0.0,0.0,0.0};
    	for(Emblem e : result) {
    		this.txtResult.appendText(e.toString()+"\n");
    		valori[e.getIdUp()] += e.getVal_up();
    		valori[e.getIdDown()] += e.getVal_down();
    	}
    	
    	this.txtResult.appendText("\nParametri finali:\n");
    	for(int c=0;c<7;c++) {
    		this.txtResult.appendText(this.arrParam[c]+": "+valori[c]+"\n");
    	}
    	
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
        assert listPicker != null : "fx:id=\"listPicker\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }

	public void setModel(Model model) {
		this.model = new Model();
		this.listPicker.getItems().addAll(this.model.getAllEmblems());
		int i=0;
		while(i <this.arrParam.length) {
			this.cmbP1.getItems().add(this.arrParam[i]);
			i++;
		}
		
	}

}
