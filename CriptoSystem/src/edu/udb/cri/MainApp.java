package edu.udb.cri;

import java.io.IOException;

import edu.udb.cri.view.DigitalSingController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("CriptoSystem");
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
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
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
			loader.setLocation(MainApp.class.getResource("view/InicioOverview.fxml"));
			AnchorPane inicioOverview = (AnchorPane) loader.load();

			// Set begin overview into the center of root layout.
			rootLayout.setCenter(inicioOverview);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showDigitalSingOverview() {
	    try {
	        // Load digital sing overview.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource("view/DigitalSing.fxml"));
	        AnchorPane digitalSingOverview = (AnchorPane) loader.load();

	        // Set digital sing overview into the center of root layout.
	        rootLayout.setCenter(digitalSingOverview);

	        // Give the controller access to the main app
	        DigitalSingController controller = loader.getController();
	        controller.setMainApp(this);

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

}
