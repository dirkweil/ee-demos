package de.gedoplan.buch.eedemos.cdi.model;

import de.gedoplan.buch.eedemos.cdi.TestBase;
import de.gedoplan.buch.eedemos.cdi.sub.beans.SomeService;
import de.gedoplan.buch.eedemos.cdi.sub.interceptor.OneInterceptor;
import de.gedoplan.buch.eedemos.cdi.sub.interceptor.OneTwoThreeInterceptor;
import de.gedoplan.buch.eedemos.cdi.sub.interceptor.ThreeInterceptor;
import de.gedoplan.buch.eedemos.cdi.sub.interceptor.TwoInterceptor;

import java.util.ArrayList;
import java.util.List;

import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.junit.Assert;
import org.junit.Test;

public class InterceptModelTest extends TestBase
{
  @Test
  public void testGetHelloWorld()
  {
    InterceptModel interceptModel = BeanProvider.getContextualReference(InterceptModel.class);

    OneTwoThreeInterceptor.clearHistory();

    interceptModel.doSomething();

    List<OneTwoThreeInterceptor.HistoryEntry> actualHistory = new ArrayList<>(OneTwoThreeInterceptor.getHistory());
    Assert.assertEquals("Interceptor calls", 6, actualHistory.size());

    String method1 = "public void " + InterceptModel.class.getName() + ".doSomething()";
    Assert.assertEquals("Interceptor", actualHistory.get(0).getInterceptorClass(), OneInterceptor.class);
    Assert.assertEquals("Intercepted method", actualHistory.get(0).getInterceptedMethod(), method1);
    Assert.assertEquals("Interceptor", actualHistory.get(1).getInterceptorClass(), TwoInterceptor.class);
    Assert.assertEquals("Intercepted method", actualHistory.get(1).getInterceptedMethod(), method1);
    Assert.assertEquals("Interceptor", actualHistory.get(2).getInterceptorClass(), ThreeInterceptor.class);
    Assert.assertEquals("Intercepted method", actualHistory.get(2).getInterceptedMethod(), method1);

    String method2 = "public void " + SomeService.class.getName() + ".doSomething()";
    Assert.assertEquals("Interceptor", actualHistory.get(3).getInterceptorClass(), OneInterceptor.class);
    Assert.assertEquals("Intercepted method", actualHistory.get(3).getInterceptedMethod(), method2);
    Assert.assertEquals("Interceptor", actualHistory.get(4).getInterceptorClass(), TwoInterceptor.class);
    Assert.assertEquals("Intercepted method", actualHistory.get(4).getInterceptedMethod(), method2);
    Assert.assertEquals("Interceptor", actualHistory.get(5).getInterceptorClass(), ThreeInterceptor.class);
    Assert.assertEquals("Intercepted method", actualHistory.get(5).getInterceptedMethod(), method2);

    /*
     *  TODO: Hier werden offensichtlich alle Interceptors aufgerufen, obwohl der 3. nur im Sub.Deployment aktiviert ist. Auf GLF 4_86 ist das nicht so! Was ist richtig? 
     */
  }
}
