package de.gedoplan.buch.eedemos.provs.projekt.repository;

import de.gedoplan.baselibs.enterprise.stereotype.DataRepository;
import de.gedoplan.baselibs.persistence.repository.SingleIdEntityRepository;
import de.gedoplan.buch.eedemos.provs.projekt.entity.Projekt;

import java.util.List;

import javax.enterprise.inject.Produces;

/**
 * Data-Repository für Projekt.
 * 
 * @author dw
 */
@DataRepository
public class ProjektRepository extends SingleIdEntityRepository<Integer, Projekt>
{
  private static final long serialVersionUID = 1L;

  @Produces
  @Override
  public List<Projekt> findAll()
  {
    return super.findAll();
  }
}
