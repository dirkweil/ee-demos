package de.gedoplan.buch.eedemos.jsf.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class FlowModelBase
{
  protected Log                   log               = LogFactory.getLog(this.getClass());

  protected String                created;

  private static final DateFormat CREATED_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

  public String getCreated()
  {
    return this.created;
  }

  @PostConstruct
  void postConstruct()
  {
    this.created = CREATED_FORMATTER.format(new Date());
    this.log.info(this.created + ": Construct");
  }

  @PreDestroy
  void preDestroy()
  {
    this.log.info(this.created + ": Destroy");
  }
}
