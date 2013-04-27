package de.gedoplan.buch.eedemos.jpa.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Access(AccessType.FIELD)
public class Book
{
  @Id
  @GeneratedValue
  private Integer   id;
  private String    name;
  private String    isbn;
  private int       pages;

  @ManyToOne
  // @JoinTable(name = "BOOK_PUBLISHER", joinColumns = { @JoinColumn(name = "book_id") })
  private Publisher publisher;

  protected Book()
  {
  }

  public Book(String name, String isbn, int pages)
  {
    this.name = name;
    this.isbn = isbn;
    this.pages = pages;
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

  public String getIsbn()
  {
    return this.isbn;
  }

  public void setIsbn(String isbn)
  {
    this.isbn = isbn;
  }

  public int getPages()
  {
    return this.pages;
  }

  public void setPages(int pages)
  {
    this.pages = pages;
  }

  public Publisher getPublisher()
  {
    return this.publisher;
  }

  public void setPublisher(Publisher publisher)
  {
    if (this.publisher != null)
    {
      this.publisher.removeBook(this);
    }

    this.publisher = publisher;

    if (this.publisher != null)
    {
      this.publisher.addBook(this);
    }
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
    final Book other = (Book) obj;

    return this.id != null && this.id.equals(other.id);
  }

  @Override
  public String toString()
  {
    return this.getClass().getSimpleName() + "{id=" + this.id + ", name=" + this.name + ", isbn=" + this.isbn + ", pages=" + this.pages + "}";
  }

}
