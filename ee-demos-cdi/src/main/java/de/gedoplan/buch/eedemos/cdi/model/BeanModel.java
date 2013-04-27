package de.gedoplan.buch.eedemos.cdi.model;

import de.gedoplan.buch.eedemos.cdi.beans.GreetingBean;
import de.gedoplan.buch.eedemos.cdi.qualifier.FormalLiteral;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class BeanModel implements Serializable
{
  @Inject
  @Any
  private Instance<GreetingBean> greetingBeans;

  public List<Class<?>> getGreetingBeanClasses()
  {
    List<Class<?>> classes = new ArrayList<>();
    for (GreetingBean bean : this.greetingBeans)
    {
      classes.add(bean.getClass());
    }
    return classes;
  }

  public List<Class<?>> getFormalGreetingBeanClasses()
  {
    List<Class<?>> classes = new ArrayList<>();
    Instance<GreetingBean> selectedBeans = this.greetingBeans.select(FormalLiteral.INSTANCE);
    for (GreetingBean bean : selectedBeans)
    {
      classes.add(bean.getClass());
    }
    return classes;
  }

  @Inject
  private BeanManager beanManager;

  public List<CharSequence> getAllBeanDescriptions()
  {
    List<CharSequence> list = new ArrayList<>();
    for (Bean<?> bean : this.beanManager.getBeans(Object.class, new AnnotationLiteral<Any>()
    {
    }))
    {
      StringBuilder beanDescription = new StringBuilder();
      for (Annotation qualifier : bean.getQualifiers())
      {
        beanDescription.append(qualifier).append(' ');
      }
      beanDescription.append(bean.getBeanClass().getName());

      list.add(beanDescription);
    }
    return list;
  }
}
