package com.tietoevry.walk.exceptions;

public class RuleNotFoundException extends DataEntityException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6718362009534919289L;

	public RuleNotFoundException(Long id) {
        super("Rule not found", id);
    }

	public RuleNotFoundException(String ruleName) {
        super("Rule not found: " + ruleName);
	}
}
