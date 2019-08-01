package edu.udb.cri.controller;

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

	private String[] arrTrisilabos;

    @FXML
    void StartFrecuency(ActionEvent event) {
    	String inputStrCifrado = txtStrCifrado.getText().toString();
    	String strTrisilabaA;
    	String strTrisilabaB;
    	String strTrisilaba=null;
    	int numPosicionIni = 0;
    	int numPosicionFin = 0;
    	int numRepeticiones = 0;
    	arrTrisilabos = new String[inputStrCifrado.length()];
    	
    	try {
        	if (inputStrCifrado.length() > 6) {        		
        		/*
        		for (int i=0; i<inputStrCifrado.length()-3; i++) {
        			strTrisilaba = inputStrCifrado.substring(i,i+3);
        			//numRepeticiones = inputStrCifrado.indexOf(strTrisilaba);
        			arrTrisilabos[i] = strTrisilaba.toString();
        			//strEncontrada = inputStrCifrado.indexOf(strTrisilaba);
        			if (strEncontrada > -1) {
        				txtStrAnalizado.setText(strTrisilaba);
        			}
        			
				}*/
        		
        		for (int a=0; a<inputStrCifrado.length()-2; a++) {
        			strTrisilabaA = inputStrCifrado.substring(a,a+3);
        			for (int b=0; b<inputStrCifrado.length()-2; b++) {
        				strTrisilabaB = inputStrCifrado.substring(b,b+3);
        				if(strTrisilabaA.contentEquals(strTrisilabaB)) {
        					numRepeticiones = numRepeticiones+1;        					
        					if(numRepeticiones==2) { 						
        						numPosicionIni= b+3;
        						strTrisilaba = strTrisilabaA;
        					}else if(numRepeticiones==3) {
        						numPosicionFin = b;
        					}        					
        				}
        			}
					txtStrAnalizado.setText(strTrisilaba +", PosicionIni: " + numPosicionIni + ", PosicionFin: " +
					numPosicionFin + ", numRepeciones: " + numRepeticiones + " Distancia : " + (numPosicionFin-numPosicionIni));
        			numRepeticiones = 0;
        			numPosicionIni = 0;
        			numPosicionFin = 0;	
        		}
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
