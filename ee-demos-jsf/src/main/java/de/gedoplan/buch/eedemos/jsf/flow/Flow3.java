/**
 * Copyright (c) 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software except in compliance with the terms of the License at: http://java.net/projects/javaeetutorial/pages/BerkeleyLicense
 */
package de.gedoplan.buch.eedemos.jsf.flow;

import java.io.Serializable;

import javax.enterprise.inject.Produces;
import javax.faces.flow.Flow;
import javax.faces.flow.builder.FlowBuilder;
import javax.faces.flow.builder.FlowBuilderParameter;
import javax.faces.flow.builder.FlowDefinition;

public class Flow3 implements Serializable
{

  private static final long serialVersionUID = 1L;

  @Produces
  @FlowDefinition
  public Flow defineFlow(@FlowBuilderParameter FlowBuilder flowBuilder)
  {
    flowBuilder.id("", "flow3");

    flowBuilder.viewNode("flow3", "/flow/flow3/flow3.xhtml").markAsStartNode();

    flowBuilder.viewNode("flow3a", "/flow/flow3/flow3a.xhtml");

    flowBuilder.viewNode("flow3b", "/flow/flow3/flow3b.xhtml");

    flowBuilder.returnNode("exitFlow").fromOutcome("/index");

    return flowBuilder.getFlow();
  }
}
