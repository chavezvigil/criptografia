package edu.udb.cri.dto;

public class CertificadoDto {

	private String alias;
	private String commonName;
	private String organization;
	private String organizationUnit;
	private String country;
	private String city;
	private String state;
	private String password;

	public CertificadoDto() {
		this.alias = "";
		this.commonName = "";
		this.organization = "";
		this.organizationUnit = "";
		this.country = "";
		this.city = "";
		this.state = "";
		this.password = "";
	}

	public CertificadoDto(String alias, String commonName, String organization, String organizationUnit, String country,
			String city, String state, String password) {
		this.alias = alias;
		this.commonName = commonName;
		this.organization = organization;
		this.organizationUnit = organizationUnit;
		this.country = country;
		this.city = city;
		this.state = state;
		this.password = password;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getCommonName() {
		return commonName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getOrganizationUnit() {
		return organizationUnit;
	}

	public void setOrganizationUnit(String organizationUnit) {
		this.organizationUnit = organizationUnit;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

}
