package edu.udb.cri.dto;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CertInfoDto {

	private StringProperty  alias;
	private StringProperty  commonName;


	public CertInfoDto(String alias, String commonName) {
		this.alias = new SimpleStringProperty(alias);
		this.commonName = new SimpleStringProperty(commonName);
	}
	
	public CertInfoDto() {
		this.alias = new SimpleStringProperty("");
		this.commonName = new SimpleStringProperty("");
	}

	public StringProperty getAlias() {
		return alias;
	}

	public void setAlias(StringProperty alias) {
		this.alias = alias;
	}
	
	public void setAlias(String alias) {
		this.alias.set(alias);
	}

	public StringProperty getCommonName() {
		return commonName;
	}

	public void setCommonName(StringProperty commonName) {
		this.commonName = commonName;
	}
	

}
