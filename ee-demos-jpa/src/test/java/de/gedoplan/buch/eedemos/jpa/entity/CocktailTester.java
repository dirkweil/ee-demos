package de.gedoplan.buch.eedemos.jpa.entity;

import de.gedoplan.buch.eedemos.jpa.helper.NameAndBasisZutat;
import de.gedoplan.buch.eedemos.jpa.helper.NameAndVolProz;
import de.gedoplan.buch.eedemos.jpa.util.JpaUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Tuple;
import javax.persistence.TupleElement;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class CocktailTester
{
  public static void main(String[] args)
  {
    insertData();
    // showAll();
    // showNames();
    // showNameAndBasiszutatTuple();
    // showNameAndBasiszutatCtor();
    // showAlcoholic();
    // showAlcoholMaximum();
    // showAllWithAlcoholAverage();
    // showAllWithAlcoholAverage2();
    // showCocktail("Ipanema");
    // showCocktailCounts();
  }

  public static void insertData()
  {
    EntityManager em = JpaUtil.createEntityManager();
    EntityTransaction tx = em.getTransaction();

    tx.begin();

    CocktailZutat cassis = new CocktailZutat("CASS", "Creme de Cassis", 15);
    em.persist(cassis);

    CocktailZutat erdbeeren = new CocktailZutat("ERDB", "Erdbeeren", 0);
    em.persist(erdbeeren);

    CocktailZutat erdbeerSirup = new CocktailZutat("ERDBS", "Erdbeer-Sirup", 0);
    em.persist(erdbeerSirup);

    CocktailZutat gingerAle = new CocktailZutat("GALE", "Ginger Ale", 0);
    em.persist(gingerAle);

    CocktailZutat limette = new CocktailZutat("LIME", "Limette", 0);
    em.persist(limette);

    CocktailZutat maracujaSaft = new CocktailZutat("MARA", "Maracuja-Saft", 0);
    em.persist(maracujaSaft);

    CocktailZutat pfirsichMark = new CocktailZutat("PMARK", "Pfirsich-Mark", 0);
    em.persist(pfirsichMark);

    CocktailZutat rum = new CocktailZutat("RUM", "Rum", 40);
    em.persist(rum);

    CocktailZutat rohrzucker = new CocktailZutat("RZUC", "Rohrzucker", 0);
    em.persist(rohrzucker);

    CocktailZutat sekt = new CocktailZutat("SEKT", "Sekt", 11.5);
    em.persist(sekt);

    CocktailZutat zitronenSaft = new CocktailZutat("ZSAFT", "Zitronen-Saft", 0);
    em.persist(zitronenSaft);

    Cocktail bellini = new Cocktail("BELL", "Bellini");
    bellini.addZutat(sekt);
    bellini.addZutat(pfirsichMark);
    em.persist(bellini);

    Cocktail ipanema = new Cocktail("IPA", "Ipanema");
    ipanema.addZutat(gingerAle);
    ipanema.addZutat(limette);
    ipanema.addZutat(rohrzucker);
    ipanema.addZutat(maracujaSaft);
    em.persist(ipanema);

    Cocktail kirRoyal = new Cocktail("KIRR", "Kir Royal");
    kirRoyal.addZutat(sekt);
    kirRoyal.addZutat(cassis);
    em.persist(kirRoyal);

    Cocktail strawberryDaiquiri = new Cocktail("STDQ", "Strawberry Daiquiri");
    strawberryDaiquiri.addZutat(rum);
    strawberryDaiquiri.addZutat(erdbeeren);
    strawberryDaiquiri.addZutat(erdbeerSirup);
    strawberryDaiquiri.addZutat(zitronenSaft);
    em.persist(strawberryDaiquiri);

    tx.commit();

    em.close();

  }

  public static void showAll()
  {
    EntityManager em = JpaUtil.createEntityManager();

    /*
     * Criteria Query aufbauen äquivalent zu "select c from Cocktail c"
     */

    CriteriaBuilder cBuilder = em.getCriteriaBuilder();

    // Criteria Query für Ergebnistyp erzeugen
    CriteriaQuery<Cocktail> cQuery = cBuilder.createQuery(Cocktail.class);

    // Projektionsvariablen erzeugen (FROM-Klausel)
    Root<Cocktail> c = cQuery.from(Cocktail.class);

    // Selektion angeben (SELECT-Klausel)
    cQuery.select(c);

    // Sortierung nach Name
    cQuery.orderBy(cBuilder.asc(c.get(Cocktail_.name)));

    // Query ausführen
    TypedQuery<Cocktail> query = em.createQuery(cQuery);
    runQuery(query);

    // Alternative Schreibweise "fluent API"
    // cQuery = cBuilder.createQuery(Cocktail.class);
    // query = em.createQuery(cQuery.select(cQuery.from(Cocktail.class)));
    // runQuery(query);

    em.close();
  }

  public static void showNames()
  {
    EntityManager em = JpaUtil.createEntityManager();

    /*
     * Criteria Query aufbauen äquivalent zu "select c.name from Cocktail c"
     */

    CriteriaBuilder cBuilder = em.getCriteriaBuilder();

    // Criteria Query für Ergebnistyp erzeugen
    CriteriaQuery<String> cQuery = cBuilder.createQuery(String.class);

    // Projektionsvariablen erzeugen (FROM-Klausel)
    Root<Cocktail> c = cQuery.from(Cocktail.class);

    // Zugriff auf das Attribut name
    Path<String> cName = c.get("name");

    // Selektion angeben (SELECT-Klausel)
    cQuery.select(cName);

    // Query ausführen
    TypedQuery<String> query = em.createQuery(cQuery);
    runQuery(query);

    em.close();
  }

  public static void showNameAndBasiszutatTuple()
  {
    EntityManager em = JpaUtil.createEntityManager();

    /*
     * Criteria Query aufbauen äquivalent zu "select c.name, c.hauptZutat.name from Cocktail c"
     */

    CriteriaBuilder cBuilder = em.getCriteriaBuilder();

    // Criteria Query für Ergebnistyp erzeugen
    CriteriaQuery<Tuple> cQuery = cBuilder.createQuery(Tuple.class);

    // Projektionsvariablen erzeugen (FROM-Klausel)
    Root<Cocktail> c = cQuery.from(Cocktail.class);

    // Selektion angeben (SELECT-Klausel)
    cQuery.multiselect(c.get(Cocktail_.name).alias("cocktailName"), c.get(Cocktail_.basisZutat).get(CocktailZutat_.name));

    // Query ausführen
    TypedQuery<Tuple> query = em.createQuery(cQuery);
    for (Tuple entry : query.getResultList())
    {
      String name = entry.get("cocktailName", String.class);
      String basisZutat = entry.get(1, String.class);
      System.out.println(name + ": " + basisZutat);
    }

    em.close();
  }

  public static void showNameAndBasiszutatCtor()
  {
    EntityManager em = JpaUtil.createEntityManager();

    /*
     * Criteria Query aufbauen äquivalent zu
     * "select new de.gedoplan.buch.eedemos.jpa.helper.NameAndBasisZutat(c.name, c.basisZutat.name) from Cocktail c"
     */

    CriteriaBuilder cBuilder = em.getCriteriaBuilder();

    // Criteria Query für Ergebnistyp erzeugen
    CriteriaQuery<NameAndBasisZutat> cQuery = cBuilder.createQuery(NameAndBasisZutat.class);

    // Projektionsvariablen erzeugen (FROM-Klausel)
    Root<Cocktail> c = cQuery.from(Cocktail.class);

    // Selektion angeben (SELECT-Klausel)
    cQuery.select(cBuilder.construct(NameAndBasisZutat.class, c.get(Cocktail_.name), c.get(Cocktail_.basisZutat).get(CocktailZutat_.name)));

    // Query ausführen
    TypedQuery<NameAndBasisZutat> query = em.createQuery(cQuery);
    for (NameAndBasisZutat entry : query.getResultList())
    {
      String name = entry.getName();
      String basisZutat = entry.getBasisZutat();
      System.out.println(name + ": " + basisZutat);
    }

    em.close();
  }

  public static void showAlcoholic()
  {
    EntityManager em = JpaUtil.createEntityManager();

    /*
     * Criteria Query aufbauen äquivalent zu "select distinct c from Cocktail c JOIN c.zutaten z where z.volProz<>0"
     */

    CriteriaBuilder cBuilder = em.getCriteriaBuilder();

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
    TypedQuery<Cocktail> query = em.createQuery(cQuery);
    runQuery(query);

    em.close();
  }

  public static void showAlcoholMaximum()
  {
    EntityManager em = JpaUtil.createEntityManager();

    /*
     * Criteria Query aufbauen äquivalent zu "select max(z.volProz) from Zutat z"
     */

    CriteriaBuilder builder = em.getCriteriaBuilder();

    // Criteria Query für Ergebnistyp erzeugen
    CriteriaQuery<Double> cQuery = builder.createQuery(Double.class);

    // Projektionsvariablen erzeugen (FROM-Klausel)
    Root<CocktailZutat> z = cQuery.from(CocktailZutat.class);

    // Selektion angeben (SELECT-Klausel)
    Path<Double> zVolProz = z.get("volProz");
    // Path<Double> zVolProz = z.get(Zutat_.volProz);
    cQuery.select(builder.max(zVolProz));

    // Query ausführen
    TypedQuery<Double> q = em.createQuery(cQuery);
    System.out.println("Maximaler Alkohol-Anteil: " + q.getSingleResult());

    em.close();
  }

  public static void showAllWithAlcoholAverage()
  {
    EntityManager em = JpaUtil.createEntityManager();

    /*
     * Criteria Query aufbauen äquivalent zu "select c.name, avg(z.volProz) from Cocktail c join c.zutaten z group by c.name"
     */

    CriteriaBuilder cBuilder = em.getCriteriaBuilder();

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
    TypedQuery<Tuple> query = em.createQuery(cQuery);
    runQuery(query);

    em.close();
  }

  public static void showAllWithAlcoholAverage2()
  {
    EntityManager em = JpaUtil.createEntityManager();

    /*
     * Criteria Query aufbauen äquivalent zu
     * "select new de.gedoplan.seminar.jpa.demo.test.NameAndVolProz(c.name, avg(z.volProz)) from Cocktail c join c.zutaten z group by c.name"
     */

    CriteriaBuilder builder = em.getCriteriaBuilder();

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
    TypedQuery<NameAndVolProz> q = em.createQuery(cQuery);
    runQuery(q);

    em.close();
  }

  public static void showCocktail(String name)
  {
    EntityManager em = JpaUtil.createEntityManager();

    /*
     * Criteria Query aufbauen äquivalent zu "select c from Cocktail c where c.name=:name"
     */

    CriteriaBuilder cBuilder = em.getCriteriaBuilder();

    // Criteria Query für Ergebnistyp erzeugen
    CriteriaQuery<Cocktail> cQuery = cBuilder.createQuery(Cocktail.class);

    // Projektionsvariablen erzeugen (FROM-Klausel)
    Root<Cocktail> c = cQuery.from(Cocktail.class);

    // Where-Klausel
    cQuery.where(cBuilder.equal(c.get(Cocktail_.name), cBuilder.parameter(String.class, "name")));

    // Selektion angeben (SELECT-Klausel)
    cQuery.select(c);

    // Query ausführen
    TypedQuery<Cocktail> q = em.createQuery(cQuery);
    q.setParameter("name", name);
    runQuery(q);

    em.close();
  }

  public static void showCocktailCounts()
  {
    EntityManager em = JpaUtil.createEntityManager();

    /*
     * Criteria Query aufbauen äquivalent zu "select c.basisZutat.name, count(c) from Cocktail c group by c.basisZutat"
     */

    CriteriaBuilder cBuilder = em.getCriteriaBuilder();

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

    TypedQuery<Tuple> query = em.createQuery(cQuery);
    runQuery(query);

    em.close();
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

}
