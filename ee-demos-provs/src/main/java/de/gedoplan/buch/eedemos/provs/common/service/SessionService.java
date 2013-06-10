package de.gedoplan.buch.eedemos.provs.common.service;

import de.gedoplan.baselibs.enterprise.stereotype.DomainService;
import de.gedoplan.buch.eedemos.provs.common.qualifier.Angemeldet;
import de.gedoplan.buch.eedemos.provs.common.qualifier.Current;

import java.io.Serializable;
import java.security.Principal;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;

/**
 * Session-Service.
 * 
 * @author dw
 */
@SessionScoped
@DomainService
public class SessionService implements Serializable
{
  @Inject
  private Log          logger;

  @Inject
  private Conversation conversation;

  private Principal    userPrincipal;
  private Principal    effectiveUserPrincipal;

  /**
   * Aktuell angemeldeten User liefern.
   * 
   * @return angemeldeten User oder <code>null</code>
   */
  @Produces
  @Current
  @Angemeldet
  public Principal getCurrentUser()
  {
    if (this.userPrincipal == null)
    {
      HttpServletRequest request = getServletRequest();
      this.userPrincipal = request.getUserPrincipal();
      if (this.logger.isDebugEnabled())
      {
        this.logger.debug("userPrincipal: " + this.userPrincipal);
      }

      if (this.userPrincipal != null)
      {
        String userId = this.userPrincipal.getName();
        if (!isAnonymous(userId))
        {
          this.effectiveUserPrincipal = this.userPrincipal;
        }
      }
    }

    return this.effectiveUserPrincipal;
  }

  /**
   * User abmelden.
   */
  public void logOut()
  {
    HttpServletRequest request = getServletRequest();
    try
    {
      request.logout();
    }
    catch (ServletException e)
    {
      // ignore
    }

    HttpSession session = request.getSession(false);
    if (session != null)
    {
      session.invalidate();
    }

    this.userPrincipal = null;
    this.effectiveUserPrincipal = null;
  }

  /**
   * Conversation beginnen.
   * 
   * Wenn noch keine Conversation aktiv ist, eine beginnen.
   */
  public void beginConversation()
  {
    if (this.conversation.isTransient())
    {
      this.conversation.begin();

      if (this.logger.isDebugEnabled())
      {
        this.logger.debug("Begin conversation " + this.conversation.getId());
      }
    }
  }

  /**
   * Conversation beenden.
   * 
   * Wenn eine Conversation aktiv ist, sie beenden.
   */
  public void endConversation()
  {
    if (!this.conversation.isTransient())
    {
      if (this.logger.isDebugEnabled())
      {
        this.logger.debug("End conversation " + this.conversation.getId());
      }

      this.conversation.end();
    }
  }

  private HttpServletRequest getServletRequest()
  {
    return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
  }

  private boolean isAnonymous(String userId)
  {
    return "anonymous".equalsIgnoreCase(userId) || "guest".equalsIgnoreCase(userId);
  }

}
