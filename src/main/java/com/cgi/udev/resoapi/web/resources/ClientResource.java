package com.cgi.udev.resoapi.web.resources;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.cgi.udev.resoapi.model.Client;
import com.cgi.udev.resoapi.model.Personne;
import com.cgi.udev.resoapi.model.exceptions.InexistantException;
import com.cgi.udev.resoapi.model.exceptions.RequeteInvalideException;
import com.cgi.udev.resoapi.model.services.ClientService;

@Path("/client")
public class ClientResource {
	ClientService cServ = new ClientService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Client> afficherClients() throws SQLException, InexistantException {
		return cServ.getAll();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(Client c, @Context UriInfo uriInfo) throws RequeteInvalideException, InexistantException {
		cServ.create(c);
		URI uri = uriInfo.getRequestUriBuilder().build();
		return Response.created(uri)
				       .entity(c)
				       .build();
	}
	
	@Path("/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Client get(@PathParam("id") int id) throws SQLException, InexistantException {
		return cServ.getClient(id);
	}
	
	@Path("{id}/contacts/")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Personne> getContacts(@PathParam("id") int id) throws SQLException, InexistantException {
		return cServ.getContacts(id);
	}
}
