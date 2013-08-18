package de.gedoplan.buch.eedemos.jsf.model;

import javax.enterprise.inject.Produces;
import javax.faces.flow.Flow;
import javax.faces.flow.FlowScoped;
import javax.faces.flow.builder.FlowBuilder;
import javax.faces.flow.builder.FlowBuilderParameter;
import javax.faces.flow.builder.FlowDefinition;
import javax.inject.Named;

@Named
@FlowScoped("flowC")
public class FlowCModel extends FlowModelBase
{
  @Produces
  @FlowDefinition
  public static Flow defineFlow(@FlowBuilderParameter FlowBuilder flowBuilder)
  {
    flowBuilder.id("", "flowC");

    flowBuilder.viewNode("flowCa", "/flow/flowC/flowCa.xhtml").markAsStartNode();

    flowBuilder.viewNode("flowCb", "/flow/flowC/flowCb.xhtml");

    flowBuilder.returnNode("exitFlow").fromOutcome("exitFlowC");

    return flowBuilder.getFlow();
  }
}
