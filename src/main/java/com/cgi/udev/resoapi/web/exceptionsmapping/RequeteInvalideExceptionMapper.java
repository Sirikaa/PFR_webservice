package com.cgi.udev.resoapi.web.exceptionsmapping;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.cgi.udev.resoapi.model.exceptions.RequeteInvalideException;

@Provider
public class RequeteInvalideExceptionMapper implements ExceptionMapper<RequeteInvalideException>{

  @Override
  public Response toResponse(RequeteInvalideException exception) {
    return Response.status(Status.BAD_REQUEST)
                   .type(MediaType.TEXT_PLAIN)
                   .entity(exception.getMessage())
                   .build();
  }

}