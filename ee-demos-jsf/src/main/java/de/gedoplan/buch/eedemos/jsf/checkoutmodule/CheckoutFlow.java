/**
 * Copyright (c) 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software except in
 * compliance with the terms of the License at:
 * http://java.net/projects/javaeetutorial/pages/BerkeleyLicense
 */
package de.gedoplan.buch.eedemos.jsf.checkoutmodule;

import java.io.Serializable;

import javax.enterprise.inject.Produces;
import javax.faces.flow.Flow;
import javax.faces.flow.builder.FlowBuilder;
import javax.faces.flow.builder.FlowBuilderParameter;
import javax.faces.flow.builder.FlowDefinition;

public class CheckoutFlow implements Serializable
{

  private static final long serialVersionUID = 1L;

  @Produces
  @FlowDefinition
  public Flow defineFlow(@FlowBuilderParameter FlowBuilder flowBuilder)
  {

    String flowId = "checkoutFlow";
    flowBuilder.id("", flowId);
    flowBuilder.viewNode(flowId, "/" + flowId + "/" + flowId + ".xhtml").
        markAsStartNode();

    flowBuilder.returnNode("returnFromCheckoutFlow").
        fromOutcome("#{checkoutFlowBean.returnValue}");

    //        flowBuilder.inboundParameter("param1FromJoinFlow", 
    //                "#{flowScope.param1Value}");
    //        flowBuilder.inboundParameter("param2FromJoinFlow", 
    //                "#{flowScope.param2Value}");
    //
    //        flowBuilder.flowCallNode("calljoin").flowReference("", "joinFlow").
    //                outboundParameter("param1FromCheckoutFlow", 
    //                "#{checkoutFlowBean.name}").
    //                outboundParameter("param2FromCheckoutFlow", 
    //                "#{checkoutFlowBean.city}");

    return flowBuilder.getFlow();
  }
}
