package edu.udb.cri.controller;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	private String pathKeyStore = UtilMessage.getMensaje("edu.udb.cri.keystore.path.resources.keystore");
	
	private String pathDefaultKeystore = UtilMessage.getMensaje("edu.udb.cri.system.keystore.file");
	private static final Logger LOGGER = Logger.getLogger(ConfigurationViewController.class.getName());

	@FXML
	private Button createKeystoreButton;

	@FXML
	private Button createCertButton;
	@FXML
	private Button resetCertButton;
	
	@FXML
	private Button verCertButton;
	@FXML
	private Button eliminarCertButton;

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
	private TableColumn<CertInfoDto, Number> numberColumn;
	@FXML
	private TableColumn<CertInfoDto, String> aliasColumn;
	@FXML
	private TableColumn<CertInfoDto, String> commonNameColumn;
	@FXML
	private TableColumn<CertInfoDto, String> organizationColumn;
	@FXML
	private TableColumn<CertInfoDto, String> organizationUnitColumn;
	@FXML
	private TableColumn<CertInfoDto, String> cityColumn;
	@FXML
	private TableColumn<CertInfoDto, String> stateColumn;
	@FXML
	private TableColumn<CertInfoDto, String> countryColumn;

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
		initializeGui();
		try {
			File keyStore = new File(UtilMessage.getMensaje("edu.udb.cri.keystore.path.resources.keystore"));
			keyStoreUrl = keyStore.toURI().toURL();
			refrescarCertificados();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

	}

	public void refrescarCertificados() {
		try {
			ObservableList<CertInfoDto> lista = Utils.getAllCerts(keyStoreUrl, keyStorePass);
			certTable.setItems(lista);

			numberColumn.setCellValueFactory(cellData -> cellData.getValue().getNumber());
			numberColumn.setStyle("-fx-alignment: CENTER;");
			aliasColumn.setCellValueFactory(cellData -> cellData.getValue().getAlias());
			commonNameColumn.setCellValueFactory(cellData -> cellData.getValue().getCommonName());
			organizationColumn.setCellValueFactory(cellData -> cellData.getValue().getOrganization());
			organizationUnitColumn.setCellValueFactory(cellData -> cellData.getValue().getOrganizationUnit());
			cityColumn.setCellValueFactory(cellData -> cellData.getValue().getCity());
			stateColumn.setCellValueFactory(cellData -> cellData.getValue().getState());
			countryColumn.setCellValueFactory(cellData -> cellData.getValue().getCountry());
		} catch (Exception ex) {
			ex.printStackTrace();
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
		
		URL imgVer = getClass().getResource(UtilMessage.getMensaje("edu.udb.cri.system.icon.view"));
		Image imageVer = new Image(imgVer.toString());
		verCertButton.setGraphic(new ImageView(imageVer));
		
		URL imgDel = getClass().getResource(UtilMessage.getMensaje("edu.udb.cri.system.icon.delete"));
		Image imageDel = new Image(imgDel.toString());
		eliminarCertButton.setGraphic(new ImageView(imageDel));
		
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
					refrescarCertificados();

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
	
	public void handleViewCertificate() {
		try {
			int selectedIndex = certTable.getSelectionModel().getSelectedIndex();
		    if (selectedIndex >= 0) {
		    	//certTable.getItems().remove(selectedIndex);
		    	CertInfoDto selectedCert = certTable.getSelectionModel().getSelectedItem();
		        if (selectedCert != null) {
		        	this.mainApp.showCertificateOverview(selectedCert);
		        }
		    	
		    } else {
		        // Nothing selected.
		    	Alert alert = new Alert(AlertType.ERROR,
						UtilMessage.getMensaje("edu.udb.cri.system.alert.error.cert.selected"));
				alert.showAndWait();
		    }
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void handleDeleteCertificate() {
		try {
			int selectedIndex = certTable.getSelectionModel().getSelectedIndex();
		    if (selectedIndex >= 0) {
		    	
		    	CertInfoDto selectedCert = certTable.getSelectionModel().getSelectedItem();
		        if (selectedCert != null) {
		        	Alert alert = new Alert(AlertType.CONFIRMATION,
							UtilMessage.getMensaje("edu.udb.cri.system.alert.confirm.cert.delete"), ButtonType.YES,
							ButtonType.CANCEL);
					alert.showAndWait();

					if (alert.getResult() == ButtonType.YES) {
			        	//Eliminar del keystore
						UseKeyTool.clearKeyStore(keyStoreUrl, pathKeyStore, selectedCert.getAlias().getValue(), keyStorePass);
						certTable.getItems().remove(selectedIndex);
						refrescarCertificados();
					}
		        }
		    	
		    } else {
		        // Nothing selected.
		    	Alert alert = new Alert(AlertType.ERROR,
						UtilMessage.getMensaje("edu.udb.cri.system.alert.error.cert.selected"));
				alert.showAndWait();
		    }
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void handleSaveKeyStore() {
		
		try {
			FileChooser fileChooser = new FileChooser();

			// Set extension filter
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Keystore files (*.ks)", "*.ks");
			fileChooser.getExtensionFilters().add(extFilter);
			// Show save file dialog
			fileChooser.setTitle(UtilMessage.getMensaje("edu.udb.cri.system.keystore.file.text"));
			fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
			fileChooser.setInitialFileName(UtilMessage.getMensaje("edu.udb.cri.system.keystore.file.name"));
			File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());
			if (file != null) {
				// Make sure it has the correct extension
				file = new File(file.getPath());
				URL keystoreUrl = AboutController.class.getResource(pathDefaultKeystore);
				URLConnection conn = (URLConnection) keystoreUrl.openConnection();
				InputStream is = conn.getInputStream();							
				Files.copy(is, file.toPath());
				Alert msgVerify = new Alert(AlertType.INFORMATION,
						UtilMessage.getMensaje("edu.udb.cri.system.alert.information.keystore"));
				msgVerify.showAndWait();
			}
		} catch (Exception exception) {
			LOGGER.log(Level.SEVERE, "Exception occur", exception);
		}
	}

}
