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

import com.cgi.udev.resoapi.model.TypeInterface;
import com.cgi.udev.resoapi.model.exceptions.InexistantException;
import com.cgi.udev.resoapi.model.exceptions.RequeteInvalideException;
import com.cgi.udev.resoapi.model.services.TypeInterfaceService;

@Path("/typeinterface/{id}")
public class TypeInterfaceResource {
	TypeInterfaceService tiServ = new TypeInterfaceService();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public TypeInterface get(@PathParam("id") int id) throws SQLException, InexistantException{
		return tiServ.getTypeInterface(id);
	}
	
	@DELETE
	public void delete(@PathParam("id") int id) throws InexistantException{
		tiServ.delete(id);
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void update(@PathParam("id") int id, TypeInterface ti) throws RequeteInvalideException, InexistantException{
		ti.setId(id);
		tiServ.update(ti);
	}
}
