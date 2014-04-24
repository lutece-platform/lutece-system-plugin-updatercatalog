/*
 * Copyright (c) 2002-2014, Mairie de Paris
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
 * This is the business class for the object Catalog
 */
public class Catalog
{
    // Variables declarations 
    private int _nIdCatalog;
    private String _strCatalogLocale;
    private String _strCatalogName;
    private String _strCatalogDescription;
    private String _strOutputFilename;
    private String _strUrlRepository;

    /**
     * Returns the IdCatalog
     * @return The IdCatalog
     */
    public int getId(  )
    {
        return _nIdCatalog;
    }

    /**
     * Sets the IdCatalog
     * @param nIdCatalog The IdCatalog
     */
    public void setId( int nIdCatalog )
    {
        _nIdCatalog = nIdCatalog;
    }

    /**
     * Returns the Catalog's Locale
     * @return The Catalog's Locale
     */
    public String getLocale(  )
    {
        return _strCatalogLocale;
    }

    /**
     * Sets the CatalogLocale
     * @param strCatalogLocale The CatalogLocale
     */
    public void setLocale( String strCatalogLocale )
    {
        _strCatalogLocale = strCatalogLocale;
    }

    /**
     * Returns the CatalogName
     * @return The CatalogName
     */
    public String getName(  )
    {
        return _strCatalogName;
    }

    /**
     * Sets the CatalogName
     * @param strCatalogName The CatalogName
     */
    public void setName( String strCatalogName )
    {
        _strCatalogName = strCatalogName;
    }

    /**
     * Returns the CatalogDescription
     * @return The CatalogDescription
     */
    public String getDescription(  )
    {
        return _strCatalogDescription;
    }

    /**
     * Sets the CatalogDescription
     * @param strCatalogDescription The CatalogDescription
     */
    public void setDescription( String strCatalogDescription )
    {
        _strCatalogDescription = strCatalogDescription;
    }

    /**
     * Returns the OutputFilename
     * @return The OutputFilename
     */
    public String getOutputFilename(  )
    {
        return _strOutputFilename;
    }

    /**
     * Sets the OutputFilename
     * @param strOutputFilename The OutputFilename
     */
    public void setOutputFilename( String strOutputFilename )
    {
        _strOutputFilename = strOutputFilename;
    }

    /**
     * Returns the UrlRepository
     * @return The UrlRepository
     */
    public String getUrlRepository(  )
    {
        return _strUrlRepository;
    }

    /**
     * Sets the UrlRepository
     * @param strUrlRepository The UrlRepository
     */
    public void setUrlRepository( String strUrlRepository )
    {
        _strUrlRepository = strUrlRepository;
    }
}
