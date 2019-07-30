package edu.udb.cri.dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CertInfoDto {

	private StringProperty alias;
	private IntegerProperty number;

	private StringProperty commonName;
	private StringProperty organization;
	private StringProperty organizationUnit;
	private StringProperty city;
	private StringProperty state;
	private StringProperty country;

	public CertInfoDto(Integer number, String alias, String commonName, String organization, String organizationUnit,
			String city, String state, String country) {
		this.alias = new SimpleStringProperty(alias);
		this.commonName = new SimpleStringProperty(commonName);
		this.number = new SimpleIntegerProperty(number);
		this.organization = new SimpleStringProperty(organization);
		this.organizationUnit = new SimpleStringProperty(organizationUnit);
		this.city = new SimpleStringProperty(city);
		this.state = new SimpleStringProperty(state);
		this.country = new SimpleStringProperty(country);
	}

	public CertInfoDto() {
		this.number = new SimpleIntegerProperty();
		this.alias = new SimpleStringProperty("");
		this.commonName = new SimpleStringProperty("");
		this.organization = new SimpleStringProperty("");
		this.organizationUnit = new SimpleStringProperty("");
		this.city = new SimpleStringProperty("");
		this.state = new SimpleStringProperty("");
		this.country = new SimpleStringProperty("");

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

	public void setCommonName(String commonName) {
		this.commonName.set(commonName);
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

	public StringProperty getOrganization() {
		return organization;
	}

	public void setOrganization(StringProperty organization) {
		this.organization = organization;
	}

	public void setOrganization(String organization) {
		this.organization.set(organization);
	}

	public StringProperty getOrganizationUnit() {
		return organizationUnit;
	}

	public void setOrganizationUnit(StringProperty organizationUnit) {
		this.organizationUnit = organizationUnit;
	}

	public void setOrganizationUnit(String organizationUnit) {
		this.organizationUnit.set(organizationUnit);
	}

	public StringProperty getCity() {
		return city;
	}

	public void setCity(StringProperty city) {
		this.city = city;
	}

	public void setCity(String city) {
		this.city.set(city);
	}

	public StringProperty getState() {
		return state;
	}

	public void setState(StringProperty state) {
		this.state = state;
	}

	public void setState(String state) {
		this.state.set(state);
	}

	public StringProperty getCountry() {
		return country;
	}

	public void setCountry(StringProperty country) {
		this.country = country;
	}

	public void setCountry(String country) {
		this.country.set(country);
	}

}
