package com.tietoevry.walk.exceptions;

public class RuleUpdatedException extends DataEntityException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6511398597910400534L;

	public RuleUpdatedException(final Long id) {
        super("Rule was updated since last read", id);
    }
}
