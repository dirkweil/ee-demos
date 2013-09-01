package de.gedoplan.buch.eedemos.jpa.entity;

import de.gedoplan.buch.eedemos.jpa.TestBase;

import javax.persistence.EntityGraph;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

// CHECKSTYLE:OFF

/**
 * Test der Persistence-Fuktionalität bzgl. der Entity Publisher.
 * 
 * @author dw
 */
public class PublisherEntityGraphTest extends TestBase
{
  public static Publisher   testPublisher1        = new Publisher("O'Melly Publishing", "Books", "Music");
  public static Publisher   testPublisher2        = new Publisher("Expert Press", "Books");
  public static Publisher   testPublisher3        = new Publisher("Books reloaded", "Books");
  public static Publisher[] testPublishers        = { testPublisher1, testPublisher2, testPublisher3 };

  public static Person      testPerson1           = new Person("Brummer", "Bernd", new MailAddress("brummer", "gmx.de"));
  public static Person      testPerson2           = new Person("Wacker", "Willi", new MailAddress("wacker", "web.de"));
  public static Person[]    testPersons           = { testPerson1, testPerson2 };

  public static Book        testBook11            = new Book("Better JPA Programs", "12345-6789-0", 340);
  public static Book        testBook12            = new Book("Inside JPA", "54321-9876-X", 265);
  public static Book        testBook13            = new Book("Java and Databases", "11111-2222-6", 850);
  public static Book        testBook21            = new Book("Java for Beginners", "564534-432-2", 735);
  public static Book        testBook22            = new Book("Java vs. C#", "333333-123-0", 145);
  public static Book        testBook23            = new Book("Optimizing Java Programs", "765432-767-8", 230);
  public static Book        testBook30            = new Book("Is there a World after Java?", null, 1);
  public static Book[]      testBooks             = { testBook11, testBook12, testBook13, testBook21, testBook22, testBook23, testBook30 };
  public static Book[]      testBooksOfPublisher1 = { testBook11, testBook12, testBook13 };
  public static Book[]      testBooksOfPublisher2 = { testBook21, testBook22, testBook23 };
  static
  {
    for (Book book : testBooksOfPublisher1)
    {
      book.setPublisher(testPublisher1);
      book.getAuthors().add(testPerson1);
    }

    for (Book book : testBooksOfPublisher2)
    {
      book.setPublisher(testPublisher2);
      book.getAuthors().add(testPerson2);
    }

    testBook13.getAuthors().add(testPerson2);
  }

  /**
   * Testdaten aufsetzen.
   */
  @BeforeClass
  public static void setup()
  {
    deleteAll(Book.AUTHORS_TABLE_NAME, MailAddress.TABLE_NAME, Person.TABLE_NAME_HOBBIES, Person.TABLE_NAME_PHONES, Person.TABLE_NAME, Book.TABLE_NAME, Publisher.TABLE_NAME);
    insertAll(testPublishers, testPersons, testBooks);
  }

  @Test
  //  @Ignore
  public void testGetEntityGraph()
  {
    System.out.println("----- testGetEntityGraph -----");

    for (String name : new String[] { "Publisher_books" /*, "Publisher_booksAndAuthors" */})
    {
      EntityGraph<?> entityGraph = this.entityManager.getEntityGraph(name);
      Assert.assertNotNull("Entity graph " + name, entityGraph);
    }
  }

  @Test
  //  @Ignore
  public void testQueryWithoutEntityGraph()
  {
    System.out.println("----- testQueryWithoutEntityGraph -----");

    testFetchOrLoad(null, null, false, false, false, true);
  }

  @Test
  //  @Ignore
  public void testQueryWithSimpleLoadGraph()
  {
    System.out.println("----- testQueryWithSimpleLoadGraph -----");

    testFetchOrLoad("javax.persistence.loadgraph", "Publisher_books", true, false, false, true);
  }

  @Test
  //  @Ignore
  public void testQueryWithSimpleFetchGraph()
  {
    System.out.println("----- testQueryWithSimpleFetchGraph -----");

    testFetchOrLoad("javax.persistence.fetchgraph", "Publisher_books", true, false, false, false);
  }

  @Test
  //  @Ignore
  public void testQueryWithSimpleFetchGraphDynamic()
  {
    System.out.println("----- testQueryWithSimpleFetchGraphDynamic -----");

    EntityGraph<Publisher> entityGraph = this.entityManager.createEntityGraph(Publisher.class);
    entityGraph.addAttributeNodes(Publisher_.books.getName());
    entityManagerFactory.addNamedEntityGraph("Publisher_books(dynamic)", entityGraph);

    testFetchOrLoad("javax.persistence.fetchgraph", "Publisher_books(dynamic)", true, false, false, false);
  }

  @Test
  @Ignore("NPE bei HBN")
  public void testQueryWithComplexLoadGraph()
  {
    System.out.println("----- testQueryWithComplexLoadGraph -----");

    testFetchOrLoad("javax.persistence.loadgraph", "Publisher_booksAndAuthors", true, true, false, true);
  }

  private void testFetchOrLoad(String hintKey, Object hintValue, boolean booksLoadedExpected, boolean authorsLoadedExpected, boolean mailAddressesLoadedExpected, boolean categoriesLoadedExpected)
  {
    TypedQuery<Publisher> query = this.entityManager.createQuery("select p from Publisher p", Publisher.class);
    //    query.setParameter("id", testPublisher1.getId());

    if (hintKey != null)
    {
      // TODO: EclipseLink findet den Entity Graph nicht immer, wenn er nur als Name angegeben wird. Daher separat suchen.
      EntityGraph<?> entityGraph = this.entityManager.getEntityGraph((String) hintValue);

      Assert.assertNotNull("Entity graph", entityGraph);

      query.setHint(hintKey, entityGraph);
    }
    System.out.println("Hint: " + hintKey + " = " + hintValue);

    //    Publisher publisher = query.getSingleResult();
    //    Publisher publisher = query.getResultList().get(0);
    for (Publisher publisher : query.getResultList())
    {
      System.out.println(publisher.toDebugString());
      System.out.println("  id: " + publisher.getId());
      System.out.println("  name: " + publisher.getName());

      PersistenceUnitUtil persistenceUnitUtil = entityManagerFactory.getPersistenceUnitUtil();
      for (String fieldName : new String[] { "books", "categories" })
      {
        System.out.println("  " + fieldName + " loaded: " + persistenceUnitUtil.isLoaded(publisher, fieldName));
      }

      //    boolean booksLoadedActual = persistenceUnitUtil.isLoaded(publisher, Publisher_.books.getName());
      //    Assert.assertEquals("publisher.books loaded", booksLoadedExpected, booksLoadedActual);
      //    if (booksLoadedExpected)
      //    {
      //      for (Book book : publisher.getBooks())
      //      {
      //        Assert.assertEquals("books.authors loaded", authorsLoadedExpected, persistenceUnitUtil.isLoaded(book, Book_.authors.getName()));
      //        if (authorsLoadedExpected)
      //        {
      //          for (Person person : book.getAuthors())
      //          {
      //            Assert.assertEquals("person.mailAddresses loaded", mailAddressesLoadedExpected, persistenceUnitUtil.isLoaded(person, Person_.mailAddresses.getName()));
      //          }
      //        }
      //      }
      //    }
      //    Assert.assertEquals("publisher.categories loaded", categoriesLoadedExpected, persistenceUnitUtil.isLoaded(publisher, Publisher_.categories.getName()));
    }
  }
}
