package de.gedoplan.buch.eedemos.jpa.entity;

import de.gedoplan.buch.eedemos.jpa.util.JpaUtil;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class PublisherTester
{
  public static void main(String[] args)
  {
    // insertPublishersAndBooks();
    // showAllPublishers();
    showPublisher("Books reloaded");
    // showBooksOfPublisher("O'Melly Publishing");
    // showPublishersForTopic("JPA");
  }

  public static void insertPublishersAndBooks()
  {
    EntityManager em = JpaUtil.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    tx.begin();

    Publisher p = new Publisher("O'Melly Publishing");
    em.persist(p);

    Book b = new Book("Better JPA Programs", "12345-6789-0", 340);
    b.setPublisher(p);
    em.persist(b);

    b = new Book("Inside JPA", "54321-9876-X", 265);
    b.setPublisher(p);
    em.persist(b);

    b = new Book("Java and Databases", "11111-2222-6", 850);
    b.setPublisher(p);
    em.persist(b);

    p = new Publisher("Expert Press");
    em.persist(p);

    b = new Book("Java for Beginners", "564534-432-2", 735);
    b.setPublisher(p);
    em.persist(b);

    b = new Book("Java vs. C#", "333333-123-0", 145);
    b.setPublisher(p);
    em.persist(b);

    b = new Book("Optimizing Java Programs", "765432-767-8", 230);
    b.setPublisher(p);
    em.persist(b);

    b = new Book("Is there a World after Java?", null, 1);
    em.persist(b);

    p = new Publisher("Books reloaded");
    em.persist(p);

    tx.commit();
    em.close();
  }

  public static void showAllPublishers()
  {
    EntityManager em = JpaUtil.createEntityManager();
    TypedQuery<Publisher> q = em.createQuery("select p from Publisher p", Publisher.class);
    // TypedQuery<Publisher> q = em.createQuery("select p from Publisher p join fetch p.books", Publisher.class);

    List<Publisher> pList = q.getResultList();
    for (Publisher publisher : pList)
    {
      System.out.println("Found: " + publisher);
      for (Book book : publisher.getBooks())
      {
        System.out.println("        " + book);
      }
    }
  }

  public static void showBooksOfPublisher(String publisherName)
  {
    EntityManager em = JpaUtil.createEntityManager();
    TypedQuery<Book> q = em.createQuery("select b from Book b where b.publisher.name=:publisherName", Book.class);
    q.setParameter("publisherName", publisherName);

    List<Book> bookList = q.getResultList();
    for (Book book : bookList)
    {
      System.out.println("Found: " + book);
    }
  }

  public static void showPublisher(String publisherName)
  {
    EntityManager em = JpaUtil.createEntityManager();

    CriteriaBuilder cBuilder = em.getCriteriaBuilder();

    // select p from Publisher p where p.name=:publisherName
    CriteriaQuery<Publisher> cQuery = cBuilder.createQuery(Publisher.class);
    Root<Publisher> p = cQuery.from(Publisher.class);
    // p.fetch(Publisher_.books, JoinType.LEFT);
    cQuery.where(cBuilder.equal(p.get(Publisher_.name), cBuilder.parameter(String.class, "publisherName")));

    cQuery.select(p);
    cQuery.distinct(true);

    TypedQuery<Publisher> query = em.createQuery(cQuery);
    query.setParameter("publisherName", publisherName);

    List<Publisher> pList = query.getResultList();
    for (Publisher publisher : pList)
    {
      System.out.println("Found: " + publisher);
      for (Book book : publisher.getBooks())
      {
        System.out.println("        " + book);
      }
    }

    em.close();
  }

  public static void showPublishersForTopic(String topic)
  {
    EntityManager em = JpaUtil.createEntityManager();
    TypedQuery<Publisher> q = em.createQuery("select distinct p from Publisher p join p.books b where b.name like :topicPattern", Publisher.class);
    q.setParameter("topicPattern", "%" + topic + "%");

    List<Publisher> pList = q.getResultList();
    for (Publisher publisher : pList)
    {
      System.out.println("Found: " + publisher);
    }
  }
}
