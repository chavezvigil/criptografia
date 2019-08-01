package edu.udb.cri.controller;

import java.awt.Button;
import java.awt.event.ActionEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javafx.fxml.FXML;

public class AboutController {
	
    @FXML
    private Button btnGuiaUsuario;

    @FXML
    void getGuiaUsuario(ActionEvent event) {
    	try {
    		  FileWriter fl = new FileWriter("test.txt");
    		  PrintWriter pw = new PrintWriter(fl);

    		  for(int i = 0; i < 10; i++) {
    		    pw.println(i + 1);
    		    System.out.println(i + 1);
    		  }

    		  pw.close();
    		} catch(IOException e) {
    		  e.printStackTrace();
    		}
    }
}
