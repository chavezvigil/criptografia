package edu.udb.cri.controller;

import javax.swing.JOptionPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import edu.udb.cri.utils.Symmetric;

public class SymmetricEncryptionController {
    //Código interno que representa el algoritmo.
    public final static int ALGORITMO_AES = 20;
    public final static int ALGORITMO_DES = 22;
    private int flagAlg = 0;
    private int flagEnaProperty = 0;
    
    @FXML
    private TextField txtKeyDescifraAes;

    @FXML
    private ToggleGroup groupRbTamBitAes;

    @FXML
    private RadioButton rbOFBDes;

    @FXML
    private TextField txtKeyDescifraDes;

    @FXML
    private RadioButton rbECBDes;

    @FXML
    private TextField txtKeyCifraDes;

    @FXML
    private TextField txtInputSinAes;

    @FXML
    private TextField txtInputSinDes;

    @FXML
    private RadioButton rbECBAes;

    @FXML
    private TextArea txtOutputConDes;

    @FXML
    private Button btnDecifradoDes;

    @FXML
    private RadioButton rb256Aes;

    @FXML
    private RadioButton rb192Aes;

    @FXML
    private TextField txtKeyCifraAes;

    @FXML
    private Button btnCifradoDes;

    @FXML
    private Button btnCifradoAes;

    @FXML
    private RadioButton rbCBCDes;

    @FXML
    private TextArea txtOutputSinDes;

    @FXML
    private TextArea txtOutputSinAes;

    @FXML
    private TextField txtVectorDes;

    @FXML
    private RadioButton rbCBCAes;

    @FXML
    private TextField txtVectorAes;

    @FXML
    private ToggleGroup groupRbModOpAes;

    @FXML
    private RadioButton rb128Aes;

    @FXML
    private Button btnDescifradoAes;

    @FXML
    private ToggleGroup groupRbModOpDes;

    @FXML
    private TextArea txtOutputConAes;

    @FXML
    private RadioButton rbCFBDes;

    @FXML
    private RadioButton rbCFBAes;

    @FXML
    private RadioButton rbOFBAes;
    
    @FXML
    void EncryptionDes(ActionEvent event) {
		String inputStrSinDes = txtInputSinDes.getText().toString();
		String inputStrTamBit = "56";
		String inputStrModOpe = null;
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
	    	
	    	if (inputStrSinDes.length() != 0 && inputStrModOpe != null 
	    			&& inputStrVecIni.length() != 0	&& inputStrKeyCif.length() != 0) {
	    		if (inputStrVecIni != "ECB") {
	    			Symmetric objSimmControl = new Symmetric(inputStrTamBit, inputStrModOpe, ALGORITMO_DES, inputStrVecIni);
	    			txtOutputConDes.setText(objSimmControl.cifrar(inputStrSinDes,inputStrKeyCif,inputStrVecIni));
	    		} else {
	    			Symmetric objSimmControl = new Symmetric(inputStrTamBit, inputStrModOpe, ALGORITMO_DES, null);
	    			txtOutputConDes.setText(objSimmControl.cifrar(inputStrSinDes,inputStrKeyCif,inputStrVecIni));
	    		}
	    		this.flagAlg = ALGORITMO_DES;
	    		this.flagEnaProperty = 2;
	    		disableProperty();
	    	}else {
	    		JOptionPane.showMessageDialog(null,"Insertar texto cifrar y vector inicial");
	    	}			
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null,"Error: " + ex.getMessage());
		}
    }

    @FXML
    void DecodeDes(ActionEvent event) {
		String inputStrCifDes = txtOutputConDes.getText().toString();
		String inputStrTamBit = "56";
		String inputStrModOpe = null;
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
	    	
	    	if (inputStrCifDes.length() != 0 && inputStrModOpe != null 
	    			&& inputKeyDescifraDes.length() != 0 && inputStrVecIni.length() !=0) {
	    		if (inputStrModOpe != "ECB") {
	    			Symmetric objSimmControl = new Symmetric(inputStrTamBit, inputStrModOpe, ALGORITMO_DES, inputStrVecIni);
	    			txtOutputSinDes.setText(objSimmControl.descifrar(inputStrCifDes,inputKeyDescifraDes,inputStrVecIni));
	    		} else {
	    			Symmetric objSimmControl = new Symmetric(inputStrTamBit, inputStrModOpe, ALGORITMO_DES, null);
	    			txtOutputSinDes.setText(objSimmControl.descifrar(inputStrCifDes,inputKeyDescifraDes,inputStrVecIni));
	    		}
	    	}else {
	    		JOptionPane.showMessageDialog(null,"Insertar texto cifrar y vector inicial");
	    	}			
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null,"Error: " + ex.getMessage());
		}

    }

    @FXML
    void EncryptionAes(ActionEvent event) {
		String inputStrSinAes = txtInputSinAes.getText().toString();
		String inputStrTamBit = null;
		String inputStrModOpe = null;
		String inputStrVecIni = txtVectorAes.getText().toString();
		String inputStrKeyCif = txtKeyCifraAes.getText().toString();
		
		try {
	    	if (rb128Aes.isSelected()) {
	    		inputStrTamBit = rb128Aes.getText();
	    	}else if (rb192Aes.isSelected()) {
	    		inputStrTamBit = rb192Aes.getText();
	    	}else if (rb256Aes.isSelected()) {
	    		inputStrTamBit = rb256Aes.getText();
	    	}
	    	
	    	if(rbCBCAes.isSelected()){
	    		inputStrModOpe = rbCBCAes.getText();
	    	}else if(rbCFBAes.isSelected()){
	    		inputStrModOpe = rbCFBAes.getText();
	    	}else if(rbECBAes.isSelected()){
	    		inputStrModOpe = rbECBAes.getText();
	    	}else if(rbOFBAes.isSelected()){
	    		inputStrModOpe = rbOFBAes.getText();
	    	}
	    	
	    	if (inputStrSinAes.length() != 0 && inputStrVecIni.length() != 0 && inputStrKeyCif.length() != 0 
	    			&& inputStrTamBit != null && inputStrModOpe != null) {
	    		if(inputStrVecIni != "ECB") {
	    			Symmetric objSimmControl = new Symmetric(inputStrTamBit, inputStrModOpe, ALGORITMO_AES, inputStrVecIni);
	    			txtOutputConAes.setText(objSimmControl.cifrar(inputStrSinAes,inputStrKeyCif, inputStrVecIni));
	    		}else {
	    			Symmetric objSimmControl = new Symmetric(inputStrTamBit, inputStrModOpe, ALGORITMO_AES, null);
	    			txtOutputConAes.setText(objSimmControl.cifrar(inputStrSinAes,inputStrKeyCif, inputStrVecIni));
	    		}
	    		this.flagAlg = ALGORITMO_AES;
	    		this.flagEnaProperty = 2;
	    		disableProperty();
	    	}else {
	    		JOptionPane.showMessageDialog(null,"Error: No se ha insertar texto a cifrar, clave o vector de inicializacion");
	    	}			
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null,"Error: " + ex.getMessage());
		}		
    }

    @FXML
    void DecodeAes(ActionEvent event) {
		String inputStrCifAes = txtOutputConAes.getText().toString();
		String inputStrTamBit = null;
		String inputStrModOpe = null;
		String inputStrVecIni = txtVectorAes.getText().toString();
		String inputStrKeyCif = txtKeyDescifraAes.getText().toString();
		
		try {
	    	if (rb128Aes.isSelected()) {
	    		inputStrTamBit = rb128Aes.getText();
	    	}else if (rb192Aes.isSelected()) {
	    		inputStrTamBit = rb192Aes.getText();
	    	}else if (rb256Aes.isSelected()) {
	    		inputStrTamBit = rb256Aes.getText();
	    	}
	    	
	    	if(rbCBCAes.isSelected()){
	    		inputStrModOpe = rbCBCAes.getText();
	    	}else if(rbCFBAes.isSelected()){
	    		inputStrModOpe = rbCFBAes.getText();
	    	}else if(rbECBAes.isSelected()){
	    		inputStrModOpe = rbECBAes.getText();
	    	}else if(rbOFBAes.isSelected()){
	    		inputStrModOpe = rbOFBAes.getText();
	    	}
	    	
	    	if (inputStrCifAes.length() != 0 && inputStrVecIni.length() != 0 && inputStrKeyCif.length() != 0 
	    			&& inputStrTamBit != null && inputStrModOpe != null) {
	    		if(inputStrVecIni != "ECB") {
	    			Symmetric objSimmControl = new Symmetric(inputStrTamBit, inputStrModOpe, ALGORITMO_AES, inputStrVecIni);
	    			txtOutputSinAes.setText(objSimmControl.descifrar(inputStrCifAes, inputStrKeyCif, inputStrVecIni));
	    		}else {
	    			Symmetric objSimmControl = new Symmetric(inputStrTamBit, inputStrModOpe, ALGORITMO_AES, null);
	    			txtOutputSinAes.setText(objSimmControl.descifrar(inputStrCifAes,inputStrKeyCif, inputStrVecIni));
	    		}
	    		
	    	}else {
	    		JOptionPane.showMessageDialog(null,"Error: No se ha insertar texto a cifrar, clave o vector de inicializacion");
	    	}			
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null,"Error: " + ex.getMessage());
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
	        	txtVectorAes.setDisable(false);
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
	        	txtVectorDes.setDisable(false);
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
    
}
