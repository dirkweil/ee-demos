package de.gedoplan.buch.eedemos.rs.webservice.provider;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

/**
 * Einfacher Message Body Writer für Object --> text/plain.
 *
 * Einige REST-Provider (z. B. RestEasy) enthalten einen solchen MBW bereits. Damit das Projekt auch mit anderen läuft, ist dieser
 * MBW hier enthalten.
 *
 * @author dw
 */
@Provider
@Produces(MediaType.TEXT_PLAIN)
public class ObjectPlainTextMessageBodyWriter implements MessageBodyWriter<Object>
{

  @Override
  public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
  {
    return true;
  }

  @Override
  public long getSize(Object t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
  {
    return -1;
  }

  @Override
  public void writeTo(Object t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
      throws IOException, WebApplicationException
  {
    String charset = mediaType.getParameters().get(MediaType.CHARSET_PARAMETER);
    if (charset == null)
    {
      charset = "UTF-8";
    }
    entityStream.write(t.toString().getBytes(charset));
  }

}
