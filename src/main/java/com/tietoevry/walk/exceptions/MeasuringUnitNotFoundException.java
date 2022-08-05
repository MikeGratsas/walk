package com.tietoevry.walk.exceptions;

public class MeasuringUnitNotFoundException extends DataEntityException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1687281532751904252L;

	public MeasuringUnitNotFoundException(final Long id) {
        super("Measuring unit not found", id);
    }
}
