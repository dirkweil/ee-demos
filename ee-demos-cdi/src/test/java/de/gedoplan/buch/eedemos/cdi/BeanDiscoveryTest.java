package de.gedoplan.buch.eedemos.cdi;

import de.gedoplan.buch.eedemos.cdi.beans.DiscoverableBean01;
import de.gedoplan.buch.eedemos.cdi.sub1.beans.DiscoverableBean11;
import de.gedoplan.buch.eedemos.cdi.sub2.beans.Discoverable;
import de.gedoplan.buch.eedemos.cdi.sub2.beans.DiscoverableBean21;

import java.util.HashSet;
import java.util.Set;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.util.AnnotationLiteral;

import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;

public class BeanDiscoveryTest extends TestBase
{

  @Test
  public void showBeans()
  {
    BeanManager beanManager = BeanProvider.getContextualReference(BeanManager.class);
    Set<Bean<?>> beans = beanManager.getBeans(Discoverable.class, new AnnotationLiteral<Any>()
    {
    });
    Set<Class<?>> beanClasses = new HashSet<>();
    for (Bean<?> bean : beans)
    {
      beanClasses.add(bean.getBeanClass());
    }
    Class<?>[] expectedBeanClasses = { DiscoverableBean01.class, DiscoverableBean11.class, DiscoverableBean21.class };
    ReflectionAssert.assertLenientEquals("Discovered bean classes", expectedBeanClasses, beanClasses);
  }

}
