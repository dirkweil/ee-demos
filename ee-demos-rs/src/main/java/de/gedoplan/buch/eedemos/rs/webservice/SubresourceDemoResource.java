package de.gedoplan.buch.eedemos.rs.webservice;

import java.util.Date;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("subresource")
public class SubresourceDemoResource
{
  @Path("option-{option}")
  public Object getSubresource(@PathParam("option") String option)
  {
    switch (option)
    {
    case "A":
      return new AResource();

    case "B":
      return new BResource();

    default:
      throw new BadRequestException();
    }
  }

  public static class AResource
  {
    @GET
    @Path("name")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public String getName()
    {
      return "This is AResource";
    }

  }

  public static class BResource
  {
    @GET
    @Path("date")
    @Produces(MediaType.TEXT_PLAIN)
    public Date getCurrentDate()
    {
      return new Date();
    }

  }

}
