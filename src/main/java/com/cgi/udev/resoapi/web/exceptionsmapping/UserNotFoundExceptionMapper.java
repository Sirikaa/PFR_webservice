package com.cgi.udev.resoapi.web.exceptionsmapping;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.cgi.udev.resoapi.model.exceptions.UserNotFoundException;

@Provider
public class UserNotFoundExceptionMapper implements ExceptionMapper<UserNotFoundException>{

	 @Override
	  public Response toResponse(UserNotFoundException exception) {
	    return Response.status(Status.UNAUTHORIZED)
	                   .type(MediaType.TEXT_PLAIN)
	                   .entity(exception.getMessage())
	                   .build();
	  }
}
