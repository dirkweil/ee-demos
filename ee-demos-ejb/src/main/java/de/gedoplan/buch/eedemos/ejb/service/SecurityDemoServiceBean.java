package de.gedoplan.buch.eedemos.ejb.service;

import java.security.Principal;

import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.logging.Log;

@Stateless
@PermitAll
@DeclareRoles("demoRole")
public class SecurityDemoServiceBean
{
  @Resource
  private EJBContext ejbCtx;

  @Inject
  private Log        log;

  public String getCallerName()
  {
    Principal caller = this.ejbCtx.getCallerPrincipal();
    return caller == null ? "unauthenticated" : caller.getName();
  }

  public boolean isCallerInDemoRole()
  {
    return this.ejbCtx.isCallerInRole("demoRole");
  }

  @RolesAllowed("demoRole")
  public void restrictedMethod()
  {
    this.log.debug("restrictedMethod");
  }

  public void unrestrictedMethod()
  {
    this.log.debug("unrestrictedMethod");
  }

  @RolesAllowed("eeDemoUser")
  public void partlyRestrictedMethod(double amount)
  {
    if (amount >= 10000)
    {
      if (!this.ejbCtx.isCallerInRole("demoRole"))
      {
        throw new IllegalArgumentException("Big amounts need role demoRole");
      }
    }
    this.log.debug("partlyRestrictedMethod");
  }
}
