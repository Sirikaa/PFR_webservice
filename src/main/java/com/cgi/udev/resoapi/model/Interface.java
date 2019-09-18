package com.cgi.udev.resoapi.model;

public class Interface {

	private int id;
	private String nom;
	private String mac;
	private TypeInterface type;
	
	public Interface() {
		
	}
	
	public Interface(int id, String nom, TypeInterface type) {
		this.setId(id);
		this.setNom(nom);
		this.setType(type);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public TypeInterface getType() {
		return type;
	}
	public void setType(TypeInterface type) {
		this.type = type;
	}
}
