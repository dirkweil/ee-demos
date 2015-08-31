package de.gedoplan.buch.eedemos.rs.persistence;

import de.gedoplan.buch.eedemos.rs.entity.TalkType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TalkTypeConverter implements AttributeConverter<TalkType, String>
{

  @Override
  public String convertToDatabaseColumn(TalkType attribute)
  {
    return attribute != null ? attribute.getPersistentForm() : null;
  }

  @Override
  public TalkType convertToEntityAttribute(String dbData)
  {
    return dbData != null ? TalkType.ofPersistentForm(dbData) : null;
  }

}
