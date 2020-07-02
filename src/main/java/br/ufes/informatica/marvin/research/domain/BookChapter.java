package br.ufes.informatica.marvin.research.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * TODO: document this type.
 *
 * @author Thiago Rocha Salvatore (https://github.com/thiagosalvatore)
 * @author Vítor E. Silva Souza (https://github.com/vitorsouza/)
 */
@Entity
@DiscriminatorValue("chapter")
public class BookChapter extends Publication {
  /** The unique identifier for a serializable class. */
  private static final long serialVersionUID = 1L;

  /** TODO: document this field. */
  private String bookTitle;

  /** TODO: document this field. */
  private String edition;

  /** Constructor. */
  protected BookChapter() {}

  /** Constructor. */
  public BookChapter(String title, int year, String pages, String doi, String publisher,
      String bookTitle, String edition) {
    super(title, year, pages, doi, publisher);
    if (bookTitle.length() > 254)
      bookTitle = bookTitle.substring(0, 254);
    this.bookTitle = bookTitle;
    this.edition = edition;
  }

  /** Getter for bookTitle. */
  public String getBookTitle() {
    return bookTitle;
  }

  /** Setter for bookTitle. */
  public void setBookTitle(String bookTitle) {
    this.bookTitle = bookTitle;
  }

  /** Getter for edition. */
  public String getEdition() {
    return edition;
  }

  /** Setter for edition. */
  public void setEdition(String edition) {
    this.edition = edition;
  }

  @Override
  public String getVenueString() {
    return "";
  }

  @Override
  public String toBibTeX() {
    StringBuilder builder = new StringBuilder();

    // Produces the BibTeX entry for this type of publication.
    builder.append("@incollection{").append(getBibKey()).append(",\n"); // @incollection{bibKey,
    builder.append("\ttitle = {{").append(title).append("}},\n"); // title = {{Publication's
                                                                  // Title}},
    builder.append("\tauthor = {").append(getAuthorList().toUpperCase()).append("},\n"); // author =
                                                                                         // {Author
                                                                                         // list},
    builder.append("\tbooktitle = {{").append(bookTitle).append("}},\n"); // booktitle = {{Book
                                                                          // title}},
    if (edition != null && !edition.isEmpty())
      builder.append("\tvolume = {").append(edition).append("},\n"); // volume
                                                                     // = {Book
                                                                     // edition},
    if (pages != null && !pages.isEmpty())
      builder.append("\tpages = {").append(pages).append("},\n"); // pages = {Start
                                                                  // Page--End
                                                                  // Page},
    if (doi != null && !doi.isEmpty())
      builder.append("\tdoi = {").append(doi).append("},\n"); // doi = {Digital Object
                                                              // Identifier},
    if (publisher != null && !publisher.isEmpty())
      builder.append("\tpublisher = {{").append(publisher).append("}},\n"); // publisher
                                                                            // =
                                                                            // {{Publisher's
                                                                            // name}},
    builder.append("\tyear = {").append(getYear()).append("}\n"); // year = {Publication year}
    builder.append("}\n"); // }

    return builder.toString();
  }
}
