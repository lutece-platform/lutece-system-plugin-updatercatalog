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
package fr.paris.lutece.plugins.updatercatalog.service;

import fr.paris.lutece.plugins.updatercatalog.business.Catalog;
import fr.paris.lutece.plugins.updatercatalog.business.CatalogHome;
import fr.paris.lutece.plugins.updatercatalog.business.CatalogPluginEntry;
import fr.paris.lutece.plugins.updatercatalog.business.ReleaseUpgrade;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.util.xml.XmlUtil;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

import java.util.List;


/**
 * Catalog Generation Service
 */
public final class CatalogGenerationService
{
    private static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
    private static final String TAG_CATALOG = "catalog";
    private static final String TAG_PLUGINS = "plugins";
    private static final String TAG_PLUGIN = "plugin";
    private static final String TAG_NAME = "name";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_VERSION = "version";
    private static final String TAG_MIN_CORE_VERSION = "min-core-version";
    private static final String TAG_MAX_CORE_VERSION = "max-core-version";
    private static final String TAG_AUTHOR = "author";
    private static final String TAG_URL_HOMEPAGE = "url-homepage";
    private static final String TAG_URL_DOWNLOAD = "url-download";
    private static final String TAG_UPGRADES = "upgrades";
    private static final String TAG_UPGRADE = "upgrade";
    private static final String TAG_VERSION_FROM = "version-from";
    private static final String TAG_CRITICAL = "critical";
    private static final String TAG_URL_UPGRADE_DOWNLOAD = "url-upgrade-download";

    /**
     * Private constructor
     */
    private CatalogGenerationService(  )
    {
    }

    /**
     * Generate a given catalog
     * @param nCatalogId The catalog ID
     */
    public static void generateCatalog( int nCatalogId )
    {
        try
        {
            Catalog catalog = CatalogHome.findByPrimaryKey( nCatalogId );
            File file = new File( AppPathService.getWebAppPath(  ) + "/" + catalog.getOutputFilename(  ) );
            FileUtils.writeStringToFile( file, generateCatalogXmlDocument( catalog ) );
        }
        catch ( IOException e )
        {
            AppLogService.error( "Error writing catalog : " + e.getMessage(  ), e );
        }
    }

    /**
     * Generate the XML document of a given catalog
     * @param catalog The catalog
     * @return The catalog as XML
     */
    private static String generateCatalogXmlDocument( Catalog catalog )
    {
        List<CatalogPluginEntry> listEntries = CatalogHome.getPluginsEntries( catalog.getId(  ) );

        StringBuffer sbXml = new StringBuffer( XML_HEADER );
        XmlUtil.beginElement( sbXml, TAG_CATALOG );
        XmlUtil.beginElement( sbXml, TAG_PLUGINS );

        for ( CatalogPluginEntry entry : listEntries )
        {
            String strRepository = catalog.getUrlRepository(  ) + "/plugin-" + entry.getPluginName(  ) + "/";
            XmlUtil.beginElement( sbXml, TAG_PLUGIN );
            XmlUtil.addElement( sbXml, TAG_NAME, entry.getPluginName(  ) );
            XmlUtil.addElementHtml( sbXml, TAG_DESCRIPTION, entry.getPluginDescription(  ) );
            XmlUtil.addElement( sbXml, TAG_VERSION, entry.getPluginVersion(  ) );
            XmlUtil.addElement( sbXml, TAG_MIN_CORE_VERSION, entry.getCoreVersionMin(  ) );

            if ( ( entry.getCoreVersionMax(  ) != null ) && !entry.getCoreVersionMax(  ).equals( "" ) )
            {
                XmlUtil.addElement( sbXml, TAG_MAX_CORE_VERSION, entry.getCoreVersionMax(  ) );
            }

            XmlUtil.addElementHtml( sbXml, TAG_AUTHOR, entry.getPluginAuthor(  ) );
            XmlUtil.addElement( sbXml, TAG_URL_HOMEPAGE, entry.getUrlHomepage(  ) );
            XmlUtil.addElement( sbXml, TAG_URL_DOWNLOAD, strRepository + entry.getUrlDownload(  ) );
            XmlUtil.beginElement( sbXml, TAG_UPGRADES );

            for ( ReleaseUpgrade upgrade : entry.getUpgrades(  ) )
            {
                XmlUtil.beginElement( sbXml, TAG_UPGRADE );
                XmlUtil.addElement( sbXml, TAG_VERSION_FROM, upgrade.getVersionFrom(  ) );
                XmlUtil.addElement( sbXml, TAG_CRITICAL, upgrade.getCritical(  ) );
                XmlUtil.addElement( sbXml, TAG_URL_UPGRADE_DOWNLOAD, strRepository + upgrade.getUrlDownload(  ) );
                XmlUtil.endElement( sbXml, TAG_UPGRADE );
            }

            XmlUtil.endElement( sbXml, TAG_UPGRADES );
            XmlUtil.endElement( sbXml, TAG_PLUGIN );
        }

        XmlUtil.endElement( sbXml, TAG_PLUGINS );
        XmlUtil.endElement( sbXml, TAG_CATALOG );

        return sbXml.toString(  );
    }
}
