package de.gedoplan.buch.eedemos.cdi;

import java.util.Set;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.util.AnnotationLiteral;

import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.junit.Test;

public class BeanDiscoveryTest extends TestBase
{

  @Test
  public void showBeans()
  {
    BeanManager beanManager = BeanProvider.getContextualReference(BeanManager.class);
    Set<Bean<?>> beans = beanManager.getBeans(Object.class, new AnnotationLiteral<Any>()
    {
    });
    for (Bean<?> bean : beans)
    {
      if (bean.getBeanClass().getName().startsWith("de.gedoplan."))
      {
        System.out.println(bean);
      }
    }
  }

}
