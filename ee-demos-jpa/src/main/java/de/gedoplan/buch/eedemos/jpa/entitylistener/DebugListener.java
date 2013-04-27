package de.gedoplan.buch.eedemos.jpa.entitylistener;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

public class DebugListener
{
  @PrePersist
  public void prePersist(Object entity)
  {
    System.out.println("prePersist(" + entity + ")");
  }

  @PostPersist
  public void postPersist(Object entity)
  {
    System.out.println("postPersist(" + entity + ")");
  }

  @PreRemove
  public void preRemove(Object entity)
  {
    System.out.println("preRemove(" + entity + ")");
  }

  @PostRemove
  public void postRemove(Object entity)
  {
    System.out.println("postRemove(" + entity + ")");
  }

  @PreUpdate
  public void preUpdate(Object entity)
  {
    System.out.println("preUpdate(" + entity + ")");
  }

  @PostUpdate
  public void postUpdate(Object entity)
  {
    System.out.println("postUpdate(" + entity + ")");
  }

  @PostLoad
  public void postLoad(Object entity)
  {
    System.out.println("postLoad(" + entity + ")");
  }

}
