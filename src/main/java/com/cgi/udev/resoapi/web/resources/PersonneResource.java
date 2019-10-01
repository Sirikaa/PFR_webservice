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

import com.cgi.udev.resoapi.model.Personne;
import com.cgi.udev.resoapi.model.exceptions.InexistantException;
import com.cgi.udev.resoapi.model.exceptions.RequeteInvalideException;
import com.cgi.udev.resoapi.model.services.PersonneService;

@Path("/personne/{id}")
public class PersonneResource {
	PersonneService pServ = new PersonneService();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Personne get(@PathParam("id") int id) throws SQLException, InexistantException{
		return pServ.getPersonne(id);
	}
	
	@DELETE
	public void delete(@PathParam("id") int id) throws InexistantException{
		pServ.delete(id);
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void update(@PathParam("id") int id, Personne p) throws RequeteInvalideException, InexistantException{
		p.setId(id);
		pServ.update(p);
	}
}
