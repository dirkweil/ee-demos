package de.gedoplan.buch.eedemos.bv.validation.repository;

import de.gedoplan.baselibs.enterprise.interceptor.TransactionRequired;
import de.gedoplan.buch.eedemos.bv.validation.entity.Fragebogen;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class FragebogenRepository
{
  @PersistenceContext(name = "ee_demos")
  private EntityManager entityManager;

  @TransactionRequired
  public void save(Fragebogen fragebogen)
  {
    this.entityManager.merge(fragebogen);
  }

}
