package de.gedoplan.buch.eedemos.ejb.service;

import java.util.concurrent.Future;

import javax.ejb.Remote;

@Remote
public interface DeepThoughtService
{
  public Future<String> getAnswerToQuestionAboutLifeUniverseAndEverything();
}
