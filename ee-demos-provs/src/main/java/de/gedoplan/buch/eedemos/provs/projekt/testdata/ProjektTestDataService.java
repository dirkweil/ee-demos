package de.gedoplan.buch.eedemos.provs.projekt.testdata;

import de.gedoplan.baselibs.enterprise.testdata.TestDataService;
import de.gedoplan.baselibs.utils.exception.BugException;
import de.gedoplan.baselibs.utils.util.StringSplitter;
import de.gedoplan.buch.eedemos.provs.common.testdata.TestDataLevel;
import de.gedoplan.buch.eedemos.provs.common.testdata.TestDataLoaderUtil;
import de.gedoplan.buch.eedemos.provs.common.testdata.TestDataLoaderUtil.TestDataLineLoader;
import de.gedoplan.buch.eedemos.provs.common.testdata.TestDataSzenario;
import de.gedoplan.buch.eedemos.provs.firma.entity.Firma;
import de.gedoplan.buch.eedemos.provs.firma.entity.Mitarbeiter;
import de.gedoplan.buch.eedemos.provs.firma.repository.FirmaRepository;
import de.gedoplan.buch.eedemos.provs.firma.repository.MitarbeiterRepository;
import de.gedoplan.buch.eedemos.provs.projekt.entity.Aufgabe;
import de.gedoplan.buch.eedemos.provs.projekt.entity.MitarbeiterAufgabe;
import de.gedoplan.buch.eedemos.provs.projekt.entity.Projekt;
import de.gedoplan.buch.eedemos.provs.projekt.entity.ProjektStatus;
import de.gedoplan.buch.eedemos.provs.projekt.entity.ProjektTyp;
import de.gedoplan.buch.eedemos.provs.projekt.entity.ProjektZeit;
import de.gedoplan.buch.eedemos.provs.projekt.repository.ProjektRepository;
import de.gedoplan.buch.eedemos.provs.projekt.repository.ProjektZeitRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Testdaten-Service für die Komponente projekt.
 * 
 * @author dw
 */
public class ProjektTestDataService implements TestDataService
{
  private static final long     serialVersionUID = 1L;

  @Inject
  private ProjektRepository     projektDao;

  @Inject
  private FirmaRepository       firmaService;

  @Inject
  private MitarbeiterRepository mitarbeiterService;

  @Inject
  private ProjektZeitRepository projektZeitRepository;

  @Inject
  private EntityManager         entityManager;

  private List<ProjektZeit>     projektZeitListe = new ArrayList<>();

  /**
   * {@inheritDoc}
   * 
   * @see de.gedoplan.baselibs.enterprise.testdata.TestDataService#getLevel()
   */
  @Override
  public int getLevel()
  {
    return TestDataLevel.PROJEKTMGMT.ordinal();
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.gedoplan.baselibs.enterprise.testdata.TestDataService#loadTestData(java.lang.String, java.util.Date)
   */
  @Override
  public void loadTestData(String szenarioName, Date moment)
  {
    TestDataSzenario szenario = TestDataSzenario.getSzenario(szenarioName);

    TestDataLineLoader projektLineLoader = new TestDataLineLoader()
    {
      private Aufgabe            aufgabe            = null;

      private MitarbeiterAufgabe mitarbeiteraufgabe = null;

      // 0=Projekt, 1=Teilprojekt, 2=Teilprojekt vom Teilprojekt, ...
      private int                level              = -1;

      @Override
      public void loadTestDataLine(TestDataSzenario szenario, Date moment, String line)
      {
        StringSplitter stringSplitter = new StringSplitter(line, "\\s*\\|\\s*");

        String discriminator = stringSplitter.get(0);
        if ("P".equalsIgnoreCase(discriminator))
        {
          this.aufgabe = loadProjekt(szenario, moment, stringSplitter);
          this.level = 0;
        }
        else if (discriminator.startsWith("T"))
        {
          int newLevel = discriminator.length();
          if (newLevel > this.level + 1)
          {
            throw new BugException("Teilprojekt-Level zu hoch");
          }
          else
          {
            while (newLevel <= this.level)
            {
              this.aufgabe = this.aufgabe.getVaterAufgabe();
              --this.level;
            }
          }

          this.aufgabe = loadTeilProjekt(szenario, moment, stringSplitter, this.aufgabe);
          ++this.level;
        }
        else if ("M".equalsIgnoreCase(discriminator))
        {
          this.mitarbeiteraufgabe = loadMitarbeiterZuordnung(szenario, moment, stringSplitter, this.aufgabe);
        }
        else if ("Z".equalsIgnoreCase(discriminator))
        {
          loadMitarbeiterZeit(szenario, moment, stringSplitter, this.mitarbeiteraufgabe);
        }
        else
        {
          throw new BugException("Ungültiger Diskriminator: " + discriminator);
        }
      }
    };

    TestDataLoaderUtil.loadTestDataFromFile(szenario, moment, "Projekt", projektLineLoader);

    for (ProjektZeit projektZeit : this.projektZeitListe)
    {
      this.projektZeitRepository.persist(projektZeit);
    }
  }

  private void loadMitarbeiterZeit(TestDataSzenario szenario, Date moment, StringSplitter stringSplitter, MitarbeiterAufgabe mitarbeiteraufgabe)
  {
    int idx = 0;
    String minutenEingabe = stringSplitter.get(++idx);
    String datumEingabe = stringSplitter.get(++idx);
    String abgerechnetEingabe = stringSplitter.get(++idx);
    String anzahlWiederholungenEingabe = stringSplitter.get(++idx);

    if (minutenEingabe == null || datumEingabe == null)
    {
      throw new BugException("Minuten und Datum sind Pflichtfelder");
    }

    int minuten;
    Date datum;
    try
    {
      minuten = Integer.parseInt(minutenEingabe);
      datum = parseDay(datumEingabe, moment);
    }
    catch (NumberFormatException ex)
    {
      throw new BugException("Eingabe konnte nicht als Zahl interpretiert werden : " + minutenEingabe);
    }
    catch (ParseException ex)
    {
      throw new BugException("Nicht erwartetes Datumformat (yyyy-dd-MM oder now+dd): " + datumEingabe);
    }

    boolean abgerechnet = abgerechnetEingabe != null && "1".equals(abgerechnetEingabe);

    int anzahlWiederholungen = 1;
    if (anzahlWiederholungenEingabe != null)
    {
      try
      {
        anzahlWiederholungen = Integer.parseInt(anzahlWiederholungenEingabe);
      }
      catch (NumberFormatException e)
      {
        throw new BugException("Eingabe konnte nicht als Zahl interpretiert werden : " + anzahlWiederholungenEingabe);
      }
    }

    while (anzahlWiederholungen > 0)
    {
      ProjektZeit projektZeit = new ProjektZeit(datum, mitarbeiteraufgabe, minuten);
      projektZeit.setAbgerechnet(abgerechnet);
      this.projektZeitListe.add(projektZeit);

      Calendar cal = Calendar.getInstance();
      cal.setTime(datum);
      cal.add(Calendar.DATE, 1);
      datum = cal.getTime();

      --anzahlWiederholungen;
    }
  }

  private Date parseDay(String datumEingabe, Date moment) throws ParseException
  {
    if (datumEingabe.startsWith("now"))
    {
      try
      {
        int diff = Integer.parseInt(datumEingabe.substring(3));
        Calendar cal = Calendar.getInstance();
        cal.setTime(moment);
        cal.add(Calendar.DATE, diff);
        return cal.getTime();
      }
      catch (NumberFormatException e)
      {
        throw new ParseException("Illegal day count", 3);
      }
    }
    else
    {
      return new SimpleDateFormat("yyyy-dd-MM").parse(datumEingabe);
    }
  }

  private Projekt loadProjekt(TestDataSzenario szenario, Date moment, StringSplitter stringSplitter)
  {
    int idx = 0;
    String nummer = stringSplitter.get(++idx);
    String titel = stringSplitter.get(++idx);
    String auftraggeberName = stringSplitter.get(++idx);
    String auftraggeberAnsprechpartnerName = stringSplitter.get(++idx);
    String auftraggeberAnsprechpartnerVorname = stringSplitter.get(++idx);
    String auftragnehmerName = stringSplitter.get(++idx);
    String auftragnehmerAnsprechpartnerName = stringSplitter.get(++idx);
    String auftragnehmerAnsprechpartnerVorname = stringSplitter.get(++idx);
    String typString = stringSplitter.get(++idx);
    String statusString = stringSplitter.get(++idx);
    String maxEurString = stringSplitter.get(++idx);
    String maxMinString = stringSplitter.get(++idx);

    Firma auftraggeber = ProjektTestDataService.this.firmaService.findByName(auftraggeberName);
    if (auftraggeber == null)
    {
      throw new BugException("Auftraggeber nicht gefunden: " + auftraggeberName);
    }

    Mitarbeiter auftraggeberAnsprechpartner = null;
    if (auftraggeberAnsprechpartnerName != null)
    {
      List<Mitarbeiter> maListe = ProjektTestDataService.this.mitarbeiterService.findByFirmaAndName(auftraggeber, auftraggeberAnsprechpartnerName, auftraggeberAnsprechpartnerVorname);
      if (maListe.isEmpty())
      {
        throw new BugException("Ansprechpartner nicht gefunden: " + auftraggeberName + "/" + auftraggeberAnsprechpartnerName + "," + auftraggeberAnsprechpartnerVorname);
      }
      auftraggeberAnsprechpartner = maListe.get(0);
    }

    Firma auftragnehmer = ProjektTestDataService.this.firmaService.findByName(auftragnehmerName);
    if (auftragnehmer == null)
    {
      throw new BugException("Auftragnehmer nicht gefunden: " + auftragnehmerName);
    }

    List<Mitarbeiter> maListe = ProjektTestDataService.this.mitarbeiterService.findByFirmaAndName(auftragnehmer, auftragnehmerAnsprechpartnerName, auftragnehmerAnsprechpartnerVorname);
    if (maListe.isEmpty())
    {
      throw new BugException("Ansprechpartner nicht gefunden: " + auftragnehmerName + "/" + auftragnehmerAnsprechpartnerName + "," + auftragnehmerAnsprechpartnerVorname);
    }
    Mitarbeiter auftragnehmerAnsprechpartner = maListe.get(0);

    Projekt projekt = new Projekt(nummer, titel);

    if (auftraggeber != null)
    {
      projekt.setAuftraggeber(auftraggeber);

      if (auftraggeberAnsprechpartner != null)
      {
        projekt.setAuftraggeberAnsprechpartner(auftraggeberAnsprechpartner);
      }
    }

    if (auftragnehmer != null)
    {
      projekt.setAuftragnehmer(auftragnehmer);

      if (auftragnehmerAnsprechpartner != null)
      {
        projekt.setAuftragnehmerAnsprechpartner(auftragnehmerAnsprechpartner);
      }
    }

    projekt.setTyp(ProjektTyp.valueOf(typString));
    projekt.setStatus(ProjektStatus.valueOf(statusString));

    if (maxEurString != null)
    {
      projekt.getLimit().setBudget(Double.valueOf(maxEurString));
    }

    if (maxMinString != null)
    {
      projekt.getLimit().setMaxMinuten(Integer.valueOf(maxMinString));
    }

    ProjektTestDataService.this.projektDao.persist(projekt);

    return projekt;
  }

  private Aufgabe loadTeilProjekt(TestDataSzenario szenario, Date moment, StringSplitter stringSplitter, Aufgabe parent)
  {
    int idx = 0;
    String nummer = stringSplitter.get(++idx);
    String titel = stringSplitter.get(++idx);
    String fakturaProzentString = stringSplitter.get(++idx);
    String arbeitProzentString = stringSplitter.get(++idx);
    String maxEurString = stringSplitter.get(++idx);
    String maxMinString = stringSplitter.get(++idx);

    Aufgabe teilProjekt = parent.addTeilAufgabe(nummer, titel);

    if (maxEurString != null)
    {
      teilProjekt.getLimit().setBudget(Double.valueOf(maxEurString));
    }

    if (maxMinString != null)
    {
      teilProjekt.getLimit().setMaxMinuten(Integer.valueOf(maxMinString));
    }

    if (fakturaProzentString != null)
    {
      teilProjekt.setFakturaProzent(Integer.valueOf(fakturaProzentString));
    }

    if (arbeitProzentString != null)
    {
      teilProjekt.setArbeitProzent(Integer.valueOf(arbeitProzentString));
    }

    return teilProjekt;
  }

  private MitarbeiterAufgabe loadMitarbeiterZuordnung(TestDataSzenario szenario, Date moment, StringSplitter stringSplitter, Aufgabe aufgabe)
  {
    int idx = 0;
    String firmaName = stringSplitter.get(++idx);
    String name = stringSplitter.get(++idx);
    String vorname = stringSplitter.get(++idx);

    String vkString = stringSplitter.get(++idx);
    String ekString = stringSplitter.get(++idx);
    String planProzentString = stringSplitter.get(++idx);
    String maxEurString = stringSplitter.get(++idx);
    String maxMinString = stringSplitter.get(++idx);

    String start = stringSplitter.get(++idx);
    String ende = stringSplitter.get(++idx);

    Firma firma = ProjektTestDataService.this.firmaService.findByName(firmaName);
    if (firma == null)
    {
      throw new BugException("Firma nicht gefunden: " + firmaName);
    }

    Mitarbeiter mitarbeiter = null;
    if (name != null)
    {
      List<Mitarbeiter> maListe = ProjektTestDataService.this.mitarbeiterService.findByFirmaAndName(firma, name, vorname);
      if (maListe.isEmpty())
      {
        throw new BugException("Mitarbeiter nicht gefunden: " + firmaName + "/" + name + "," + vorname);
      }
      mitarbeiter = maListe.get(0);
    }

    MitarbeiterAufgabe mitarbeiterAufgabe = aufgabe.addMitarbeiterAufgabe(mitarbeiter);

    if (vkString != null)
    {
      mitarbeiterAufgabe.setVkSatz(Double.parseDouble(vkString));
    }

    if (ekString != null)
    {
      mitarbeiterAufgabe.setEkSatz(Double.parseDouble(ekString));
    }

    if (planProzentString != null)
    {
      mitarbeiterAufgabe.setPlanProzent(Integer.parseInt(planProzentString));
    }

    if (maxEurString != null)
    {
      mitarbeiterAufgabe.getLimit().setBudget(Double.valueOf(planProzentString));
    }

    if (maxMinString != null)
    {
      mitarbeiterAufgabe.getLimit().setMaxMinuten(Integer.valueOf(maxMinString));
    }

    if (start != null)
    {
      try
      {
        mitarbeiterAufgabe.getLimit().setStart(parseDay(start.trim(), moment));
      }
      catch (ParseException e)
      {
        throw new BugException(e);
      }
    }

    if (ende != null)
    {
      try
      {
        mitarbeiterAufgabe.getLimit().setEnde(parseDay(ende.trim(), moment));
      }
      catch (ParseException e)
      {
        throw new BugException(e);
      }
    }

    return mitarbeiterAufgabe;
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.gedoplan.fw.test.TestDataService#unloadTestData()
   */
  @Override
  public void unloadTestData()
  {
    this.entityManager.createNativeQuery("delete from " + ProjektZeit.TABLE_NAME).executeUpdate();
    this.entityManager.createNativeQuery("delete from " + MitarbeiterAufgabe.TABLE_NAME).executeUpdate();
    this.entityManager.createNativeQuery("update " + Aufgabe.TABLE_NAME + " set VATERAUFGABE_ID=null").executeUpdate();
    this.entityManager.createNativeQuery("delete from " + Projekt.TABLE_NAME).executeUpdate();

    this.entityManager.clear();
  }
}
