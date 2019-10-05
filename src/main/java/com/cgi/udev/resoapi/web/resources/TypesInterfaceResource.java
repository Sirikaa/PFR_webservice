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

import com.cgi.udev.resoapi.model.TypeInterface;
import com.cgi.udev.resoapi.model.exceptions.InexistantException;
import com.cgi.udev.resoapi.model.exceptions.RequeteInvalideException;
import com.cgi.udev.resoapi.model.services.TypeInterfaceService;

@Path("/typesinterface")
public class TypesInterfaceResource {
	TypeInterfaceService tiServ = new TypeInterfaceService();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<TypeInterface> afficherPersonnes() throws SQLException, InexistantException {
		return tiServ.getAll();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(TypeInterface ti, @Context UriInfo uriInfo) throws RequeteInvalideException, InexistantException {
		tiServ.create(ti);
		URI uri = uriInfo.getRequestUriBuilder().build();
		return Response.created(uri)
				       .entity(ti)
				       .build();
	}
}
