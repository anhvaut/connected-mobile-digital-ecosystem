/**
 * Opt4J is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * Opt4J is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with Opt4J. If not, see http://www.gnu.org/licenses/. 
 */
package org.opt4j.config;

/**
 * The {@link ModuleAutoFinderListener} is a listener interface for the
 * {@link ModuleAutoFinder}.
 * 
 * @author lukasiewycz
 * 
 */
public interface ModuleAutoFinderListener {

	/**
	 * Invoked for a standard message.
	 * 
	 * @param message
	 *            the message
	 */
	public void out(String message);

	/**
	 * Invoked for an error message.
	 * 
	 * @param message
	 *            the error message
	 */
	public void err(String message);

}