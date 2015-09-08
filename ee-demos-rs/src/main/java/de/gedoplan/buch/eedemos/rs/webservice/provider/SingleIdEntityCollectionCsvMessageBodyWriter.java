package de.gedoplan.buch.eedemos.rs.webservice.provider;

import de.gedoplan.baselibs.persistence.entity.SingleIdEntity;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

@Provider
@Produces("application/csv")
public class SingleIdEntityCollectionCsvMessageBodyWriter implements MessageBodyWriter<Collection<SingleIdEntity<?>>>
{

  @Override
  public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
  {
    if (Collection.class.isAssignableFrom(type))
    {
      if (genericType instanceof ParameterizedType)
      {
        Type typeParm = ((ParameterizedType) genericType).getActualTypeArguments()[0];
        if (typeParm instanceof Class<?> && SingleIdEntity.class.isAssignableFrom((Class<?>) typeParm))
        {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public long getSize(Collection<SingleIdEntity<?>> t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
  {
    // Unknown output size
    return -1;
  }

  @Override
  public void writeTo(
      Collection<SingleIdEntity<?>> entityCollection, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
      throws IOException, WebApplicationException
  {
    try (PrintWriter printWriter = new PrintWriter(entityStream))
    {
      Iterator<SingleIdEntity<?>> iterator = entityCollection.iterator();
      while (iterator.hasNext())
      {
        SingleIdEntity<?> entity = iterator.next();
        SingleIdEntityCsvEntityProvider.writeEntity(entity, printWriter);
        if (iterator.hasNext())
        {
          printWriter.print("\n");
        }
      }
    }
  }
}
