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

import com.cgi.udev.resoapi.model.TypeAffectation;
import com.cgi.udev.resoapi.model.exceptions.InexistantException;
import com.cgi.udev.resoapi.model.exceptions.RequeteInvalideException;
import com.cgi.udev.resoapi.model.services.TypeAffectationService;

@Path("/typeaffectation/{id}")
public class TypeAffectationResource {
	TypeAffectationService taServ = new TypeAffectationService();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public TypeAffectation get(@PathParam("id") int id) throws SQLException, InexistantException{
		return taServ.getTypeAffectation(id);
	}
	
	@DELETE
	public void delete(@PathParam("id") int id) throws InexistantException{
		taServ.delete(id);
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void update(@PathParam("id") int id, TypeAffectation ta) throws RequeteInvalideException, InexistantException{
		ta.setId(id);
		taServ.update(ta);
	}
}
