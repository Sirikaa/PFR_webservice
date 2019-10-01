package com.cgi.udev.resoapi.web.resources;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.cgi.udev.resoapi.model.AdresseIp;
import com.cgi.udev.resoapi.model.exceptions.InexistantException;
import com.cgi.udev.resoapi.model.exceptions.RequeteInvalideException;
import com.cgi.udev.resoapi.model.services.AdresseIpService;

@Path("/ips")
public class AdressesIpResource {
AdresseIpService ipServ = new AdresseIpService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<AdresseIp> afficherAdresseIps() throws SQLException, InexistantException {
		return ipServ.getAll();
	}
	
	@Path("/interface/{idInterface}")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void create(AdresseIp ip, @PathParam("idInterface") int idInterface) throws RequeteInvalideException, InexistantException {
		ipServ.create(ip, idInterface);
	}
}
