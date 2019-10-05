package com.cgi.udev.resoapi.web.resources;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.cgi.udev.resoapi.model.Fonction;
import com.cgi.udev.resoapi.model.exceptions.InexistantException;
import com.cgi.udev.resoapi.model.services.FonctionService;

@Path("/fonction")
public class FonctionResource {

FonctionService fServ = new FonctionService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Fonction> afficherFonctions() throws SQLException, InexistantException {
		return fServ.getAll();
	}
	
	@Path("/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Fonction get(@PathParam("id") int id) throws SQLException, InexistantException {
		return fServ.getFonction(id);
	}
	
	@Path("/client/{idClient}/personne/{idPersonne}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Fonction> get(@PathParam("idClient") int idClient, @PathParam("idPersonne") int idPersonne) throws SQLException, InexistantException {
		return fServ.getFonctionsOfPersonneForClient(idClient, idPersonne);
	}
}
