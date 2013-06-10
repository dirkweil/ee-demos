package de.gedoplan.buch.eedemos.provs.web.bean;

import de.gedoplan.buch.eedemos.provs.firma.entity.MitarbeitsTyp;
import de.gedoplan.buch.eedemos.provs.firma.entity.PostAdressTyp;
import de.gedoplan.buch.eedemos.provs.projekt.entity.ProjektStatus;
import de.gedoplan.buch.eedemos.provs.projekt.entity.ProjektTyp;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 * Helferklasse zur Bereitstellung von Enum-Wertebereichen.
 * 
 * @author dw
 */
@ApplicationScoped
@Named
public class EnumHelper
{
  /**
   * Alle PostAdressTyp-Werte liefern.
   * 
   * @return PostAdressTyp-Werte
   */
  public PostAdressTyp[] getPostAdressTypValues()
  {
    return PostAdressTyp.values();
  }

  /**
   * Alle MitarbeitsTyp-Werte liefern.
   * 
   * @return MitarbeitsTyp-Werte
   */
  public MitarbeitsTyp[] getMitarbeitsTypValues()
  {
    return MitarbeitsTyp.values();
  }

  /**
   * Alle ProjektStatus-Werte liefern.
   * 
   * @return ProjektStatus-Werte
   */
  public ProjektStatus[] getProjektStatusValues()
  {
    return ProjektStatus.values();
  }

  /**
   * Alle ProjektTyp-Werte liefern.
   * 
   * @return ProjektTyp-Werte
   */
  public ProjektTyp[] getProjektTypValues()
  {
    return ProjektTyp.values();
  }
}
