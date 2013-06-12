package de.gedoplan.buch.eedemos.cdi.beans;

import de.gedoplan.buch.eedemos.cdi.extension.Startup;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.commons.logging.Log;

@ApplicationScoped
@Startup
public class StartupBean
{
  @Inject
  Log log;

  @PostConstruct
  void init()
  {
    this.log.info("initializing ...");
  }

}
