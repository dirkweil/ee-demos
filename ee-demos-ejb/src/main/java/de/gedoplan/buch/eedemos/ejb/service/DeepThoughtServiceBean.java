package de.gedoplan.buch.eedemos.ejb.service;

import java.util.concurrent.Future;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;

/**
 * Demo-Bean für asynchrone Methoden.
 * 
 * "Deep Thought" ist der Supercomputer in "Per Anhalter durch die Galaxis", der von einer außerirdischen Kultur speziell dafür
 * gebaut wurde, die Antwort auf die Frage aller Fragen, nämlich die "nach dem Leben, dem Universum und allem" zu errechnen. Nach
 * einer Rechenzeit von 7,5 Millionen Jahren erbringt er dann die Antwort, nämlich "Zweiundvierzig".
 * 
 * @author dw
 * 
 */
@Stateless
public class DeepThoughtServiceBean implements DeepThoughtService
{
  @Asynchronous
  @Override
  public Future<String> getAnswerToQuestionAboutLifeUniverseAndEverything()
  {
    try
    {
      Thread.sleep(7500);
    }
    catch (InterruptedException e)
    {
      // ignore
    }

    return new AsyncResult<String>("Zweiundvierzig");
  }

}
