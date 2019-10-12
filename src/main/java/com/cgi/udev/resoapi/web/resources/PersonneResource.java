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
import javax.ws.rs.core.Response;

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
	public Response delete(@PathParam("id") int id) throws InexistantException{
		pServ.delete(id);
		return Response.ok()
			       .build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") int id, Personne p) throws RequeteInvalideException, InexistantException{
		p.setId(id);
		pServ.update(p);
		return Response.ok()
				       .build();
	}
}
