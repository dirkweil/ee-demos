package de.gedoplan.buch.eedemos.jpa.entity;

import de.gedoplan.baselibs.persistence.entity.GeneratedIntegerIdEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Access(AccessType.FIELD)
@Table(name = Publisher.TABLE_NAME)
@NamedEntityGraphs({
    @NamedEntityGraph(
        name = "Publisher_books",
        attributeNodes = @NamedAttributeNode("books")),
    @NamedEntityGraph(
        name = "Publisher_booksAndAuthors",
        attributeNodes = @NamedAttributeNode(value = "books", subgraph = "Book_authors"),
        subgraphs = @NamedSubgraph(name = "Book_authors", attributeNodes = @NamedAttributeNode("authors"))) })
public class Publisher extends GeneratedIntegerIdEntity
{
  public static final String TABLE_NAME = "EEDEMOS_PUBLISHER";

  private String             name;

  @OneToMany(mappedBy = "publisher", fetch = FetchType.LAZY)
  // @OrderColumn(name = "order_index")
  private List<Book>         books;

  @ElementCollection(fetch = FetchType.EAGER)
  private List<String>       categories;

  protected Publisher()
  {
  }

  public Publisher(String name, String... categories)
  {
    this.name = name;

    this.books = new ArrayList<>();
    this.categories = new ArrayList<>(Arrays.asList(categories));
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

  public List<String> getCategories()
  {
    return this.categories;
  }
}
