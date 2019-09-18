package com.cgi.udev.resoapi.model;

import java.util.List;

public class Client {

	private int id;
	private String nom;
	private String adresse1;
	private String adresse2;
	private String cp;
	private String ville;
	private List<Personne> personnes;
	private List<Materiel> materiels;
	
	public Client() {
		
	}
	public Client(int id, String nom, String adresse1, String adresse2) {
		this.setId(id);
		this.setNom(nom);
		this.setAdresse1(adresse1);
		this.setAdresse2(adresse2);
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
	public String getAdresse1() {
		return adresse1;
	}
	public void setAdresse1(String adresse1) {
		this.adresse1 = adresse1;
	}
	public String getAdresse2() {
		return adresse2;
	}
	public void setAdresse2(String adresse2) {
		this.adresse2 = adresse2;
	}
	public String getCp() {
		return cp;
	}
	public void setCp(String cp) {
		this.cp = cp;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public List<Personne> getPersonnes() {
		return personnes;
	}
	public void setPersonnes(List<Personne> personnes) {
		this.personnes = personnes;
	}
	public List<Materiel> getMateriels() {
		return materiels;
	}
	public void setMateriels(List<Materiel> materiels) {
		this.materiels = materiels;
	}
}
