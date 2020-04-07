package br.ufes.informatica.marvin.research.application;

/**
 * TODO: document this type.
 *
 * @author Vítor E. Silva Souza (vitorsouza@gmail.com)
 * @version 1.0
 */
class LattesEntry implements Comparable<LattesEntry> {
	/** TODO: document this field. */
	private String type;

	/** TODO: document this field. */
	private int year;

	/** TODO: document this field. */
	private String title;

	/** TODO: document this field. */
	private String doi;

	/** TODO: document this field. */
	private String venue;

	/** TODO: document this field. */
	private String authors;

	/** TODO: document this field. */
	private String pageStart;

	/** TODO: document this field. */
	private String pageEnd;

	/** TODO: document this field. */
	private String publisher;

	/** TODO: document this field. */
	private String extra01;

	/** TODO: document this field. */
	private String extra02;

	/** TODO: document this field. */
	private String extra03;

	/** Constructor. */
	LattesEntry(String type, int year, String title, String doi, String venue, String authors) {
		this.type = type;
		this.year = year;
		this.title = title;
		this.doi = doi;
		this.venue = venue;
		this.authors = authors;
	}

	/** Getter for type. */
	public String getType() {
		return type;
	}

	/** Setter for type. */
	public void setType(String type) {
		this.type = type;
	}

	/** Getter for year. */
	public int getYear() {
		return year;
	}

	/** Setter for year. */
	public void setYear(int year) {
		this.year = year;
	}

	/** Getter for title. */
	public String getTitle() {
		return title;
	}

	/** Setter for title. */
	public void setTitle(String title) {
		this.title = title;
	}

	/** Getter for doi. */
	public String getDoi() {
		return doi;
	}

	/** Setter for doi. */
	public void setDoi(String doi) {
		this.doi = doi;
	}

	/** Getter for venue. */
	public String getVenue() {
		return venue;
	}

	/** Setter for venue. */
	public void setVenue(String venue) {
		this.venue = venue;
	}

	/** Getter for authors. */
	public String getAuthors() {
		return authors;
	}

	/** Setter for authors. */
	public void setAuthors(String authors) {
		this.authors = authors;
	}

	/** @see java.lang.Comparable#compareTo(java.lang.Object) */
	@Override
	public int compareTo(LattesEntry o) {
		int cmp = year - o.year;
		if (cmp != 0) return cmp;

		return title.compareTo(o.title);
	}

	/** Getter for pageStart. */
	public String getPageStart() {
		return pageStart;
	}

	/** Setter for pageStart. */
	public void setPageStart(String pageStart) {
		this.pageStart = pageStart;
	}

	/** Getter for pageEnd. */
	public String getPageEnd() {
		return pageEnd;
	}

	/** Setter for pageEnd. */
	public void setPageEnd(String pageEnd) {
		this.pageEnd = pageEnd;
	}

	/**
	 * TODO: document this method.
	 * 
	 * @return
	 */
	public String getPages() {
		if (pageStart == null || pageEnd == null || pageStart.isEmpty() || pageEnd.isEmpty()) return null;
		return pageStart + "--" + pageEnd;
	}

	/** Getter for publisher. */
	public String getPublisher() {
		return publisher;
	}

	/** Setter for publisher. */
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	/** Getter for extra01. */
	public String getExtra01() {
		return extra01;
	}

	/** Setter for extra01. */
	public void setExtra01(String extra01) {
		this.extra01 = extra01;
	}

	/** Getter for extra02. */
	public String getExtra02() {
		return extra02;
	}

	/** Setter for extra02. */
	public void setExtra02(String extra02) {
		this.extra02 = extra02;
	}

	/** Getter for extra03. */
	public String getExtra03() {
		return extra03;
	}

	/** Setter for extra03. */
	public void setExtra03(String extra03) {
		this.extra03 = extra03;
	}
}
