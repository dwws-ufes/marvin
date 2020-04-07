package br.ufes.informatica.marvin.research.application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import br.ufes.inf.nemo.jbutler.ResourceUtil;
import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.research.domain.Book;
import br.ufes.informatica.marvin.research.domain.BookChapter;
import br.ufes.informatica.marvin.research.domain.ConferencePaper;
import br.ufes.informatica.marvin.research.domain.JournalPaper;
import br.ufes.informatica.marvin.research.domain.Publication;
import br.ufes.informatica.marvin.research.exceptions.LattesParseException;

/**
 * TODO: document this type.
 *
 * @author Vítor E. Silva Souza (vitorsouza@gmail.com)
 * @version 1.0
 */
class LattesParser implements PublicationInfo {
	/** The logger. */
	private static final Logger logger = Logger.getLogger(LattesParser.class.getCanonicalName());

	/** Name of the configuration file with all the settings for this script. */
	private static final String CONFIG_FILE_NAME = "lattes-parser.properties";
	
	/** Contents of publication's nature that indicates it's a full paper. */
	private static final String PUBLICATION_NATURE_FULL = "COMPLETO";

	/** Properties object that holds all the configuration. */
	private static Properties CONFIG;

	/** Contents of the XML file that holds the CV information. */
	private StringBuilder xmlContents = new StringBuilder();

	/** TODO: document this field. */
	private SortedSet<JournalPaper> journalPapers;

	/** TODO: document this field. */
	private SortedSet<Book> books;

	/** TODO: document this field. */
	private SortedSet<BookChapter> bookChapters;

	/** TODO: document this field. */
	private SortedSet<ConferencePaper> conferencePapers;

	/** The curriculum ID. */
	private Long lattesId;

	/** The researcher's name. */
	private String researcherName;

	/** The academic object that represents the researcher in the system. */
	private Academic researcher;

	/** The amount of publications that the researcher currently has. */
	private long previousQuantity;

	/** Constructor. */
	public LattesParser(InputStream inputStream) throws LattesParseException {
		try {
			// Lazily loads the configurations of the Lattes parser.
			if (CONFIG == null) {
				logger.log(Level.INFO, "Loading Lattes parser configuration from file: {0}", CONFIG_FILE_NAME);
				CONFIG = new Properties();
				String parserConfigFileName = getClass().getPackage().getName().replaceAll("\\.", "/") + "/" + CONFIG_FILE_NAME;
				File parserConfigFile = ResourceUtil.getResourceAsFile(parserConfigFileName);
				logger.log(Level.FINE, "Lattes parser configuration file located at: {0}", parserConfigFile.getAbsolutePath());
				CONFIG.load(new FileInputStream(parserConfigFile));
			}

			// Reads the contents of the input stream (i.e., the XML file) into a string buffer ready for Jsoup.
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, CONFIG.getProperty("encoding")))) {
				String line = reader.readLine();
				while (line != null) {
					xmlContents.append(line).append('\n');
					line = reader.readLine();
				}
			}
			logger.log(Level.INFO, "Read {0} characters from the given CV's input stream.", xmlContents.length());
		}
		catch (Exception e) {
			// In case there's any exception that prevents us from reading the CV, wraps it in an exception the controller can
			// handle.
			logger.log(Level.SEVERE, "Exception while trying to read the contents of the XML file that holds the Lattes CV.", e);
			throw new LattesParseException(e);
		}
	}

	/** Getter for lattesId. */
	public Long getLattesId() {
		return lattesId;
	}

	/** Getter for researcherName. */
	public String getResearcherName() {
		return researcherName;
	}

	/** Setter for researcher. */
	public void setResearcher(Academic researcher) {
		this.researcher = researcher;
	}

	/** Getter for researcher. */
	public Academic getResearcher() {
		return researcher;
	}

	/** Getter for previousQuantity. */
	public long getPreviousQuantity() {
		return previousQuantity;
	}

	/** Setter for previousQuantity. */
	public void setPreviousQuantity(long previousQuantity) {
		this.previousQuantity = previousQuantity;
	}

	/** Getter for articles. */
	public Set<JournalPaper> getJournalPapers() {
		return journalPapers;
	}

	/** Getter for books. */
	public Set<Book> getBooks() {
		return books;
	}

	/** Getter for inCollections. */
	public Set<BookChapter> getBookChapters() {
		return bookChapters;
	}

	/** Getter for inProceedings. */
	public Set<ConferencePaper> getConferencePapers() {
		return conferencePapers;
	}

	/**
	 * TODO: document this method.
	 * 
	 * @param doc
	 */
	public void parse() throws LattesParseException {
		// Parses the XML file with the Lattes CV using Jsoup.
		Document doc = Jsoup.parse(xmlContents.toString(), "", Parser.xmlParser());

		// Extracts the ID of the CV and the name of the researcher.
		Element curriculum = doc.select(CONFIG.getProperty("jsoupSelectorCurriculum")).first();
		lattesId = Long.parseLong(curriculum.attr(CONFIG.getProperty("curriculumId")));
		Element generalData = doc.select(CONFIG.getProperty("jsoupSelectorGeneralData")).first();
		researcherName = generalData.attr(CONFIG.getProperty("generalDataName"));
		logger.log(Level.FINE, "Jsoup successfully parsed the CV # {0}, of {1}", new Object[] { lattesId, researcherName });

		// Navigates to the nodes that contains the production (supports multiple, although we expect a single one).
		Elements bibliographyLists = doc.select(CONFIG.getProperty("jsoupSelectorBibliographic"));

		// Goes through all the bibliography.
		for (Element elem : bibliographyLists) {
			// Extracts the data about the publications.
			extractArticles(extractEntries(elem, "Journals"));
			extractBooks(extractEntries(elem, "Books"));
			extractInCollections(extractEntries(elem, "Chapters"));
			extractInProceedings(extractEntries(elem, "Events"));

			// FIXME: support these types of publications?
			// extractEntries(elem, "Magazines");
			// extractEntries(elem, "Others");
		}
	}

	/**
	 * TODO: document this method.
	 * 
	 * @param researcher
	 * @param element
	 * @param name
	 */
	private SortedSet<LattesEntry> extractEntries(Element element, String name) {
		SortedSet<LattesEntry> entries = new TreeSet<>();

		// Gets the structural information from the configuration.
		String selector = CONFIG.getProperty("jsoupSelectorBibliographic" + name);
		String selectorGeneral = CONFIG.getProperty("jsoupSelectorBibliographic" + name + "General");
		String selectorDetails = CONFIG.getProperty("jsoupSelectorBibliographic" + name + "Details");
		String selectorAuthors = CONFIG.getProperty("jsoupSelectorBibliographic" + name + "Authors");
		String baseType = CONFIG.getProperty("bibliographic" + name + "BaseType");
		String attrType = CONFIG.getProperty("bibliographic" + name + "Type");
		String attrYear = CONFIG.getProperty("bibliographic" + name + "Year");
		String attrTitle = CONFIG.getProperty("bibliographic" + name + "Title");
		String attrDoi = CONFIG.getProperty("bibliographic" + name + "DOI");
		String[] attrVenue = CONFIG.getProperty("bibliographic" + name + "Venue").split("\\s*\\|\\s*");
		String attrAuthors = CONFIG.getProperty("bibliographic" + name + "Authors");
		String attrPageStart = CONFIG.getProperty("bibliographic" + name + "PageStart");
		String attrPageEnd = CONFIG.getProperty("bibliographic" + name + "PageEnd");
		String attrPublisher = CONFIG.getProperty("bibliographic" + name + "Publisher");
		String attrExtra01 = CONFIG.getProperty("bibliographic" + name + "Extra01");
		String attrExtra02 = CONFIG.getProperty("bibliographic" + name + "Extra02");
		String attrExtra03 = CONFIG.getProperty("bibliographic" + name + "Extra03");

		// Goes through all entries.
		Elements elems = element.select(selector);
		for (Element elem : elems) {
			Element general = elem.select(selectorGeneral).first();
			Element detail = elem.select(selectorDetails).first();

			// Extracts the information needed.
			int year = parseYear(general.attr(attrYear));
			String type = baseType + " / " + general.attr(attrType);
			String title = general.attr(attrTitle);
			String doi = general.attr(attrDoi);

			// Venue can be split into more than one attribute. Gets the first non-empty one.
			String venues = "";
			for (String attr : attrVenue) {
				venues = detail.attr(attr);
				if (venues != null && venues.length() > 0) break;
			}

			// Authors can be multiple.
			StringBuilder authors = new StringBuilder();
			for (Element author : elem.select(selectorAuthors))
				authors.append(author.attr(attrAuthors)).append("; ");
			authors.deleteCharAt(authors.length() - 1);
			authors.deleteCharAt(authors.length() - 1);

			// Creates an entry with the mandatory data.
			LattesEntry entry = new LattesEntry(type, year, title, doi, venues.toString(), authors.toString());

			// Check if any of the optional attributes are present.
			if (attrPageStart != null && detail.hasAttr(attrPageStart)) entry.setPageStart(detail.attr(attrPageStart));
			if (attrPageEnd != null && detail.hasAttr(attrPageEnd)) entry.setPageEnd(detail.attr(attrPageEnd));
			if (attrPublisher != null && detail.hasAttr(attrPublisher)) entry.setPublisher(detail.attr(attrPublisher));
			if (attrExtra01 != null && detail.hasAttr(attrExtra01)) entry.setExtra01(detail.attr(attrExtra01));
			if (attrExtra02 != null && detail.hasAttr(attrExtra02)) entry.setExtra02(detail.attr(attrExtra02));
			if (attrExtra03 != null && detail.hasAttr(attrExtra03)) entry.setExtra03(detail.attr(attrExtra03));

			// Adds the entry to the set.
			entries.add(entry);
		}

		return entries;
	}

	/**
	 * TODO: document this method.
	 * 
	 * @param yearData
	 * @return
	 */
	private int parseYear(String yearData) {
		if (yearData.matches("\\d{4}")) return Integer.parseInt(yearData);
		String[] data = yearData.split(" ");
		for (int i = 0; i < data.length; i++)
			if (data[i].matches("\\d{4}")) return Integer.parseInt(data[i]);
		return 0;
	}

	/**
	 * TODO: document this method.
	 * 
	 * @param entries
	 */
	private void extractArticles(SortedSet<LattesEntry> entries) {
		journalPapers = new TreeSet<>();
		for (LattesEntry entry : entries) {
			JournalPaper article = new JournalPaper(entry.getTitle(), entry.getYear(), entry.getPages(), entry.getDoi(), entry.getPublisher(), entry.getVenue(), entry.getExtra02(), entry.getExtra03(), entry.getExtra01());
			extractAuthors(article, entry.getAuthors());
			journalPapers.add(article);
		}
	}

	/**
	 * TODO: document this method.
	 * 
	 * @param entries
	 */
	private void extractBooks(SortedSet<LattesEntry> entries) {
		books = new TreeSet<>();
		for (LattesEntry entry : entries) {
			Book book = new Book(entry.getTitle(), entry.getYear(), entry.getPages(), entry.getDoi(), entry.getPublisher(), entry.getExtra01());
			extractAuthors(book, entry.getAuthors());
			books.add(book);
		}
	}

	/**
	 * TODO: document this method.
	 * 
	 * @param entries
	 */
	private void extractInCollections(SortedSet<LattesEntry> entries) {
		bookChapters = new TreeSet<>();
		for (LattesEntry entry : entries) {
			BookChapter chapter = new BookChapter(entry.getTitle(), entry.getYear(), entry.getPages(), entry.getDoi(), entry.getPublisher(), entry.getVenue(), entry.getExtra01());
			extractAuthors(chapter, entry.getAuthors());
			bookChapters.add(chapter);
		}
	}

	/**
	 * TODO: document this method.
	 * 
	 * @param entries
	 */
	private void extractInProceedings(SortedSet<LattesEntry> entries) {
		conferencePapers = new TreeSet<>();
		for (LattesEntry entry : entries) {
			boolean fullPaper = entry.getType().contains(PUBLICATION_NATURE_FULL);
			ConferencePaper paper = new ConferencePaper(entry.getTitle(), entry.getYear(), entry.getPages(), entry.getDoi(), entry.getPublisher(), entry.getExtra01(), entry.getVenue(), fullPaper);
			extractAuthors(paper, entry.getAuthors());
			conferencePapers.add(paper);
		}
	}

	/**
	 * TODO: document this method.
	 * 
	 * @param publication
	 * @param authors
	 */
	private void extractAuthors(Publication publication, String authors) {
		// Separate authors using ";" and add them to the publication in order.
		String[] array = authors.split(";");
		for (String author : array)
			publication.addAuthor(author.trim());
	}
}
