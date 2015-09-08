package de.gedoplan.buch.eedemos.rs.webservice;

import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.CookieParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.UriInfo;

@Path("param")
@Produces(MediaType.TEXT_PLAIN)
public class ParamDemoResource
{
  /*
   * Demo für Query-Parameter.
   *
   * Aufruf: GET http://.../param/query?foo=42&bar=hugo&bar=otto
   */
  @GET
  @Path("query")
  public String getQueryParam(@QueryParam("foo") int foo, @QueryParam("bar") List<String> bars)
  {
    return "QueryParam: foo=" + foo + ", bars=" + bars;
  }

  /*
   * Demo für Form-Parameter.
   *
   * Aufruf: POST http://.../param/form mit Parametern foo und bar im Body, der application/x-www-form-urlencoded ist.
   */
  @POST
  @Path("form")
  public String getPostParam(@FormParam("foo") int foo, @FormParam("bar") String bar)
  {
    return "FormParam: foo=" + foo + ", bar=" + bar;
  }

  /*
   * Demo für Header-Parameter.
   *
   * Aufruf: GET http://.../param/header.
   */
  @GET
  @Path("header")
  public String getHeaderParam(@HeaderParam(HttpHeaders.USER_AGENT) String userAgent)
  {
    return "HeaderParam: userAgent=" + userAgent;
  }

  /*
   * Demo für Abfrage aller Header.
   *
   * Aufruf: GET http://.../param/headers.
   */
  @GET
  @Path("headers")
  public String getAllHeaders(@Context HttpHeaders headers)
  {
    return "Headers: " + headers.getRequestHeaders();
  }

  /*
   * Demo für Cookie-Parameter.
   *
   * Aufruf: GET http://.../param/cookie mit dem Cookie someKey.
   */
  @GET
  @Path("cookie")
  public String getCookieParam(@CookieParam("someKey") String value)
  {
    return "CookieParam: someKey -> " + value;
  }

  /*
   * Demo für Abfrage aller Cookies.
   *
   * Aufruf: GET http://.../param/cookies.
   */
  @GET
  @Path("cookies")
  public String getAllCookies(@Context HttpHeaders headers)
  {
    return "Cookies: " + headers.getCookies();
  }

  /*
   * Demo für Matrix-Parameter.
   *
   * Aufruf: GET http://.../param/matrix;foo=1234;foo=5678.
   */
  @GET
  @Path("matrix")
  public String getMatrixParam(@MatrixParam("foo") List<Integer> foos)
  {
    return "MatrixParam: foos=" + foos;
  }

  /*
   * Demo für PathParam-Parameter mit Zieltyp PathSegment.
   *
   * Aufruf: GET http://.../param/segment/a/b/c;type=x/d;type=y/e.
   */
  @GET
  @Path("segment/{foo}/{bar: .+}")
  public String getPathSegments(@PathParam("foo") PathSegment foo, @PathParam("bar") List<PathSegment> bars)
  {
    StringBuilder sb = new StringBuilder();
    sb.append("PathSegment \"foo\":\n");
    sb.append("  path=").append(foo.getPath()).append(", matrixParameters=").append(foo.getMatrixParameters()).append("\n");

    sb.append("PathSegments \"bar\":\n");
    for (PathSegment pathSegment : bars)
    {
      sb.append("  path=").append(pathSegment.getPath()).append(", matrixParameters=").append(pathSegment.getMatrixParameters()).append("\n");
    }

    return sb.toString();
  }

  /*
   * Demo für UriInfo.
   *
   * Aufruf: GET http://.../param/uriinfo?foo=42&bar=hugo&bar=otto
   */
  @GET
  @Path("uriinfo")
  public String getUriInfo(@Context UriInfo uriInfo)
  {
    StringBuilder sb = new StringBuilder();
    sb.append("Base URI:          ").append(uriInfo.getBaseUri()).append("\n");
    sb.append("Path:              ").append(uriInfo.getPath()).append("\n");
    sb.append("Absolute path:     ").append(uriInfo.getAbsolutePath()).append("\n");
    sb.append("Query parameters:  ").append(uriInfo.getQueryParameters()).append("\n");
    sb.append("Request URI:       ").append(uriInfo.getRequestUri()).append("\n");
    sb.append("Path segments:     ").append(uriInfo.getPathSegments()).append("\n");
    sb.append("Matched resources: ").append(uriInfo.getMatchedResources()).append("\n");

    return sb.toString();
  }

  /*
   * Demo für BeanParam.
   *
   * Aufruf: GET http://.../param/bean?foo=42&bar=hugo&bar=otto
   */
  @GET
  @Path("bean")
  public String getBeanParam(@BeanParam DemoBeanParam beanParam)
  {
    return beanParam.toString();
  }

  public static class DemoBeanParam
  {
    @QueryParam("foo")
    String foo;

    @HeaderParam(HttpHeaders.USER_AGENT)
    String userAgent;

    @CookieParam("someKey")
    String cookieValue;

    @Override
    public String toString()
    {
      return "DemoBeanParam [foo=" + this.foo + ", userAgent=" + this.userAgent + ", cookieValue=" + this.cookieValue + "]";
    }

  }

}
