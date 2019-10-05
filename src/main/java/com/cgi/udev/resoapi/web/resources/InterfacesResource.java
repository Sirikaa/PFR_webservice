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

import com.cgi.udev.resoapi.model.Interface;
import com.cgi.udev.resoapi.model.exceptions.InexistantException;
import com.cgi.udev.resoapi.model.exceptions.RequeteInvalideException;
import com.cgi.udev.resoapi.model.services.InterfaceService;

@Path("/interfaces")
public class InterfacesResource {
InterfaceService iServ = new InterfaceService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Interface> afficherInterfaces() throws SQLException, InexistantException {
		return iServ.getAll();
	}
	
	@Path("/materiel/{id}")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(Interface i, @PathParam("id") int id, @Context UriInfo uriInfo) throws RequeteInvalideException, InexistantException {
		iServ.create(i, id);
		URI uri = uriInfo.getRequestUriBuilder().build();
		return Response.created(uri)
				       .entity(i)
				       .build();
	}
}
