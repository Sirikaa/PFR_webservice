package com.cgi.udev.resoapi.web.resources;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.cgi.udev.resoapi.model.Client;
import com.cgi.udev.resoapi.model.Materiel;
import com.cgi.udev.resoapi.model.Personne;
import com.cgi.udev.resoapi.model.exceptions.InexistantException;
import com.cgi.udev.resoapi.model.exceptions.RequeteInvalideException;
import com.cgi.udev.resoapi.model.services.ClientService;
import com.cgi.udev.resoapi.model.services.MaterielService;
import com.cgi.udev.resoapi.model.services.PersonneService;

@Path("/client/{id}")
public class ClientResource {
	ClientService cServ = new ClientService();
	PersonneService pServ = new PersonneService();
	MaterielService mServ = new MaterielService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Client get(@PathParam("id") int id) throws SQLException, InexistantException {
		return cServ.getClient(id);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") int id, Client c) throws RequeteInvalideException, InexistantException {
		c.setId(id);
		cServ.update(c);
		return Response.ok()
			       .build();
	}
	
	@DELETE
	public Response delete(@PathParam("id") int id) throws InexistantException{
		cServ.delete(id);
		return Response.ok()
			       .build();
	}
	
	@Path("/addContact")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(Personne p, @PathParam("id") int idClient, @Context UriInfo uriInfo) throws RequeteInvalideException, InexistantException {
		pServ.create(p, idClient);
		URI uri = uriInfo.getRequestUriBuilder().build();
		return Response.created(uri)
				       .entity(p)
				       .build();
	}
	
	@Path("/contacts")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Personne> getContacts(@PathParam("id") int id) throws SQLException, InexistantException {
		return cServ.getContacts(id);
	}
	
	@Path("/personne/{id}/fonctions")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Personne> getFonctions(@PathParam("id") int id) throws SQLException, InexistantException {
		return cServ.getContacts(id);
	}
	
	@Path("/materiel/{idMateriel}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") int idClient,@PathParam("idMateriel") int idMateriel, Materiel m) throws RequeteInvalideException, InexistantException{
		m.setId(idMateriel);
		mServ.update(m, idClient);
		return Response.ok()
			       .build();
	}
}
