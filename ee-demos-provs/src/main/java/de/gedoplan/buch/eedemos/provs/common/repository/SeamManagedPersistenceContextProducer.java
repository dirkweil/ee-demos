package de.gedoplan.buch.eedemos.provs.common.repository;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.jboss.solder.core.ExtensionManaged;

/**
 * Producer für einen SeamManagedPersistenceContext.
 * 
 * Dadurch wird im Ergebnis ein Producer für einen @ConversationScoped EntityManager bereit gestellt.
 * 
 * @author dw
 */
public class SeamManagedPersistenceContextProducer
{
  @ExtensionManaged
  @PersistenceUnit(unitName = "ee-demos")
  @Produces
  @ConversationScoped
  EntityManagerFactory entityManagerFactory;
}
