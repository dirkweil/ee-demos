package de.gedoplan.buch.eedemos.cdi.qualifier;

import javax.enterprise.util.AnnotationLiteral;

public class TakeawayLiteral extends AnnotationLiteral<Takeaway> implements Takeaway
{
  public static final TakeawayLiteral INSTANCE = new TakeawayLiteral();
}
