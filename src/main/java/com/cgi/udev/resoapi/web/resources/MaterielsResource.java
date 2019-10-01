package com.cgi.udev.resoapi.web.resources;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.cgi.udev.resoapi.model.Materiel;
import com.cgi.udev.resoapi.model.exceptions.InexistantException;
import com.cgi.udev.resoapi.model.exceptions.RequeteInvalideException;
import com.cgi.udev.resoapi.model.services.MaterielService;

@Path("/materiels")
public class MaterielsResource {
	
	MaterielService mServ = new MaterielService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Materiel> afficherMateriels() throws SQLException, InexistantException {
		return mServ.getAll();
	}
	
	@Path("/client/{id}")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void create(Materiel m, @PathParam("id") int id) throws RequeteInvalideException, InexistantException {
		mServ.create(m, id);
	}
}
