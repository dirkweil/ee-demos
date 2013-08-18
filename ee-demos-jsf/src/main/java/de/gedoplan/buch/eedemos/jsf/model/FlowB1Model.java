package de.gedoplan.buch.eedemos.jsf.model;

import javax.enterprise.inject.Produces;
import javax.faces.flow.Flow;
import javax.faces.flow.FlowScoped;
import javax.faces.flow.builder.FlowBuilder;
import javax.faces.flow.builder.FlowBuilderParameter;
import javax.faces.flow.builder.FlowDefinition;
import javax.inject.Named;

@Named
@FlowScoped("flowB1")
public class FlowB1Model extends FlowModelBase
{
  @Produces
  @FlowDefinition
  public static Flow defineFlow(@FlowBuilderParameter FlowBuilder flowBuilder)
  {

    flowBuilder.id("", "flowB1");

    flowBuilder.viewNode("flowB1", "/flowB1/flowB1a.xhtml").markAsStartNode();

    flowBuilder.returnNode("exitFlow").fromOutcome("exitFlowB1");

    return flowBuilder.getFlow();
  }

}
