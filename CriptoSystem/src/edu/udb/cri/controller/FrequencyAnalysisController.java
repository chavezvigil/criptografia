package edu.udb.cri.controller;


import java.util.ArrayList;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class FrequencyAnalysisController {
    @FXML
    private Button btnAnalizarFre;

    @FXML
    private TextArea txtStrCifrado;

    @FXML
    private Button btnIniciarFre;

    @FXML
    private Button btnDividirFre;

    @FXML
    private TextArea txtStrDividido;

    @FXML
    private Button btnCrearFre;

    @FXML
    private TextArea txtStrAnalizado;

    @FXML
    private TextArea txtStrAlfabetos;

	private ArrayList<String> arrTrisilabos;
	Analysis getCadena;
	
    @FXML
    private TextArea txtStrResCadenas  = new TextArea();

    @FXML
    void StartFrecuency(ActionEvent event) {
    	String inputStrCifrado = txtStrCifrado.getText().toString();
    	getCadena = new Analysis();
    	
    	try {
        	if (inputStrCifrado.length() > 6) {
        		arrTrisilabos = getCadena.searchString(inputStrCifrado, 3);
        		
        		for ( int I=0 ; I < arrTrisilabos.size() ; I++ ) {
        			System.out.println( arrTrisilabos.get(I)  );
        			txtStrResCadenas.setText(arrTrisilabos.get(I) .toString() );
        		}
        		
        		/*for (int a=0; a<inputStrCifrado.length(); a++) {
        			strTrisilabaA = inputStrCifrado.substring(a,a+3);
        			
        			for (int b=0; a<inputStrCifrado.length(); b++) {
        				strTrisilabaB = inputStrCifrado.substring(b,b+3);
        				
        				if(strTrisilabaA.contains(strTrisilabaB)) {
        					numRepeticiones = numRepeticiones+1;        					
        					if(numRepeticiones == 2) { 						
        						numPosicionIni= b+3;
        						strTrisilaba = strTrisilabaA;
        					}else if(numRepeticiones==3) {
        						numPosicionFin = b;
        					} 
        					if(numRepeticiones >= 2) {
        						System.out.println(strTrisilaba +" : "+ numRepeticiones );
        					}  
        				}
        				b=b+2;
        			}
        			System.out.println(strTrisilabaA  );
        			a=a+2;
        		}*/

        	}else {
        		JOptionPane.showMessageDialog(null,"La logitud de texto debe ser mayor a 100 letras");
        	}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null,"Error: " + ex.getMessage());
		}
    }

    @FXML
    void AnalyticFrecuency(ActionEvent event) {

    }

    @FXML
    void DivideFrecuency(ActionEvent event) {

    }

    @FXML
    void CreatedFrecuency(ActionEvent event) {

    }
    
}
