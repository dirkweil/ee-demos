package de.gedoplan.buch.eedemos.cdi.qualifier;

import javax.enterprise.util.AnnotationLiteral;

public class TakeawayLiteral extends AnnotationLiteral<Takeaway> implements Takeaway
{
  private static final long           serialVersionUID = 1L;

  public static final TakeawayLiteral INSTANCE         = new TakeawayLiteral();
}
