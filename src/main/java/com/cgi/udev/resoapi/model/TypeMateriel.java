package com.cgi.udev.resoapi.model;

public class TypeMateriel {

	private int id;
	private String libelle;
	
	public TypeMateriel() {
		
	}
	
	public TypeMateriel(int id, String libelle) {
		this.setId(id);
		this.setLibelle(libelle);
	}
	
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
}
