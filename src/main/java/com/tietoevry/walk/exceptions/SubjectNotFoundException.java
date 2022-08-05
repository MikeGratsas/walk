package com.tietoevry.walk.exceptions;

public class SubjectNotFoundException extends DataEntityException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7683612277031496552L;

	public SubjectNotFoundException(final Long id) {
        super("Subject not found", id);
    }
}
