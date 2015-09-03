package de.gedoplan.buch.eedemos.rs.webservice.provider;

import de.gedoplan.baselibs.persistence.entity.SingleIdEntity;
import de.gedoplan.baselibs.persistence.entity.SingleIdEntity.PropertyMeta;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

@Provider
@Produces("application/csv")
@Consumes("application/csv")
public class SingleIdEntityCsvMessageBodyProvider implements MessageBodyWriter<SingleIdEntity<?>>, MessageBodyReader<SingleIdEntity<?>>
{
  private static final String SEPARATOR = ",";

  @Override
  public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
  {
    return isProcessable(type, genericType, annotations, mediaType);
  }

  @Override
  public long getSize(SingleIdEntity<?> t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
  {
    // Unknown output size
    return -1;
  }

  @Override
  public void writeTo(SingleIdEntity<?> entity, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
      throws IOException, WebApplicationException
  {
    try (PrintWriter printWriter = new PrintWriter(entityStream))
    {
      Iterator<Entry<String, PropertyMeta>> iter = entity.getPropertyMap().entrySet().iterator();
      while (iter.hasNext())
      {
        Entry<String, PropertyMeta> entry = iter.next();
        Method getter = entry.getValue().getter;
        if (getter != null)
        {
          try
          {
            Object value = getter.invoke(entity, (Object[]) null);
            if (value instanceof Date)
            {
              printWriter.print(((Date) value).getTime());
            }
            else
            {
              printWriter.print(value);
            }
          }
          catch (Exception ex) // CHECKSTYLE:IGNORE
          {
            printWriter.print(ex);
          }
        }

        if (iter.hasNext())
        {
          printWriter.print(SEPARATOR);
        }
      }
    }
  }

  @Override
  public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
  {
    return isProcessable(type, genericType, annotations, mediaType);
  }

  @Override
  public SingleIdEntity<?> readFrom(Class<SingleIdEntity<?>> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
      throws IOException, WebApplicationException
  {
    String line;

    /*
     * Achtung: Hier wird einfach mit UTF-8 gearbeitet.
     * Im echtem Leben sollte das Encoding aus dem Header Content-Type entnommen werden!
     */
    try (InputStreamReader isr = new InputStreamReader(entityStream, "UTF-8"); BufferedReader br = new BufferedReader(isr))
    {
      line = br.readLine();
      if (line == null || br.readLine() != null)
      {
        throw new BadRequestException("Body must bei exactly one line");
      }
    }

    try
    {
      Iterator<String> iterator = Arrays.asList(line.split(SEPARATOR)).iterator();
      SingleIdEntity<?> entity = type.newInstance();
      for (Entry<String, PropertyMeta> entry : entity.getPropertyMap().entrySet())
      {
        String name = entry.getKey();
        PropertyMeta meta = entry.getValue();

        if (meta.setter == null)
        {
          if ("id".equals(name))
          {
            iterator.next();
          }
          else
          {
            throw new IllegalArgumentException("No setter for property " + name);
          }
        }
        else
        {
          Object value = null;
          String fieldValue = iterator.next();
          if (!"null".equals(fieldValue))
          {
            if (Date.class.isAssignableFrom(meta.type))
            {
              value = new Date(Long.parseLong(fieldValue));
            }
            else
            {
              try
              {
                Method valueOfMethod = meta.type.getMethod("valueOf", String.class);
                value = valueOfMethod.invoke(null, fieldValue);
              }
              catch (Exception e)
              {
                Constructor<?> constructor = meta.type.getConstructor(String.class);
                value = constructor.newInstance(fieldValue);
              }
            }
          }

          if (value != null)
          {
            meta.setter.invoke(entity, value);
          }
        }
      }

      return entity;
    }
    catch (IllegalAccessException | IllegalArgumentException | InstantiationException | InvocationTargetException | NoSuchMethodException e)
    {
      throw new WebApplicationException(e);
    }
  }

  private boolean isProcessable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
  {
    // Handle SingleIdEntity and classes derived therefrom
    return SingleIdEntity.class.isAssignableFrom(type);
  }
}
