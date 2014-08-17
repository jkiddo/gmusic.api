/*******************************************************************************
 * Copyright (c) 2012 Jens Kristian Villadsen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 *
 * Contributors:
 *     Jens Kristian Villadsen - initial API and implementation
 ******************************************************************************/
package gmusic.api.model;

/**
 * Query response
 */
public class QueryResponse
{

    private QueryResults results;

    public QueryResults getResults()
    {
        return results;
    }

    public void setResults(final QueryResults results)
    {
        this.results = results;
    }

}
