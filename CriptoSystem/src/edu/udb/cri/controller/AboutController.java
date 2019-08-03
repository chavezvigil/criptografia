package edu.udb.cri.controller;

import java.io.File;
import java.net.URL;

import javax.swing.JOptionPane;

import edu.udb.cri.utils.UtilMessage;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class AboutController extends Application{
	private String rutRelativa = "recursos\\keystore\\Manual_Usuario.pdf";
	private HostServices hostServices;
	private File file;

    @FXML
    private Button btnGuiaUsuario;
    
    @FXML
    private ImageView imgLogo;

	@FXML
	private void initialize() {
	    URL imgLogos = getClass().getResource(UtilMessage.getMensaje("edu.udb.cri.system.icon.logo"));
	    Image imageLogos = new Image(imgLogos.toString());
	    imgLogo.setImage(imageLogos);
	    
	    
	    URL imgOpen = getClass().getResource(UtilMessage.getMensaje("edu.udb.cri.system.icon.openPdf"));
	    Image imageOpen = new Image(imgOpen.toString());
	    btnGuiaUsuario.setGraphic(new ImageView(imageOpen));	
	}
	
    @FXML
    void getGuiaUsuario(ActionEvent event) {   	
    	file = new File(rutRelativa);
    	try {
			if (file.exists()) {
		        hostServices = getHostServices();
		        hostServices.showDocument(file.getAbsolutePath());
			}else {
				JOptionPane.showMessageDialog(null,"Archivo no existe, en ruta relativa");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
    }

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
	}
	
	/*public AboutController(){
	    URL imgDigital = getClass().getResource(UtilMessage.getMensaje("edu.udb.cri.system.icon.digital.sign"));
	    Image imageDigital = new Image(imgDigital.toString());
	    btnGuiaUsuario.setGraphic(new ImageView(imageDigital));
	}*/


}
