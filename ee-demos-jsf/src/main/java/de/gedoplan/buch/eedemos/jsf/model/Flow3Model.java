package de.gedoplan.buch.eedemos.jsf.model;

import javax.enterprise.inject.Produces;
import javax.faces.flow.Flow;
import javax.faces.flow.FlowScoped;
import javax.faces.flow.builder.FlowBuilder;
import javax.faces.flow.builder.FlowBuilderParameter;
import javax.faces.flow.builder.FlowDefinition;
import javax.inject.Named;

@Named
@FlowScoped("flow3")
public class Flow3Model extends FlowModelBase
{
  @Produces
  @FlowDefinition
  public static Flow defineFlow(@FlowBuilderParameter FlowBuilder flowBuilder)
  {
    flowBuilder.id("", "flow3");

    flowBuilder.viewNode("flow3a", "/flow/flow3/flow3a.xhtml").markAsStartNode();

    flowBuilder.viewNode("flow3b", "/flow/flow3/flow3b.xhtml");

    flowBuilder.returnNode("exitFlow").fromOutcome("exitFlow");

    return flowBuilder.getFlow();
  }
}
