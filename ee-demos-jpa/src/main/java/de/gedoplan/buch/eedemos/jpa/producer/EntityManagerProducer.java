package de.gedoplan.buch.eedemos.jpa.producer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;

/**
 * Producer für einen transaktionsgebundenen Entity Manager.
 * 
 * @author dw
 */
@ApplicationScoped
public class EntityManagerProducer
{
  @PersistenceContext(unitName = "ee-demos")
  private EntityManager entityManager;

  @Inject
  private Log           log;

  @Produces
  public EntityManager createEntityManager()
  {
    if (this.log.isTraceEnabled())
    {
      this.log.trace("createEntityManager: " + this.entityManager + " (flushMode=" + this.entityManager.getFlushMode() + ", properties=" + this.entityManager.getProperties() + ")");
    }

    return this.entityManager;
  }
}
