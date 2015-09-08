package de.gedoplan.buch.eedemos.rs.webservice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("extendedmatching")
@Produces(MediaType.TEXT_PLAIN)
public class ExtendedMatchingResource
{
  @GET
  @Path("{integer : \\d+}")
  public String getInteger(@PathParam("integer") int integer)
  {
    return "Integer: " + integer;
  }

  @GET
  @Path("{date : \\d{4}-\\d?\\d-\\d?\\d}")
  public String getDate(@PathParam("date") String yyyyMmDd)
  {
    return "Date: " + java.sql.Date.valueOf(yyyyMmDd);
  }

  @GET
  @Path("{any : .+}")
  public String getAny(@PathParam("any") String any)
  {
    return "Any: " + any;
  }
}
