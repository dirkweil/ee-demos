package de.gedoplan.buch.eedemos.jpa.entitylistener;

import javax.inject.Inject;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import org.apache.commons.logging.Log;

public class TraceListener
{
  // Im EE-Umfeld wird diese Injektion ausgeführt; im SE-Umfeld bleibt die Variable null
  @Inject
  Log log;

  @PrePersist
  public void prePersist(Object entity)
  {
    doLog("prePersist(" + entity + ")");
  }

  @PostPersist
  public void postPersist(Object entity)
  {
    doLog("postPersist(" + entity + ")");
  }

  @PreRemove
  public void preRemove(Object entity)
  {
    doLog("preRemove(" + entity + ")");
  }

  @PostRemove
  public void postRemove(Object entity)
  {
    doLog("postRemove(" + entity + ")");
  }

  @PreUpdate
  public void preUpdate(Object entity)
  {
    doLog("preUpdate(" + entity + ")");
  }

  @PostUpdate
  public void postUpdate(Object entity)
  {
    doLog("postUpdate(" + entity + ")");
  }

  @PostLoad
  public void postLoad(Object entity)
  {
    doLog("postLoad(" + entity + ")");
  }

  private void doLog(String string)
  {
    if (this.log != null && this.log.isTraceEnabled())
    {
      this.log.trace(string);
    }

  }

}
