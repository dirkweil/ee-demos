package de.gedoplan.buch.eedemos.ejb.service;

import java.util.Collection;

import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.ScheduleExpression;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.inject.Inject;

import org.apache.commons.logging.Log;

@SuppressWarnings("unused")
@Stateless
public class SchedulerServiceBean implements SchedulerService
{
  @Inject
  private Log log;

  @Schedule(second = "0/10", minute = "*", hour = "*", persistent = false, info = "alle10Sekunden")
  private void alle10Sekunden()
  {
    this.log.debug("alle10Sekunden");
  }

  @Schedule(dayOfWeek = "Mon", info = "montags")
  private void montags()
  {
    this.log.debug("montags");
  }

  @Schedule(minute = "30", hour = "16", dayOfWeek = "Mon-Fri", info = "wochentagsUm1630")
  private void wochentagsUm1630()
  {
    this.log.debug("wochentagsUm1630");
  }

  private void wochentagsUm0315()
  {
    this.log.debug("wochentagsUmxxx");
  }

  @Schedule(minute = "*/5", hour = "*", persistent = false, info = "alle5Minuten")
  private void alle5Minuten()
  {
    this.log.debug("alle5Minuten");
  }

  @Schedule(dayOfMonth = "Last", info = "ultimo")
  private void ultimo()
  {
    this.log.debug("ultimo");
  }

  @Schedule(dayOfMonth = "2nd Fri", hour = "20", info = "jedenZweitenFreitagUm2000")
  private void jedenZweitenFreitagUm2000()
  {
    this.log.debug("jedenZweitenFreitagUm2000");
  }

  @Resource
  private TimerService timerService;

  /**
   * Einige zusätzliche Timer starten.
   */
  @Override
  public void startTimers()
  {
    stopTimers();

    this.timerService.createSingleActionTimer(3000, new TimerConfig("einmalig nach 3 Sekunden", false));
    this.timerService.createIntervalTimer(0, 1000, new TimerConfig("sekuendlich", false));
    this.timerService.createCalendarTimer(new ScheduleExpression().second("0/2").minute("*").hour("*"), new TimerConfig("alle 2 Sekunden", false));
  }

  /**
   * Alle Timer stoppen. Achtung: Dies stoppt auch die durch @Schedule erzeugten Timer!
   */
  @Override
  public void stopTimers()
  {
    Collection<Timer> allTimers = this.timerService.getTimers();
    for (Timer timer : allTimers)
    {
      this.log.debug("Stopping timer " + timer.getInfo());
      timer.cancel();
    }
  }

  @Timeout
  public void tick(Timer timer)
  {
    this.log.debug("tick: " + timer.getInfo());
  }
}
