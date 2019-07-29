package edu.udb.cri.view;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyStore;
import java.security.KeyStoreException;

import edu.udb.cri.MainApp;
import edu.udb.cri.dto.CertInfoDto;
import edu.udb.cri.dto.CertificadoDto;
import edu.udb.cri.utils.UseKeyTool;
import edu.udb.cri.utils.UtilMessage;
import edu.udb.cri.utils.Utils;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class ConfigurationViewController {

	private MainApp mainApp;
	private URL keyStoreUrl;
	private String keyStorePass = UtilMessage.getMensaje("edu.udb.cri.keystore.pass");
	
	@FXML
	private Button createKeystoreButton;

	@FXML
	private Button createCertButton;
	@FXML
	private Button resetCertButton;

	@FXML
	private TextField aliasText;
	@FXML
	private TextField commonNameText;
	@FXML
	private TextField organizationText;
	@FXML
	private TextField organizationUnitText;
	@FXML
	private TextField countryText;
	@FXML
	private TextField cityText;
	@FXML
	private TextField stateText;
	@FXML
	PasswordField passField;

	@FXML
	private TableView<CertInfoDto> certTable;
	@FXML
	private TableColumn<CertInfoDto, String> aliasColumn;


	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	@FXML
	private void initialize() {
		// Create button
		initializeGui();
		try {
			File keyStore = new File(UtilMessage.getMensaje("edu.udb.cri.keystore.path.resources.keystore"));
			keyStoreUrl = keyStore.toURI().toURL();
			ObservableList<CertInfoDto> lista = Utils.getAllCerts(keyStoreUrl, keyStorePass);
			certTable.setItems(lista);
			aliasColumn.setCellValueFactory(cellData-> cellData.getValue().getAlias());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}	

	}

	public void initializeGui() {

		URL imgKeystore = getClass().getResource(UtilMessage.getMensaje("edu.udb.cri.system.icon.save.keystore"));
		Image imageKeystore = new Image(imgKeystore.toString());
		createKeystoreButton.setGraphic(new ImageView(imageKeystore));

		URL imgCertificate = getClass().getResource(UtilMessage.getMensaje("edu.udb.cri.system.icon.save.certificate"));
		Image imageCertificate = new Image(imgCertificate.toString());
		createCertButton.setGraphic(new ImageView(imageCertificate));

		URL imgReset = getClass().getResource(UtilMessage.getMensaje("edu.udb.cri.system.icon.reset"));
		Image imageReset = new Image(imgReset.toString());

		resetCertButton.setGraphic(new ImageView(imageReset));
		resetCertButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				restablecerDatos();
			}
		});
	}

	public void restablecerDatos() {
		try {
			Alert alert = new Alert(AlertType.CONFIRMATION,
					UtilMessage.getMensaje("edu.udb.cri.system.alert.confirm.reset"), ButtonType.YES,
					ButtonType.CANCEL);
			alert.showAndWait();

			if (alert.getResult() == ButtonType.YES) {
				resetFields();
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage());
			alert.showAndWait();
		}
	}

	public void resetFields() {
		aliasText.setText("");
		commonNameText.setText("");
		organizationText.setText("");
		organizationUnitText.setText("");
		countryText.setText("");
		cityText.setText("");
		stateText.setText("");
		passField.setText("");
	}

	public void crearCertificadoKeyStore() {
		try {
			String alias = aliasText.getText();
			String commonName = commonNameText.getText();
			String organization = organizationText.getText();
			String organizationUnit = organizationUnitText.getText();
			String country = countryText.getText();
			String city = cityText.getText();
			String state = stateText.getText();
			String password = passField.getText();

			CertificadoDto dto = new CertificadoDto(alias, commonName, organization, organizationUnit, country, city,
					state, password);

			boolean valido = verificarCampos(dto);
			if (valido == true) {
				Alert alert = new Alert(AlertType.CONFIRMATION,
						UtilMessage.getMensaje("edu.udb.cri.system.alert.confirm.cert.save"), ButtonType.YES,
						ButtonType.CANCEL);
				alert.showAndWait();

				if (alert.getResult() == ButtonType.YES) {
					UseKeyTool.createCertificate(dto.getAlias(), dto.getCommonName(), dto.getOrganization(),
							dto.getOrganizationUnit(), dto.getCity(), dto.getState(), dto.getCountry(),
							dto.getPassword());

					Alert msgSuccess = new Alert(AlertType.INFORMATION,
							UtilMessage.getMensaje("edu.udb.cri.system.alert.information.cert"));
					msgSuccess.showAndWait();
					resetFields();

				}
			}

		} catch (Exception ex) {
			Alert alert = new Alert(AlertType.ERROR, ex.getMessage());
			alert.showAndWait();
		}
	}

	public boolean verificarCampos(CertificadoDto dto) {
		boolean valid = true;
		try {

			if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
				valid = false;
				Alert alert = new Alert(AlertType.ERROR,
						UtilMessage.getMensaje("edu.udb.cri.system.alert.error.cert.pass"));
				alert.showAndWait();
			} else if (dto.getAlias() == null || dto.getAlias().isEmpty()) {
				valid = false;
				Alert alert = new Alert(AlertType.ERROR,
						UtilMessage.getMensaje("edu.udb.cri.system.alert.error.cert.alias"));
				alert.showAndWait();
			} else if (dto.getCommonName() == null || dto.getCommonName().isEmpty()) {
				valid = false;
				Alert alert = new Alert(AlertType.ERROR,
						UtilMessage.getMensaje("edu.udb.cri.system.alert.error.cert.common.name"));
				alert.showAndWait();
			} else if (dto.getOrganization() == null || dto.getOrganization().isEmpty()) {
				valid = false;
				Alert alert = new Alert(AlertType.ERROR,
						UtilMessage.getMensaje("edu.udb.cri.system.alert.error.cert.organization"));
				alert.showAndWait();
			} else if (dto.getOrganizationUnit() == null || dto.getOrganizationUnit().isEmpty()) {
				valid = false;
				Alert alert = new Alert(AlertType.ERROR,
						UtilMessage.getMensaje("edu.udb.cri.system.alert.error.cert.organization.unit"));
				alert.showAndWait();
			} else if (dto.getCountry() == null || dto.getCountry().isEmpty()) {
				valid = false;
				Alert alert = new Alert(AlertType.ERROR,
						UtilMessage.getMensaje("edu.udb.cri.system.alert.error.cert.country"));
				alert.showAndWait();
			} else if (dto.getCity() == null || dto.getCity().isEmpty()) {
				valid = false;
				Alert alert = new Alert(AlertType.ERROR,
						UtilMessage.getMensaje("edu.udb.cri.system.alert.error.cert.city"));
				alert.showAndWait();
			} else if (dto.getState() == null || dto.getState().isEmpty()) {
				valid = false;
				Alert alert = new Alert(AlertType.ERROR,
						UtilMessage.getMensaje("edu.udb.cri.system.alert.error.cert.state"));
				alert.showAndWait();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return valid;
	}

	/**
	 * Opens a FileChooser to let the user select a file to save to.
	 */
	@FXML
	private void handleSaveAs() {
		FileChooser fileChooser = new FileChooser();

		// Set extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);

		// Show save file dialog
		File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

		if (file != null) {
			// Make sure it has the correct extension
			if (!file.getPath().endsWith(".xml")) {
				file = new File(file.getPath() + ".xml");
			}

		}
	}

	public void handleSaveKeyStore() {
		try {
			FileChooser fileChooser = new FileChooser();

			// Set extension filter
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("KeyStore files (*.txt)", "*.txt");
			fileChooser.getExtensionFilters().add(extFilter);
			// Show save file dialog
			File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());
			if (file != null) {
				// Make sure it has the correct extension
				if (!file.getPath().endsWith(".txt")) {
					file = new File(file.getPath() + ".txt");
					// SaveKeyStore(file, "1234".toCharArray());
					SaveFile("Puesb archivoa", file);
				}

			}
			System.out.println("Archivo: " + file);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void SaveKeyStore(File file, char[] password) {
		try {
			KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
			ks.load(null, null);
			ks.store(new FileOutputStream(file), password);
		} catch (IOException | KeyStoreException ex) {
			ex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void SaveFile(String content, File file) {
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
