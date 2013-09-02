package de.gedoplan.buch.eedemos.cdi.model;

import de.gedoplan.buch.eedemos.cdi.beans.GreetingBean;
import de.gedoplan.buch.eedemos.cdi.qualifier.Formal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.spi.CreationalContext;
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
  private static final long      serialVersionUID = 1L;
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
    Instance<GreetingBean> selectedBeans = this.greetingBeans.select(new AnnotationLiteral<Formal>()
    {
      private static final long serialVersionUID = 1L;
    });
    for (GreetingBean bean : selectedBeans)
    {
      classes.add(bean.getClass());
    }
    return classes;
  }

  @Inject
  private BeanManager beanManager;

  public List<Bean<?>> getAllBeans()
  {
    List<Bean<?>> list = new ArrayList<Bean<?>>();
    for (Bean<?> bean : this.beanManager.getBeans(Object.class, new AnnotationLiteral<Any>()
    {
      private static final long serialVersionUID = 1L;
    }))
    {
      list.add(bean);
    }

    return list;
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  public String getFormalGreeting()
  {
    Set<Bean<? extends GreetingBean>> beans = (Set) this.beanManager.getBeans(GreetingBean.class, new AnnotationLiteral<Formal>()
    {
      private static final long serialVersionUID = 1L;
    });
    if (beans.size() != 1)
    {
      return "Fehler: Nicht genau eine formelle GreetingBean gefunden!";
    }

    Bean<? extends GreetingBean> bean = beans.iterator().next();
    CreationalContext<? extends GreetingBean> beanContext = this.beanManager.createCreationalContext(bean);
    GreetingBean greetingBean = (GreetingBean) this.beanManager.getReference(bean, GreetingBean.class, beanContext);
    return greetingBean.getGreeting();
  }
}
