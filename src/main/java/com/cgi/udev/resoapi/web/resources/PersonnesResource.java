package com.cgi.udev.resoapi.web.resources;

import java.sql.SQLException;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.cgi.udev.resoapi.model.Personne;
import com.cgi.udev.resoapi.model.exceptions.InexistantException;
import com.cgi.udev.resoapi.model.services.PersonneService;

@Path("/personnes")
public class PersonnesResource {
	PersonneService pServ = new PersonneService();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Personne> afficherPersonnes() throws SQLException, InexistantException {
		return pServ.getAll();
	}
	
	//On crée un contact sur ClientResource
	/*@Path("/client/{id}")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(Personne p, @PathParam("id") int idClient, @Context UriInfo uriInfo) throws RequeteInvalideException, InexistantException {
		pServ.create(p, idClient);
		URI uri = uriInfo.getRequestUriBuilder().build();
		return Response.created(uri)
				       .entity(p)
				       .build();
	}*/
}
