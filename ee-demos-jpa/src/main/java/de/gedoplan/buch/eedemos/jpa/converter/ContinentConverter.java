package de.gedoplan.buch.eedemos.jpa.converter;

import de.gedoplan.buch.eedemos.jpa.entity.Continent;

import javax.persistence.AttributeConverter;

/**
 * Converter für {@link Continent} <-> VARCHAR.
 * 
 * @author dw
 */
// TODO: Konverter wegen Hibernate/WildFly-Bugs HHH-8111 und HHH-8316 derzeit deaktiviert
//@Converter(autoApply = true)
public class ContinentConverter implements AttributeConverter<Continent, String>
{

  @Override
  public String convertToDatabaseColumn(Continent fieldValue)
  {
    if (fieldValue == null)
    {
      return null;
    }

    switch (fieldValue)
    {
    case AFRICA:
      return "AF";

    case ANTARCTICA:
      return "AN";

    case ASIA:
      return "AS";

    case AUSTRALIA:
      return "OC";

    case EUROPE:
      return "EU";

    case NORTH_AMERICA:
      return "NA";

    case SOUTH_AMERICA:
      return "SA";

    default:
      throw new IllegalArgumentException("Unmapped continent: " + fieldValue);
    }

  }

  @Override
  public Continent convertToEntityAttribute(String columnValue)
  {
    if (columnValue == null)
    {
      return null;
    }

    switch (columnValue)
    {
    case "AF":
      return Continent.AFRICA;

    case "AN":
      return Continent.ANTARCTICA;

    case "AS":
      return Continent.ASIA;

    case "OC":
      return Continent.AUSTRALIA;

    case "EU":
      return Continent.EUROPE;

    case "NA":
      return Continent.NORTH_AMERICA;

    case "SA":
      return Continent.SOUTH_AMERICA;

    default:
      throw new IllegalArgumentException("Illegal continent code");
    }

  }

}
