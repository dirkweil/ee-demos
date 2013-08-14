package de.gedoplan.buch.eedemos.jsf.model;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.apache.commons.logging.Log;

public abstract class FlowModelBase
{
  @Inject
  Log log;

  public String getDummy()
  {
    return null;
  }

  @PostConstruct
  void postConstruct()
  {
    this.log.info(getClass().getSimpleName() + ": Construct");
  }

  @PreDestroy
  void preDestroy()
  {
    this.log.info(getClass().getSimpleName() + ": Destroy");
  }
}
