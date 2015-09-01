package de.gedoplan.buch.eedemos.rs.webservice;

import de.gedoplan.buch.eedemos.rs.entity.Talk;
import de.gedoplan.buch.eedemos.rs.persistence.TalkRepository;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
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

@Path("talk")
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public class TalkResource
{
  @Inject
  TalkRepository talkRepository;

  @GET
  public List<Talk> getTalks()
  {
    List<Talk> allTalks = this.talkRepository.findAll();
    return allTalks;
  }

  @GET
  @Path("{id}")
  public Talk getTalk(@PathParam("id") Integer id)
  {
    Talk talk = this.talkRepository.findById(id);
    if (talk == null)
    {
      throw new NotFoundException();
    }

    return talk;
  }

  @PUT
  @Path("{id}")
  public void updateTalk(@PathParam("id") Integer id, Talk talk)
  {
    if (!id.equals(talk.getId()))
    {
      throw new BadRequestException("id in url and updated object must be identical");
    }

    if (this.talkRepository.findById(id) == null)
    {
      throw new NotFoundException();
    }

    this.talkRepository.merge(talk);
  }

  @POST
  public Response createTalk(Talk talk, @Context UriInfo uriInfo) throws URISyntaxException
  {
    if (talk.getId() != null)
    {
      throw new BadRequestException("id of new entry must not be set");
    }

    this.talkRepository.persist(talk);

    URI createdUri = uriInfo.getBaseUriBuilder()
        .path(TalkResource.class)
        .path(TalkResource.class, "getTalk")
        .resolveTemplate("id", talk.getId())
        .build();
    return Response.created(createdUri).build();
  }

  @DELETE
  @Path("{id}")
  public void deleteTalk(@PathParam("id") Integer id)
  {
    this.talkRepository.removeById(id);
  }
}
