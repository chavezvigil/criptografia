package edu.udb.cri.controller;

import java.util.ArrayList;

import edu.udb.cri.utils.Analysis;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class FrequencyAnalysisController {
	
    @FXML
    private TextArea txtStrMcd;
    @FXML
    private Button btnAnalizarFre;
    @FXML
    private TextArea txtStrCifrado;
    @FXML
    private Button btnIniciarFre;
    @FXML
    private ComboBox<String> cmbNumSilabas;
	private ArrayList<String> arrLisSilabas;
	Analysis getCadena;	
    @FXML
    private TextArea txtStrResCadenas  = new TextArea();
    
    
	@FXML
	private void initialize() {
		try {
			cmbNumSilabas.getItems().addAll(
	    			"Dos",
	    			"Tres"
	    			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
    @FXML
    void StartFrecuency(ActionEvent event) {
    	String inputStrCifrado = txtStrCifrado.getText().toString();
    	getCadena = new Analysis();
    	String cadenaPrint = new String();
    	
    	try {
        	if(cmbNumSilabas.getValue().contains("Dos")) {
                if (inputStrCifrado.length() > 100) {
             	   arrLisSilabas = getCadena.searchStringTri(inputStrCifrado, 2);                		
         			for ( int I=1 ; I <arrLisSilabas.size() ; I++ ) {
         				int valMostrar = Integer.parseInt(arrLisSilabas.get(I).substring(3,4)) ;
         				if(valMostrar >= 3) {
     					cadenaPrint += arrLisSilabas.get(I) .toString() + "\n";
         				}    					              			
         			}        			
                }
                txtStrResCadenas.setText (cadenaPrint);
                
        	}else {
                   if (inputStrCifrado.length() > 100) {
                	   arrLisSilabas = getCadena.searchStringTri(inputStrCifrado, 3);                		
            			for ( int I=1 ; I <arrLisSilabas.size() ; I++ ) {
            				int valMostrar = Integer.parseInt(arrLisSilabas.get(I).substring(4,5)) ;
            				if(valMostrar >= 3) {
        					cadenaPrint += arrLisSilabas.get(I) .toString() + "\n";
            				}    					              			
            			}        			
                   }
                   txtStrResCadenas.setText (cadenaPrint);
        	}
		} catch (Exception ex) {

		}     	
    }

    
    @FXML
    void AnalyticFrecuency(ActionEvent event) {
    	try {
        	if (arrLisSilabas.isEmpty()) {
        		//mostrar mensaje
        	}else {
        		//Crear array de interos para enviarlos a tra clase
        		getCadena = new Analysis();
        		int cadenaPrint;
        		
            	if(cmbNumSilabas.getValue().contains("Dos")) {
            		cadenaPrint = getCadena.getMcd(arrLisSilabas,2);                		
            		txtStrMcd.setText("El valor de MCD es : " + String.valueOf(cadenaPrint));
            		if (cadenaPrint == 1) {
            			txtStrMcd.setText("No se encontro valor para MCD");
            		}else {
            			txtStrMcd.setText("El valor de MCD es : " + String.valueOf(cadenaPrint));
            		}
            	}else {
            		cadenaPrint = getCadena.getMcd(arrLisSilabas,3);
            		if (cadenaPrint == 1) {
            			txtStrMcd.setText("No se encontro valor para MCD");
            		}else {
            			txtStrMcd.setText("El valor de MCD es : " + String.valueOf(cadenaPrint));
            		}
            	}	
        	}
    	} catch (Exception ex) {
    		
    	}
    }
    
}
