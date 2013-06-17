package de.gedoplan.buch.eedemos.bv.validation.repository;

import de.gedoplan.buch.eedemos.bv.validation.entity.Fragebogen;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

public class FragebogenRepository
{
  @PersistenceContext(name = "ee_demos")
  private EntityManager entityManager;

  @Transactional
  public void save(Fragebogen fragebogen)
  {
    this.entityManager.merge(fragebogen);
  }

}
