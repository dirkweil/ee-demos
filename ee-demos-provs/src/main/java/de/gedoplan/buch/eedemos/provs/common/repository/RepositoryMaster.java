package de.gedoplan.buch.eedemos.provs.common.repository;

import de.gedoplan.baselibs.enterprise.stereotype.DataRepository;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.apache.commons.logging.Log;

/**
 * Repository-Master für ProVS.
 * 
 * Diese Klasse bietet Repository-übergreifende Methoden (derzeit nur {@link #saveAll()}) an.
 * 
 * @author dw
 */
@DataRepository
public class RepositoryMaster implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Inject
  EntityManager             entityManager;

  @Inject
  Log                       log;

  /**
   * Alle Änderungen abspeichern.
   */
  @Transactional
  public void saveAll()
  {
    try
    {
      this.entityManager.joinTransaction();
      this.entityManager.flush();
    }
    catch (RuntimeException e) // CHECKSTYLE:IGNORE
    {
      this.log.error("Fehler beim Speichern", e);
    }
  }
}
