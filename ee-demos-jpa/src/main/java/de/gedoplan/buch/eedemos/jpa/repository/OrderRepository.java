package de.gedoplan.buch.eedemos.jpa.repository;

import de.gedoplan.baselibs.enterprise.interceptor.TransactionRequired;
import de.gedoplan.buch.eedemos.jpa.entity.Order;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class OrderRepository
{
  @PersistenceContext(name = "ee_demos")
  EntityManager entityManager;

  @TransactionRequired
  public void insert(Order order)
  {
    this.entityManager.persist(order);
  }

  public Order findById(Integer id)
  {
    return this.entityManager.find(Order.class, id);
  }

  public List<Order> findAll()
  {
    return this.entityManager.createQuery("select o from Order o", Order.class).getResultList();
  }

  @TransactionRequired
  public void update(Order order)
  {
    this.entityManager.merge(order);
  }

  @TransactionRequired
  public void delete(Integer id)
  {
    Order order = findById(id);
    if (order != null)
    {
      this.entityManager.remove(order);
    }
  }
}
