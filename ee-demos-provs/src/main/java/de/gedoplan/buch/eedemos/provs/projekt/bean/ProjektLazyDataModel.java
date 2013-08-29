package de.gedoplan.buch.eedemos.provs.projekt.bean;

import de.gedoplan.baselibs.persistence.repository.QueryFilter.FilterOperation;
import de.gedoplan.baselibs.persistence.repository.SingleIdEntityRepository;
import de.gedoplan.buch.eedemos.provs.common.bean.SingleIdEntityLazyDataModel;
import de.gedoplan.buch.eedemos.provs.projekt.entity.Projekt;
import de.gedoplan.buch.eedemos.provs.projekt.repository.ProjektRepository;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

@SessionScoped
public class ProjektLazyDataModel extends SingleIdEntityLazyDataModel<Projekt>
{
  @Inject
  private ProjektRepository projektRepository;

  @Override
  protected SingleIdEntityRepository<?, Projekt> getRepository()
  {
    return this.projektRepository;
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.gedoplan.buch.eedemos.provs.common.bean.SingleIdEntityLazyDataModel#getFilterOperation(java.lang.String)
   */
  @Override
  protected FilterOperation getFilterOperation(String field)
  {
    if ("nummer".equals(field))
    {
      return FilterOperation.CONTAINS;
    }

    return super.getFilterOperation(field);
  }
}
