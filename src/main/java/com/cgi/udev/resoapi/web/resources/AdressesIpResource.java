package com.cgi.udev.resoapi.web.resources;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

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
	public Response create(AdresseIp ip, @PathParam("idInterface") int idInterface, @Context UriInfo uriInfo) throws RequeteInvalideException, InexistantException {
		ipServ.create(ip, idInterface);
		URI uri = uriInfo.getRequestUriBuilder().build();
		return Response.created(uri)
				       .entity(ip)
				       .build();
	}
}
