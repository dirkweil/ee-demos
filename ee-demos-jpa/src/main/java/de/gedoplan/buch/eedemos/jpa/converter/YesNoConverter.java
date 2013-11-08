package de.gedoplan.buch.eedemos.jpa.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class YesNoConverter implements AttributeConverter<Boolean, String>
{

  @Override
  public String convertToDatabaseColumn(Boolean fieldValue)
  {
    if (fieldValue == null)
    {
      return null;
    }

    return fieldValue ? "Y" : "N";
  }

  @Override
  public Boolean convertToEntityAttribute(String columnValue)
  {
    if (columnValue == null)
    {
      return null;
    }

    return "Y".equals(columnValue);
  }

}
