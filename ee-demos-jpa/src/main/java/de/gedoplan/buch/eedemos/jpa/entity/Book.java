package de.gedoplan.buch.eedemos.jpa.entity;

import de.gedoplan.baselibs.persistence.entity.GeneratedIntegerIdEntity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Access(AccessType.FIELD)
@Table(name = Book.TABLE_NAME)
public class Book extends GeneratedIntegerIdEntity
{
  public static final String TABLE_NAME = "EEDEMOS_BOOK";

  private String             name;
  private String             isbn;
  private int                pages;

  @ManyToOne
  // @JoinTable(name = "BOOK_PUBLISHER", joinColumns = { @JoinColumn(name = "book_id") })
  private Publisher          publisher;

  protected Book()
  {
  }

  public Book(String name, String isbn, int pages)
  {
    this.name = name;
    this.isbn = isbn;
    this.pages = pages;
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
}
