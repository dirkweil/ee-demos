package de.gedoplan.buch.eedemos.cdi.model;

import de.gedoplan.buch.eedemos.cdi.TestBase;
import de.gedoplan.buch.eedemos.cdi.sub1.beans.SomeService;
import de.gedoplan.buch.eedemos.cdi.sub1.interceptor.OneInterceptor;
import de.gedoplan.buch.eedemos.cdi.sub1.interceptor.OneTwoThreeInterceptor;
import de.gedoplan.buch.eedemos.cdi.sub1.interceptor.OneTwoThreeInterceptor.HistoryEntry;
import de.gedoplan.buch.eedemos.cdi.sub1.interceptor.ThreeInterceptor;
import de.gedoplan.buch.eedemos.cdi.sub1.interceptor.TwoInterceptor;

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
    Assert.assertEquals("Interceptor calls", 5, actualHistory.size());

    /*
     * Für den Aufruf von InterceptModel.doSomething wird erwartet, dass die Interceptors OneInterceptor und TwoInterceptor
     * laufen, da beide mit Hilfe von @Priority global aktiviert sind.
     */
    String method1 = "public void " + InterceptModel.class.getName() + ".doSomething()";

    int index = 0;
    HistoryEntry historyEntry = actualHistory.get(index++);
    Assert.assertEquals("Interceptor", historyEntry.getInterceptorClass(), OneInterceptor.class);
    Assert.assertEquals("Intercepted method", historyEntry.getInterceptedMethod(), method1);

    historyEntry = actualHistory.get(index++);
    Assert.assertEquals("Interceptor", historyEntry.getInterceptorClass(), TwoInterceptor.class);
    Assert.assertEquals("Intercepted method", historyEntry.getInterceptedMethod(), method1);

    /*
     * Für den Aufruf von SomeService.doSomething werden die gleichen Interceptors erwartet und zusätzlich ThreeInterceptor, da
     * dieser im Subdeployment, in dem sich SomeService befindet, aktiviert ist.
     */
    String method2 = "public void " + SomeService.class.getName() + ".doSomething()";

    historyEntry = actualHistory.get(index++);
    Assert.assertEquals("Interceptor", historyEntry.getInterceptorClass(), OneInterceptor.class);
    Assert.assertEquals("Intercepted method", historyEntry.getInterceptedMethod(), method2);

    historyEntry = actualHistory.get(index++);
    Assert.assertEquals("Interceptor", historyEntry.getInterceptorClass(), TwoInterceptor.class);
    Assert.assertEquals("Intercepted method", historyEntry.getInterceptedMethod(), method2);

    historyEntry = actualHistory.get(index++);
    Assert.assertEquals("Interceptor", historyEntry.getInterceptorClass(), ThreeInterceptor.class);
    Assert.assertEquals("Intercepted method", historyEntry.getInterceptedMethod(), method2);
  }
}
