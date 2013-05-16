package de.gedoplan.buch.eedemos.jpa.producer;

import de.gedoplan.buch.eedemos.jpa.qualifier.Unsynchronized;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.SynchronizationType;

import org.apache.commons.logging.Log;

/**
 * Producer für einen konversationsgebundenen, unsynchronisierten Entity Manager.
 * 
 * @author dw
 */
@ApplicationScoped
public class UnsynchronizedEntityManagerProducer
{
  @PersistenceContext(unitName = "ee-demos", synchronization = SynchronizationType.UNSYNCHRONIZED)
  private EntityManager entityManager;

  @Inject
  private Log           log;

  /**
   * Konversationsgebundenen EntityManager erstellen.
   * 
   * @return Entity Manager
   */
  @Produces
  @ConversationScoped
  @Unsynchronized
  public EntityManager createEntityManager()
  {
    //    if (!(this.entityManager instanceof Serializable))
    //    {
    //      if (this.log.isTraceEnabled())
    //      {
    //        this.log.trace("EntityManager is not serializable; creating serializable wrapper");
    //      }
    //
    //      this.entityManager = ObjectUtil.createSerializableObject(this.entityManager, EntityManager.class);
    //    }

    if (this.log.isTraceEnabled())
    {
      this.log.trace("createEntityManager: " + this.entityManager + " (flushMode=" + this.entityManager.getFlushMode() + ", properties=" + this.entityManager.getProperties() + ")");
    }

    return this.entityManager;
  }
}
