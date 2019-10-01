package com.cgi.udev.resoapi.web.resources;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.cgi.udev.resoapi.model.TypeAffectation;
import com.cgi.udev.resoapi.model.exceptions.InexistantException;
import com.cgi.udev.resoapi.model.exceptions.RequeteInvalideException;
import com.cgi.udev.resoapi.model.services.TypeAffectationService;

@Path("/typesaffectation")
public class TypesAffectationResource {
	TypeAffectationService taServ = new TypeAffectationService();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<TypeAffectation> afficherPersonnes() throws SQLException, InexistantException {
		return taServ.getAll();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void create(TypeAffectation ta) throws RequeteInvalideException, InexistantException {
		taServ.create(ta);
	}
}
