package de.gedoplan.buch.eedemos.ejb.service;

import java.util.Date;

import javax.ejb.Remote;

@Remote
public interface SrvDateService
{
  public Date getSrvDate();
}
