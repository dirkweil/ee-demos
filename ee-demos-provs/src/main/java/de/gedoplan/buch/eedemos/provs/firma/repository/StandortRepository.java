package de.gedoplan.buch.eedemos.provs.firma.repository;

import de.gedoplan.baselibs.enterprise.stereotype.DataRepository;
import de.gedoplan.baselibs.persistence.repository.SingleIdEntityRepository;
import de.gedoplan.buch.eedemos.provs.firma.entity.Standort;

/**
 * Data-Repository für Standort.
 * 
 * @author dw
 */
@DataRepository
public class StandortRepository extends SingleIdEntityRepository<Integer, Standort>
{
  private static final long serialVersionUID = 1L;
}
