package de.gedoplan.buch.eedemos.jsf.model;

import de.gedoplan.buch.eedemos.jsf.entity.Bank;
import de.gedoplan.buch.eedemos.jsf.repository.BankRepository;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class BankModel implements Serializable
{
  @Inject
  private BankRepository bankRepository;

  private Bank           searchObj = new Bank();
  private List<Bank>     searchResult;

  private Bank           editObj;

  public Bank getSearchObj()
  {
    return this.searchObj;
  }

  public Bank getEditObj()
  {
    return this.editObj;
  }

  public List<Bank> getSearchResult()
  {
    return this.searchResult;
  }

  public void search()
  {
    this.searchResult = this.bankRepository.findByExample(this.searchObj.getBlz(), this.searchObj.getName(), this.searchObj.getPlz(), this.searchObj.getOrt());
  }

  public String edit(Bank bank)
  {
    this.editObj = new Bank(bank);
    return "edit";
  }

  public String save()
  {
    this.bankRepository.save(this.editObj);
    search();
    return "home";
  }
}
