package edu.udb.cri.dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CertInfoDto {

	private StringProperty alias;
	private StringProperty commonName;
	private IntegerProperty number;

	public CertInfoDto(Integer number, String alias, String commonName) {
		this.alias = new SimpleStringProperty(alias);
		this.commonName = new SimpleStringProperty(commonName);
		this.number = new SimpleIntegerProperty(number);
	}

	public CertInfoDto() {
		this.alias = new SimpleStringProperty("");
		this.commonName = new SimpleStringProperty("");
		this.number = new SimpleIntegerProperty();
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

	public IntegerProperty getNumber() {
		return number;
	}

	public void setNumber(IntegerProperty number) {
		this.number = number;
	}

	public void setNumber(Integer number) {
		this.number.set(number);
	}

}
