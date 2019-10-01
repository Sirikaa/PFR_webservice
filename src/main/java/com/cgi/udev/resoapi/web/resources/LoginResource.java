package com.cgi.udev.resoapi.web.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.cgi.udev.resoapi.model.Client;
import com.cgi.udev.resoapi.model.exceptions.InexistantException;
import com.cgi.udev.resoapi.model.services.ClientService;

@Path("/login")
public class LoginResource {
	ClientService cServ = new ClientService();

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Client login(@FormParam("matricule") String matricule, @FormParam("password") String password) throws InexistantException {
		return cServ.login(matricule, password);
	}
}
