package com.cgi.udev.resoapi.web.resources;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.cgi.udev.resoapi.model.TypeMateriel;
import com.cgi.udev.resoapi.model.exceptions.InexistantException;
import com.cgi.udev.resoapi.model.exceptions.RequeteInvalideException;
import com.cgi.udev.resoapi.model.services.TypeMaterielService;

@Path("/typesmateriel")
public class TypesMaterielResource {
	TypeMaterielService tmServ = new TypeMaterielService();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<TypeMateriel> afficherPersonnes() throws SQLException, InexistantException {
		return tmServ.getAll();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void create(TypeMateriel tm) throws RequeteInvalideException, InexistantException {
		tmServ.create(tm);
	}

}
