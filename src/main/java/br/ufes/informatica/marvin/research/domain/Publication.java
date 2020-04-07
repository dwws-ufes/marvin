package br.ufes.informatica.marvin.research.domain;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;
import br.ufes.informatica.marvin.core.domain.Academic;

/**
 * TODO: document this type.
 *
 * @author Thiago Rocha Salvatore
 * @author Vítor E. Silva Souza (vitorsouza@gmail.com)
 * @version 1.0
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "class", discriminatorType = DiscriminatorType.STRING)
public abstract class Publication extends PersistentObjectSupport implements Comparable<Publication> {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** TODO: document this field. */
	protected String title;

	/** TODO: document this field. */
	protected int year;

	/** TODO: document this field. */
	protected String pages;

	/** TODO: document this field. */
	protected String doi;

	/** TODO: document this field. */
	protected String publisher;

	/** TODO: document this field. */
	@ManyToOne
	@NotNull
	private Academic owner;

	/** TODO: document this field. */
	@ManyToOne
	private Venue venue;

	/** TODO: document this field. */
	@ElementCollection
	private List<String> authors = new ArrayList<>();

	/** Constructor. */
	protected Publication() {}

	/** Constructor. */
	public Publication(String title, int year, String pages, String doi, String publisher) {
		this.title = title;
		this.year = year;
		this.pages = pages;
		this.doi = doi;
		this.publisher = publisher;
	}

	/** Getter for title. */
	public String getTitle() {
		return title;
	}

	/** Setter for title. */
	public void setTitle(String title) {
		this.title = title;
	}

	/** Getter for year. */
	public int getYear() {
		return year;
	}

	/** Setter for year. */
	public void setYear(int year) {
		this.year = year;
	}

	/** Getter for authors. */
	public List<String> getAuthors() {
		return authors;
	}

	/** Setter for authors. */
	protected void setAuthors(List<String> authors) {
		this.authors = authors;
	}

	/** Getter for owner. */
	public Academic getOwner() {
		return owner;
	}

	/** Setter for owner. */
	public void setOwner(Academic owner) {
		this.owner = owner;
	}

	public Venue getVenue() {
		return venue;
	}

	public void setVenue(Venue venue) {
		this.venue = venue;
	}

	/** Getter for pages. */
	public String getPages() {
		return pages;
	}

	/** Setter for pages. */
	public void setPages(String pages) {
		this.pages = pages;
	}

	/** Getter for doi. */
	public String getDoi() {
		return doi;
	}

	/** Setter for doi. */
	public void setDoi(String doi) {
		this.doi = doi;
	}

	/** Getter for publisher. */
	public String getPublisher() {
		return publisher;
	}

	/** Setter for publisher. */
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	/**
	 * TODO: document this method.
	 * 
	 * @param author
	 */
	public void addAuthor(String author) {
		authors.add(author);
	}

	/**
	 * TODO: document this method.
	 * 
	 * @return
	 */
	protected String getBibKey() {
		// Starts from the title.
		String bibKey = title;

		// Removes accents from letters.
		bibKey = Normalizer.normalize(bibKey, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");

		// Converts all charactes to lowercase.
		bibKey = bibKey.toLowerCase();

		// Replace all non-alphanumeric characters into underscores.
		bibKey = bibKey.replaceAll("[^a-z0-9]", "_");

		// Attaches the year to the modified title to finish the key.
		return bibKey + "_" + year;
	}

	/**
	 * TODO: document this method.
	 * 
	 * @return
	 */
	protected String getAuthorList() {
		// Produces a string with the author names separated by the "and" keyword.
		StringBuilder builder = new StringBuilder();
		for (String author : authors) {
			// If not already in the Surname, Name format, puts it in this format.
			int idx = author.indexOf(',');
			if (idx == -1) {
				idx = author.lastIndexOf(' ');
				if (idx != -1) author = author.substring(idx + 1) + ", " + author.substring(0, idx);
			}

			builder.append(author).append(" and ");
		}

		// Deletes the trailing "and".
		int len = builder.length();
		builder.delete(len - 5, len);

		return builder.toString();
	}

	/**
	 * TODO: document this method.
	 * 
	 * @return
	 */
	public abstract String toBibTeX();

	/**
	 * TODO: document this method.
	 * 
	 * @return
	 */
	public abstract String getVenueString();

	/** @see java.lang.Comparable#compareTo(java.lang.Object) */
	@Override
	public int compareTo(Publication o) {
		int cmp = 0;

		// Compares first by year.
		cmp = o.year - year;
		if (cmp != 0) return cmp;

		// If still a draw, compares by title.
		cmp = title.compareTo(o.title);
		return cmp;

		// Lastly, compares by persistence id.
		// return super.compareTo(o);
	}
}
