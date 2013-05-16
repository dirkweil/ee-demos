package de.gedoplan.buch.eedemos.jpa.producer;

import de.gedoplan.baselibs.utils.util.ObjectUtil;
import de.gedoplan.buch.eedemos.jpa.qualifier.ApplicationManaged;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.apache.commons.logging.Log;

/**
 * Producer für einen konversationsgebundenen, applicationmanaged Entity Manager.
 * 
 * @author dw
 */
@ApplicationScoped
public class ApplicationManagedEntityManagerProducer
{
  @PersistenceUnit(unitName = "ee-demos")
  private EntityManagerFactory entityManagerFactory;

  @Inject
  private Log                  log;

  /**
   * Konversationsgebundenen EntityManager erstellen.
   * 
   * @return Entity Manager
   */
  @Produces
  @ConversationScoped
  @ApplicationManaged
  public EntityManager createEntityManager()
  {
    EntityManager entityManager = this.entityManagerFactory.createEntityManager();

    if (!(entityManager instanceof Serializable))
    {
      if (this.log.isTraceEnabled())
      {
        this.log.trace("EntityManager is not serializable; creating serializable wrapper");
      }

      entityManager = ObjectUtil.createSerializableObject(entityManager, EntityManager.class);
    }

    if (this.log.isTraceEnabled())
    {
      this.log.trace("createEntityManager: " + entityManager + " (flushMode=" + entityManager.getFlushMode() + ", properties=" + entityManager.getProperties() + ")");
    }

    return entityManager;
  }

  /**
   * Entity Manager nach Gebrauch entsorgen.
   * 
   * @param entityManager
   */
  public void disposeEntityManager(@Disposes @Any EntityManager entityManager)
  {
    if (this.log.isTraceEnabled())
    {
      this.log.trace("disposeEntityManager: " + entityManager);
    }

    if (entityManager.isOpen())
    {
      entityManager.close();
    }
  }
}
