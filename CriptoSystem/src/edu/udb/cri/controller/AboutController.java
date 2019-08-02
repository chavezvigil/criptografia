package edu.udb.cri.controller;

import java.io.File;
import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AboutController {
	public File file;
	HostServices hostServices;

    @FXML
    private Button btnGuiaUsuario;

    @FXML
    void getGuiaUsuario(ActionEvent event) {
    	//FileChooser file = new FileChooser();
    	//FileChooser.ExtensionFilter extFile = new FileChooser().getExtensionFilters();
    	file = new File("/CriptoSystem/recursos/keystore/Manual_Usuario.pdf");
    	//hostServices.showDocument(file.getAbsolutePath());
    	//HostServices hostServices.getClass()
    	//hostServices.showDocument(file.getAbsolutePath());
        /*FileChooser fileChooser = new FileChooser();
        
        //Set extension filter
        FileChooser.ExtensionFilter extFilter = 
                new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilter);
          
        //Show open file dialog
        File file = new File("/CriptoSystem/recursos/keystore/Manual_Usuario.pdf");*/

        HostServices hostServices = getHostServices();
        hostServices.showDocument(file.getAbsolutePath());
    }

	private HostServices getHostServices() {
		return hostServices;
	}


}
