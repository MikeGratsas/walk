package com.tietoevry.walk.exceptions;

public class ItemNotFoundException extends DataEntityException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2982572866191487755L;

	public ItemNotFoundException(final Long id) {
        super("Item not found", id);
    }
}
