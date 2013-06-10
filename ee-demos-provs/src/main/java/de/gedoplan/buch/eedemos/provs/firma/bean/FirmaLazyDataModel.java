package de.gedoplan.buch.eedemos.provs.firma.bean;

import de.gedoplan.baselibs.persistence.repository.SingleIdEntityRepository;
import de.gedoplan.buch.eedemos.provs.common.bean.SingleIdEntityLazyDataModel;
import de.gedoplan.buch.eedemos.provs.firma.entity.Firma;
import de.gedoplan.buch.eedemos.provs.firma.repository.FirmaRepository;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

@SessionScoped
public class FirmaLazyDataModel extends SingleIdEntityLazyDataModel<Firma>
{
  @Inject
  private FirmaRepository firmaRepository;

  @Override
  protected SingleIdEntityRepository<?, Firma> getRepository()
  {
    return this.firmaRepository;
  }
}
