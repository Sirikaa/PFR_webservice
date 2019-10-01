package com.cgi.udev.resoapi.web.resources;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.cgi.udev.resoapi.model.Ville;
import com.cgi.udev.resoapi.model.exceptions.InexistantException;
import com.cgi.udev.resoapi.model.exceptions.RequeteInvalideException;
import com.cgi.udev.resoapi.model.services.VilleService;

@Path("/villes")
public class VillesResource {

	VilleService vServ = new VilleService();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Ville> afficherPersonnes() throws SQLException, InexistantException {
		return vServ.getAll();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void create(Ville v) throws RequeteInvalideException, InexistantException {
		vServ.create(v);
	}
}
