package de.gedoplan.buch.eedemos.rs.webservice.provider;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

/**
 * Response Filter zur Ergänzung des Standard-Encodings.
 *
 * Laut Spezifikation ist das Default Encoding für REST Responses UTF-8. Einige Clients (z. B. RESTClient) haben aber scheinbar
 * andere Defaults. Daher wird mit diesem Filter jedem Response ohne explizit angegebenes Encoding ein entsprechender Header
 * mitgegeben.
 *
 * @author dw
 *
 */
@Provider
public class ResponseEncodingFilter implements ContainerResponseFilter
{
  private static final String DEFAULT_CHARSET = "utf-8";

  @Override
  public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException
  {
    MediaType type = responseContext.getMediaType();
    if (type != null)
    {
      String charset = type.getParameters().get(MediaType.CHARSET_PARAMETER);
      if (charset == null)
      {
        responseContext.getHeaders().putSingle(HttpHeaders.CONTENT_TYPE, type.withCharset(DEFAULT_CHARSET).toString());
      }
    }
  }

}
