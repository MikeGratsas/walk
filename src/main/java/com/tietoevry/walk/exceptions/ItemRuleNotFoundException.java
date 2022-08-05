package com.tietoevry.walk.exceptions;

public class ItemRuleNotFoundException extends DataEntityException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6671757395683929659L;

	public ItemRuleNotFoundException(final Long id) {
        super("Item rule not found", id);
    }
}
