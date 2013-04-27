package de.gedoplan.buch.eedemos.provs.web.config;

import org.jboss.seam.faces.transaction.SeamManagedTransaction;
import org.jboss.seam.faces.transaction.SeamManagedTransactionType;
import org.jboss.seam.faces.view.config.ViewConfig;
import org.jboss.seam.faces.view.config.ViewPattern;

@ViewConfig
public interface ViewConfiguration
{
  static enum ViewConfigurationParameter
  {
    @ViewPattern("/*")
    @SeamManagedTransaction(SeamManagedTransactionType.DISABLED)
    ALL;
  }
}
