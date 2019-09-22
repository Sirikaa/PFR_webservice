package com.cgi.udev.resoapi;

public class Ville {

	private int id;
	private String Cp;
	private String Ville;
	
	public Ville() {}
	public Ville(int id, String cp, String ville) {
		this.setId(id);
		this.setCp(cp);
		this.setVille(ville);
	}
	public String getCp() {
		return Cp;
	}
	public void setCp(String cp) {
		Cp = cp;
	}
	public String getVille() {
		return Ville;
	}
	public void setVille(String ville) {
		Ville = ville;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
