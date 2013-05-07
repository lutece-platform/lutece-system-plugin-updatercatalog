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

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * This class provides Data Access methods for CatalogPlugin objects
 */
public final class CatalogPluginDAO implements ICatalogPluginDAO
{
    // Constants
    private static final String SQL_QUERY_SELECT = "SELECT plugin_name, plugin_locale, plugin_description, plugin_author, url_homepage FROM updatercatalog_plugin WHERE plugin_name = ? AND plugin_locale = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO updatercatalog_plugin ( plugin_name, plugin_locale, plugin_description, plugin_author, url_homepage ) VALUES ( ?, ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM updatercatalog_plugin WHERE plugin_name = ?  AND plugin_locale = ?";
    private static final String SQL_QUERY_UPDATE = "UPDATE updatercatalog_plugin SET plugin_name = ?, plugin_locale = ?, plugin_description = ?, plugin_author = ?, url_homepage = ? WHERE plugin_name = ?  AND plugin_locale = ? ";
    private static final String SQL_QUERY_SELECTALL = "SELECT plugin_name, plugin_locale, plugin_description, plugin_author, url_homepage FROM updatercatalog_plugin";
    private static final String SQL_QUERY_SELECT_BY_LOCALE = "SELECT plugin_name, plugin_description FROM updatercatalog_plugin WHERE plugin_locale = ?";
    private static final String SQL_QUERY_SELECT_AVAILABLE_PLUGINS = "SELECT b.id_release , a.plugin_name, a.plugin_description, a.plugin_author, b.plugin_version, b.url_download, a.url_homepage " +
        "FROM updatercatalog_plugin a, updatercatalog_plugin_release b " +
        "WHERE a.plugin_name = b.plugin_name AND a.plugin_locale = ? ";
    private static final String SQL_QUERY_SELECT_PLUGINS = "SELECT DISTINCT plugin_name FROM updatercatalog_plugin";

    /**
     * Insert a new record in the table.
     * @param catalogPlugin instance of the CatalogPlugin object to insert
     * @param plugin The plugin
     */
    public void insert( CatalogPlugin catalogPlugin, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );

        daoUtil.setString( 1, catalogPlugin.getPluginName(  ) );
        daoUtil.setString( 2, catalogPlugin.getPluginLocale(  ) );
        daoUtil.setString( 3, catalogPlugin.getPluginDescription(  ) );
        daoUtil.setString( 4, catalogPlugin.getPluginAuthor(  ) );
        daoUtil.setString( 5, catalogPlugin.getUrlHomepage(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Load the data of the catalogPlugin from the table
     * @param strPluginName The identifier of the catalogPlugin
     * @param plugin The plugin
     * @param strLocale The locale
     * @return the instance of the CatalogPlugin
     */
    public CatalogPlugin load( String strPluginName, String strLocale, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setString( 1, strPluginName );
        daoUtil.setString( 2, strLocale );
        daoUtil.executeQuery(  );

        CatalogPlugin catalogPlugin = null;

        if ( daoUtil.next(  ) )
        {
            catalogPlugin = new CatalogPlugin(  );

            catalogPlugin.setPluginName( daoUtil.getString( 1 ) );
            catalogPlugin.setPluginLocale( daoUtil.getString( 2 ) );
            catalogPlugin.setPluginDescription( daoUtil.getString( 3 ) );
            catalogPlugin.setPluginAuthor( daoUtil.getString( 4 ) );
            catalogPlugin.setUrlHomepage( daoUtil.getString( 5 ) );
        }

        daoUtil.free(  );

        return catalogPlugin;
    }

    /**
     * Delete a record from the table
     * @param strPluginName The identifier of the catalogPlugin
     * @param strLocale The locale
     * @param plugin The plugin
     */
    public void delete( String strPluginName, String strLocale, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setString( 1, strPluginName );
        daoUtil.setString( 2, strLocale );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Update the record in the table
     * @param catalogPlugin The reference of the catalogPlugin
     * @param plugin The plugin
     */
    public void store( CatalogPlugin catalogPlugin, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );

        daoUtil.setString( 1, catalogPlugin.getPluginName(  ) );
        daoUtil.setString( 2, catalogPlugin.getPluginLocale(  ) );
        daoUtil.setString( 3, catalogPlugin.getPluginDescription(  ) );
        daoUtil.setString( 4, catalogPlugin.getPluginAuthor(  ) );
        daoUtil.setString( 5, catalogPlugin.getUrlHomepage(  ) );
        daoUtil.setString( 6, catalogPlugin.getPluginName(  ) );
        daoUtil.setString( 7, catalogPlugin.getPluginLocale(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Load the data of all the catalogPlugins and returns them as a List
     * @param plugin The plugin
     * @return The List which contains the data of all the catalogPlugins
     */
    public List<CatalogPlugin> selectCatalogPluginsList( Plugin plugin )
    {
        List<CatalogPlugin> catalogPluginList = new ArrayList<CatalogPlugin>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            CatalogPlugin catalogPlugin = new CatalogPlugin(  );

            catalogPlugin.setPluginName( daoUtil.getString( 1 ) );
            catalogPlugin.setPluginLocale( daoUtil.getString( 2 ) );
            catalogPlugin.setPluginDescription( daoUtil.getString( 3 ) );
            catalogPlugin.setPluginAuthor( daoUtil.getString( 4 ) );
            catalogPlugin.setUrlHomepage( daoUtil.getString( 5 ) );

            catalogPluginList.add( catalogPlugin );
        }

        daoUtil.free(  );

        return catalogPluginList;
    }

    /**
     * Returns a list of plugins for a given locale
     * @param plugin The plugin
     * @param strLocale The locale as String
     * @return The list of plugins
     */
    public ReferenceList selectPluginsListByLocale( Plugin plugin, String strLocale )
    {
        ReferenceList list = new ReferenceList(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_LOCALE, plugin );
        daoUtil.setString( 1, strLocale );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            list.addItem( daoUtil.getString( 1 ), daoUtil.getString( 2 ) );
        }

        daoUtil.free(  );

        return list;
    }

    /**
     * Select available plugins for a given locale
     * @param plugin The plugin
     * @param strLocale The locale
     * @return A list of plugins entries
     */
    public List<CatalogPluginEntry> selectAvailablePluginsByLocale( Plugin plugin, String strLocale )
    {
        List<CatalogPluginEntry> list = new ArrayList<CatalogPluginEntry>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_AVAILABLE_PLUGINS, plugin );
        daoUtil.setString( 1, strLocale );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            CatalogPluginEntry entry = new CatalogPluginEntry(  );

            entry.setReleaseId( daoUtil.getInt( 1 ) );
            entry.setPluginName( daoUtil.getString( 2 ) );
            entry.setPluginDescription( daoUtil.getString( 3 ) );
            entry.setPluginAuthor( daoUtil.getString( 4 ) );
            entry.setPluginVersion( daoUtil.getString( 5 ) );
            entry.setUrlDownload( daoUtil.getString( 6 ) );
            entry.setUrlHomepage( daoUtil.getString( 7 ) );

            list.add( entry );
        }

        daoUtil.free(  );

        return list;
    }

    /**
     * Gets the plugins list
     * @param plugin the plugin
     * @return A reference list
     */
    public ReferenceList getPlugins( Plugin plugin )
    {
        ReferenceList list = new ReferenceList(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_PLUGINS, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            String strPluginName = daoUtil.getString( 1 );
            list.addItem( strPluginName, strPluginName );
        }

        daoUtil.free(  );

        return list;
    }
}
