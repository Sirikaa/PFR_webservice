package com.cgi.udev.resoapi.web.resources;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.cgi.udev.resoapi.model.Personne;
import com.cgi.udev.resoapi.model.exceptions.InexistantException;
import com.cgi.udev.resoapi.model.exceptions.RequeteInvalideException;
import com.cgi.udev.resoapi.model.services.PersonneService;

@Path("/personnes")
public class PersonnesResource {

	PersonneService pServ = new PersonneService();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Personne> afficherPersonnes() throws SQLException, InexistantException {
		return pServ.getAll();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	//Pour afficher ce qui a été créé
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(Personne p, @Context UriInfo uriInfo) throws RequeteInvalideException, InexistantException {
		pServ.create(p);
		URI uri = uriInfo.getRequestUriBuilder().build();
		return Response.created(uri)
				       .entity(p)
				       .build();
	}
}
