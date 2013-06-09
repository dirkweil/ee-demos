package de.gedoplan.buch.eedemos.jpa.repository;

import de.gedoplan.buch.eedemos.jpa.entity.Country;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

public class CountryRepository
{
  @PersistenceContext(name = "ee_demos")
  private EntityManager entityManager;

  @Transactional(value = TxType.REQUIRED)
  public void insert(Country country)
  {
    this.entityManager.persist(country);
  }

  public Country findById(String id)
  {
    return this.entityManager.find(Country.class, id);
  }

  @Transactional(value = TxType.REQUIRED)
  public void update(Country country)
  {
    this.entityManager.merge(country);
  }

  @Transactional(value = TxType.REQUIRED)
  public void delete(String id)
  {
    Country country = findById(id);
    if (country != null)
    {
      this.entityManager.remove(country);
    }
  }
}
