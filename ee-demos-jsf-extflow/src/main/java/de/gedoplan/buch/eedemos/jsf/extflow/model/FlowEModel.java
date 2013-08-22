package de.gedoplan.buch.eedemos.jsf.extflow.model;

import javax.enterprise.inject.Produces;
import javax.faces.flow.Flow;
import javax.faces.flow.FlowScoped;
import javax.faces.flow.builder.FlowBuilder;
import javax.faces.flow.builder.FlowBuilderParameter;
import javax.faces.flow.builder.FlowDefinition;
import javax.inject.Named;

@Named
@FlowScoped("flowE")
public class FlowEModel extends FlowModel
{
  @Produces
  @FlowDefinition
  public static Flow defineFlow(@FlowBuilderParameter FlowBuilder flowBuilder)
  {
    flowBuilder.id("", "flowE");

    flowBuilder.viewNode("flowEa", "/flowE/flowEa.xhtml").markAsStartNode();

    flowBuilder.viewNode("flowEb", "/flowE/flowEb.xhtml");

    flowBuilder.returnNode("exitFlow").fromOutcome("exitFlowE");

    flowBuilder.navigationCase().fromViewId("/flowE/flowEa.xhtml").fromOutcome("next").toViewId("/flowE/flowEb.xhtml");
    flowBuilder.navigationCase().fromViewId("/flowE/flowEb.xhtml").fromOutcome("prev").toViewId("/flowE/flowEa.xhtml");

    return flowBuilder.getFlow();
  }
}
