package com.tietoevry.walk.exceptions;

public class DataEntityException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3984426890069482956L;

	private final Long id;

    public DataEntityException(String message) {
        super(message);
        this.id = -1L;
    }

    public DataEntityException(String message, Long id) {
        super(message + ": id = " + id);
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
