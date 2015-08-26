package de.gedoplan.buch.eedemos.jpa.entity;

import de.gedoplan.buch.eedemos.jpa.TestBase;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeNode;
import javax.persistence.EntityGraph;
import javax.persistence.PersistenceException;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;

// CHECKSTYLE:OFF
/**
 * Test der Persistence-Fuktionalität bzgl. der Entity Publisher.
 *
 * @author dw
 */
public class PublisherTest extends TestBase
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

  /**
   * Alle Testdaten ausgeben.
   *
   * Dies ist kein Unit-Test im eigentlichen Sinne. Er kann probeweise für eine Ausgabe der Testdaten genutzt werden.
   */
  @Test
  // @Ignore
  public void showAll()
  {
    System.out.println("----- showAll -----");

    TypedQuery<Publisher> query = this.entityManager.createQuery("select x from Publisher x", Publisher.class);
    List<Publisher> publishers = query.getResultList();
    for (Publisher publisher : publishers)
    {
      System.out.println(publisher.toDebugString());
      for (Book book : publisher.getBooks())
      {
        System.out.println("  " + book.toDebugString());
      }
    }
  }

  /**
   * Test: Sind die Testdaten korrekt in der DB?
   */
  @Test
  // @Ignore
  public void testFindAll()
  {
    System.out.println("----- testFindAll -----");

    TypedQuery<Publisher> query = this.entityManager.createQuery("select x from Publisher x order by x.id", Publisher.class);
    List<Publisher> publishers = query.getResultList();
    assertResultCorrect("Publisher", testPublishers, publishers);
  }

  /**
   * Test: Bücher für einen bestimmten Publisher finden (per JPQL).
   */
  @Test
  // @Ignore
  public void testFindBooksByPublisherName()
  {
    System.out.println("----- testFindBooksByPublisherName -----");

    String publisherName = testPublisher2.getName();

    TypedQuery<Book> q = this.entityManager.createQuery("select b from Book b where b.publisher.name=:publisherName order by b.id", Book.class);
    q.setParameter("publisherName", publisherName);
    List<Book> books = q.getResultList();
    assertResultCorrect("Book", testBooksOfPublisher2, books);
  }

  /**
   * Test: Publisher anhand seines Namens finden (per Criteria Query).
   */
  @Test
  // @Ignore
  public void testFindPublisherByName()
  {
    System.out.println("----- testFindPublisherByName -----");

    String publisherName = testPublisher1.getName();

    CriteriaBuilder cBuilder = this.entityManager.getCriteriaBuilder();

    // select p from Publisher p where p.name=:publisherName
    CriteriaQuery<Publisher> cQuery = cBuilder.createQuery(Publisher.class);
    Root<Publisher> p = cQuery.from(Publisher.class);
    // p.fetch(Publisher_.books, JoinType.LEFT);
    cQuery.where(cBuilder.equal(p.get(Publisher_.name), cBuilder.parameter(String.class, "publisherName")));

    cQuery.select(p);
    cQuery.distinct(true);

    TypedQuery<Publisher> query = this.entityManager.createQuery(cQuery);
    query.setParameter("publisherName", publisherName);

    Publisher publisher = query.getSingleResult();
    Assert.assertEquals("Publisher", testPublisher1, publisher);
  }

  /**
   * Test: Publisher anhand eines Teils eines Buchtitels finden (JPQL).
   */
  @Test
  // @Ignore
  public void testFindPublisherByTopic()
  {
    System.out.println("----- testFindPublisherByTopic -----");

    String topic = "JPA";
    String topicPattern = "%" + topic + "%";

    TypedQuery<Publisher> q = this.entityManager.createQuery("select distinct p from Publisher p join p.books b where b.name like :topicPattern order by p.id", Publisher.class);
    q.setParameter("topicPattern", topicPattern);
    List<Publisher> publishers = q.getResultList();

    List<Publisher> expected = new ArrayList<>();
    for (Book book : testBooks)
    {
      if (book.getName().contains(topic))
      {
        Publisher publisher = book.getPublisher();
        if (!expected.contains(publisher))
        {
          expected.add(publisher);
        }
      }
    }
    assertResultCorrect("Publisher", expected.toArray(), publishers);
  }

  private static void assertResultCorrect(String name, Object[] expected, List<?> actual)
  {
    Assert.assertEquals(name + " count", expected.length, actual.size());
    for (int i = 0; i < expected.length; ++i)
    {
      ReflectionAssert.assertReflectionEquals(name, expected[i], actual.get(i), ReflectionComparatorMode.LENIENT_ORDER);
    }
  }

  /**
   * Demo eine Outer Joins mit ON.
   */
  @Test
  // @Ignore
  public void showPublishersWithBigBooks()
  {
    TypedQuery<Object[]> query = this.entityManager.createQuery("select p.name, b.name from Publisher p left join p.books b on b.pages>300", Object[].class);
    for (Object[] entry : query.getResultList())
    {
      System.out.printf("%s|%s\n", entry[0], entry[1]);
    }

  }

  @Test
  // @Ignore
  public void testGetSimpleEntityGraph()
  {
    System.out.println("----- testGetSimpleEntityGraph -----");

    EntityGraph<?> entityGraph = this.entityManager.getEntityGraph("Publisher_books");
    Assert.assertNotNull("Entity graph", entityGraph);

    List<AttributeNode<?>> attributeNodes = entityGraph.getAttributeNodes();
    Assert.assertEquals("Attribute node count", 1, attributeNodes.size());

    AttributeNode<?> booksNode = attributeNodes.get(0);
    Assert.assertEquals("Attribute name", "books", booksNode.getAttributeName());
  }

  @Test
  // @Ignore
  public void testQueryWithoutEntityGraph()
  {
    System.out.println("----- testQueryWithoutEntityGraph -----");

    testFetchOrLoad(null, null, false, false, false, true);
  }

  @Test
  // @Ignore
  public void testQueryWithSimpleNamedLoadGraph()
  {
    System.out.println("----- testQueryWithSimpleNamedLoadGraph -----");

    testFetchOrLoad("javax.persistence.loadgraph", "Publisher_books", true, false, false, true);
  }

  @Test
  /*
   * Achung: Dieser Test erfordert bei EclipseLink eine Instrumentierung (Weaving) der Klassen.
   * Dies kann bspw. mit Hilfe eines Agenten zur Laufzeit geschehen. Geben Sie dazu diese Option beim Java-Start an:
   * -javaagent:voller/Pfad/zu/org.eclipse.persistence.jpa-x.y.z.jar
   */
  public void testQueryWithSimpleNamedFetchGraph()
  {
    System.out.println("----- testQueryWithSimpleNamedFetchGraph -----");

    try
    {
      testFetchOrLoad("javax.persistence.fetchgraph", "Publisher_books", true, false, false, false);
    }
    catch (PersistenceException e)
    {
      if (e.getMessage().contains("You must define a fetch group manager"))
      {
        throw new AssertionError("EclipseLink requires weaving for this test", e);
      }
      else
      {
        throw e;
      }
    }
  }

  @Test
  /*
   * Achung: Dieser Test erfordert bei EclipseLink eine Instrumentierung (Weaving) der Klassen.
   * Dies kann bspw. mit Hilfe eines Agenten zur Laufzeit geschehen. Geben Sie dazu diese Option beim Java-Start an:
   * -javaagent:voller/Pfad/zu/org.eclipse.persistence.jpa-x.y.z.jar
   */
  public void testQueryWithSimpleDynamicFetchGraph()
  {
    System.out.println("----- testQueryWithSimpleDynamicFetchGraph -----");

    EntityGraph<Publisher> entityGraph = this.entityManager.createEntityGraph(Publisher.class);
    entityGraph.addAttributeNodes(Publisher_.books.getName());

    try
    {
      testFetchOrLoad("javax.persistence.fetchgraph", entityGraph, true, false, false, false);
    }
    catch (PersistenceException e)
    {
      if (e.getMessage().contains("You must define a fetch group manager"))
      {
        throw new AssertionError("EclipseLink requires weaving for this test", e);
      }
      else
      {
        throw e;
      }
    }
  }

  @Test
  // @Ignore("Entity Graph Support ist derzeit noch zu unvollstaendig implementiert, um komplexe Graphen sinnvoll zu testen")
  public void testQueryWithComplexLoadGraph()
  {
    System.out.println("----- testQueryWithComplexLoadGraph -----");

    testFetchOrLoad("javax.persistence.loadgraph", "Publisher_booksAndAuthors", true, true, false, true);
  }

  private void testFetchOrLoad(String hintKey, Object hintValue, boolean booksLoadedExpected, boolean authorsLoadedExpected, boolean mailAddressesLoadedExpected, boolean categoriesLoadedExpected)
  {
    TypedQuery<Publisher> query = this.entityManager.createQuery("select p from Publisher p", Publisher.class);

    if (hintKey != null)
    {
      if (hintValue instanceof String)
      {
        // Einige Provider finden den Entity Graph nicht, wenn er nur als Name angegeben wird. Daher separat suchen.
        EntityGraph<?> entityGraph = this.entityManager.getEntityGraph((String) hintValue);
        hintValue = entityGraph;
      }

      Assert.assertNotNull("Entity graph", hintValue);

      query.setHint(hintKey, hintValue);
    }
    System.out.println("Hint: " + hintKey + " = " + hintValue);

    Publisher publisher = query.getResultList().get(0);

    PersistenceUnitUtil persistenceUnitUtil = entityManagerFactory.getPersistenceUnitUtil();
    // for (String fieldName : new String[]
    // {
    // "books", "categories"
    // })
    // {
    // System.out.println("  " + fieldName + " loaded: " + persistenceUnitUtil.isLoaded(publisher, fieldName));
    // }

    boolean booksLoadedActual = persistenceUnitUtil.isLoaded(publisher, Publisher_.books.getName());
    assertLoaded("publisher.books", booksLoadedExpected, booksLoadedActual);

    if (booksLoadedActual && !publisher.getBooks().isEmpty())
    {
      Book book = publisher.getBooks().get(0);
      boolean authorsLoadedActual = persistenceUnitUtil.isLoaded(book, Book_.authors.getName());
      assertLoaded("books.authors", authorsLoadedExpected, authorsLoadedActual);

      if (authorsLoadedActual && !book.getAuthors().isEmpty())
      {
        Person person = book.getAuthors().iterator().next();
        boolean mailAddressesLoadedActual = persistenceUnitUtil.isLoaded(person, Person_.mailAddresses.getName());
        assertLoaded("person.mailAddresses", mailAddressesLoadedExpected, mailAddressesLoadedActual);
      }
    }

    boolean categoriesLoadedActual = persistenceUnitUtil.isLoaded(publisher, Publisher_.categories.getName());
    assertLoaded("publisher.categories", categoriesLoadedExpected, categoriesLoadedActual);
  }

  private void assertLoaded(String attributeName, boolean loadedExpected, boolean loadedActual)
  {
    if (loadedExpected)
    {
      Assert.assertTrue(attributeName + " loaded", loadedActual);
    }
    else
    {
      System.out.println("Warning: " + attributeName + " is loaded although not requested in entity graph");
    }
  }
}
