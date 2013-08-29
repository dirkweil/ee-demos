package de.gedoplan.buch.eedemos.jpa.model;

import de.gedoplan.buch.eedemos.jpa.entity.Publisher;
import de.gedoplan.buch.eedemos.jpa.repository.PublisherRepository;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUtil;

@Model
public class PublisherModel
{
  @Inject
  PublisherRepository     publisherRepository;
  private List<Publisher> publishers;

  public List<Publisher> getPublishers()
  {
    return this.publishers;
  }

  public boolean isLoaded(Object entity, String attrName)
  {
    PersistenceUtil persistenceUtil = Persistence.getPersistenceUtil();
    return persistenceUtil.isLoaded(entity, attrName);
  }

  @PostConstruct
  void postConstruct()
  {
    this.publishers = this.publisherRepository.findAll("Publisher_books", true);
  }

}
