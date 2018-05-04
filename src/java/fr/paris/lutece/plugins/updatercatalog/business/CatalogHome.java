/*
 * Copyright (c) 2002-2017, Mairie de Paris
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

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;

import java.util.List;


/**
 * This class provides instances management methods (create, find, ...) for Catalog objects
 */
public final class CatalogHome
{
    // Static variable pointed at the DAO instance
    private static ICatalogDAO _dao = (ICatalogDAO) SpringContextService.getPluginBean( "updatercatalog", "updatercatalog.catalogDAO" );
    private static Plugin _plugin = PluginService.getPlugin( "updatercatalog" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private CatalogHome(  )
    {
    }

    /**
     * Create an instance of the catalog class
     * @param catalog The instance of the Catalog which contains the informations to store
     * @return The  instance of catalog which has been created with its primary key.
     */
    public static Catalog create( Catalog catalog )
    {
        _dao.insert( catalog, _plugin );

        return catalog;
    }

    /**
     * Update of the catalog which is specified in parameter
     * @param catalog The instance of the Catalog which contains the data to store
     * @return The instance of the  catalog which has been updated
     */
    public static Catalog update( Catalog catalog )
    {
        _dao.store( catalog, _plugin );

        return catalog;
    }

    /**
     * Remove the catalog whose identifier is specified in parameter
     * @param nCatalogId The catalog Id
     */
    public static void remove( int nCatalogId )
    {
        _dao.delete( nCatalogId, _plugin );
    }

    ///////////////////////////////////////////////////////////////////////////
    // Finders
    /**
     * Returns an instance of a catalog whose identifier is specified in parameter
     * @param nKey The catalog primary key
     * @return an instance of Catalog
     */
    public static Catalog findByPrimaryKey( int nKey )
    {
        return _dao.load( nKey, _plugin );
    }

    /**
     * Load the data of all the catalog objects and returns them in form of a List
     * @return the List which contains the data of all the catalog objects
     */
    public static List<Catalog> findAll(  )
    {
        return _dao.selectCatalogsList( _plugin );
    }

    /**
     * Load plugins entries
     * @param nCatalogId Catalog Id
     * @return The List which contains the data of all the catalogs
     */
    public static List<CatalogPluginEntry> getPluginsEntries( int nCatalogId )
    {
        return _dao.selectPluginsEntries( nCatalogId, _plugin );
    }

    /**
     * Add a plugin release to a given catalog
     * @param nCatalogId The catalog ID
     * @param nReleaseId The Release Id
     */
    public static void addRelease( int nCatalogId, int nReleaseId )
    {
        _dao.addRelease( nCatalogId, nReleaseId, _plugin );
    }

    /**
     * Remove a plugin release from a given catalog
     * @param nCatalogId The catalog ID
     * @param nReleaseId The Release Id
     */
    public static void removeRelease( int nCatalogId, int nReleaseId )
    {
        _dao.removeRelease( nCatalogId, nReleaseId, _plugin );
    }
}
