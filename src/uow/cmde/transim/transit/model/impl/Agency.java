package uow.cmde.transim.transit.model.impl;

import uow.cmde.transim.transit.model.IAgency;
/**
 * 
 * @author Vu The Tran
 * @since 12/11/2011
 */
public class Agency implements IAgency {
	
	/**
	 * uniquely identifies a transit agency
	 */
	private int agencyID;
	
	/**
	 * contains the full name of the transit agency
	 */
	private String agencyName;
	
	/**
	 * contains the URL of the transit agency
	 */
	private String agencyUrl;
	
	/**
	 * contains the timezone where the transit agency is located
	 */
	private String agencyTimezone;
	
	/**
	 * ontains a two-letter ISO 639-1 code for the primary language used by this transit agency
	 */
	private String agencyLang;
	
	/**
	 * contains a single voice telephone number for the specified agency
	 */
	private String agencyPhone;
	
	/**
	 * specifies the URL of a web page that allows a rider to purchase tickets or other fare instruments for that agency online
	 */
	private String agencyFareRrl;
}
