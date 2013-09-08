package de.gedoplan.buch.eedemos.provs.firma.repository;

import de.gedoplan.baselibs.enterprise.stereotype.DataRepository;
import de.gedoplan.baselibs.persistence.repository.SingleIdEntityRepository;
import de.gedoplan.buch.eedemos.provs.firma.entity.Land;

/**
 * Data-Repository für Land.
 * 
 * @author dw
 */
@DataRepository
public class LandRepository extends SingleIdEntityRepository<String, Land>
{
  private static final long serialVersionUID = 1L;
}
