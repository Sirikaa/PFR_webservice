package com.cgi.udev.resoapi.model;

import java.util.List;

public class Materiel {

	private int id;
	private String libelle;
	private String serial;
	private TypeMateriel type;
	private List<Interface> interfaces;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public TypeMateriel getType() {
		return type;
	}
	public void setType(TypeMateriel type) {
		this.type = type;
	}
	public List<Interface> getInterfaces() {
		return interfaces;
	}
	public void setInterfaces(List<Interface> interfaces) {
		this.interfaces = interfaces;
	}
	
	
}
