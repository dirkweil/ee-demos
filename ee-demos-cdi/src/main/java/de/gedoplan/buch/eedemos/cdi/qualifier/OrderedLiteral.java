package de.gedoplan.buch.eedemos.cdi.qualifier;

import javax.enterprise.util.AnnotationLiteral;

public class OrderedLiteral extends AnnotationLiteral<Ordered> implements Ordered
{
  private static final long          serialVersionUID = 1L;

  public static final OrderedLiteral INSTANCE         = new OrderedLiteral();
}
