package com.cgi.udev.resoapi.web.resources;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.cgi.udev.resoapi.model.Client;
import com.cgi.udev.resoapi.model.exceptions.InexistantException;
import com.cgi.udev.resoapi.model.services.ClientService;

@Path("/client")
public class ClientResource {
	ClientService cServ = new ClientService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Client> afficherClients() throws SQLException, InexistantException {
		return cServ.getAll();
	}
}
