package de.gedoplan.buch.eedemos.cdi.extension;

import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessBean;

public class BeanStarter implements Extension
{
  private Set<Bean<?>> startupBeans = new HashSet<Bean<?>>();

  /**
   * Beans merken, die mit {@link Startup @StartUp} und {@link ApplicationScoped @ApplicationScoped} annotiert sind.
   * 
   * @param event Event
   */
  <X> void processBean(@Observes ProcessBean<X> event)
  {
    Annotated annotated = event.getAnnotated();
    if (annotated.isAnnotationPresent(Startup.class) && annotated.isAnnotationPresent(ApplicationScoped.class))
    {
      this.startupBeans.add(event.getBean());
    }
  }

  /**
   * Gemerkte Beans instanziieren.
   * 
   * @param event Event
   * @param beanManager BeanManager
   */
  void afterDeploymentValidation(@Observes AfterDeploymentValidation event, BeanManager beanManager)
  {
    for (Bean<?> bean : this.startupBeans)
    {
      // Zur Instanziierung irgendeine Methode aufrufen - hier toString
      beanManager.getReference(bean, bean.getBeanClass(), beanManager.createCreationalContext(bean)).toString();
    }
  }
}
