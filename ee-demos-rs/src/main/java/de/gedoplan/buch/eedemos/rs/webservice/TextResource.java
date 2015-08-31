package de.gedoplan.buch.eedemos.rs.webservice;

import java.net.URI;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("text")
public class TextResource
{
  private static ConcurrentHashMap<Integer, String> textMap = new ConcurrentHashMap<>();
  private static AtomicInteger                      nextId  = new AtomicInteger(1);

  static
  {
    textMap.put(nextId.getAndIncrement(), "Guten Tag");
    textMap.put(nextId.getAndIncrement(), "Mit freundlichem Gruß");
  }

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public Collection<String> getAll()
  {
    return textMap.values();
  }

  @GET
  @Path("{id}")
  @Produces(MediaType.TEXT_PLAIN)
  public String getText(@PathParam("id") Integer id)
  {
    String text = textMap.get(id);
    if (text != null)
    {
      return text;
    }

    throw new NotFoundException();
  }

  @PUT
  @Path("{id}")
  @Consumes(MediaType.TEXT_PLAIN)
  public void setText(@PathParam("id") Integer id, String text)
  {
    if (textMap.replace(id, text) == null)
    {
      throw new NotFoundException();
    }
  }

  @DELETE
  @Path("{id}")
  public void removeText(@PathParam("id") Integer id)
  {
    textMap.remove(id);
  }

  @POST
  @Consumes(MediaType.TEXT_PLAIN)
  public Response createText(String text, @Context UriInfo uriInfo)
  {
    int id = nextId.getAndIncrement();
    textMap.put(id, text);

    URI createdUri = uriInfo.getAbsolutePathBuilder().path(TextResource.class, "getText").resolveTemplate("id", id).build();
    return Response.created(createdUri).build();
  }
}
