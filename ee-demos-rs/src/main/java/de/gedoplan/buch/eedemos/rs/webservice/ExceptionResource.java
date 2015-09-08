package de.gedoplan.buch.eedemos.rs.webservice;

import javax.persistence.EntityNotFoundException;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;

@Path("exception")
public class ExceptionResource
{
  @GET
  @Path("507")
  public String get507()
  {
    throw new WebApplicationException(507);
  }

  @GET
  @Path("notFound")
  public String getNotFoundException()
  {
    throw new NotFoundException();
  }

  @GET
  @Path("nullPointer")
  public String getNullPointerException()
  {
    throw new NullPointerException();
  }

  @GET
  @Path("entityNotFound")
  public String getEntityNotFoundException()
  {
    throw new EntityNotFoundException("Entity 4711 not found");
  }

}
