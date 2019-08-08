package edu.udb.cri.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.udb.cri.MainApp;
import edu.udb.cri.utils.UtilMessage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class AboutController {
	
	private String pathManual = UtilMessage.getMensaje("edu.udb.cri.system.document.manual");
	private static final Logger LOGGER = Logger.getLogger(AboutController.class.getName());
	private MainApp mainApp;
	
	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	@FXML
	private Button btnGuiaUsuario;
	@FXML
	private ImageView imgLogo;
	@FXML
	private Label textoLicencia;
	@FXML
	private Label textoContactos;

	@FXML
	private void initialize() {
		URL imgLogos = getClass().getResource(UtilMessage.getMensaje("edu.udb.cri.system.icon.logo.original"));
		Image imageLogos = new Image(imgLogos.toString());
		imgLogo.setImage(imageLogos);

		URL imgOpen = getClass().getResource(UtilMessage.getMensaje("edu.udb.cri.system.icon.openPdf"));
		Image imageOpen = new Image(imgOpen.toString());
		btnGuiaUsuario.setGraphic(new ImageView(imageOpen));
		btnGuiaUsuario.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				descargarManual();
			}
		});

		inicioTextos();
	}

	public void inicioTextos() {
		textoLicencia.setText(UtilMessage.getMensaje("edu.udb.cri.abt.msg.license"));
		textoContactos.setText(UtilMessage.getMensaje("edu.udb.cri.abt.msg.contact"));
	}

	public void descargarManual() {
		try {
			FileChooser fileChooser = new FileChooser();

			// Set extension filter
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
			fileChooser.getExtensionFilters().add(extFilter);
			// Show save file dialog
			File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());
			if (file != null) {
				// Make sure it has the correct extension
				if (!file.getPath().endsWith(".pdf")) {
					file = new File(file.getPath() + ".pdf");
					// SaveKeyStore(file, "1234".toCharArray());
					//SaveFile("Puesb archivoa", file);
				}

			}
		} catch (Exception exception) {
			LOGGER.log(Level.SEVERE, "Exception occur", exception);
		}
	}
	
	public void SaveFile(String content, File file) {
		try {
			FileWriter fileWriter = null;

			fileWriter = new FileWriter(file);
			fileWriter.write(content);
			fileWriter.close();
		} catch (IOException ex) {
			ex.getMessage();
		}

	}

}
