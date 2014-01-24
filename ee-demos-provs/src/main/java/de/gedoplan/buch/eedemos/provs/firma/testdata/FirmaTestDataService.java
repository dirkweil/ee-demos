package de.gedoplan.buch.eedemos.provs.firma.testdata;

import de.gedoplan.baselibs.enterprise.testdata.TestDataService;
import de.gedoplan.baselibs.utils.exception.BugException;
import de.gedoplan.baselibs.utils.util.StringSplitter;
import de.gedoplan.buch.eedemos.provs.common.testdata.TestDataLevel;
import de.gedoplan.buch.eedemos.provs.common.testdata.TestDataLoaderUtil;
import de.gedoplan.buch.eedemos.provs.common.testdata.TestDataLoaderUtil.TestDataLineLoader;
import de.gedoplan.buch.eedemos.provs.common.testdata.TestDataSzenario;
import de.gedoplan.buch.eedemos.provs.firma.entity.Firma;
import de.gedoplan.buch.eedemos.provs.firma.entity.Land;
import de.gedoplan.buch.eedemos.provs.firma.entity.Mitarbeiter;
import de.gedoplan.buch.eedemos.provs.firma.entity.MitarbeitsTyp;
import de.gedoplan.buch.eedemos.provs.firma.entity.Person;
import de.gedoplan.buch.eedemos.provs.firma.entity.Standort;
import de.gedoplan.buch.eedemos.provs.firma.entity.StrassenAdresse;
import de.gedoplan.buch.eedemos.provs.firma.repository.FirmaRepository;
import de.gedoplan.buch.eedemos.provs.firma.repository.LandRepository;
import de.gedoplan.buch.eedemos.provs.firma.repository.MitarbeiterRepository;
import de.gedoplan.buch.eedemos.provs.firma.repository.PersonRepository;

import java.util.Date;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.apache.commons.logging.Log;

/**
 * Testdaten-Service für die Komponente firma.
 * 
 * @author dw
 */
public class FirmaTestDataService implements TestDataService
{
  private static final long serialVersionUID = 1L;

  @Inject
  FirmaRepository           firmaRepository;

  @Inject
  LandRepository            landRepository;

  @Inject
  PersonRepository          personRepository;

  @Inject
  MitarbeiterRepository     mitarbeiterRepository;

  @Inject
  EntityManager             entityManager;

  @Inject
  Log                       logger;

  /**
   * {@inheritDoc}
   * 
   * @see de.gedoplan.baselibs.enterprise.testdata.TestDataService#getLevel()
   */
  @Override
  public int getLevel()
  {
    return TestDataLevel.FIRMENMGMT.ordinal();
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.gedoplan.baselibs.enterprise.testdata.TestDataService#loadTestData(java.lang.String, java.util.Date)
   */
  @Override
  @Transactional
  public void loadTestData(String szenarioName, Date moment)
  {
    TestDataSzenario szenario = TestDataSzenario.getSzenario(szenarioName);

    TestDataLineLoader landLineLoader = new TestDataLineLoader()
    {
      @Override
      public void loadTestDataLine(TestDataSzenario szenario, Date moment, String line)
      {
        StringSplitter stringSplitter = new StringSplitter(line, "\\s*\\|\\s*");

        Land land = new Land(stringSplitter.get(0), stringSplitter.get(1));
        land.setPlzPraefix(stringSplitter.get(2));
        land.setTelefonVorwahl(stringSplitter.get(3));

        FirmaTestDataService.this.landRepository.persist(land);
      }
    };

    TestDataLoaderUtil.loadTestDataFromFile(szenario, moment, "Land", landLineLoader);

    TestDataLineLoader firmaLineLoader = new TestDataLineLoader()
    {
      private Firma    firma    = null;

      private Standort standort = null;

      @Override
      public void loadTestDataLine(TestDataSzenario szenario, Date moment, String line)
      {
        StringSplitter stringSplitter = new StringSplitter(line, "\\s*\\|\\s*");

        String discriminator = stringSplitter.get(0);
        if ("F".equalsIgnoreCase(discriminator))
        {
          this.firma = loadFirma(szenario, moment, stringSplitter);
          this.standort = null;
        }
        else
        if ("S".equalsIgnoreCase(discriminator))
        {
          this.standort = loadStandort(szenario, moment, stringSplitter, this.firma);
        }
        else
        if ("M".equalsIgnoreCase(discriminator))
        {
          loadMitarbeiter(szenario, moment, stringSplitter, this.firma, this.standort);
        }
        else
        {
          throw new BugException("Ungültiger Diskriminator: " + discriminator);
        }
      }
    };

    TestDataLoaderUtil.loadTestDataFromFile(szenario, moment, "Firma", firmaLineLoader);

    // loadAdditionalPersonen();
  }

  // private void loadAdditionalPersonen()
  // {
  // for (char anfangsBuchstabe = 'A'; anfangsBuchstabe <= 'Z'; ++anfangsBuchstabe)
  // {
  // for (int i = 100; i < 200; ++i)
  // {
  // Person person = new Person("" + anfangsBuchstabe + i, "Heinz");
  // this.personRepository.persist(person);
  // }
  // }
  // }

  private Firma loadFirma(TestDataSzenario szenario, Date moment, StringSplitter stringSplitter)
  {
    Firma firma = new Firma(stringSplitter.get(1));

    Land land = this.landRepository.findById(stringSplitter.get(4));
    StrassenAdresse besuchsAdresse = firma.getBesuchsAdresse();
    besuchsAdresse.setStrasse(stringSplitter.get(2));
    besuchsAdresse.setHausNummer(stringSplitter.get(3));
    besuchsAdresse.setPlz(stringSplitter.get(5));
    besuchsAdresse.setOrt(stringSplitter.get(6));
    besuchsAdresse.setLand(land);
    firma.setMailAdresse(stringSplitter.get(7));
    firma.setTelefonNummer(stringSplitter.get(8));
    this.firmaRepository.persist(firma);
    return firma;
  }

  private Standort loadStandort(TestDataSzenario szenario, Date moment, StringSplitter stringSplitter, Firma firma)
  {
    Standort standort = firma.addStandort();
    standort.setName(stringSplitter.get(1));
    Land land = this.landRepository.findById(stringSplitter.get(4));
    StrassenAdresse besuchsAdresse = standort.getBesuchsAdresse();
    besuchsAdresse.setStrasse(stringSplitter.get(2));
    besuchsAdresse.setHausNummer(stringSplitter.get(3));
    besuchsAdresse.setPlz(stringSplitter.get(5));
    besuchsAdresse.setOrt(stringSplitter.get(6));
    besuchsAdresse.setLand(land);
    standort.setMailAdresse(stringSplitter.get(7));
    standort.setTelefonNummer(stringSplitter.get(8));
    return standort;
  }

  private void loadMitarbeiter(TestDataSzenario szenario, Date moment, StringSplitter stringSplitter, Firma firma, Standort standort)
  {
    int idx = 0;
    String name = stringSplitter.get(++idx);
    String vorname = stringSplitter.get(++idx);
    String mailAdresse = stringSplitter.get(++idx);
    String maTyp = stringSplitter.get(++idx);

    Person person = new Person(name, vorname);
    person.setUserId(name.toLowerCase());
    this.personRepository.persist(person);

    Mitarbeiter mitarbeiter = firma.addMitarbeiter();
    mitarbeiter.setPerson(person);
    if (standort != null)
    {
      mitarbeiter.setStandort(standort);
    }
    mitarbeiter.setMailAdresse(mailAdresse);
    if (maTyp != null)
    {
      mitarbeiter.setMitarbeitsTyp(MitarbeitsTyp.valueOf(maTyp));
    }
    while (true)
    {
      String telNr = stringSplitter.get(++idx);
      if (telNr == null)
      {
        break;
      }
      if (telNr != null)
      {
        mitarbeiter.getTelefonNummern().add(telNr);
      }
    }
    this.mitarbeiterRepository.persist(mitarbeiter);
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.gedoplan.baselibs.enterprise.testdata.TestDataService#unloadTestData()
   */
  @Override
  @Transactional
  public void unloadTestData()
  {
    this.entityManager.joinTransaction();

    this.entityManager.createNativeQuery("delete from " + Mitarbeiter.TABLE_NAME_TELEFONNUMMERN).executeUpdate();
    this.entityManager.createQuery("delete from Mitarbeiter").executeUpdate();
    this.entityManager.createNativeQuery("delete from " + Person.TABLE_NAME_TELEFONNUMMERN).executeUpdate();
    this.entityManager.createQuery("delete from Person").executeUpdate();
    this.entityManager.createQuery("delete from Standort").executeUpdate();
    this.entityManager.createQuery("delete from Firma").executeUpdate();
    this.entityManager.createQuery("delete from Land").executeUpdate();

    this.entityManager.clear();
  }
}
