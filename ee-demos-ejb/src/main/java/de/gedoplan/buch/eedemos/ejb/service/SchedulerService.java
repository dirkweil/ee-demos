package de.gedoplan.buch.eedemos.ejb.service;

import javax.ejb.Remote;

@Remote
public interface SchedulerService
{
  public void startTimers();

  public void stopTimers();
}
