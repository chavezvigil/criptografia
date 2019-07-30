package edu.udb.cri;

import java.io.IOException;

import edu.udb.cri.controller.CertificateViewController;
import edu.udb.cri.controller.ConfigurationViewController;
import edu.udb.cri.controller.InicioOverviewController;
import edu.udb.cri.controller.RootLayoutController;
import edu.udb.cri.dto.CertInfoDto;
import edu.udb.cri.utils.UtilMessage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;

	public MainApp() {

	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle(UtilMessage.getMensaje("edu.udb.cri.system.title"));
		this.primaryStage.setResizable(false);
		initRootLayout();
		showInicioOverview();
	}

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Initializes the root layout.
	 */
	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource(UtilMessage.getMensaje("edu.udb.cri.system.view.root")));
			rootLayout = (BorderPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);

			RootLayoutController controller = loader.getController();
			controller.setMainApp(this);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Shows the inicio overview inside the root layout.
	 */
	public void showInicioOverview() {
		try {
			// Load begin overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource(UtilMessage.getMensaje("edu.udb.cri.system.view.init")));
			AnchorPane inicioOverview = (AnchorPane) loader.load();

			// Set begin overview into the center of root layout.
			rootLayout.setCenter(inicioOverview);

			InicioOverviewController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showDigitalSingOverview() {
		try {
			// Load digital sing overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource(UtilMessage.getMensaje("edu.udb.cri.system.view.sign")));
			AnchorPane digitalSingOverview = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle(UtilMessage.getMensaje("edu.udb.cri.system.dialog.sign.title"));
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(digitalSingOverview);
			dialogStage.setScene(scene);
			dialogStage.setResizable(false);

			// Give the controller access to the main app

			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showSymmetricCipherOverview() {
		try {
			// Load digital sing overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource(UtilMessage.getMensaje("edu.udb.cri.system.view.simmetric")));
			AnchorPane symmetricEncryptionOverview = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle(UtilMessage.getMensaje("edu.udb.cri.system.dialog.symmetric.title"));
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(symmetricEncryptionOverview);
			dialogStage.setScene(scene);
			dialogStage.setResizable(false);

			dialogStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showConfigurationOverview() {
		try {
			// Load digital sing overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(
					MainApp.class.getResource(UtilMessage.getMensaje("edu.udb.cri.system.view.configuration")));
			AnchorPane configurationOverview = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle(UtilMessage.getMensaje("edu.udb.cri.system.dialog.configuration.title"));
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(configurationOverview);
			dialogStage.setScene(scene);
			dialogStage.setResizable(false);
			
			ConfigurationViewController  controller = loader.getController();
			controller.setMainApp(this);

			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showCertificateOverview(CertInfoDto cert) {
		try {
			// Load digital sing overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(
					MainApp.class.getResource(UtilMessage.getMensaje("edu.udb.cri.system.view.certificate")));
			AnchorPane certificateOverview = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle(UtilMessage.getMensaje("edu.udb.cri.system.dialog.certificate.title"));
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(certificateOverview);
			dialogStage.setScene(scene);
			dialogStage.setResizable(false);
			
			  // Set the person into the controller.
			CertificateViewController controller = loader.getController();
	        controller.setCertificate(cert);

			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showAboutOverview() {
		try {
			// Load digital sing overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(
					MainApp.class.getResource(UtilMessage.getMensaje("edu.udb.cri.system.view.about")));
			AnchorPane aboutOverview = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle(UtilMessage.getMensaje("edu.udb.cri.system.dialog.help.title"));
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(aboutOverview);
			dialogStage.setScene(scene);
			dialogStage.setResizable(false);
			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the main stage.
	 * 
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

}
