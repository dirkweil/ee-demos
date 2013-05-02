package de.gedoplan.buch.eedemos.jpa.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Access(AccessType.FIELD)
@Table(name = Publisher.TABLE_NAME)
public class Publisher
{
  public static final String TABLE_NAME = "EEDEMOS_PUBLISHER";

  @Id
  @GeneratedValue
  private Integer            id;
  private String             name;

  @OneToMany(mappedBy = "publisher", fetch = FetchType.LAZY)
  // @OrderColumn(name = "order_index")
  private List<Book>         books      = new ArrayList<Book>();

  protected Publisher()
  {
  }

  public Publisher(String name)
  {
    this.name = name;
  }

  public Integer getId()
  {
    return this.id;
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public List<Book> getBooks()
  {
    return Collections.unmodifiableList(this.books);
  }

  void addBook(Book book)
  {
    this.books.add(book);
  }

  void removeBook(Book book)
  {
    this.books.remove(book);
  }

  @Override
  public int hashCode()
  {
    return this.id != null ? this.id.hashCode() : 0;
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
    final Publisher other = (Publisher) obj;

    return this.id != null && this.id.equals(other.id);
  }

  @Override
  public String toString()
  {
    return this.getClass().getSimpleName() + "{id=" + this.id + ",name=" + this.name + "}";
  }

}
