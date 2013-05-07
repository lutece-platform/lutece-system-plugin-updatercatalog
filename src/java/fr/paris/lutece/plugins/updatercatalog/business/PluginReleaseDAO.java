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
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.util.sql.DAOUtil;
import fr.paris.lutece.util.sql.Transaction;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


/**
 * This class provides Data Access methods for PluginRelease objects
 */
public final class PluginReleaseDAO implements IPluginReleaseDAO
{
    // Constants
    private static final String SQL_QUERY_NEW_PK = "SELECT max( id_release ) FROM updatercatalog_plugin_release";
    private static final String SQL_QUERY_SELECT = "SELECT id_release, plugin_name, plugin_version, url_download, core_version_min, core_version_max FROM updatercatalog_plugin_release WHERE id_release = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO updatercatalog_plugin_release ( id_release, plugin_name, plugin_version, url_download, core_version_min, core_version_max ) VALUES ( ?, ?, ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM updatercatalog_plugin_release WHERE id_release = ? ";
    private static final String SQL_QUERY_DELETE_UPGRADES = "DELETE FROM updatercatalog_plugin_upgrade WHERE id_release = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE updatercatalog_plugin_release SET id_release = ?, plugin_name = ?, plugin_version = ?, url_download = ?, core_version_min = ?, core_version_max = ? WHERE id_release = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_release, plugin_name, plugin_version, url_download, core_version_min, core_version_max FROM updatercatalog_plugin_release";
    private static final String SQL_INSERT_UPGRADE = "INSERT INTO updatercatalog_plugin_upgrade ( id_release , version_from , critical_upgrade , url_download ) VALUES( ? , ? , ? , ? )";
    private static final String SQL_DELETE_UPGRADE = "DELETE FROM updatercatalog_plugin_upgrade WHERE id_release = ? AND version_from = ? ";
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
     * @param pluginRelease instance of the PluginRelease object to insert
     * @param plugin The plugin
     */
    public void insert( PluginRelease pluginRelease, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );

        pluginRelease.setId( newPrimaryKey( plugin ) );

        daoUtil.setInt( 1, pluginRelease.getId(  ) );
        daoUtil.setString( 2, pluginRelease.getPluginName(  ) );
        daoUtil.setString( 3, pluginRelease.getPluginVersion(  ) );
        daoUtil.setString( 4, pluginRelease.getUrlDownload(  ) );
        daoUtil.setString( 5, pluginRelease.getCoreVersionMin(  ) );
        daoUtil.setString( 6, pluginRelease.getCoreVersionMax(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Load the data of the pluginRelease from the table
     * @param nId The identifier of the pluginRelease
     * @param plugin The plugin
     * @return the instance of the PluginRelease
     */
    public PluginRelease load( int nId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nId );
        daoUtil.executeQuery(  );

        PluginRelease release = null;

        if ( daoUtil.next(  ) )
        {
            release = new PluginRelease(  );

            release.setId( daoUtil.getInt( 1 ) );
            release.setPluginName( daoUtil.getString( 2 ) );
            release.setPluginVersion( daoUtil.getString( 3 ) );
            release.setUrlDownload( daoUtil.getString( 4 ) );
            release.setCoreVersionMin( daoUtil.getString( 5 ) );
            release.setCoreVersionMax( daoUtil.getString( 6 ) );
        }

        daoUtil.free(  );

        if ( release != null )
        {
            loadUpgrades( release, plugin );
        }

        return release;
    }

    /**
     * Delete a record from the table
     * @param nPluginReleaseId The identifier of the pluginRelease
     * @param plugin The plugin
     */
    public void delete( int nPluginReleaseId, Plugin plugin )
    {
        Transaction transaction = new Transaction(  );

        try
        {
            transaction.prepareStatement( SQL_QUERY_DELETE_UPGRADES );
            transaction.getStatement(  ).setInt( 1, nPluginReleaseId );
            transaction.executeStatement(  );

            transaction.prepareStatement( SQL_QUERY_DELETE );
            transaction.getStatement(  ).setInt( 1, nPluginReleaseId );
            transaction.executeStatement(  );

            transaction.commit(  );
        }
        catch ( SQLException e )
        {
            transaction.rollback( e );
            AppLogService.error( "Error deleting Release " + e.getMessage(  ), e.getCause(  ) );
        }
    }

    /**
     * Update the record in the table
     * @param pluginRelease The reference of the pluginRelease
     * @param plugin The plugin
     */
    public void store( PluginRelease pluginRelease, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );

        daoUtil.setInt( 1, pluginRelease.getId(  ) );
        daoUtil.setString( 2, pluginRelease.getPluginName(  ) );
        daoUtil.setString( 3, pluginRelease.getPluginVersion(  ) );
        daoUtil.setString( 4, pluginRelease.getUrlDownload(  ) );
        daoUtil.setString( 5, pluginRelease.getCoreVersionMin(  ) );
        daoUtil.setString( 6, pluginRelease.getCoreVersionMax(  ) );
        daoUtil.setInt( 7, pluginRelease.getId(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Load the data of all the pluginReleases and returns them as a List
     * @param plugin The plugin
     * @return The List which contains the data of all the pluginReleases
     */
    public List<PluginRelease> selectPluginReleasesList( Plugin plugin )
    {
        List<PluginRelease> pluginReleaseList = new ArrayList<PluginRelease>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            PluginRelease pluginRelease = new PluginRelease(  );

            pluginRelease.setId( daoUtil.getInt( 1 ) );
            pluginRelease.setPluginName( daoUtil.getString( 2 ) );
            pluginRelease.setPluginVersion( daoUtil.getString( 3 ) );
            pluginRelease.setUrlDownload( daoUtil.getString( 4 ) );
            pluginRelease.setCoreVersionMin( daoUtil.getString( 5 ) );
            pluginRelease.setCoreVersionMax( daoUtil.getString( 6 ) );

            pluginReleaseList.add( pluginRelease );
        }

        daoUtil.free(  );

        return pluginReleaseList;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Upgrades

    /**
     * Add an upgrade to a release
     * @param nReleaseId The identifier of the pluginRelease
     * @param upgrade The version
     * @param plugin The plugin
     */
    public void addUpgrade( int nReleaseId, ReleaseUpgrade upgrade, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_INSERT_UPGRADE, plugin );

        daoUtil.setInt( 1, nReleaseId );
        daoUtil.setString( 2, upgrade.getVersionFrom(  ) );
        daoUtil.setInt( 3, upgrade.getCritical(  ) );
        daoUtil.setString( 4, upgrade.getUrlDownload(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Delete an update from a release
     * @param nReleaseId The identifier of the pluginRelease
     * @param strVersionFrom The version
     * @param plugin The plugin
     */
    public void deleteUpgrade( int nReleaseId, String strVersionFrom, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_DELETE_UPGRADE, plugin );
        daoUtil.setInt( 1, nReleaseId );
        daoUtil.setString( 2, strVersionFrom );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Load upgrades for a given release
     * @param release The release
     * @param plugin The plugin
     */
    private void loadUpgrades( PluginRelease release, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_UPGRADES, plugin );
        daoUtil.setInt( 1, release.getId(  ) );
        daoUtil.executeQuery(  );

        release.getUpgrades(  ).clear(  );

        while ( daoUtil.next(  ) )
        {
            ReleaseUpgrade upgrade = new ReleaseUpgrade(  );

            upgrade.setVersionFrom( daoUtil.getString( 1 ) );
            upgrade.setCritical( daoUtil.getInt( 2 ) );
            upgrade.setUrlDownload( daoUtil.getString( 3 ) );

            release.addUpgrade( upgrade );
        }

        daoUtil.free(  );
    }
}
