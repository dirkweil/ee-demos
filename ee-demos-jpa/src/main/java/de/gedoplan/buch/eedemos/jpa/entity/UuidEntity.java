package de.gedoplan.buch.eedemos.jpa.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class UuidEntity
{
  @Id
  protected String id;

  public UuidEntity()
  {
    /*
     * Die ID wird mit einer UUID besetzt. Die hier genutzte Methode von java.util.UUID liefert rein zufällige UUID. Damit gibt es
     * eine - äusserst kleine - Wahrscheinlichkeit für die mehrfache Vergabe der gleichen ID. Eine Alternative ist die
     * UUID-Implementierung von Johann Burkard (http://johannburkard.de/software/uuid/).
     */
    this.id = java.util.UUID.randomUUID().toString();
    // this.id = (new com.eaio.uuid.UUID().toString()).toString();
  }

  public String getId()
  {
    return this.id;
  }

  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = prime * result + this.id.hashCode();
    return result;
  }

  @Override
  public boolean equals(Object obj)
  {
    if (this == obj)
    {
      return true;
    }
    if (obj == null)
    {
      return false;
    }
    if (getClass() != obj.getClass())
    {
      return false;
    }
    UuidEntity other = (UuidEntity) obj;
    return this.id.equals(other.id);
  }

}
