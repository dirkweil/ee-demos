/**
 * Copyright (c) 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software except in compliance with the terms of the License at: http://java.net/projects/javaeetutorial/pages/BerkeleyLicense
 */
package de.gedoplan.buch.eedemos.jsf.flow1;

import java.io.Serializable;

import javax.enterprise.inject.Produces;
import javax.faces.flow.Flow;
import javax.faces.flow.builder.FlowBuilder;
import javax.faces.flow.builder.FlowBuilderParameter;
import javax.faces.flow.builder.FlowDefinition;

public class Flow1 implements Serializable
{

  private static final long serialVersionUID = 1L;

  @Produces
  @FlowDefinition
  public Flow defineFlow(@FlowBuilderParameter FlowBuilder flowBuilder)
  {

    String flowId = "flow1";
    flowBuilder.id("", flowId);
    flowBuilder.viewNode(flowId, "/" + flowId + "/" + flowId + ".xhtml").
            markAsStartNode();

    flowBuilder.returnNode("exitFlow").fromOutcome("/index");

    return flowBuilder.getFlow();
  }
}
