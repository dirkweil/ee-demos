package de.gedoplan.buch.eedemos.rs.entity;

public enum TalkType
{
  KEYNOTE("K"), SESSION("S"), WORKSHOP("W");

  private String persistentForm;

  private TalkType(String persistentForm)
  {
    this.persistentForm = persistentForm;
  }

  public String getPersistentForm()
  {
    return this.persistentForm;
  }

  public static TalkType ofPersistentForm(String persistentForm)
  {
    for (TalkType talkType : values())
    {
      if (talkType.getPersistentForm().equals(persistentForm))
      {
        return talkType;
      }
    }

    throw new IllegalArgumentException("Unknown persistent form");
  }
}
