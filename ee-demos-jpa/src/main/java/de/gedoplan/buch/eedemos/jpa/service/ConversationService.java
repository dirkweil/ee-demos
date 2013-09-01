package de.gedoplan.buch.eedemos.jpa.service;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Conversation;
import javax.inject.Inject;

import org.apache.commons.logging.Log;

/**
 * Session-Service.
 * 
 * @author dw
 */
@ApplicationScoped
public class ConversationService implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Inject
  private Log               logger;

  @Inject
  private Conversation      conversation;

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
}
