package com.cgi.udev.resoapi.web.resources;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.cgi.udev.resoapi.model.Client;
import com.cgi.udev.resoapi.model.Personne;
import com.cgi.udev.resoapi.model.exceptions.InexistantException;
import com.cgi.udev.resoapi.model.exceptions.RequeteInvalideException;
import com.cgi.udev.resoapi.model.services.ClientService;

@Path("/client/{id}")
public class ClientResource {
	ClientService cServ = new ClientService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Client get(@PathParam("id") int id) throws SQLException, InexistantException {
		return cServ.getClient(id);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void update(@PathParam("id") int id, Client c) throws RequeteInvalideException, InexistantException {
		c.setId(id);
		cServ.update(c);
	}
	
	@DELETE
	public void delete(@PathParam("id") int id) throws InexistantException{
		cServ.delete(id);
	}
	
	@Path("/contacts")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Personne> getContacts(@PathParam("id") int id) throws SQLException, InexistantException {
		return cServ.getContacts(id);
	}
}
