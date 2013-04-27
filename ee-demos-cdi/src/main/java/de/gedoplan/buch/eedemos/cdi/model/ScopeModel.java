package de.gedoplan.buch.eedemos.cdi.model;

import de.gedoplan.buch.eedemos.cdi.beans.ApplicationInfoBean;
import de.gedoplan.buch.eedemos.cdi.beans.ConversationInfoBean;
import de.gedoplan.buch.eedemos.cdi.beans.DependentInfoBean;
import de.gedoplan.buch.eedemos.cdi.beans.RequestInfoBean;
import de.gedoplan.buch.eedemos.cdi.beans.SessionInfoBean;

import java.io.Serializable;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ConversationScoped
public class ScopeModel implements Serializable
{
  @Inject
  private RequestInfoBean      requestInfoBean;

  @Inject
  private SessionInfoBean      sessionInfoBean;

  @Inject
  private ApplicationInfoBean  applicationInfoBean;

  @Inject
  private ConversationInfoBean conversationInfoBean;

  @Inject
  private DependentInfoBean    dependentInfoBean;

  public String getScopeInfo()
  {
    return String.format(
        "Request #%02d, Session #%02d, Application #%02d, Conversation #%02d, Dependent #%02d", this.requestInfoBean.getRequestNumber(), this.sessionInfoBean.getSessionNumber(),
        this.applicationInfoBean.getApplicationNumber(), this.conversationInfoBean.getConversationNumber(), this.dependentInfoBean.getDependentNumber());
  }

  @Inject
  private Conversation conversation;

  public void beginConversation()
  {
    if (this.conversation.isTransient())
    {
      this.conversation.begin();
    }
  }

  public void endConversation()
  {
    if (!this.conversation.isTransient())
    {
      this.conversation.end();
    }
  }
}
