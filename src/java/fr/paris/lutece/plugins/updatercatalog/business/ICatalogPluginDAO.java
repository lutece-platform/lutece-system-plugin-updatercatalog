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

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.ReferenceList;

import java.util.List;


/**
 * CatalogPluginDAO Interface
 */
public interface ICatalogPluginDAO
{
    /**
     * Insert a new record in the table.
     * @param catalogPlugin instance of the CatalogPlugin object to inssert
     * @param plugin the Plugin
     */
    void insert( CatalogPlugin catalogPlugin, Plugin plugin );

    /**
     * Update the record in the table
     * @param catalogPlugin the reference of the CatalogPlugin
     * @param plugin the Plugin
     */
    void store( CatalogPlugin catalogPlugin, Plugin plugin );

    /**
     * Delete a record from the table
     * @param strPluginName int identifier of the CatalogPlugin to delete
     * @param strLocale The locale
     * @param plugin the Plugin
     */
    void delete( String strPluginName, String strLocale, Plugin plugin );

    ///////////////////////////////////////////////////////////////////////////
    // Finders
    /**
     * Load the data from the table
     * @param strPluginName The identifier of the catalogPlugin
     * @param strLocale The locale
     * @param plugin the Plugin
     * @return The instance of the catalogPlugin
     */
    CatalogPlugin load( String strPluginName, String strLocale, Plugin plugin );

    /**
     * Load the data of all the catalogPlugin objects and returns them as a List
     * @param plugin the Plugin
     * @return The List which contains the data of all the catalogPlugin objects
     */
    List<CatalogPlugin> selectCatalogPluginsList( Plugin plugin );

    /**
     * Returns a list of plugins for a given locale
     * @param plugin The plugin
     * @param strLocale The locale as String
     * @return The list of plugins
     */
    ReferenceList selectPluginsListByLocale( Plugin plugin, String strLocale );

    /**
     * Returns a list of plugins for a given locale
     * @param plugin The plugin
     * @param strLocale The locale
     * @return The list of plugins
     */
    List<CatalogPluginEntry> selectAvailablePluginsByLocale( Plugin plugin, String strLocale );

    /**
     * Gets the list of all plugins
     * @param plugin the plugin
     * @return A reference list
     */
    ReferenceList getPlugins( Plugin plugin );
}
