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

import com.cgi.udev.resoapi.model.Ville;
import com.cgi.udev.resoapi.model.exceptions.InexistantException;
import com.cgi.udev.resoapi.model.exceptions.RequeteInvalideException;
import com.cgi.udev.resoapi.model.services.VilleService;

@Path("/ville/{id}")
public class VilleResource {
	VilleService vServ = new VilleService();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Ville get(@PathParam("id") int id) throws SQLException, InexistantException{
		return vServ.getVille(id);
	}
	
	@DELETE
	public void delete(@PathParam("id") int id) throws InexistantException{
		vServ.delete(id);
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void update(@PathParam("id") int id, Ville v) throws RequeteInvalideException, InexistantException{
		v.setId(id);
		vServ.update(v);
	}
}
