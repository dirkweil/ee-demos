package de.gedoplan.buch.eedemos.bv.validation.util;

import de.gedoplan.buch.eedemos.bv.validation.entity.Fragebogen;

import java.util.Date;

public class FragebogenSamples
{
  public static final Fragebogen   FRAGEBOGEN_NULL;
  public static final Fragebogen   FRAGEBOGEN_LEERNAME;
  public static final Fragebogen   FRAGEBOGEN_TEILADRESSE_INZUKUNFT;
  public static final Fragebogen   FRAGEBOGEN_OK;

  public static final Fragebogen[] FRAGEBOEGEN;

  static
  {
    Date now = new Date();
    Date farFuture = new Date(2539029600000L);

    FRAGEBOGEN_NULL = new Fragebogen();

    FRAGEBOGEN_LEERNAME = new Fragebogen();
    FRAGEBOGEN_LEERNAME.setName("");
    FRAGEBOGEN_LEERNAME.setLebensalter(29);
    FRAGEBOGEN_LEERNAME.setUmfrageDatum(now);

    FRAGEBOGEN_TEILADRESSE_INZUKUNFT = new Fragebogen();
    FRAGEBOGEN_TEILADRESSE_INZUKUNFT.setName("Max Mustermann");
    FRAGEBOGEN_TEILADRESSE_INZUKUNFT.getAdresse().setPlz("1234");
    FRAGEBOGEN_TEILADRESSE_INZUKUNFT.setLebensalter(31);
    FRAGEBOGEN_TEILADRESSE_INZUKUNFT.setUmfrageDatum(farFuture);
    FRAGEBOGEN_TEILADRESSE_INZUKUNFT.setEmail("max.mustermann");

    FRAGEBOGEN_OK = new Fragebogen();
    FRAGEBOGEN_OK.setName("Erika Mustermann");
    FRAGEBOGEN_OK.getAdresse().setStrasse("Musterweg 1");
    FRAGEBOGEN_OK.getAdresse().setPlz("12345");
    FRAGEBOGEN_OK.getAdresse().setOrt("Musterstadt");
    FRAGEBOGEN_OK.setLebensalter(29);
    FRAGEBOGEN_OK.setUmfrageDatum(now);
    FRAGEBOGEN_OK.setBemerkungen("Ich finde Java toll!");
    FRAGEBOGEN_OK.setEmail("erika.mustermann@sample.info");

    FRAGEBOEGEN = new Fragebogen[] { FRAGEBOGEN_NULL, FRAGEBOGEN_LEERNAME, FRAGEBOGEN_TEILADRESSE_INZUKUNFT, FRAGEBOGEN_OK };
  }

}
