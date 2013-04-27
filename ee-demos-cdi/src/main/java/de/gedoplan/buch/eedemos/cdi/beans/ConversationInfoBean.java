package de.gedoplan.buch.eedemos.cdi.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;

@ConversationScoped
public class ConversationInfoBean implements Serializable
{
  private static int nextNumber = 1;

  private int        conversationNumber;

  public int getConversationNumber()
  {
    return this.conversationNumber;
  }

  @PostConstruct
  public void init()
  {
    this.conversationNumber = nextNumber;
    ++nextNumber;
  }

}
