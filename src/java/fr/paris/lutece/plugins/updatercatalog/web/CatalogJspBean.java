/*
 * Copyright (c) 2002-2009, Mairie de Paris
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
package fr.paris.lutece.plugins.updatercatalog.web;

import fr.paris.lutece.plugins.updatercatalog.business.Catalog;
import fr.paris.lutece.plugins.updatercatalog.business.CatalogHome;
import fr.paris.lutece.plugins.updatercatalog.business.CatalogPlugin;
import fr.paris.lutece.plugins.updatercatalog.business.CatalogPluginEntry;
import fr.paris.lutece.plugins.updatercatalog.business.CatalogPluginHome;
import fr.paris.lutece.plugins.updatercatalog.business.PluginRelease;
import fr.paris.lutece.plugins.updatercatalog.business.PluginReleaseHome;
import fr.paris.lutece.plugins.updatercatalog.business.ReleaseUpgrade;
import fr.paris.lutece.plugins.updatercatalog.service.CatalogGenerationService;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.web.admin.PluginAdminPageJspBean;
import fr.paris.lutece.portal.web.constants.Messages;
import fr.paris.lutece.util.html.HtmlTemplate;
import fr.paris.lutece.util.url.UrlItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


/**
 * Catalog Jsp Bean
 */
public class CatalogJspBean extends PluginAdminPageJspBean
{
    /**
     *
     */
    public static final String RIGHT_CATALOG_MANAGEMENT = "UPDATER_CATALOG_MANAGEMENT";

    // Templates
    private static final String TEMPLATE_MANAGE_CATALOG = "admin/plugins/updatercatalog/manage_catalogs.html";
    private static final String TEMPLATE_CREATE_CATALOG = "admin/plugins/updatercatalog/create_catalog.html";
    private static final String TEMPLATE_MODIFY_CATALOG = "admin/plugins/updatercatalog/modify_catalog.html";
    private static final String TEMPLATE_MANAGE_RELEASES = "admin/plugins/updatercatalog/manage_releases.html";
    private static final String TEMPLATE_CREATE_RELEASE = "admin/plugins/updatercatalog/create_release.html";
    private static final String TEMPLATE_MODIFY_RELEASE = "admin/plugins/updatercatalog/modify_release.html";
    private static final String TEMPLATE_MANAGE_PLUGINS = "admin/plugins/updatercatalog/manage_plugins.html";
    private static final String TEMPLATE_CREATE_PLUGIN = "admin/plugins/updatercatalog/create_plugin.html";
    private static final String TEMPLATE_MODIFY_PLUGIN = "admin/plugins/updatercatalog/modify_plugin.html";
    private static final String TEMPLATE_CREATE_UPGRADE = "admin/plugins/updatercatalog/create_upgrade.html";

    // Bookmarks
    private static final String MARK_CATALOGS_LIST = "catalogs_list";
    private static final String MARK_PLUGINS_LIST = "plugins_list";
    private static final String MARK_CATALOG = "catalog";
    private static final String MARK_AVAILABLE_PLUGINS = "available_plugins_list";
    private static final String MARK_RELEASE = "release";
    private static final String MARK_RELEASE_ID = "release_id";
    private static final String MARK_PLUGIN = "plugin";

    // Parameters
    private static final String PARAMETER_CATALOG_ID = "id_catalog";
    private static final String PARAMETER_RELEASE_ID = "id_release";
    private static final String PARAMETER_NAME = "name";
    private static final String PARAMETER_DESCRIPTION = "description";
    private static final String PARAMETER_LOCALE = "locale";
    private static final String PARAMETER_OUTPUT_FILENAME = "output_filename";
    private static final String PARAMETER_URL_REPOSITORY = "url_repository";
    private static final String PARAMETER_VERSION = "version";
    private static final String PARAMETER_URL_DOWNLOAD = "url_download";
    private static final String PARAMETER_CORE_VERSION_MIN = "core_version_min";
    private static final String PARAMETER_CORE_VERSION_MAX = "core_version_max";
    private static final String PARAMETER_AUTHOR = "author";
    private static final String PARAMETER_URL_HOMEPAGE = "url_homepage";
    private static final String PARAMETER_CRITICAL = "critical";

    // JSP
    private static final String JSP_EDIT_CATALOG = "EditCatalog.jsp";
    private static final String JSP_DO_DELETE_CATALOG = "jsp/admin/plugins/updatercatalog/DoDeleteCatalog.jsp";
    private static final String JSP_DO_DELETE_PLUGIN_RELEASE = "jsp/admin/plugins/updatercatalog/DoDeletePluginRelease.jsp";
    private static final String JSP_DO_DELETE_PLUGIN = "jsp/admin/plugins/updatercatalog/DoDeletePlugin.jsp";
    private static final String JSP_DO_DELETE_UPGRADE = "jsp/admin/plugins/updatercatalog/DoDeleteUpgrade.jsp";
    private static final String JSP_MANAGE_PLUGINS_RELEASES = "ManagePluginsReleases.jsp";
    private static final String JSP_MANAGE_PLUGINS = "ManagePlugins.jsp";
    private static final String JSP_MODIFY_PLUGIN_RELEASE = "ModifyPluginRelease.jsp";

    // Messages 
    private static final String MESSAGE_CATALOG_GENERATED = "updatercatalog.message.catalogGenerated";
    private static final String MESSAGE_CONFIRM_DELETE_CATALOG = "updatercatalog.message.confirmDeleteCatalog";
    private static final String MESSAGE_CONFIRM_DELETE_PLUGIN_RELEASE = "updatercatalog.message.confirmDeletePluginRelease";
    private static final String MESSAGE_CONFIRM_DELETE_PLUGIN = "updatercatalog.message.confirmDeletePlugin";
    private static final String MESSAGE_CONFIRM_DELETE_UPGRADE = "updatercatalog.message.confirmDeleteUpgrade";

    /**
     * Provides the manage catalog page
     * @param request The HTTP request
     * @return The page
     */
    public String getManageCatalog( HttpServletRequest request )
    {
        List<Catalog> listCatalogs = CatalogHome.findAll(  );

        HashMap model = new HashMap(  );
        model.put( MARK_CATALOGS_LIST, listCatalogs );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MANAGE_CATALOG, getLocale(  ), model );

        return getAdminPage( template.getHtml(  ) );
    }

    /**
     * Returns The Edit page
     * @param request The HTTP request
     * @return The Edit page
     */
    public String getEditCatalog( HttpServletRequest request )
    {
        String strCatalogId = request.getParameter( PARAMETER_CATALOG_ID );
        int nCatalogId = Integer.parseInt( strCatalogId );

        Catalog catalog = CatalogHome.findByPrimaryKey( nCatalogId );
        List<CatalogPluginEntry> listEntries = CatalogHome.getPluginsEntries( nCatalogId );
        List<CatalogPluginEntry> listPlugins = CatalogPluginHome.getAvailablePluginsByLocale( catalog.getLocale(  ) );
        listPlugins = filterAlreadyInCatalog( listEntries, listPlugins );

        HashMap model = new HashMap(  );
        model.put( MARK_CATALOG, catalog );
        model.put( MARK_PLUGINS_LIST, listEntries );
        model.put( MARK_AVAILABLE_PLUGINS, listPlugins );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MODIFY_CATALOG, getLocale(  ), model );

        return getAdminPage( template.getHtml(  ) );
    }

    /**
     * Remove from the list all plugin already in the catalog
     * @param listInCatalog The catalog's list of plugins
     * @param listAll The list of all plugins
     * @return The filtered list
     */
    private List<CatalogPluginEntry> filterAlreadyInCatalog( List<CatalogPluginEntry> listInCatalog,
        List<CatalogPluginEntry> listAll )
    {
        List<CatalogPluginEntry> list = new ArrayList<CatalogPluginEntry>(  );

        for ( CatalogPluginEntry entry : listAll )
        {
            if ( !isInCatalog( entry.getPluginName(  ), listInCatalog ) )
            {
                list.add( entry );
            }
        }

        return list;
    }

    /**
     * Tells weither the plugin is in the catalog or not
     * @param strPluginName The plugin name
     * @param listInCatalog The catalog's list of plugins
     * @return true if the plugin is already in the catalog, otherwise false
     */
    private boolean isInCatalog( String strPluginName, List<CatalogPluginEntry> listInCatalog )
    {
        for ( CatalogPluginEntry entry : listInCatalog )
        {
            if ( entry.getPluginName(  ).equals( strPluginName ) )
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Add a release of a plugin in the catalog
     * @param request The HTTP request
     * @return The forward url
     */
    public String doCatalogAddRelease( HttpServletRequest request )
    {
        String strCatalogId = request.getParameter( PARAMETER_CATALOG_ID );
        int nCatalogId = Integer.parseInt( strCatalogId );
        String[] releases = request.getParameterValues( PARAMETER_RELEASE_ID );

        for ( int i = 0; i < releases.length; i++ )
        {
            int nReleaseId = Integer.parseInt( releases[i] );
            CatalogHome.addRelease( nCatalogId, nReleaseId );
        }

        UrlItem url = new UrlItem( JSP_EDIT_CATALOG );
        url.addParameter( PARAMETER_CATALOG_ID, strCatalogId );

        return url.getUrl(  );
    }

    /**
     * Remove a plugin release from the catalog
     * @param request The HTTP request
     * @return The forward url
     */
    public String doCatalogRemoveRelease( HttpServletRequest request )
    {
        String strCatalogId = request.getParameter( PARAMETER_CATALOG_ID );
        int nCatalogId = Integer.parseInt( strCatalogId );
        String strReleaseId = request.getParameter( PARAMETER_RELEASE_ID );
        int nReleaseId = Integer.parseInt( strReleaseId );
        CatalogHome.removeRelease( nCatalogId, nReleaseId );

        UrlItem url = new UrlItem( JSP_EDIT_CATALOG );
        url.addParameter( PARAMETER_CATALOG_ID, strCatalogId );

        return url.getUrl(  );
    }

    /**
     * Generate a catalog file
     * @param request The HTTP request
     * @return The forward url
     */
    public String doGenerateCatalog( HttpServletRequest request )
    {
        String strCatalogId = request.getParameter( PARAMETER_CATALOG_ID );
        int nCatalogId = Integer.parseInt( strCatalogId );

        CatalogGenerationService.generateCatalog( nCatalogId );

        return AdminMessageService.getMessageUrl( request, MESSAGE_CATALOG_GENERATED, getHomeUrl( request ),
            AdminMessage.TYPE_INFO );
    }

    /**
     * Provides the create catalog page
     * @param request The HTTP request
     * @return The page
     */
    public String getCreateCatalog( HttpServletRequest request )
    {
        HashMap model = new HashMap(  );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_CREATE_CATALOG, getLocale(  ), model );

        return getAdminPage( template.getHtml(  ) );
    }

    /**
     * Create a new catalog
     * @param request The HTTP request
     * @return the forward url
     */
    public String doCreateCatalog( HttpServletRequest request )
    {
        Catalog catalog = new Catalog(  );
        String strErrorUrl = getData( request, catalog );

        if ( strErrorUrl != null )
        {
            return strErrorUrl;
        }

        CatalogHome.create( catalog );

        return getHomeUrl( request );
    }

    /**
     * Modify catalog's attributes
     * @param request The HTTP request
     * @return The forward url
     */
    public String doModifyCatalog( HttpServletRequest request )
    {
        String strCatalogId = request.getParameter( PARAMETER_CATALOG_ID );
        int nCatalogId = Integer.parseInt( strCatalogId );

        Catalog catalog = CatalogHome.findByPrimaryKey( nCatalogId );
        String strErrorUrl = getData( request, catalog );

        if ( strErrorUrl != null )
        {
            return strErrorUrl;
        }

        CatalogHome.update( catalog );

        return getHomeUrl( request );
    }

    /**
     * Fills catalog infos from the request
     * @param request The HTTP request
     * @param catalog The catalog object to fill
     * @return An ErrorUrl or null if no error
     */
    private String getData( HttpServletRequest request, Catalog catalog )
    {
        String strName = request.getParameter( PARAMETER_NAME );
        String strDescription = request.getParameter( PARAMETER_DESCRIPTION );
        String strLocale = request.getParameter( PARAMETER_LOCALE );
        String strOutputFilename = request.getParameter( PARAMETER_OUTPUT_FILENAME );
        String strUrlRepository = request.getParameter( PARAMETER_URL_REPOSITORY );

        if ( ( strName == null ) || ( strName.equals( "" ) ) || ( strDescription == null ) ||
                ( strDescription.equals( "" ) ) || ( strLocale == null ) || ( strLocale.equals( "" ) ) ||
                ( strOutputFilename == null ) || ( strOutputFilename.equals( "" ) ) || ( strUrlRepository == null ) ||
                ( strUrlRepository.equals( "" ) ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        catalog.setName( strName );
        catalog.setDescription( strDescription );
        catalog.setLocale( strLocale );
        catalog.setOutputFilename( strOutputFilename );
        catalog.setUrlRepository( strUrlRepository );

        return null;
    }

    /**
     * Confirm the catalog deletion
     * @param request The HTTP request
     * @return The forward url
     */
    public String deleteCatalog( HttpServletRequest request )
    {
        String strCatalogId = request.getParameter( PARAMETER_CATALOG_ID );
        UrlItem url = new UrlItem( JSP_DO_DELETE_CATALOG );
        url.addParameter( PARAMETER_CATALOG_ID, strCatalogId );

        return AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_DELETE_CATALOG, url.getUrl(  ),
            AdminMessage.TYPE_CONFIRMATION );
    }

    /**
     * Delete the catalog
     * @param request The HTTP request
     * @return The forward url
     */
    public String doDeleteCatalog( HttpServletRequest request )
    {
        String strCatalogId = request.getParameter( PARAMETER_CATALOG_ID );
        int nCatalogId = Integer.parseInt( strCatalogId );

        CatalogHome.remove( nCatalogId );

        return getHomeUrl( request );
    }

    /**
     * Provides the manage catalog page
     * @param request The HTTP request
     * @return The page
     */
    public String getManagePluginsReleases( HttpServletRequest request )
    {
        List<PluginRelease> listPlugins = PluginReleaseHome.findAll(  );

        Collections.sort( listPlugins );

        HashMap model = new HashMap(  );
        model.put( MARK_PLUGINS_LIST, listPlugins );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MANAGE_RELEASES, getLocale(  ), model );

        return getAdminPage( template.getHtml(  ) );
    }

    /**
     * Provides the create plugin release page
     * @param request The HTTP request
     * @return The page
     */
    public String getCreatePluginRelease( HttpServletRequest request )
    {
        HashMap model = new HashMap(  );

        String strPluginName = request.getParameter( PARAMETER_NAME );
        strPluginName = ( strPluginName == null ) ? "" : strPluginName;

        model.put( MARK_PLUGIN, strPluginName );
        model.put( MARK_PLUGINS_LIST, CatalogPluginHome.getPlugins(  ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_CREATE_RELEASE, getLocale(  ), model );

        return getAdminPage( template.getHtml(  ) );
    }

    /**
     * Create a new plugin release
     * @param request The HTTP request
     * @return the forward url
     */
    public String doCreatePluginRelease( HttpServletRequest request )
    {
        PluginRelease release = new PluginRelease(  );
        String strErrorUrl = getPluginReleaseData( request, release );

        if ( strErrorUrl != null )
        {
            return strErrorUrl;
        }

        PluginReleaseHome.create( release );

        return JSP_MANAGE_PLUGINS_RELEASES;
    }

    /**
     * Provides the modify plugin release page
     * @param request The HTTP request
     * @return The page
     */
    public String getModifyPluginRelease( HttpServletRequest request )
    {
        String strReleaseId = request.getParameter( PARAMETER_RELEASE_ID );
        int nReleaseId = Integer.parseInt( strReleaseId );

        PluginRelease release = PluginReleaseHome.findByPrimaryKey( nReleaseId );
        HashMap model = new HashMap(  );
        model.put( MARK_RELEASE, release );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MODIFY_RELEASE, getLocale(  ), model );

        return getAdminPage( template.getHtml(  ) );
    }

    /**
     * Create a new plugin release
     * @param request The HTTP request
     * @return the forward url
     */
    public String doModifyPluginRelease( HttpServletRequest request )
    {
        String strReleaseId = request.getParameter( PARAMETER_RELEASE_ID );
        int nReleaseId = Integer.parseInt( strReleaseId );

        PluginRelease release = PluginReleaseHome.findByPrimaryKey( nReleaseId );
        String strErrorUrl = getPluginReleaseData( request, release );

        if ( strErrorUrl != null )
        {
            return strErrorUrl;
        }

        PluginReleaseHome.update( release );

        return JSP_MANAGE_PLUGINS_RELEASES;
    }

    /**
     * Gets Data from the request
     * @param request the HTTP request
     * @param release The release object to fill
     * @return ErrorUrl or null if no error
     */
    private String getPluginReleaseData( HttpServletRequest request, PluginRelease release )
    {
        String strName = request.getParameter( PARAMETER_NAME );
        String strVersion = request.getParameter( PARAMETER_VERSION );
        String strUrlDownload = request.getParameter( PARAMETER_URL_DOWNLOAD );
        String strCoreVersionMin = request.getParameter( PARAMETER_CORE_VERSION_MIN );
        String strCoreVersionMax = request.getParameter( PARAMETER_CORE_VERSION_MAX );

        if ( ( strName == null ) || ( strName.equals( "" ) ) || ( strVersion == null ) || ( strVersion.equals( "" ) ) ||
                ( strUrlDownload == null ) || ( strUrlDownload.equals( "" ) ) || ( strCoreVersionMin == null ) ||
                ( strCoreVersionMin.equals( "" ) ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        release.setPluginName( strName );
        release.setPluginVersion( strVersion );
        release.setUrlDownload( strUrlDownload );
        release.setCoreVersionMin( strCoreVersionMin );
        release.setCoreVersionMax( strCoreVersionMax );

        return null;
    }

    /**
     * Confirm the release deletion
     * @param request The HTTP request
     * @return The forward url
     */
    public String deletePluginRelease( HttpServletRequest request )
    {
        String strReleaseId = request.getParameter( PARAMETER_RELEASE_ID );
        UrlItem url = new UrlItem( JSP_DO_DELETE_PLUGIN_RELEASE );
        url.addParameter( PARAMETER_RELEASE_ID, strReleaseId );

        return AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_DELETE_PLUGIN_RELEASE, url.getUrl(  ),
            AdminMessage.TYPE_CONFIRMATION );
    }

    /**
     * Delete the plugin release
     * @param request The HTTP request
     * @return The forward url
     */
    public String doDeletePluginRelease( HttpServletRequest request )
    {
        String strReleaseId = request.getParameter( PARAMETER_RELEASE_ID );
        int nReleaseId = Integer.parseInt( strReleaseId );

        PluginReleaseHome.remove( nReleaseId );

        return JSP_MANAGE_PLUGINS_RELEASES;
    }

    /**
     * Provides the manage plugins page
     * @param request The HTTP request
     * @return The page
     */
    public String getManagePlugins( HttpServletRequest request )
    {
        List<CatalogPlugin> listPlugins = CatalogPluginHome.findAll(  );
        Collections.sort( listPlugins );

        HashMap model = new HashMap(  );
        model.put( MARK_PLUGINS_LIST, listPlugins );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MANAGE_PLUGINS, getLocale(  ), model );

        return getAdminPage( template.getHtml(  ) );
    }

    /**
     * Provides the create plugin page
     * @param request The HTTP request
     * @return The page
     */
    public String getCreatePlugin( HttpServletRequest request )
    {
        HashMap model = new HashMap(  );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_CREATE_PLUGIN, getLocale(  ), model );

        return getAdminPage( template.getHtml(  ) );
    }

    /**
     * Create a new plugin
     * @param request The HTTP request
     * @return the forward url
     */
    public String doCreatePlugin( HttpServletRequest request )
    {
        CatalogPlugin plugin = new CatalogPlugin(  );
        String strErrorUrl = getPluginData( request, plugin );

        if ( strErrorUrl != null )
        {
            return strErrorUrl;
        }

        CatalogPluginHome.create( plugin );

        return JSP_MANAGE_PLUGINS;
    }

    /**
     * Provides the modify plugin page
     * @param request The HTTP request
     * @return The page
     */
    public String getModifyPlugin( HttpServletRequest request )
    {
        String strPluginName = request.getParameter( PARAMETER_NAME );
        String strLocale = request.getParameter( PARAMETER_LOCALE );

        CatalogPlugin plugin = CatalogPluginHome.findByPrimaryKey( strPluginName, strLocale );
        HashMap model = new HashMap(  );
        model.put( MARK_PLUGIN, plugin );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MODIFY_PLUGIN, getLocale(  ), model );

        return getAdminPage( template.getHtml(  ) );
    }

    /**
     * Create a new plugin
     * @param request The HTTP request
     * @return the forward url
     */
    public String doModifyPlugin( HttpServletRequest request )
    {
        String strPluginName = request.getParameter( PARAMETER_NAME );
        String strLocale = request.getParameter( PARAMETER_LOCALE );

        CatalogPlugin plugin = CatalogPluginHome.findByPrimaryKey( strPluginName, strLocale );
        String strErrorUrl = getPluginData( request, plugin );

        if ( strErrorUrl != null )
        {
            return strErrorUrl;
        }

        CatalogPluginHome.update( plugin );

        return JSP_MANAGE_PLUGINS;
    }

    /** Gets Data from the request
     * @param request the HTTP request
     * @param plugin The plugin object to fill
     * @return ErrorUrl or null if no error
     */
    private String getPluginData( HttpServletRequest request, CatalogPlugin plugin )
    {
        String strName = request.getParameter( PARAMETER_NAME );
        String strLocale = request.getParameter( PARAMETER_LOCALE );
        String strDescription = request.getParameter( PARAMETER_DESCRIPTION );
        String strAuthor = request.getParameter( PARAMETER_AUTHOR );
        String strUrlHomepage = request.getParameter( PARAMETER_URL_HOMEPAGE );

        if ( ( strName == null ) || ( strName.equals( "" ) ) || ( strLocale == null ) || ( strLocale.equals( "" ) ) ||
                ( strDescription == null ) || ( strDescription.equals( "" ) ) || ( strAuthor == null ) ||
                ( strAuthor.equals( "" ) ) || ( strUrlHomepage == null ) || ( strUrlHomepage.equals( "" ) ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        plugin.setPluginName( strName );
        plugin.setPluginLocale( strLocale );
        plugin.setPluginDescription( strDescription );
        plugin.setPluginAuthor( strAuthor );
        plugin.setUrlHomepage( strUrlHomepage );

        return null;
    }

    /**
     * Confirm the plugin deletion
     * @param request The HTTP request
     * @return The forward url
     */
    public String deletePlugin( HttpServletRequest request )
    {
        String strName = request.getParameter( PARAMETER_NAME );
        String strLocale = request.getParameter( PARAMETER_LOCALE );
        UrlItem url = new UrlItem( JSP_DO_DELETE_PLUGIN );
        url.addParameter( PARAMETER_NAME, strName );
        url.addParameter( PARAMETER_LOCALE, strLocale );

        return AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_DELETE_PLUGIN, url.getUrl(  ),
            AdminMessage.TYPE_CONFIRMATION );
    }

    /**
     * Delete the plugin
     * @param request The HTTP request
     * @return The forward url
     */
    public String doDeletePlugin( HttpServletRequest request )
    {
        String strName = request.getParameter( PARAMETER_NAME );
        String strLocale = request.getParameter( PARAMETER_LOCALE );

        CatalogPluginHome.remove( strName, strLocale );

        return JSP_MANAGE_PLUGINS;
    }

    /**
     * Provides the create upgrade page
     * @param request The HTTP request
     * @return The page
     */
    public String getCreateUpgrade( HttpServletRequest request )
    {
        String strReleaseId = request.getParameter( PARAMETER_RELEASE_ID );

        HashMap model = new HashMap(  );
        model.put( MARK_RELEASE_ID, strReleaseId );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_CREATE_UPGRADE, getLocale(  ), model );

        return getAdminPage( template.getHtml(  ) );
    }

    /**
     * Create a new upgrade
     * @param request The HTTP request
     * @return the forward url
     */
    public String doCreateUpgrade( HttpServletRequest request )
    {
        String strReleaseId = request.getParameter( PARAMETER_RELEASE_ID );
        int nReleaseId = Integer.parseInt( strReleaseId );

        ReleaseUpgrade upgrade = new ReleaseUpgrade(  );
        String strErrorUrl = getUpgradeData( request, upgrade );

        if ( strErrorUrl != null )
        {
            return strErrorUrl;
        }

        PluginReleaseHome.addUpgrade( nReleaseId, upgrade );

        UrlItem url = new UrlItem( JSP_MODIFY_PLUGIN_RELEASE );
        url.addParameter( PARAMETER_RELEASE_ID, strReleaseId );

        return url.getUrl(  );
    }

    /** Gets Data from the request
     * @param request the HTTP request
     * @param upgrade The upgrade object to fill
     * @return ErrorUrl or null if no error
     */
    private String getUpgradeData( HttpServletRequest request, ReleaseUpgrade upgrade )
    {
        String strVersion = request.getParameter( PARAMETER_VERSION );
        String strCritical = request.getParameter( PARAMETER_CRITICAL );
        int nCritical = Integer.parseInt( strCritical );
        String strUrlDownload = request.getParameter( PARAMETER_URL_DOWNLOAD );

        if ( ( strVersion == null ) || ( strVersion.equals( "" ) ) || ( strUrlDownload == null ) ||
                ( strUrlDownload.equals( "" ) ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        upgrade.setVersionFrom( strVersion );
        upgrade.setCritical( nCritical );
        upgrade.setUrlDownload( strUrlDownload );

        return null;
    }

    /**
     * Confirm the upgrade deletion
     * @param request The HTTP request
     * @return The forward url
     */
    public String deleteUpgrade( HttpServletRequest request )
    {
        String strReleaseId = request.getParameter( PARAMETER_RELEASE_ID );
        String strVersionFrom = request.getParameter( PARAMETER_VERSION );
        UrlItem url = new UrlItem( JSP_DO_DELETE_UPGRADE );
        url.addParameter( PARAMETER_RELEASE_ID, strReleaseId );
        url.addParameter( PARAMETER_VERSION, strVersionFrom );

        return AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_DELETE_UPGRADE, url.getUrl(  ),
            AdminMessage.TYPE_CONFIRMATION );
    }

    /**
     * Delete the upgrade release
     * @param request The HTTP request
     * @return The forward url
     */
    public String doDeleteUpgrade( HttpServletRequest request )
    {
        String strReleaseId = request.getParameter( PARAMETER_RELEASE_ID );
        int nReleaseId = Integer.parseInt( strReleaseId );
        String strVersionFrom = request.getParameter( PARAMETER_VERSION );

        PluginReleaseHome.deleteUpgrade( nReleaseId, strVersionFrom );

        UrlItem url = new UrlItem( JSP_MODIFY_PLUGIN_RELEASE );
        url.addParameter( PARAMETER_RELEASE_ID, strReleaseId );

        return url.getUrl(  );
    }
}
