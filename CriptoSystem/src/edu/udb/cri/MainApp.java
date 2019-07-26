package edu.udb.cri;

import java.io.IOException;

import edu.udb.cri.utils.UtilMessage;
import edu.udb.cri.view.DigitalSingController;
import edu.udb.cri.view.InicioOverviewController;
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
	
	public boolean showDigitalSingOverview() {
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

	        // Give the controller access to the main app
	        DigitalSingController controller = loader.getController();
	        controller.setDialogStage(dialogStage);
	        //controller.setMainApp(this);
	        
	     // Show the dialog and wait until the user closes it
	        dialogStage.showAndWait();

	        return controller.isOkClicked();
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	 /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

}
