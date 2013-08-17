package de.gedoplan.buch.eedemos.jsf.model;

import javax.enterprise.inject.Produces;
import javax.faces.flow.Flow;
import javax.faces.flow.FlowScoped;
import javax.faces.flow.builder.FlowBuilder;
import javax.faces.flow.builder.FlowBuilderParameter;
import javax.faces.flow.builder.FlowDefinition;
import javax.inject.Named;

@Named
@FlowScoped("flow1")
public class Flow1Model extends FlowModelBase
{
  @Produces
  @FlowDefinition
  public static Flow defineFlow(@FlowBuilderParameter FlowBuilder flowBuilder)
  {

    flowBuilder.id("", "flow1");

    flowBuilder.viewNode("flow1", "/flow1/flow1a.xhtml").
        markAsStartNode();

    flowBuilder.returnNode("exitFlow").fromOutcome("exitFlow");

    return flowBuilder.getFlow();
  }

}
