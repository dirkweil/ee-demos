package de.gedoplan.buch.eedemos.jsf.model;

import javax.enterprise.inject.Produces;
import javax.faces.flow.Flow;
import javax.faces.flow.FlowScoped;
import javax.faces.flow.builder.FlowBuilder;
import javax.faces.flow.builder.FlowBuilderParameter;
import javax.faces.flow.builder.FlowDefinition;
import javax.inject.Named;

@Named
@FlowScoped("flowB2")
public class FlowB2Model extends FlowModelBase
{
  @Produces
  @FlowDefinition
  public static Flow defineFlow(@FlowBuilderParameter FlowBuilder flowBuilder)
  {

    flowBuilder.id("", "flowB2");

    flowBuilder.viewNode("flowB2", "/flowB2/flowB2.xhtml").markAsStartNode();

    flowBuilder.returnNode("exitFlow").fromOutcome("exitFlowB2");

    flowBuilder.flowCallNode("callFlowB1").flowReference("", "flowB1");

    flowBuilder.navigationCase().fromViewId("*").fromOutcome("exitFlowB1").toViewId("/flowB2/flowB2.xhtml");

    return flowBuilder.getFlow();
  }
}
