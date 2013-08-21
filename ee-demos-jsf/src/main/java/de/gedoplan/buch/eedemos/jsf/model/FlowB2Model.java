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
    // Flow-Id setzen
    flowBuilder.id("", "flowB2");

    // Startknoten definieren
    flowBuilder.viewNode("flowB2", "/flowB2/flowB2.xhtml").markAsStartNode();

    // Returnknoten definieren (inkl. seinem Outcome)
    flowBuilder.returnNode("exitFlow").fromOutcome("exitFlowB2");

    // Knoten zum Aufruf von flowB1 definieren
    flowBuilder.flowCallNode("callFlowB1").flowReference("", "flowB1");

    // Navigation für die Rückkehr von flowB1
    flowBuilder.navigationCase().fromViewId("*").fromOutcome("exitFlowB1").toViewId("/flowB2/flowB2.xhtml");

    return flowBuilder.getFlow();
  }
}
