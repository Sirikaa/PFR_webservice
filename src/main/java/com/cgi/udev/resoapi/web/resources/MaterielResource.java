package com.cgi.udev.resoapi.web.resources;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.cgi.udev.resoapi.model.Materiel;
import com.cgi.udev.resoapi.model.exceptions.InexistantException;
import com.cgi.udev.resoapi.model.exceptions.RequeteInvalideException;
import com.cgi.udev.resoapi.model.services.MaterielService;

@Path("/materiel/{id}")
public class MaterielResource {
	MaterielService mServ = new MaterielService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Materiel get(@PathParam("id") int id) throws SQLException, InexistantException {
		return mServ.getMateriel(id);
	}
	
	@DELETE
	public void delete(@PathParam("id") int id) throws InexistantException{
		mServ.delete(id);
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void update(@PathParam("id") int id, Materiel m) throws RequeteInvalideException, InexistantException{
		m.setId(id);
		mServ.update(m, id);
	}

}
