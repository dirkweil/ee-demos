package de.gedoplan.buch.eedemos.cdi.qualifier;

import javax.enterprise.util.AnnotationLiteral;

public class FormalLiteral extends AnnotationLiteral<Formal> implements Formal
{
  public static final FormalLiteral INSTANCE = new FormalLiteral();
}
