package de.gedoplan.buch.eedemos.provs.common.repository;

import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

/**
 * Producer für einen konversationsgebundenen, applicationmanaged Entity Manager.
 *
 * @author dw
 */
@Stateful
@ConversationScoped
public class ExtendedEntityManagerProducer
{
  @PersistenceContext(unitName = "ee-demos", type = PersistenceContextType.EXTENDED)
  private EntityManager entityManager;

  /**
   * Konversationsgebundenen EntityManager liefern.
   *
   * @return Entity Manager
   */
  @Produces
  @ConversationScoped
  public EntityManager getEntityManager()
  {
    return this.entityManager;
  }
}
