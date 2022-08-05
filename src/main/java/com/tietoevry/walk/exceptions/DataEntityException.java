package com.tietoevry.walk.exceptions;

public class DataEntityException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3984426890069482956L;

	private final Long id;

    public DataEntityException(final String message) {
        super(message);
        this.id = -1L;
    }

    public DataEntityException(final String message, final Long id) {
        super(message + ": id = " + id);
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
