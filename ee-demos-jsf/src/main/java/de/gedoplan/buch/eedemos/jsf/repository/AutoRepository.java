package de.gedoplan.buch.eedemos.jsf.repository;

import de.gedoplan.buch.eedemos.jsf.entity.Auto;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@ApplicationScoped
public class AutoRepository
{

  @PersistenceContext(name = "ee_demos")
  private EntityManager entityManager;

  @Transactional
  public void save(Auto auto)
  {
    this.entityManager.merge(auto);
  }
}
