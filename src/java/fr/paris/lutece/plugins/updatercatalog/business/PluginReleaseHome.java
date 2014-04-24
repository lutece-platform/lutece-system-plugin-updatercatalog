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
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;

import java.util.List;


/**
 * This class provides instances management methods (create, find, ...) for PluginRelease objects
 */
public final class PluginReleaseHome
{
    // Static variable pointed at the DAO instance
    private static IPluginReleaseDAO _dao = (IPluginReleaseDAO) SpringContextService.getPluginBean( "updatercatalog",
            "updatercatalog.pluginReleaseDAO" );
    private static Plugin _plugin = PluginService.getPlugin( "updatercatalog" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private PluginReleaseHome(  )
    {
    }

    /**
     * Create an instance of the pluginRelease class
     * @param pluginRelease The instance of the PluginRelease which contains the informations to store
     * @return The  instance of pluginRelease which has been created with its primary key.
     */
    public static PluginRelease create( PluginRelease pluginRelease )
    {
        _dao.insert( pluginRelease, _plugin );

        return pluginRelease;
    }

    /**
     * Update of the pluginRelease data specified in parameter
     * @param pluginRelease The instance of the PluginRelease which contains the data to store
     * @return The instance of the  pluginRelease which has been updated
     */
    public static PluginRelease update( PluginRelease pluginRelease )
    {
        _dao.store( pluginRelease, _plugin );

        return pluginRelease;
    }

    /**
     * Remove the pluginRelease whose identifier is specified in parameter
     * @param nPluginReleaseId The pluginRelease Id
     */
    public static void remove( int nPluginReleaseId )
    {
        _dao.delete( nPluginReleaseId, _plugin );
    }

    /**
     * Add an upgrade to a release
     * @param nReleaseId The identifier of the pluginRelease
     * @param upgrade The upgrade
     */
    public static void addUpgrade( int nReleaseId, ReleaseUpgrade upgrade )
    {
        _dao.addUpgrade( nReleaseId, upgrade, _plugin );
    }

    /**
     * Delete an update from a release
     * @param nReleaseId The identifier of the pluginRelease
     * @param strVersionFrom The version
     */
    public static void deleteUpgrade( int nReleaseId, String strVersionFrom )
    {
        _dao.deleteUpgrade( nReleaseId, strVersionFrom, _plugin );
    }

    ///////////////////////////////////////////////////////////////////////////
    // Finders

    /**
     * Returns an instance of a pluginRelease whose identifier is specified in parameter
     * @param nKey The pluginRelease primary key
     * @return an instance of PluginRelease
     */
    public static PluginRelease findByPrimaryKey( int nKey )
    {
        return _dao.load( nKey, _plugin );
    }

    /**
     * Load the data of all the pluginRelease objects and returns them in form of a List
     * @return the list which contains the data of all the pluginRelease objects
     */
    public static List<PluginRelease> findAll(  )
    {
        return _dao.selectPluginReleasesList( _plugin );
    }
}
