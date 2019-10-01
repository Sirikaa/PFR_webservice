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

import com.cgi.udev.resoapi.model.TypeMateriel;
import com.cgi.udev.resoapi.model.exceptions.InexistantException;
import com.cgi.udev.resoapi.model.exceptions.RequeteInvalideException;
import com.cgi.udev.resoapi.model.services.TypeMaterielService;

@Path("/typemateriel/{id}")
public class TypeMaterielResource {
	TypeMaterielService tmServ = new TypeMaterielService();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public TypeMateriel get(@PathParam("id") int id) throws SQLException, InexistantException{
		return tmServ.getTypeMateriel(id);
	}
	
	@DELETE
	public void delete(@PathParam("id") int id) throws InexistantException{
		tmServ.delete(id);
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void update(@PathParam("id") int id, TypeMateriel tm) throws RequeteInvalideException, InexistantException{
		tm.setId(id);
		tmServ.update(tm);
	}
}
