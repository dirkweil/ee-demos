package de.gedoplan.buch.eedemos.rs.service;

import de.gedoplan.buch.eedemos.rs.entity.Talk;
import de.gedoplan.buch.eedemos.rs.entity.TalkType;
import de.gedoplan.buch.eedemos.rs.entity.Track;
import de.gedoplan.buch.eedemos.rs.persistence.EntityManagerProducer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Initialisierungsservice für Demo-Daten.
 *
 * Dieser Service nutzt den Lifecycle-Event nach der Erzeugung des Application Scopes zur Ausführung einer Methode, die einige
 * Demo-Daten in die Datenbank schreibt.
 *
 * Achtung: Es kann hier nicht der "normale", von {@link EntityManagerProducer} erzeugte Entitymanager genutzt werden, da der dazu
 * nötige Request Scope hier noch nicht aktiv ist. Damit sind auch die normalen Repositories nicht verwendbar. Es muss also direkt
 * mit einem eigenen Enitymanager gearbeitet werden!
 *
 * @author dw
 */
@ApplicationScoped
public class InitTrackTalkDemoDataService
{
  @PersistenceContext
  private EntityManager     entityManager;

  private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

  private static Log        log        = LogFactory.getLog(InitTrackTalkDemoDataService.class);

  @Transactional
  private void createDemoData(@Observes @Initialized(ApplicationScoped.class) Object event)
  {
    try
    {
      if (this.entityManager.createQuery("select count(t) from Track t", Long.class).getSingleResult() == 0)
      {
        Track powerWorkshops = new Track("Power Workshops");
        Talk lambdasAndStreams = new Talk("Workshop zu Lambdas und Streams in Java 8", TalkType.WORKSHOP, dateFormat.parse("2014-05-12 09:00"), 480, "Angelika Langer", "Klaus Kreft");
        powerWorkshops.getTalks().add(lambdasAndStreams);
        Talk javaEEWorkshop = new Talk("Power Workshop Java EE", TalkType.WORKSHOP, dateFormat.parse("2014-05-12 09:00"), 480, "Dirk Weil");
        powerWorkshops.getTalks().add(javaEEWorkshop);
        this.entityManager.persist(powerWorkshops);

        Track javaEeDay = new Track("Java EE Day");
        Talk javaEEQuantumOfCode = new Talk("JavaEE (7) Quantum of Code", TalkType.SESSION, dateFormat.parse("2014-05-13 09:45"), 75, "Adam Bean");
        javaEeDay.getTalks().add(javaEEQuantumOfCode);
        Talk feigeSein = new Talk("Feige sein!", TalkType.SESSION, dateFormat.parse("2014-05-13 15:00"), 60, "Dirk Weil");
        javaEeDay.getTalks().add(feigeSein);
        Talk hypermediaMitJaxRs = new Talk("Hypermedia mit JAX-RS 2.0", TalkType.SESSION, dateFormat.parse("2014-05-13 18:00"), 60, "Thilo Frotscher");
        javaEeDay.getTalks().add(hypermediaMitJaxRs);
        this.entityManager.persist(javaEeDay);

        Track internetOfThingsDay = new Track("Internet of Things Day");
        Talk eclipseSmartHome = new Talk("Eclipse SmartHome", TalkType.SESSION, dateFormat.parse("2014-05-14 16:00"), 60, "Thomas Eichstädt-Engelen");
        internetOfThingsDay.getTalks().add(eclipseSmartHome);
        Talk javaSe8ForTabletsPisAndLegos = new Talk("Java SE 8 for Tablets, Pis, and Legos", TalkType.SESSION, dateFormat.parse("2014-05-14 17:15"), 60, "Stephen Chin");
        internetOfThingsDay.getTalks().add(javaSe8ForTabletsPisAndLegos);
        Talk realSteal = new Talk("Real Steel – der ultimative Roboterfight!", TalkType.SESSION, dateFormat.parse("2014-05-14 20:55"), 120, "Bernhard Löwenstein");
        internetOfThingsDay.getTalks().add(realSteal);
        this.entityManager.persist(internetOfThingsDay);

        Track dummyTrack = new Track("Dummy Track");
        for (int i = 1; i <= 100; ++i)
        {
          Talk dummyTalk = new Talk(String.format("Dummy Talk %03d", i), TalkType.SESSION, dateFormat.parse("2014-05-18 10:00"), 15, String.format("Dummy Talker %03d", i));
          dummyTrack.getTalks().add(dummyTalk);
        }
        this.entityManager.persist(dummyTrack);
      }
    }
    catch (Exception e)
    {
      log.warn("Cannot create demo data", e);
    }

  }

}
