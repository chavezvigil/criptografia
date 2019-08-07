package edu.udb.cri.controller;

import java.io.IOException;
import java.util.function.UnaryOperator;
import javax.swing.JOptionPane;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import edu.udb.cri.MainApp;
import edu.udb.cri.utils.Symmetric;
import edu.udb.cri.utils.UtilMessage;

public class SymmetricEncryptionController {
    //Código interno que representa el algoritmo.
    public static int ALGORITMO_AES = 20;
    public static int ALGORITMO_DES = 22;
    private int flagAlg = 0;
    private int flagEnaProperty = 0;
    private String inputStrModOpe = null;
    private String inputStrTamBit = null;   

    
    @FXML
    private RadioButton rbCFBAes;
    @FXML
    private RadioButton rbOFBAes;
    @FXML
    private RadioButton rbECBAes;
    @FXML
    private RadioButton rb256Aes;
    @FXML
    private RadioButton rb192Aes;
    @FXML
    private RadioButton rbCBCAes;
    @FXML
    private RadioButton rb128Aes;
    @FXML
    private RadioButton rbCFBDes;
    @FXML
    private RadioButton rbOFBDes;
    @FXML
    private RadioButton rbCBCDes;
    @FXML
    private RadioButton rbECBDes;   

    @FXML
    private TextField txtVectorAes;
    @FXML
    private TextField txtKeyCifraAes;
    @FXML
    private TextField txtKeyDescifraAes;
    @FXML
    private TextField txtInputSinAes;
    @FXML
    private TextField txtKeyDescifraDes;
    @FXML
    private TextField txtVectorDes;
    @FXML
    private TextField txtInputSinDes;    
    @FXML
    private TextArea txtOutputSinDes;
    @FXML
    private TextField txtKeyCifraDes;
    @FXML
    private TextArea txtOutputConDes;
    @FXML
    private TextArea txtOutputConAes;
    @FXML
    private TextArea txtOutputSinAes;
    
    @FXML
    private Button btnCifradoDes;
    @FXML
    private Button btnCifradoAes;
    @FXML
    private Button btnDecifradoDes;
    @FXML
    private Button btnDescifradoAes;

    @FXML
    private ToggleGroup groupRbModOpAes;    
    @FXML
    private ToggleGroup groupRbTamBitAes;
    @FXML
    private ToggleGroup groupRbModOpDes;    

    @FXML
    private Tab tabSelectAes;    
    @FXML
    private Tab tabSelectDes;
    
    
    @FXML
    void EncryptionDes(ActionEvent event) {
		String inputStrSinDes = txtInputSinDes.getText().toString();
		String inputStrTamBit = "56";
		String inputStrVecIni = txtVectorDes.getText().toString();
		String inputStrKeyCif = txtKeyCifraDes.getText().toString();
		
		try {
	    	if(rbCBCDes.isSelected()){
	    		inputStrModOpe = rbCBCDes.getText();
	    	}else if(rbCFBDes.isSelected()){
	    		inputStrModOpe = rbCFBDes.getText();
	    	}else if(rbECBDes.isSelected()){
	    		inputStrModOpe = rbECBDes.getText();
	    	}else if(rbOFBDes.isSelected()){
	    		inputStrModOpe = rbOFBDes.getText();
	    	}
	    	
	    	if (inputStrSinDes.length() != 0 && inputStrModOpe != null && inputStrKeyCif.length() != 0) {
	    		Symmetric objSimmControl = new Symmetric(inputStrTamBit, inputStrModOpe, ALGORITMO_DES, inputStrVecIni);
	    		txtOutputConDes.setText(objSimmControl.cifrar(inputStrSinDes,inputStrKeyCif,inputStrVecIni));
	    		this.flagAlg = ALGORITMO_DES;
	    		this.flagEnaProperty = 2;
	    		disableProperty();
	    		
	    	}else {
	    		Alert alert = new Alert(AlertType.CONFIRMATION,	
	    				UtilMessage.getMensaje("edu.udb.cri.system.alert.information.encryption"), ButtonType.YES,
	    				ButtonType.CANCEL);alert.showAndWait();
	    	}			
		} catch (Exception ex) {
			Alert alert = new Alert(AlertType.ERROR, ex.getMessage());
			alert.showAndWait();
		}
    }

    @FXML
    void DecodeDes(ActionEvent event) {
		String inputStrCifDes = txtOutputConDes.getText().toString();
		String inputStrTamBit = "56";
		String inputStrVecIni = txtVectorDes.getText().toString();
		String inputKeyDescifraDes = txtKeyDescifraDes.getText().toString();
		
		try {
	    	if(rbCBCDes.isSelected()){
	    		inputStrModOpe = rbCBCDes.getText();
	    	}else if(rbCFBDes.isSelected()){
	    		inputStrModOpe = rbCFBDes.getText();
	    	}else if(rbECBDes.isSelected()){
	    		inputStrModOpe = rbECBDes.getText();
	    	}else if(rbOFBDes.isSelected()){
	    		inputStrModOpe = rbOFBDes.getText();
	    	}
	    	
	    	if (inputStrCifDes.length() != 0 && inputStrModOpe != null && inputKeyDescifraDes.length() != 0) 
	    	{
	    		Symmetric objSimmControl = new Symmetric(inputStrTamBit, inputStrModOpe, ALGORITMO_DES, inputStrVecIni);
	    		txtOutputSinDes.setText(objSimmControl.descifrar(inputStrCifDes,inputKeyDescifraDes,inputStrVecIni));

	    	}else {
	    		Alert alert = new Alert(AlertType.CONFIRMATION,	
	    				UtilMessage.getMensaje("edu.udb.cri.system.alert.information.decode"), ButtonType.YES,
	    				ButtonType.CANCEL);alert.showAndWait();
	    	}			
		} catch (Exception ex) {
			Alert alert = new Alert(AlertType.ERROR, ex.getMessage());
			alert.showAndWait();
		}

    }

    @FXML
    void EncryptionAes(ActionEvent event) {
		String inputStrSinAes = txtInputSinAes.getText().toString();
		String inputStrVecIni = txtVectorAes.getText().toString();
		String inputStrKeyCif = txtKeyCifraAes.getText().toString();
		
		try {	    	
	    	if(rbCBCAes.isSelected()){
	    		inputStrModOpe = rbCBCAes.getText();
	    	}else if(rbCFBAes.isSelected()){
	    		inputStrModOpe = rbCFBAes.getText();
	    	}else if(rbECBAes.isSelected()){
	    		inputStrModOpe = rbECBAes.getText();
	    	}else if(rbOFBAes.isSelected()){
	    		inputStrModOpe = rbOFBAes.getText();
	    	}
	    	
	    	if (inputStrSinAes.length() != 0 && inputStrKeyCif.length() != 0 && inputStrTamBit != null 
	    			&& inputStrModOpe != null) 
	    	{
	    		Symmetric objSimmControl = new Symmetric(inputStrTamBit, inputStrModOpe, ALGORITMO_AES, inputStrVecIni);
	    		txtOutputConAes.setText(objSimmControl.cifrar(inputStrSinAes,inputStrKeyCif, inputStrVecIni));
	    		this.flagAlg = ALGORITMO_AES;
	    		this.flagEnaProperty = 2;
	    		disableProperty();
	    	}else {
	    		Alert alert = new Alert(AlertType.CONFIRMATION,	
	    				UtilMessage.getMensaje("edu.udb.cri.system.alert.information.encryption"), ButtonType.YES,
	    				ButtonType.CANCEL);alert.showAndWait();
	    	}			
		} catch (Exception ex) {
			Alert alert = new Alert(AlertType.ERROR, ex.getMessage());
			alert.showAndWait();
		}		
    }

    @FXML
    void DecodeAes(ActionEvent event) {
		String inputStrCifAes = txtOutputConAes.getText().toString();
		String inputStrVecIni = txtVectorAes.getText().toString();
		String inputStrKeyCif = txtKeyDescifraAes.getText().toString();
		
		try {	    	
	    	if(rbCBCAes.isSelected()){
	    		inputStrModOpe = rbCBCAes.getText();
	    	}else if(rbCFBAes.isSelected()){
	    		inputStrModOpe = rbCFBAes.getText();
	    	}else if(rbECBAes.isSelected()){
	    		inputStrModOpe = rbECBAes.getText();
	    	}else if(rbOFBAes.isSelected()){
	    		inputStrModOpe = rbOFBAes.getText();
	    	}
	    	
	    	if (inputStrCifAes.length() != 0 && inputStrKeyCif.length() != 0 && inputStrTamBit != null 
	    			&& inputStrModOpe != null) {
	    			Symmetric objSimmControl = new Symmetric(inputStrTamBit, inputStrModOpe, ALGORITMO_AES, inputStrVecIni);
	    			txtOutputSinAes.setText(objSimmControl.descifrar(inputStrCifAes, inputStrKeyCif, inputStrVecIni));
	    		
	    	}else {
	    		Alert alert = new Alert(AlertType.CONFIRMATION,	
	    				UtilMessage.getMensaje("edu.udb.cri.system.alert.information.decode"), ButtonType.YES,
	    				ButtonType.CANCEL);alert.showAndWait();
	    	}			
		} catch (Exception ex) {
			Alert alert = new Alert(AlertType.ERROR, ex.getMessage());
			alert.showAndWait();
		}
    }
    
    @FXML
    void CleanPropertyAes(ActionEvent event) {
    	this.flagAlg = ALGORITMO_AES;
    	this.flagEnaProperty = 1;
    	disableProperty();
    }
    
    @FXML
    void CleanPropertyDes(ActionEvent event) {
    	this.flagAlg = ALGORITMO_DES;
    	this.flagEnaProperty = 1;
    	disableProperty();
    }

    @FXML
    private int disableProperty() {
    	switch (flagEnaProperty) {
		case 1: //habilitar propiedades
	    	if ( this.flagAlg == 20) {
	        	txtInputSinAes.setDisable(false);
	        	txtKeyCifraAes.setDisable(false);
	        	if (inputStrModOpe.contentEquals("ECB")) {
	        		txtVectorAes.setDisable(true);
	        	}else {
	        		txtVectorAes.setDisable(false);
	        	}
	        	rbECBAes.setDisable(false);
	        	rbECBAes.setDisable(false);
	        	rbCBCAes.setDisable(false);
	        	rbCFBAes.setDisable(false);
	        	rbOFBAes.setDisable(false);  
	        	rb128Aes.setDisable(false);
	        	rb192Aes.setDisable(false);
	        	rb256Aes.setDisable(false);
	    	}else if(this.flagAlg == 22) {
	        	txtInputSinDes.setDisable(false);
	        	txtKeyCifraDes.setDisable(false);
	        	if (inputStrModOpe.contentEquals("ECB")) {
	        		txtVectorDes.setDisable(true);
	        	}else {
	        		txtVectorDes.setDisable(false);  
	        	}
	        	rbECBDes.setDisable(false);
	        	rbECBDes.setDisable(false);
	        	rbCBCDes.setDisable(false);
	        	rbCFBDes.setDisable(false);
	        	rbOFBDes.setDisable(false);
	    	}
			break;
		case 2://Deshabilitar propiedades
	    	if ( this.flagAlg == 20) {
	        	txtInputSinAes.setDisable(true);
	        	txtKeyCifraAes.setDisable(true);
	        	txtVectorAes.setDisable(true);
	        	rbECBAes.setDisable(true);
	        	rbECBAes.setDisable(true);
	        	rbCBCAes.setDisable(true);
	        	rbCFBAes.setDisable(true);
	        	rbOFBAes.setDisable(true);  
	        	rb128Aes.setDisable(true);
	        	rb192Aes.setDisable(true);
	        	rb256Aes.setDisable(true);
	    	}else if(this.flagAlg == 22) {
	        	txtInputSinDes.setDisable(true);
	        	txtKeyCifraDes.setDisable(true);
	        	txtVectorDes.setDisable(true);
	        	rbECBDes.setDisable(true);
	        	rbECBDes.setDisable(true);
	        	rbCBCDes.setDisable(true);
	        	rbCFBDes.setDisable(true);
	        	rbOFBDes.setDisable(true);
	    	}
			break;
		}
    	
    	return 0;
    }
    
    
    public static void limitTextField(TextField textField, int limit) {
        UnaryOperator<Change> textLimitFilter = change -> {
            if (change.isContentChange()) {
                int newLength = change.getControlNewText().length();
                if (newLength > limit) {
                    String trimmedText = change.getControlNewText().substring(0, limit);
                    change.setText(trimmedText);
                    int oldLength = change.getControlText().length();
                    change.setRange(0, oldLength);
                }
            }
            return change;
        };
        textField.setTextFormatter(new TextFormatter<Object>(textLimitFilter));
    } 
    
	@FXML
	private void initialize() {
		this.flagAlg = ALGORITMO_DES;
		inputStrTamBit = rb128Aes.getText();
		limitTextField(txtKeyCifraAes, 16);
		limitTextField(txtKeyCifraDes, 8);
		txtVectorDes.setDisable(true);
		txtVectorAes.setDisable(true);
	}
    
    @FXML
    void radioSelect(ActionEvent event) {
    	if (this.flagAlg == 22) {
	    	txtKeyCifraDes.setText("");
	    	txtVectorDes.setText("");
	    	txtOutputConDes.setText("");
	    	txtKeyDescifraDes.setText("");
	    	txtOutputSinDes.setText("");
	    	
	    	
	    	if(rbECBDes.isSelected()){
    			txtVectorDes.setDisable(true);
    			limitTextField(txtVectorDes, 8);
	    	}else if(rbCBCDes.isSelected()){
	    		txtVectorDes.setDisable(false);
    			limitTextField(txtVectorDes, 8);
	    	}else if(rbCFBDes.isSelected()){
	    		txtVectorDes.setDisable(false);
    			limitTextField(txtVectorDes, 8);
	    	}else if(rbOFBDes.isSelected()){
	    		txtVectorDes.setDisable(false);
    			limitTextField(txtVectorDes, 8);
	    	}
    	}else {
	    	txtKeyCifraAes.setText("");
	    	txtVectorAes.setText("");
	    	txtOutputConAes.setText("");
	    	txtKeyDescifraAes.setText("");
	    	txtOutputSinAes.setText("");
	    	
	    	if (rb128Aes.isSelected()) {
	    		inputStrTamBit = rb128Aes.getText();
	    		limitTextField(txtKeyCifraAes, 16);
	    		limitTextField(txtVectorAes, 16);
	    	}else if (rb192Aes.isSelected()) {
	    		inputStrTamBit = rb192Aes.getText();
	    		limitTextField(txtKeyCifraAes, 24);
	    		limitTextField(txtVectorAes, 16);
	    	}else if (rb256Aes.isSelected()) {
	    		inputStrTamBit = rb256Aes.getText();
	    		limitTextField(txtKeyCifraAes, 32);
	    		limitTextField(txtVectorAes, 16);
	    	}
	    	
	    	if(rbECBAes.isSelected()){
    			txtVectorAes.setDisable(true);
	    	}else if(rbCBCAes.isSelected()){
	    		txtVectorAes.setDisable(false);
	    	}else if(rbCFBAes.isSelected()){
	    		txtVectorAes.setDisable(false);
	    	}else if(rbOFBAes.isSelected()){
	    		txtVectorAes.setDisable(false);
	    	}
    	}
    }
    
    @FXML
    void openTabAes(Event event) {
    	String tbCifrado = tabSelectAes.getText().toString();
    	String strAlgoritmo = tbCifrado.substring(8, 11);		
    	if(strAlgoritmo.contentEquals("AES")) {
    		this.flagAlg = ALGORITMO_AES;
    	}
    }
    
    @FXML
    void openTabDes(Event event) {
    	String tbCifrado = tabSelectDes.getText().toString();
    	String strAlgoritmo = tbCifrado.substring(8, 11);		
    	if(strAlgoritmo.contentEquals("DES")) {
    		this.flagAlg = ALGORITMO_DES;
    	}
    }
    
}
