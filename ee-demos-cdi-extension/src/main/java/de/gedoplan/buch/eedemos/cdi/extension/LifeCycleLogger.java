package de.gedoplan.buch.eedemos.cdi.extension;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.enterprise.inject.spi.AfterTypeDiscovery;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.BeforeShutdown;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.enterprise.inject.spi.ProcessBean;
import javax.enterprise.inject.spi.ProcessBeanAttributes;
import javax.enterprise.inject.spi.ProcessInjectionPoint;
import javax.enterprise.inject.spi.ProcessInjectionTarget;
import javax.enterprise.inject.spi.ProcessObserverMethod;
import javax.enterprise.inject.spi.ProcessProducer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LifeCycleLogger implements Extension
{
  private static final Log LOG = LogFactory.getLog(LifeCycleLogger.class);

  void logLifecycleEvent(@Observes BeforeBeanDiscovery event)
  {
    if (LOG.isTraceEnabled())
    {
      LOG.trace("BeforeBeanDiscovery");
    }
  }

  void logLifecycleEvent(@Observes AfterTypeDiscovery event)
  {
    if (LOG.isTraceEnabled())
    {
      LOG.trace("AfterTypeDiscovery: alternatives=" + event.getAlternatives() + ", interceptors=" + event.getInterceptors() + ", decorators=" + event.getDecorators());
    }
  }

  void logLifecycleEvent(@Observes AfterBeanDiscovery event)
  {
    if (LOG.isTraceEnabled())
    {
      LOG.trace("AfterBeanDiscovery");
    }
  }

  void logLifecycleEvent(@Observes AfterDeploymentValidation event)
  {
    if (LOG.isTraceEnabled())
    {
      LOG.trace("AfterDeploymentValidation");
    }
  }

  void logLifecycleEvent(@Observes BeforeShutdown event)
  {
    if (LOG.isTraceEnabled())
    {
      LOG.trace("BeforeShutdown");
    }
  }

  <X> void logLifecycleEvent(@Observes ProcessAnnotatedType<X> event)
  {
    if (LOG.isTraceEnabled())
    {
      LOG.trace("ProcessAnnotatedType: annotatedType=" + event.getAnnotatedType());
    }
  }

  <T, X> void logLifecycleEvent(@Observes ProcessInjectionPoint<T, X> event)
  {
    if (LOG.isTraceEnabled())
    {
      LOG.trace("ProcessInjectionPoint: injectionPoint=" + event.getInjectionPoint());
    }
  }

  <X> void logLifecycleEvent(@Observes ProcessInjectionTarget<X> event)
  {
    if (LOG.isTraceEnabled())
    {
      LOG.trace("ProcessInjectionTarget: annotatedType=" + event.getAnnotatedType());
    }
  }

  <T, X> void logLifecycleEvent(@Observes ProcessProducer<T, X> event)
  {
    if (LOG.isTraceEnabled())
    {
      LOG.trace("ProcessProducer: annotatedMember=" + event.getAnnotatedMember());
    }
  }

  <T> void logLifecycleEvent(@Observes ProcessBeanAttributes<T> event)
  {
    if (LOG.isTraceEnabled())
    {
      LOG.trace("ProcessBeanAttributes: beanAttributes=" + event.getBeanAttributes());
    }
  }

  <X> void logLifecycleEvent(@Observes ProcessBean<X> event)
  {
    if (LOG.isTraceEnabled())
    {
      LOG.trace("ProcessBean: bean=" + event.getBean());
    }
  }

  <T, X> void logLifecycleEvent(@Observes ProcessObserverMethod<T, X> event)
  {
    if (LOG.isTraceEnabled())
    {
      LOG.trace("ProcessObserverMethod: observerMethod" + event.getObserverMethod());
    }
  }
}
