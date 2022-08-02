package com.tietoevry.walk.exceptions;

public class ItemUpdatedException extends DataEntityException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -860179283208386034L;

	public ItemUpdatedException(Long id) {
        super("Item was updated since last read", id);
    }
}
