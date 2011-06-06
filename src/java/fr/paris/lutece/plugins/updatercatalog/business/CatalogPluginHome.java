/*
 * Copyright (c) 2002-2011, Mairie de Paris
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
import fr.paris.lutece.util.ReferenceList;

import java.util.List;


/**
 * This class provides instances management methods (create, find, ...) for CatalogPlugin objects
 */
public final class CatalogPluginHome
{
    // Static variable pointed at the DAO instance
    private static ICatalogPluginDAO _dao = (ICatalogPluginDAO) SpringContextService.getPluginBean( "updatercatalog",
            "updatercatalog.catalogPluginDAO" );
    private static Plugin _plugin = PluginService.getPlugin( "updatercatalog" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private CatalogPluginHome(  )
    {
    }

    /**
     * Create an instance of the catalogPlugin class
     * @param catalogPlugin The instance of the CatalogPlugin which contains the informations to store
     * @return The  instance of catalogPlugin which has been created with its primary key.
     */
    public static CatalogPlugin create( CatalogPlugin catalogPlugin )
    {
        _dao.insert( catalogPlugin, _plugin );

        return catalogPlugin;
    }

    /**
     * Update of the catalogPlugin which is specified in parameter
     * @param catalogPlugin The instance of the CatalogPlugin which contains the data to store
     * @return The instance of the  catalogPlugin which has been updated
     */
    public static CatalogPlugin update( CatalogPlugin catalogPlugin )
    {
        _dao.store( catalogPlugin, _plugin );

        return catalogPlugin;
    }

    /**
     * Remove the catalogPlugin whose identifier is specified in parameter
     * @param strPluginName The catalogPlugin Id
     * @param strLocale The Locale
     */
    public static void remove( String strPluginName, String strLocale )
    {
        _dao.delete( strPluginName, strLocale, _plugin );
    }

    ///////////////////////////////////////////////////////////////////////////
    // Finders
    /**
     * Returns an instance of a catalogPlugin whose identifier is specified in parameter
     * @param strPluginName The catalogPlugin primary key
     * @param strLocale The Locale
     * @return an instance of CatalogPlugin
     */
    public static CatalogPlugin findByPrimaryKey( String strPluginName, String strLocale )
    {
        return _dao.load( strPluginName, strLocale, _plugin );
    }

    /**
     * Load the data of all the catalogPlugin objects and returns them in form of a List
     * @return the List which contains the data of all the catalogPlugin objects
     */
    public static List<CatalogPlugin> findAll(  )
    {
        return _dao.selectCatalogPluginsList( _plugin );
    }

    /**
    * Returns a list of plugins for a given locale
    * @param strLocale The locale as String
    * @return The list of plugins
    */
    public static ReferenceList selectPluginsListByLocale( String strLocale )
    {
        return _dao.selectPluginsListByLocale( _plugin, strLocale );
    }

    /**
     * Returns a list of plugins for a given locale
     * @param strLocale The locale
     * @return The list of plugins
     */
    public static List<CatalogPluginEntry> getAvailablePluginsByLocale( String strLocale )
    {
        return _dao.selectAvailablePluginsByLocale( _plugin, strLocale );
    }

    /**
     * Gets the list of all plugins
     * @return A reference list
     */
    public static ReferenceList getPlugins(  )
    {
        return _dao.getPlugins( _plugin );
    }
}
