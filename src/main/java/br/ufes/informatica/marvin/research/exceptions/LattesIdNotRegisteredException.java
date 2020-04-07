package br.ufes.informatica.marvin.research.exceptions;

/**
 * TODO: document this type.
 *
 * @author Vítor E. Silva Souza (vitorsouza@gmail.com)
 * @version 1.0
 */
public class LattesIdNotRegisteredException extends Exception {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** TODO: document this field. */
	private Long lattesId;

	/** TODO: document this field. */
	private String researcherName;

	/** Constructor. */
	public LattesIdNotRegisteredException(Long lattesId, String researcherName) {
		this.lattesId = lattesId;
		this.researcherName = researcherName;
	}

	/** Getter for lattesId. */
	public Long getLattesId() {
		return lattesId;
	}

	/** Getter for researcherName. */
	public String getResearcherName() {
		return researcherName;
	}
}
