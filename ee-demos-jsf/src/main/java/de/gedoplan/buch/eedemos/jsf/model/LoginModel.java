package de.gedoplan.buch.eedemos.jsf.model;

import java.security.Principal;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class LoginModel
{
  @Inject
  private Principal currentUser;

  public Principal getCurrentUser()
  {
    return this.currentUser;
  }

  public String logout()
  {
    FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    return "/index.xhtml?faces-redirect=true";
  }
}
