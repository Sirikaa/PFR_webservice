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

import com.cgi.udev.resoapi.model.AdresseIp;
import com.cgi.udev.resoapi.model.exceptions.InexistantException;
import com.cgi.udev.resoapi.model.exceptions.RequeteInvalideException;
import com.cgi.udev.resoapi.model.services.AdresseIpService;

@Path("/ip/{id}")
public class AdresseIpResource {
AdresseIpService ipServ = new AdresseIpService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public AdresseIp get(@PathParam("id") int id) throws SQLException, InexistantException {
		return ipServ.getAdresseIp(id);
	}
	
	@DELETE
	public void delete(@PathParam("id") int id) throws InexistantException{
		ipServ.delete(id);
	}

	@Path("/interface/{idInterface}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void update(@PathParam("id") int id, AdresseIp ip, @PathParam("idInterface") int idInterface) throws RequeteInvalideException, InexistantException{
		ip.setId(id);
		ipServ.update(ip, idInterface);
	}
}
