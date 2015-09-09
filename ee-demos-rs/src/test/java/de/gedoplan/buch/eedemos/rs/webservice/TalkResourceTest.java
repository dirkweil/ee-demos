package de.gedoplan.buch.eedemos.rs.webservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import de.gedoplan.buch.eedemos.rs.entity.Talk;
import de.gedoplan.buch.eedemos.rs.entity.TalkType;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TalkResourceTest
{
  private static final String serverUrl            = "http://localhost:8080";
  private static final String serverUrlWebContext  = serverUrl + "/ee-demos-rs";
  private static final String serverUrlRestContext = serverUrlWebContext + "/rest";

  private static Integer      talkIdForGet         = null;
  private static Integer      talkIdForDelete      = null;

  private static Client       client;
  private static WebTarget    baseTarget;

  @BeforeClass
  public static void beforeClass()
  {
    client = ClientBuilder.newClient();
    baseTarget = client.target(serverUrlRestContext + "/talk");
  }

  @AfterClass
  public static void afterClass()
  {
    client.close();
  }

  @Test
  public void test_01_GetTalks()
  {
    System.out.println("----- test_01_GetTalks -----");

    List<Talk> talks = baseTarget
        .request()
        .accept(MediaType.APPLICATION_JSON)
        .get(new GenericType<List<Talk>>() {});

    assertFalse("Talk list must not be empty", talks.isEmpty());

    System.out.printf("Found %d talks:\n", talks.size());
    for (Talk talk : talks)
    {
      System.out.println("  " + talk.toDebugString());

      if (talkIdForGet == null)
      {
        talkIdForGet = talk.getId();
      }

      talkIdForDelete = talk.getId();
    }
  }

  @Test
  public void test_02_GetTalkById()
  {
    System.out.println("----- test_02_GetTalkById -----");

    if (talkIdForGet != null)
    {

      Talk talk = baseTarget
          .path("{id}")
          .resolveTemplate("id", talkIdForGet)
          .request()
          .accept(MediaType.APPLICATION_JSON)
          .get(Talk.class);

      assertNotNull("Talk should not be null", talk);
      assertEquals("Talk ID", talkIdForGet, talk.getId());

      System.out.println("Talk: " + talk.toDebugString());
    }
    else
    {
      System.out.println("This test requires talkIdForGet to be set.\n"
          + "Either run all test in one execution (the first test method will set talkIdForGet)\n"
          + "or modify the class code appropriately.");
    }
  }

  @Test
  public void test_03_CreateTalk()
  {
    System.out.println("----- test_03_CreateTalk -----");

    Talk talk = new Talk("Duck Typing Made Simple", TalkType.SESSION, null, 75, "Donald Duck");

    Response response = null;
    try
    {
      response = baseTarget
          .request()
          .post(Entity.json(talk));

      StatusType statusInfo = response.getStatusInfo();
      System.out.printf("Response status: %03d %s\n", statusInfo.getStatusCode(), statusInfo.getReasonPhrase());

      switch (statusInfo.getStatusCode())
      {
      case 201:
        System.out.printf("URI: %s\n", response.getHeaderString(HttpHeaders.LOCATION));
        break;

      case 500:
        System.out.println(response.readEntity(String.class));
        break;
      }

      assertEquals("Response status", 201, statusInfo.getStatusCode());
    }
    finally
    {
      try
      {
        response.close();
      }
      catch (Exception e)
      {
        // ignore
      }
    }
  }

  @Test
  public void test_04_DeleteTalk()
  {
    System.out.println("----- test_04_DeleteTalk -----");

    Response response = null;
    try
    {
      response = baseTarget
          .path("{id}")
          .resolveTemplate("id", talkIdForDelete != null ? talkIdForDelete : 99999)
          .request()
          .delete();

      StatusType statusInfo = response.getStatusInfo();
      System.out.printf("Response status: %03d %s\n", statusInfo.getStatusCode(), statusInfo.getReasonPhrase());

      assertEquals("Response status", 204, statusInfo.getStatusCode());
    }
    finally
    {
      try
      {
        response.close();
      }
      catch (Exception e)
      {
        // ignore
      }
    }
  }
}
