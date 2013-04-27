package de.gedoplan.buch.eedemos.jpa.repository;

import de.gedoplan.baselibs.enterprise.interceptor.TransactionRequired;
import de.gedoplan.buch.eedemos.jpa.entity.Country;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class CountryRepository
{
  @PersistenceContext(name = "ee_demos")
  private EntityManager entityManager;

  @TransactionRequired
  public void insert(Country country)
  {
    this.entityManager.persist(country);
  }

  public Country findById(String id)
  {
    return this.entityManager.find(Country.class, id);
  }

  @TransactionRequired
  public void update(Country country)
  {
    this.entityManager.merge(country);
  }

  @TransactionRequired
  public void delete(String id)
  {
    Country country = findById(id);
    if (country != null)
    {
      this.entityManager.remove(country);
    }
  }
}
