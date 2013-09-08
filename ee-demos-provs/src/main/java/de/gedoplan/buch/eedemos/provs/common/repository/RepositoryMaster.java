package de.gedoplan.buch.eedemos.provs.common.repository;

import de.gedoplan.baselibs.enterprise.stereotype.DataRepository;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

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
  @Inject
  EntityManager entityManager;

  /**
   * Alle Änderungen abspeichern.
   */
  @Transactional
  public void saveAll()
  {
    // Dies sollte eigentlich unnötig sein, da alle Änderungen ohnehin bei TX-Ende abgespeichert werden.
    // Der von Seam erzeugte Entity Manager bindet sich allerdings nur dann an eine Transaktion,
    // wenn in ihm eine Methode aufgerufen wird. Dazu wird hier flush verwendet, was dann ohnehin ausgeführt würde.
    this.entityManager.flush();
  }
}
