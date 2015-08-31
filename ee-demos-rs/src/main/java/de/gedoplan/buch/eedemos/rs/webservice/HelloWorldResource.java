package de.gedoplan.buch.eedemos.rs.webservice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("hello")
public class HelloWorldResource
{
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String getHelloWorld()
  {
    return "Hello, world!";
  }

  @Path("bin")
  @GET
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  public String getHelloWorldBin()
  {
    return "Hello, world!";
  }

  @Path("{first}-{last}")
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String getGreeting(@PathParam("first") String first, @PathParam("last") String last)
  {
    return String.format("Hello, %s %s!", first, last);
  }

  @Path("{a}/{b}")
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String getXyz(@PathParam("a") int a, @PathParam("b") String b)
  {
    return String.format("a=%s, b=%s", a, b);
  }

}
