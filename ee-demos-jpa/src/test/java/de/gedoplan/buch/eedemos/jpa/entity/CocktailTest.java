package de.gedoplan.buch.eedemos.jpa.entity;

import de.gedoplan.buch.eedemos.jpa.TestBase;
import de.gedoplan.buch.eedemos.jpa.helper.NameAndBasisZutat;
import de.gedoplan.buch.eedemos.jpa.helper.NameAndVolProz;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.TupleElement;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;

/**
 * Test der Persistence-Fuktionalität bzgl. der Entity Cocktail.
 * 
 * @author dw
 */
public class CocktailTest extends TestBase
{
  public static CocktailZutat   cassis              = new CocktailZutat("CASS", "Creme de Cassis", 15);
  public static CocktailZutat   erdbeeren           = new CocktailZutat("ERDB", "Erdbeeren", 0);
  public static CocktailZutat   erdbeerSirup        = new CocktailZutat("ERDBS", "Erdbeer-Sirup", 0);
  public static CocktailZutat   gingerAle           = new CocktailZutat("GALE", "Ginger Ale", 0);
  public static CocktailZutat   limette             = new CocktailZutat("LIME", "Limette", 0);
  public static CocktailZutat   maracujaSaft        = new CocktailZutat("MARA", "Maracuja-Saft", 0);
  public static CocktailZutat   pfirsichMark        = new CocktailZutat("PMARK", "Pfirsich-Mark", 0);
  public static CocktailZutat   rum                 = new CocktailZutat("RUM", "Rum", 40);
  public static CocktailZutat   rohrzucker          = new CocktailZutat("RZUC", "Rohrzucker", 0);
  public static CocktailZutat   sekt                = new CocktailZutat("SEKT", "Sekt", 11.5);
  public static CocktailZutat   zitronenSaft        = new CocktailZutat("ZSAFT", "Zitronen-Saft", 0);
  public static CocktailZutat[] testCocktailZutaten = { cassis, erdbeeren, erdbeerSirup, gingerAle, limette, maracujaSaft, pfirsichMark, rum, rohrzucker, sekt, zitronenSaft };

  public static Cocktail        bellini             = new Cocktail("BELL", "Bellini");
  static
  {
    bellini.addZutat(sekt);
    bellini.addZutat(pfirsichMark);
  }

  public static Cocktail        ipanema             = new Cocktail("IPA", "Ipanema");
  static
  {
    ipanema.addZutat(gingerAle);
    ipanema.addZutat(limette);
    ipanema.addZutat(rohrzucker);
    ipanema.addZutat(maracujaSaft);
  }

  public static Cocktail        kirRoyal            = new Cocktail("KIRR", "Kir Royal");
  static
  {
    kirRoyal.addZutat(sekt);
    kirRoyal.addZutat(cassis);
  }

  public static Cocktail        strawberryDaiquiri  = new Cocktail("STDQ", "Strawberry Daiquiri");
  static
  {
    strawberryDaiquiri.addZutat(rum);
    strawberryDaiquiri.addZutat(erdbeeren);
    strawberryDaiquiri.addZutat(erdbeerSirup);
    strawberryDaiquiri.addZutat(zitronenSaft);
  }

  public static Cocktail[]      testCocktails       = { bellini, ipanema, kirRoyal, strawberryDaiquiri };

  /**
   * Testdaten aufsetzen.
   */
  @BeforeClass
  public static void setup()
  {
    deleteAll(Cocktail.BASISZUTAT_TABLE_NAME, Cocktail.ZUTATEN_TABLE_NAME, CocktailZutat.TABLE_NAME, Cocktail.TABLE_NAME);
    insertAll(testCocktailZutaten, testCocktails);
  }

  /**
   * Test: Sind die Testdaten korrekt in der DB?
   */
  @Test
  //  @Ignore
  public void testFindAll()
  {
    System.out.println("----- testFindAll -----");

    TypedQuery<Cocktail> query = this.entityManager.createQuery("select x from Cocktail x order by x.name", Cocktail.class);
    List<Cocktail> cocktails = query.getResultList();

    Assert.assertEquals("Cocktail count", testCocktails.length, cocktails.size());
    for (int i = 0; i < testCocktails.length; ++i)
    {
      Cocktail testCocktail = testCocktails[i];
      Cocktail cocktail = cocktails.get(i);
      ReflectionAssert.assertReflectionEquals("Cocktail", testCocktail, cocktail, ReflectionComparatorMode.LENIENT_ORDER);
    }
  }

  /**
   * Alle Testdaten ausgeben.
   * 
   * Dies ist kein Unit-Test im eigentlichen Sinne. Er dient nur der Demonstration einer Query mit dem Criteria Query API.
   */
  @Test
  //  @Ignore
  public void showAll()
  {
    System.out.println("----- showAll -----");

    /*
     * Criteria Query aufbauen äquivalent zu "select c from Cocktail c"
     */

    CriteriaBuilder cBuilder = this.entityManager.getCriteriaBuilder();

    // Criteria Query für Ergebnistyp erzeugen
    CriteriaQuery<Cocktail> cQuery = cBuilder.createQuery(Cocktail.class);

    // Projektionsvariablen erzeugen (FROM-Klausel)
    Root<Cocktail> c = cQuery.from(Cocktail.class);

    // Selektion angeben (SELECT-Klausel)
    cQuery.select(c);

    // Sortierung nach Name
    cQuery.orderBy(cBuilder.asc(c.get(Cocktail_.name)));

    // Query ausführen
    TypedQuery<Cocktail> query = this.entityManager.createQuery(cQuery);
    runQuery(query);

    // Alternative Schreibweise "fluent API"
    // cQuery = cBuilder.createQuery(Cocktail.class);
    // query = entityManager.createQuery(cQuery.select(cQuery.from(Cocktail.class)));
    // runQuery(query);
  }

  /**
   * Alle Cocktail-Namen ausgeben.
   * 
   * Dies ist kein Unit-Test im eigentlichen Sinne. Er dient nur der Demonstration einer Query mit dem Criteria Query API.
   */
  @Test
  //  @Ignore
  public void showNames()
  {
    System.out.println("----- showNames -----");

    /*
     * Criteria Query aufbauen äquivalent zu "select c.name from Cocktail c"
     */

    CriteriaBuilder cBuilder = this.entityManager.getCriteriaBuilder();

    // Criteria Query für Ergebnistyp erzeugen
    CriteriaQuery<String> cQuery = cBuilder.createQuery(String.class);

    // Projektionsvariablen erzeugen (FROM-Klausel)
    Root<Cocktail> c = cQuery.from(Cocktail.class);

    // Zugriff auf das Attribut name
    Path<String> cName = c.get("name");

    // Selektion angeben (SELECT-Klausel)
    cQuery.select(cName);

    // Query ausführen
    TypedQuery<String> query = this.entityManager.createQuery(cQuery);
    runQuery(query);
  }

  /**
   * Alle Namen und Basiszutaten ausgeben (Tuple Select).
   * 
   * Dies ist kein Unit-Test im eigentlichen Sinne. Er dient nur der Demonstration einer Query mit dem Criteria Query API.
   */
  @Test
  //  @Ignore
  public void showNameAndBasiszutatTuple()
  {
    System.out.println("----- showNameAndBasiszutatTuple -----");

    /*
      * Criteria Query aufbauen äquivalent zu "select c.name, c.hauptZutat.name from Cocktail c"
      */

    CriteriaBuilder cBuilder = this.entityManager.getCriteriaBuilder();

    // Criteria Query für Ergebnistyp erzeugen
    CriteriaQuery<Tuple> cQuery = cBuilder.createQuery(Tuple.class);

    // Projektionsvariablen erzeugen (FROM-Klausel)
    Root<Cocktail> c = cQuery.from(Cocktail.class);

    // Selektion angeben (SELECT-Klausel)
    cQuery.multiselect(c.get(Cocktail_.name).alias("cocktailName"), c.get(Cocktail_.basisZutat).get(CocktailZutat_.name));

    // Query ausführen
    TypedQuery<Tuple> query = this.entityManager.createQuery(cQuery);
    for (Tuple entry : query.getResultList())
    {
      String name = entry.get("cocktailName", String.class);
      String basisZutat = entry.get(1, String.class);
      System.out.println(name + ": " + basisZutat);
    }
  }

  /**
   * Alle Namen und Basiszutaten ausgeben (Contructor Expression).
   * 
   * Dies ist kein Unit-Test im eigentlichen Sinne. Er dient nur der Demonstration einer Query mit dem Criteria Query API.
   */
  @Test
  //  @Ignore
  public void showNameAndBasiszutatCtor()
  {
    System.out.println("----- showNameAndBasiszutatCtor -----");

    /*
     * Criteria Query aufbauen äquivalent zu
     * "select new de.gedoplan.buch.eedemos.jpa.helper.NameAndBasisZutat(c.name, c.basisZutat.name) from Cocktail c"
     */

    CriteriaBuilder cBuilder = this.entityManager.getCriteriaBuilder();

    // Criteria Query für Ergebnistyp erzeugen
    CriteriaQuery<NameAndBasisZutat> cQuery = cBuilder.createQuery(NameAndBasisZutat.class);

    // Projektionsvariablen erzeugen (FROM-Klausel)
    Root<Cocktail> c = cQuery.from(Cocktail.class);

    // Selektion angeben (SELECT-Klausel)
    cQuery.select(cBuilder.construct(NameAndBasisZutat.class, c.get(Cocktail_.name), c.get(Cocktail_.basisZutat).get(CocktailZutat_.name)));

    // Query ausführen
    TypedQuery<NameAndBasisZutat> query = this.entityManager.createQuery(cQuery);
    for (NameAndBasisZutat entry : query.getResultList())
    {
      String name = entry.getName();
      String basisZutat = entry.getBasisZutat();
      System.out.println(name + ": " + basisZutat);
    }
  }

  /**
   * Alle alkoholischen Cocktails ausgeben.
   * 
   * Dies ist kein Unit-Test im eigentlichen Sinne. Er dient nur der Demonstration einer Query mit dem Criteria Query API.
   */
  @Test
  //  @Ignore
  public void showAlcoholic()
  {
    System.out.println("----- showAlcoholic -----");

    /*
     * Criteria Query aufbauen äquivalent zu "select distinct c from Cocktail c JOIN c.zutaten z where z.volProz<>0"
     */

    CriteriaBuilder cBuilder = this.entityManager.getCriteriaBuilder();

    // Criteria Query für Ergebnistyp erzeugen
    CriteriaQuery<Cocktail> cQuery = cBuilder.createQuery(Cocktail.class);

    // Projektionsvariablen erzeugen (FROM-Klausel)
    Root<Cocktail> c = cQuery.from(Cocktail.class);
    Join<Cocktail, CocktailZutat> z = c.join("zutaten");
    // Join<Cocktail, Zutat> z = c.join(Cocktail_.zutaten);

    // Bedingung erstellen und der Query hinzufügen
    Path<Double> zVolProz = z.get("volProz");
    // Path<Double> zVolProz = z.get(Zutat_.volProz);
    Predicate enthaeltAlkohol = cBuilder.equal(zVolProz, 0);
    cQuery.where(enthaeltAlkohol);

    // Selektion angeben (SELECT-Klausel)
    cQuery.select(c);

    // Dubletten verhindern
    cQuery.distinct(true);

    // Query ausführen
    TypedQuery<Cocktail> query = this.entityManager.createQuery(cQuery);
    runQuery(query);
  }

  /**
   * Höchsten Alkoholanteil der Zutaten ausgeben.
   * 
   * Dies ist kein Unit-Test im eigentlichen Sinne. Er dient nur der Demonstration einer Query mit dem Criteria Query API.
   */
  @Test
  //  @Ignore
  public void showAlcoholMaximum()
  {
    System.out.println("----- showAlcoholMaximum -----");

    /*
     * Criteria Query aufbauen äquivalent zu "select max(z.volProz) from Zutat z"
     */

    CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

    // Criteria Query für Ergebnistyp erzeugen
    CriteriaQuery<Double> cQuery = builder.createQuery(Double.class);

    // Projektionsvariablen erzeugen (FROM-Klausel)
    Root<CocktailZutat> z = cQuery.from(CocktailZutat.class);

    // Selektion angeben (SELECT-Klausel)
    Path<Double> zVolProz = z.get("volProz");
    // Path<Double> zVolProz = z.get(Zutat_.volProz);
    cQuery.select(builder.max(zVolProz));

    // Query ausführen
    TypedQuery<Double> q = this.entityManager.createQuery(cQuery);
    System.out.println("Maximaler Alkohol-Anteil: " + q.getSingleResult());
  }

  /**
   * Cocktails und mittlerer Alkoholanteil der Zutaten ausgeben (Tuple Select).
   * 
   * Dies ist kein Unit-Test im eigentlichen Sinne. Er dient nur der Demonstration einer Query mit dem Criteria Query API.
   */
  @Test
  //  @Ignore
  public void showAllWithAlcoholAverage()
  {
    System.out.println("----- showAllWithAlcoholAverage -----");

    /*
     * Criteria Query aufbauen äquivalent zu "select c.name, avg(z.volProz) from Cocktail c join c.zutaten z group by c.name"
     */

    CriteriaBuilder cBuilder = this.entityManager.getCriteriaBuilder();

    // Criteria Query für Ergebnistyp erzeugen
    CriteriaQuery<Tuple> cQuery = cBuilder.createTupleQuery();

    // Projektionsvariablen erzeugen (FROM)
    Root<Cocktail> c = cQuery.from(Cocktail.class);
    Join<Cocktail, CocktailZutat> z = c.join("zutaten");
    // Join<Cocktail, Zutat> z = c.join(Cocktail_.zutaten);

    // Selektion angeben (SELECT)
    Path<String> cName = c.get("name");
    // Path<String> cName = c.get(Cocktail_.name);
    Path<Double> zVolProz = z.get("volProz");
    // Path<Double> zVolProz = z.get(Zutat_.volProz);
    cQuery.multiselect(cName, cBuilder.avg(zVolProz));

    // Gruppierung angeben (GROUP BY)
    cQuery.groupBy(cName);

    // Query ausführen
    TypedQuery<Tuple> query = this.entityManager.createQuery(cQuery);
    runQuery(query);
  }

  /**
   * Cocktails und mittlerer Alkoholanteil der Zutaten ausgeben (Constructor Expression).
   * 
   * Dies ist kein Unit-Test im eigentlichen Sinne. Er dient nur der Demonstration einer Query mit dem Criteria Query API.
   */
  @Test
  //  @Ignore
  public void showAllWithAlcoholAverage2()
  {
    System.out.println("----- showAllWithAlcoholAverage2 -----");

    /*
     * Criteria Query aufbauen äquivalent zu
     * "select new de.gedoplan.seminar.jpa.demo.test.NameAndVolProz(c.name, avg(z.volProz)) from Cocktail c join c.zutaten z group by c.name"
     */

    CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

    // Criteria Query für Ergebnistyp erzeugen
    CriteriaQuery<NameAndVolProz> cQuery = builder.createQuery(NameAndVolProz.class);

    // Projektionsvariablen erzeugen (FROM)
    Root<Cocktail> c = cQuery.from(Cocktail.class);
    Join<Cocktail, CocktailZutat> z = c.join("zutaten");
    // Join<Cocktail, Zutat> z = c.join(Cocktail_.zutaten);

    // Selektion angeben (SELECT)
    Path<String> cName = c.get("name");
    // Path<String> cName = c.get(Cocktail_.name);
    Path<Double> zVolProz = z.get("volProz");
    // Path<Double> zVolProz = z.get(Zutat_.volProz);
    cQuery.select(builder.construct(NameAndVolProz.class, cName, builder.avg(zVolProz)));

    // Gruppierung angeben (GROUP BY)
    cQuery.groupBy(cName);

    // Query ausführen
    TypedQuery<NameAndVolProz> q = this.entityManager.createQuery(cQuery);
    runQuery(q);
  }

  /**
   * Cocktailmit einem bestimmten Namen ausgeben.
   * 
   * Dies ist kein Unit-Test im eigentlichen Sinne. Er dient nur der Demonstration einer Query mit dem Criteria Query API.
   */
  @Test
  //  @Ignore
  public void showCocktail()
  {
    System.out.println("----- showCocktail -----");

    String name = bellini.getName();

    /*
     * Criteria Query aufbauen äquivalent zu "select c from Cocktail c where c.name=:name"
     */

    CriteriaBuilder cBuilder = this.entityManager.getCriteriaBuilder();

    // Criteria Query für Ergebnistyp erzeugen
    CriteriaQuery<Cocktail> cQuery = cBuilder.createQuery(Cocktail.class);

    // Projektionsvariablen erzeugen (FROM-Klausel)
    Root<Cocktail> c = cQuery.from(Cocktail.class);

    // Where-Klausel
    cQuery.where(cBuilder.equal(c.get(Cocktail_.name), cBuilder.parameter(String.class, "name")));

    // Selektion angeben (SELECT-Klausel)
    cQuery.select(c);

    // Query ausführen
    TypedQuery<Cocktail> q = this.entityManager.createQuery(cQuery);
    q.setParameter("name", name);
    runQuery(q);

    this.entityManager.close();
  }

  /**
   * Basiszutaten mit der Anzahl darauf basierender Cocktails ausgeben.
   * 
   * Dies ist kein Unit-Test im eigentlichen Sinne. Er dient nur der Demonstration einer Query mit dem Criteria Query API.
   */
  @Test
  //  @Ignore
  public void showCocktailCounts()
  {
    System.out.println("----- showCocktailCounts -----");

    /*
     * Criteria Query aufbauen äquivalent zu "select c.basisZutat.name, count(c) from Cocktail c group by c.basisZutat"
     */

    CriteriaBuilder cBuilder = this.entityManager.getCriteriaBuilder();

    // Criteria Query für Ergebnistyp erzeugen
    CriteriaQuery<Tuple> cQuery = cBuilder.createQuery(Tuple.class);

    // Projektionsvariablen erzeugen (FROM-Klausel)
    Root<Cocktail> c = cQuery.from(Cocktail.class);

    // Selektion angeben (SELECT-Klausel)
    Path<CocktailZutat> cBasisZutat = c.get(Cocktail_.basisZutat);
    Path<String> cBasisZutatName = cBasisZutat.get(CocktailZutat_.name);
    cQuery.multiselect(cBasisZutatName, cBuilder.count(c));

    // Gruppierung
    cQuery.groupBy(cBasisZutat);

    // damit könnte Ginger Ale als Basiszutat ausgenommen werden
    // cQuery.having(cBuilder.notEqual(cBasisZutatName, "Ginger Ale"));

    TypedQuery<Tuple> query = this.entityManager.createQuery(cQuery);
    runQuery(query);

    this.entityManager.close();
  }

  private static void runQuery(TypedQuery<?> query)
  {
    for (Object entry : query.getResultList())
    {
      if (entry instanceof Cocktail)
      {
        display((Cocktail) entry);
      }
      else if (entry instanceof Tuple)
      {
        display((Tuple) entry);
      }
      else
      {
        System.out.println(entry);
      }
    }
  }

  private static void display(Cocktail cocktail)
  {
    System.out.println(cocktail.getName());
    for (CocktailZutat z : cocktail.getZutaten())
    {
      System.out.println("  " + z);
    }
  }

  private static void display(Tuple tuple)
  {
    String delim = "[";
    for (TupleElement<?> element : tuple.getElements())
    {
      System.out.print(delim + tuple.get(element));
      delim = "|";
    }
    System.out.println("]");
  }

  /**
   * Test: Bulk Update via JPQL.
   */
  @Test
  //  @Ignore
  public void testBulkUpdateJpql()
  {
    System.out.println("----- testBulkUpdateJpql -----");

    int dummyCocktailCount = createDummyCocktails();

    Query query = this.entityManager.createQuery("update Cocktail c set c.name=concat(c.name,' (a)') where c.name='Dummy'");
    int changedRowCount = query.executeUpdate();

    Assert.assertEquals("Changed row count", dummyCocktailCount, changedRowCount);

    // Änderungen nicht dauerhaft ablegen
    this.entityManager.getTransaction().rollback();
  }

  /**
   * Test: Bulk Delete via JPQL.
   */
  @Test
  //  @Ignore
  public void testBulkDeleteJpql()
  {
    System.out.println("----- testBulkDeleteJpql -----");

    int dummyCocktailCount = createDummyCocktails();

    Query query = this.entityManager.createQuery("delete from Cocktail c where c.name='Dummy'");
    int deletedRowCount = query.executeUpdate();

    Assert.assertEquals("Deleted row count", dummyCocktailCount, deletedRowCount);

    // Änderungen nicht dauerhaft ablegen
    this.entityManager.getTransaction().rollback();
  }

  /**
   * Test: Bulk Update via Criteria API.
   */
  @Test
  //  @Ignore
  public void testBulkUpdateCriteria()
  {
    System.out.println("----- testBulkUpdateCriteria -----");

    int dummyCocktailCount = createDummyCocktails();

    CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
    CriteriaUpdate<Cocktail> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Cocktail.class);
    Root<Cocktail> c = criteriaUpdate.from(Cocktail.class);
    Path<String> cName = c.get(Cocktail_.name);
    criteriaUpdate.set(cName, criteriaBuilder.concat(cName, " (a)"));
    criteriaUpdate.where(criteriaBuilder.equal(cName, "Dummy"));

    Query query = this.entityManager.createQuery(criteriaUpdate);
    int changedRowCount = query.executeUpdate();

    Assert.assertEquals("Changed row count", dummyCocktailCount, changedRowCount);

    // Änderungen nicht dauerhaft ablegen
    this.entityManager.getTransaction().rollback();
  }

  /**
   * Test: Bulk Delete via Criteria API.
   */
  @Test
  //  @Ignore
  public void testBulkDeleteCriteria()
  {
    System.out.println("----- testBulkDeleteCriteria -----");

    int dummyCocktailCount = createDummyCocktails();

    CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
    CriteriaDelete<Cocktail> criteriaDelete = criteriaBuilder.createCriteriaDelete(Cocktail.class);
    Root<Cocktail> c = criteriaDelete.from(Cocktail.class);
    Path<String> cName = c.get(Cocktail_.name);
    criteriaDelete.where(criteriaBuilder.equal(cName, "Dummy"));

    Query query = this.entityManager.createQuery(criteriaDelete);
    int deletedRowCount = query.executeUpdate();

    Assert.assertEquals("Deleted row count", dummyCocktailCount, deletedRowCount);

    // Änderungen nicht dauerhaft ablegen
    this.entityManager.getTransaction().rollback();
  }

  private int createDummyCocktails()
  {
    this.entityManager.persist(new Cocktail("XXX", "Dummy"));
    this.entityManager.persist(new Cocktail("YYY", "Dummy"));
    this.entityManager.flush();
    return 2;
  }
}
