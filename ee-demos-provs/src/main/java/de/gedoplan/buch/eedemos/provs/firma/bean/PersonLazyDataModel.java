package de.gedoplan.buch.eedemos.provs.firma.bean;

import de.gedoplan.baselibs.persistence.repository.SingleIdEntityRepository;
import de.gedoplan.buch.eedemos.provs.common.bean.SingleIdEntityLazyDataModel;
import de.gedoplan.buch.eedemos.provs.firma.entity.Person;
import de.gedoplan.buch.eedemos.provs.firma.repository.PersonRepository;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

@SessionScoped
public class PersonLazyDataModel extends SingleIdEntityLazyDataModel<Person>
{
  @Inject
  private PersonRepository personRepository;

  @Override
  protected SingleIdEntityRepository<?, Person> getRepository()
  {
    return this.personRepository;
  }
}
