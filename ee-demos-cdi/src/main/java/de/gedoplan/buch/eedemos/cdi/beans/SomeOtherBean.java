package de.gedoplan.buch.eedemos.cdi.beans;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class SomeOtherBean implements Serializable
{
  private static final long    serialVersionUID = 1L;

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
}
