package de.gedoplan.buch.eedemos.ejb.service;

import java.util.Date;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class SrvDateServiceBean implements SrvDateService
{

  @Override
  public Date getSrvDate()
  {
    return new Date();
  }

}
