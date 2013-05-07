/*
 * Copyright (c) 2002-2013, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.updatercatalog.business;


/**
 * This is the business class for the object CatalogPlugin
 */
public class CatalogPlugin implements Comparable
{
    // Variables declarations 
    private String _strPluginName;
    private String _strPluginLocale;
    private String _strPluginDescription;
    private String _strPluginAuthor;
    private String _strUrlHomepage;

    /**
     * Returns the PluginName
     * @return The PluginName
     */
    public String getPluginName(  )
    {
        return _strPluginName;
    }

    /**
     * Sets the PluginName
     * @param strPluginName The PluginName
     */
    public void setPluginName( String strPluginName )
    {
        _strPluginName = strPluginName;
    }

    /**
     * Returns the PluginLocale
     * @return The PluginLocale
     */
    public String getPluginLocale(  )
    {
        return _strPluginLocale;
    }

    /**
     * Sets the PluginLocale
     * @param strPluginLocale The PluginLocale
     */
    public void setPluginLocale( String strPluginLocale )
    {
        _strPluginLocale = strPluginLocale;
    }

    /**
     * Returns the PluginDescription
     * @return The PluginDescription
     */
    public String getPluginDescription(  )
    {
        return _strPluginDescription;
    }

    /**
     * Sets the PluginDescription
     * @param strPluginDescription The PluginDescription
     */
    public void setPluginDescription( String strPluginDescription )
    {
        _strPluginDescription = strPluginDescription;
    }

    /**
     * Returns the PluginAuthor
     * @return The PluginAuthor
     */
    public String getPluginAuthor(  )
    {
        return _strPluginAuthor;
    }

    /**
     * Sets the PluginAuthor
     * @param strPluginAuthor The PluginAuthor
     */
    public void setPluginAuthor( String strPluginAuthor )
    {
        _strPluginAuthor = strPluginAuthor;
    }

    /**
     * Returns the UrlHomepage
     * @return The UrlHomepage
     */
    public String getUrlHomepage(  )
    {
        return _strUrlHomepage;
    }

    /**
     * Sets the UrlHomepage
     * @param strUrlHomepage The UrlHomepage
     */
    public void setUrlHomepage( String strUrlHomepage )
    {
        _strUrlHomepage = strUrlHomepage;
    }

    /**
     * Compare implementation to sort plugins by their name
     * @param o Object to compare
     * @return The comparaison
     */
    public int compareTo( Object o )
    {
        CatalogPlugin plugin = (CatalogPlugin) o;

        return _strPluginName.compareTo( plugin.getPluginName(  ) );
    }
}
