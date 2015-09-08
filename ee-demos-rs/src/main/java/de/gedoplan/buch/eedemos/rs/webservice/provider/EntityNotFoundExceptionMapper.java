package de.gedoplan.buch.eedemos.rs.webservice.provider;

import javax.persistence.EntityNotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class EntityNotFoundExceptionMapper implements ExceptionMapper<EntityNotFoundException>
{
  @Override
  public Response toResponse(EntityNotFoundException exception)
  {
    return Response
        .status(Response.Status.NOT_FOUND)
        .entity(exception.toString())
        .type(MediaType.TEXT_PLAIN)
        .build();
  }

}
