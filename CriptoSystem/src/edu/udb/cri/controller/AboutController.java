package edu.udb.cri.controller;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.udb.cri.MainApp;
import edu.udb.cri.utils.UtilMessage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
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
			fileChooser.setTitle(UtilMessage.getMensaje("edu.udb.cri.system.document.manual.text"));
			fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
			fileChooser.setInitialFileName(UtilMessage.getMensaje("edu.udb.cri.system.document.manual.name"));
			File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());
			if (file != null) {
				// Make sure it has the correct extension
				file = new File(file.getPath());
				URL manualUrl = AboutController.class.getResource(pathManual);
				URLConnection conn = (URLConnection) manualUrl.openConnection();
				InputStream is = conn.getInputStream();							
				Files.copy(is, file.toPath());
				Alert msgVerify = new Alert(AlertType.INFORMATION,
						UtilMessage.getMensaje("edu.udb.cri.system.alert.information.manual"));
				msgVerify.showAndWait();
			}
		} catch (Exception exception) {
			LOGGER.log(Level.SEVERE, "Exception occur", exception);
		}
	}

}
