package com.cgi.udev.resoapi.model;

import java.util.List;

public class Interface {

	private int id;
	private String nom;
	private String mac;
	private TypeInterface type;
	private List<AdresseIp> adressesIp;
	
	public Interface() {
		
	}
	public Interface(int id, String nom, String mac) {
		this.setId(id);
		this.setNom(nom);
		this.setMac(mac);
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
	public List<AdresseIp> getAdressesIp() {
		return adressesIp;
	}
	public void setAdressesIp(List<AdresseIp> adressesIp) {
		this.adressesIp = adressesIp;
	}
}
