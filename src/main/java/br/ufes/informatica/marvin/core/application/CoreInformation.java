package br.ufes.informatica.marvin.core.application;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.inject.Named;
import br.ufes.inf.nemo.jbutler.ResourceUtil;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.PersistentObjectNotFoundException;
import br.ufes.informatica.marvin.core.domain.MarvinConfiguration;
import br.ufes.informatica.marvin.core.persistence.MarvinConfigurationDAO;

/**
 * TODO: document this type.
 *
 * @author Vítor E. Silva Souza (https://github.com/vitorsouza/)
 */
@Singleton
@Named("coreInfo")
public class CoreInformation implements Serializable {
  /** The unique identifier for a serializable class. */
  private static final long serialVersionUID = 1L;

  /** Logger for this class. */
  private static final Logger logger = Logger.getLogger(CoreInformation.class.getCanonicalName());

  /** The path (in the EJB module) for the quotes file. */
  private static final String QUOTES_FILE_PATH = "marvin/quotes.txt";

  /** TODO: document this field. */
  private static final String DEFAULT_DECORATOR_NAME = "admin-lte";

  /** The DAO for MarvinConfiguration objects. */
  @EJB
  private MarvinConfigurationDAO marvinConfigurationDAO;

  /** TODO: document this field. */
  private MarvinConfiguration currentConfig;

  /** Indicates if the system is properly installed. */
  private Boolean systemInstalled;

  /** Indicates the decorator being used. */
  private String decorator = DEFAULT_DECORATOR_NAME;

  /** List of quotes. */
  private List<String> quotes;

  /** Random number generator. */
  private Random random = new Random(System.currentTimeMillis());

  /** Initializer method. */
  @PostConstruct
  public void init() {
    // At first use, check if the system is installed.
    logger.log(Level.FINER, "Checking if the system has been installed...");

    // If the system is configured, it's installed.
    try {
      currentConfig = marvinConfigurationDAO.retrieveCurrentConfiguration();
      systemInstalled = true;
    } catch (PersistentObjectNotFoundException e) {
      systemInstalled = false;
    }

    // Load quotes. If anything goes wrong, falls back to a single default quote.
    quotes = new ArrayList<>();
    try {
      InputStream quotesFileStream = ResourceUtil.getResourceAsStream(QUOTES_FILE_PATH);
      try (Scanner scanner = new Scanner(quotesFileStream)) {
        while (scanner.hasNextLine())
          quotes.add(scanner.nextLine().trim());
      }
    } catch (Exception e) {
      logger.log(Level.WARNING, "Could not load quotes from path: {0}", QUOTES_FILE_PATH);
      quotes.add("I didn't ask to be made.");
    }
  }

  /** Getter for currentConfig. */
  public MarvinConfiguration getCurrentConfig() {
    return currentConfig;
  }

  /** Getter for systemInstalled. */
  public Boolean getSystemInstalled() {
    return systemInstalled;
  }

  /** Getter for decorator. */
  public String getDecorator() {
    return decorator;
  }

  /** Gets a random quote from Marvin. */
  public String getQuote() {
    // Quotes should not be empty.
    if (quotes.isEmpty())
      return "";
    int idx = random.nextInt(quotes.size());
    return quotes.get(idx);
  }
}
