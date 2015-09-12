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
  @Path("v{version}")
  public Object getSubresource(@PathParam("version") String version)
  {
    switch (version)
    {
    case "1":
      return new Version1Resource();

    case "2":
      return new Version2Resource();

    default:
      throw new BadRequestException();
    }
  }

  public static class Version1Resource
  {
    @GET
    @Path("name")
    @Produces(MediaType.TEXT_PLAIN)
    public String getName()
    {
      return "This is Version1Resource";
    }
  }

  public static class Version2Resource
  {
    @GET
    @Path("name")
    @Produces(MediaType.TEXT_PLAIN)
    public String getName()
    {
      return "This is Version2Resource";
    }

    @GET
    @Path("date")
    @Produces(MediaType.TEXT_PLAIN)
    public Date getCurrentDate()
    {
      return new Date();
    }
  }
}
