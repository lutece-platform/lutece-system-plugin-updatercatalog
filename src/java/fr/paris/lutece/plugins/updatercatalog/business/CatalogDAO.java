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
import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * This class provides Data Access methods for Catalog objects
 */
public final class CatalogDAO implements ICatalogDAO
{
    // Constants
    private static final String SQL_QUERY_NEW_PK = "SELECT max( id_catalog ) FROM updatercatalog_catalog";
    private static final String SQL_QUERY_SELECT = "SELECT id_catalog, catalog_locale, catalog_name, catalog_description, output_filename, url_repository FROM updatercatalog_catalog WHERE id_catalog = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO updatercatalog_catalog ( id_catalog, catalog_locale, catalog_name, catalog_description, output_filename, url_repository ) VALUES ( ?, ?, ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM updatercatalog_catalog WHERE id_catalog = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE updatercatalog_catalog SET id_catalog = ?, catalog_locale = ?, catalog_name = ?, catalog_description = ?, output_filename = ?, url_repository = ? WHERE id_catalog = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_catalog, catalog_locale, catalog_name, catalog_description, output_filename, url_repository FROM updatercatalog_catalog";
    private static final String SQL_QUERY_SELECT_PLUGIN_ENTRIES = "SELECT b.id_release , a.plugin_name, a.plugin_description, a.plugin_author, " +
        "b.plugin_version, b.url_download, a.url_homepage, b.core_version_min , b.core_version_max  " +
        "FROM updatercatalog_plugin a, updatercatalog_plugin_release b, updatercatalog_catalog_plugin_release c, updatercatalog_catalog d " +
        "WHERE a.plugin_name = b.plugin_name AND d.catalog_locale = a.plugin_locale " +
        "AND b.id_release = c.id_release AND c.id_catalog = d.id_catalog AND d.id_catalog = ?";
    private static final String SQL_QUERY_INSERT_RELEASE = "INSERT INTO updatercatalog_catalog_plugin_release ( id_catalog , id_release ) VALUES ( ? , ? ) ";
    private static final String SQL_QUERY_DELETE_RELEASE = "DELETE FROM updatercatalog_catalog_plugin_release WHERE id_catalog = ? AND id_release = ? ";
    private static final String SQL_QUERY_SELECT_UPGRADES = "SELECT version_from , critical_upgrade , url_download  FROM updatercatalog_plugin_upgrade WHERE id_release = ? ";

    /**
     * Generates a new primary key
     * @param plugin The Plugin
     * @return The new primary key
     */
    public int newPrimaryKey( Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_NEW_PK, plugin );
        daoUtil.executeQuery(  );

        int nKey;

        if ( !daoUtil.next(  ) )
        {
            // if the table is empty
            nKey = 1;
        }

        nKey = daoUtil.getInt( 1 ) + 1;
        daoUtil.free(  );

        return nKey;
    }

    /**
     * Insert a new record in the table.
     * @param catalog instance of the Catalog object to insert
     * @param plugin The plugin
     */
    public void insert( Catalog catalog, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );

        catalog.setId( newPrimaryKey( plugin ) );

        daoUtil.setInt( 1, catalog.getId(  ) );
        daoUtil.setString( 2, catalog.getLocale(  ) );
        daoUtil.setString( 3, catalog.getName(  ) );
        daoUtil.setString( 4, catalog.getDescription(  ) );
        daoUtil.setString( 5, catalog.getOutputFilename(  ) );
        daoUtil.setString( 6, catalog.getUrlRepository(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Load the data of the catalog from the table
     * @param nId The identifier of the catalog
     * @param plugin The plugin
     * @return the instance of the Catalog
     */
    public Catalog load( int nId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nId );
        daoUtil.executeQuery(  );

        Catalog catalog = null;

        if ( daoUtil.next(  ) )
        {
            catalog = new Catalog(  );

            catalog.setId( daoUtil.getInt( 1 ) );
            catalog.setLocale( daoUtil.getString( 2 ) );
            catalog.setName( daoUtil.getString( 3 ) );
            catalog.setDescription( daoUtil.getString( 4 ) );
            catalog.setOutputFilename( daoUtil.getString( 5 ) );
            catalog.setUrlRepository( daoUtil.getString( 6 ) );
        }

        daoUtil.free(  );

        return catalog;
    }

    /**
     * Delete a record from the table
     * @param nCatalogId The identifier of the catalog
     * @param plugin The plugin
     */
    public void delete( int nCatalogId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setInt( 1, nCatalogId );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Update the record in the table
     * @param catalog The reference of the catalog
     * @param plugin The plugin
     */
    public void store( Catalog catalog, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );

        daoUtil.setInt( 1, catalog.getId(  ) );
        daoUtil.setString( 2, catalog.getLocale(  ) );
        daoUtil.setString( 3, catalog.getName(  ) );
        daoUtil.setString( 4, catalog.getDescription(  ) );
        daoUtil.setString( 5, catalog.getOutputFilename(  ) );
        daoUtil.setString( 6, catalog.getUrlRepository(  ) );
        daoUtil.setInt( 7, catalog.getId(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Load the data of all the catalogs and returns them as a List
     * @param plugin The plugin
     * @return The List which contains the data of all the catalogs
     */
    public List<Catalog> selectCatalogsList( Plugin plugin )
    {
        List<Catalog> catalogList = new ArrayList<Catalog>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            Catalog catalog = new Catalog(  );

            catalog.setId( daoUtil.getInt( 1 ) );
            catalog.setLocale( daoUtil.getString( 2 ) );
            catalog.setName( daoUtil.getString( 3 ) );
            catalog.setDescription( daoUtil.getString( 4 ) );
            catalog.setOutputFilename( daoUtil.getString( 5 ) );
            catalog.setUrlRepository( daoUtil.getString( 6 ) );

            catalogList.add( catalog );
        }

        daoUtil.free(  );

        return catalogList;
    }

    /**
     * Load plugins entries
     * @param nCatalogId Catalog Id
     * @param plugin The plugin
     * @return The List which contains the data of all the catalogs
     */
    public List<CatalogPluginEntry> selectPluginsEntries( int nCatalogId, Plugin plugin )
    {
        List<CatalogPluginEntry> list = new ArrayList<CatalogPluginEntry>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_PLUGIN_ENTRIES, plugin );
        daoUtil.setInt( 1, nCatalogId );
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
            entry.setCoreVersionMin( daoUtil.getString( 8 ) );
            entry.setCoreVersionMax( daoUtil.getString( 9 ) );

            // load upgrades
            loadUpgrades( entry, plugin );

            list.add( entry );
        }

        daoUtil.free(  );

        return list;
    }

    /**
     * Load upgrades for an entry
     * @param entry The entry
     * @param plugin The plugin
     */
    private void loadUpgrades( CatalogPluginEntry entry, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_UPGRADES, plugin );
        daoUtil.setInt( 1, entry.getReleaseId(  ) );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            ReleaseUpgrade upgrade = new ReleaseUpgrade(  );

            upgrade.setVersionFrom( daoUtil.getString( 1 ) );
            upgrade.setCritical( daoUtil.getInt( 2 ) );
            upgrade.setUrlDownload( daoUtil.getString( 3 ) );

            entry.addUpgrade( upgrade );
        }

        daoUtil.free(  );
    }

    /**
     * Add a plugin release to a given catalog
     * @param nCatalogId The catalog ID
     * @param nReleaseId The Release Id
     * @param plugin The plugin
     */
    public void addRelease( int nCatalogId, int nReleaseId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT_RELEASE, plugin );

        daoUtil.setInt( 1, nCatalogId );
        daoUtil.setInt( 2, nReleaseId );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Remove a plugin release from a given catalog
     * @param nCatalogId The catalog ID
     * @param nReleaseId The Release Id
     * @param plugin The plugin
     */
    public void removeRelease( int nCatalogId, int nReleaseId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_RELEASE, plugin );

        daoUtil.setInt( 1, nCatalogId );
        daoUtil.setInt( 2, nReleaseId );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }
}
