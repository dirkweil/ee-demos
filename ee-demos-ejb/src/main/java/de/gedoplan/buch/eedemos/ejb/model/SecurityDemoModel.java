package de.gedoplan.buch.eedemos.ejb.model;

import de.gedoplan.buch.eedemos.ejb.service.SecurityDemoServiceBean;

import java.security.Principal;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class SecurityDemoModel
{
  @Inject
  private Principal               currentUser;

  @Inject
  private SecurityDemoServiceBean securityDemoService;

  public Principal getCurrentUser()
  {
    return this.currentUser;
  }

  public String logout()
  {
    FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    return "/index.xhtml?faces-redirect=true";
  }

  public String getCallerName()
  {
    try
    {
      return this.securityDemoService.getCallerName();
    }
    catch (Exception e)
    {
      return createExceptionDescription(e);
    }
  }

  public String getCallerInDemoRole()
  {
    try
    {
      return "" + this.securityDemoService.isCallerInDemoRole();
    }
    catch (Exception e)
    {
      return createExceptionDescription(e);
    }
  }

  public String getTryUnrestrictedMethod()
  {
    try
    {
      this.securityDemoService.unrestrictedMethod();
      return "OK";
    }
    catch (Exception e)
    {
      return createExceptionDescription(e);
    }
  }

  public String getTryRestrictedMethod()
  {
    try
    {
      this.securityDemoService.restrictedMethod();
      return "OK";
    }
    catch (Exception e)
    {
      return createExceptionDescription(e);
    }
  }

  public String getTryPartlyRestrictedMethodSmall()
  {
    return getTryPartlyRestrictedMethod(100);
  }

  public String getTryPartlyRestrictedMethodBig()
  {
    return getTryPartlyRestrictedMethod(100000);
  }

  private String getTryPartlyRestrictedMethod(double amount)
  {
    try
    {
      this.securityDemoService.partlyRestrictedMethod(amount);
      return "OK";
    }
    catch (Exception e)
    {
      return createExceptionDescription(e);
    }
  }

  private String createExceptionDescription(Exception e)
  {
    StringBuilder desc = new StringBuilder(e.toString());

    Throwable cause = e.getCause();
    while (cause != null)
    {
      desc.append(" | ").append(cause.toString());
      cause = cause.getCause();
    }

    return desc.toString();
  }
}
