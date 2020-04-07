package br.ufes.informatica.marvin.research.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * TODO: document this type.
 *
 * @author Thiago Rocha Salvatore
 * @author Vítor E. Silva Souza (vitorsouza@gmail.com)
 * @version 1.0
 */
@Entity
@DiscriminatorValue("journal")
public class JournalPaper extends Publication {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** TODO: document this field. */
	private String journal;

	/** TODO: document this field. */
	private String number;

	/** TODO: document this field. */
	private String volume;

	/** TODO: document this field. */
	private String issn;

	/** Constructor. */
	protected JournalPaper() {}

	/** Constructor. */
	public JournalPaper(String title, int year, String pages, String doi, String publisher, String journal, String number, String volume, String issn) {
		super(title, year, pages, doi, publisher);
		this.journal = journal;
		this.number = number;
		this.volume = volume;
		this.issn = issn;
	}

	/** Getter for journal. */
	public String getJournal() {
		return journal;
	}

	/** Setter for journal. */
	public void setJournal(String journal) {
		this.journal = journal;
	}

	/** Getter for number. */
	public String getNumber() {
		return number;
	}

	/** Setter for number. */
	public void setNumber(String number) {
		this.number = number;
	}

	/** Getter for volume. */
	public String getVolume() {
		return volume;
	}

	/** Setter for volume. */
	public void setVolume(String volume) {
		this.volume = volume;
	}

	/** Getter for issn. */
	public String getIssn() {
		return issn;
	}

	/** Setter for issn. */
	public void setIssn(String issn) {
		this.issn = issn;
	}

	/** @see br.ufes.informatica.marvin.research.domain.Publication#getVenueString() */
	@Override
	public String getVenueString() {
		return journal;
	}

	/** @see br.ufes.informatica.marvin.research.domain.Publication#toBibTeX() */
	@Override
	public String toBibTeX() {
		StringBuilder builder = new StringBuilder();

		// Produces the BibTeX entry for this type of publication.
		builder.append("@article{").append(getBibKey()).append(",\n"); // @article{bibKey,
		builder.append("\ttitle = {{").append(title).append("}},\n"); // title = {{Publication's Title}},
		builder.append("\tauthor = {").append(getAuthorList().toUpperCase()).append("},\n"); // author = {Author list},
		builder.append("\tjournal = {{").append(journal).append("}},\n"); // journal = {{Journal title}},
		if (number != null && !number.isEmpty()) builder.append("\tedition = {").append(number).append("},\n"); // edition =
																																																						// {Journal
																																																						// Edition},
		if (volume != null && !volume.isEmpty()) builder.append("\tvolume = {").append(volume).append("},\n"); // volume =
																																																						// {Journal
																																																						// Volume},
		if (pages != null && !pages.isEmpty()) builder.append("\tpages = {").append(pages).append("},\n"); // pages = {Start
																																																				// Page--End
																																																				// Page},
		if (doi != null && !doi.isEmpty()) builder.append("\tdoi = {").append(doi).append("},\n"); // doi = {Digital Object
																																																// Identifier},
		if (issn != null && !issn.isEmpty()) builder.append("\tissn = {").append(issn).append("},\n"); // issn = {Journal
																																																		// ISSN},
		if (publisher != null && !publisher.isEmpty()) builder.append("\tpublisher = {{").append(publisher).append("}},\n"); // publisher
																																																													// =
																																																													// {{Publisher's
																																																													// name}},
		builder.append("\tyear = {").append(getYear()).append("}\n"); // year = {Publication year}
		builder.append("}\n"); // }

		return builder.toString();
	}
}
