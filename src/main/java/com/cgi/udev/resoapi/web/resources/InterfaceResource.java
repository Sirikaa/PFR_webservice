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

import com.cgi.udev.resoapi.model.Interface;
import com.cgi.udev.resoapi.model.exceptions.InexistantException;
import com.cgi.udev.resoapi.model.exceptions.RequeteInvalideException;
import com.cgi.udev.resoapi.model.services.InterfaceService;

@Path("/interface/{id}")
public class InterfaceResource {
InterfaceService iServ = new InterfaceService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Interface get(@PathParam("id") int id) throws SQLException, InexistantException {
		return iServ.getInterface(id);
	}
	
	@DELETE
	public void delete(@PathParam("id") int id) throws InexistantException{
		iServ.delete(id);
	}

	@Path("/materiel/{idMateriel}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void update(@PathParam("id") int id, Interface i, @PathParam("idMateriel") int idMateriel) throws RequeteInvalideException, InexistantException{
		i.setId(id);
		iServ.update(i, idMateriel);
	}
}
