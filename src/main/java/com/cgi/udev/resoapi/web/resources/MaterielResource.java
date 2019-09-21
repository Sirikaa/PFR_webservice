package com.cgi.udev.resoapi.web.resources;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.cgi.udev.resoapi.model.Materiel;
import com.cgi.udev.resoapi.model.exceptions.InexistantException;
import com.cgi.udev.resoapi.model.services.MaterielService;

@Path("/materiel")
public class MaterielResource {
MaterielService mServ = new MaterielService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Materiel> afficherMateriels() throws SQLException, InexistantException {
		return mServ.getAll();
	}
	
	@Path("/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Materiel get(@PathParam("id") int id) throws SQLException, InexistantException {
		return mServ.getMateriel(id);
	}

}
