package com.cgi.udev.resoapi.model;

import java.util.List;

public class Personne {
	private int id;
	private String nom;
	private String prenom;
	private String telephone;
	private String email;
	private List<Fonction> fonctions;
	
	public Personne() {
		
	}
	public Personne(int id, String nom, String prenom, String email, String telephone) {
		this.setId(id);
		this.setNom(nom);
		this.setPrenom(prenom);
		this.setEmail(email);
		this.setTelephone(telephone);
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
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<Fonction> getFonctions() {
		return fonctions;
	}
	public void setFonctions(List<Fonction> fonctions) {
		this.fonctions = fonctions;
	}

	
}
