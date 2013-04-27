package de.gedoplan.buch.eedemos.cdi.beans;

import java.io.Serializable;
import java.util.Calendar;

//@Default
//@Greeting(type = GreetingType.NORMAL)
public class GreetingBean implements Serializable
{
  public String getGreeting()
  {
    int hourOfDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    if (hourOfDay < 10)
    {
      return "Guten Morgen";
    }
    else if (hourOfDay < 18)
    {
      return "Guten Tag";
    }
    else
    {
      return "Guten Abend";
    }

  }
}
