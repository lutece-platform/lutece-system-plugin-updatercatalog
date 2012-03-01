/*
 * Copyright (c) 2002-2012, Mairie de Paris
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

import fr.paris.lutece.test.LuteceTestCase;


/**
 *
 */
public class CatalogPluginTest extends LuteceTestCase
{
    private final static String PLUGINNAME = "PluginName1";
    private final static String LOCALE = "en";
    private final static String PLUGINDESCRIPTION1 = "PluginDescription1";
    private final static String PLUGINDESCRIPTION2 = "PluginDescription2";
    private final static String PLUGINAUTHOR1 = "PluginAuthor1";
    private final static String PLUGINAUTHOR2 = "PluginAuthor2";
    private final static String URLHOMEPAGE1 = "UrlHomepage1";
    private final static String URLHOMEPAGE2 = "UrlHomepage2";

    public void testBusiness(  )
    {
        // Initialize an object
        CatalogPlugin plugin = new CatalogPlugin(  );
        plugin.setPluginName( PLUGINNAME );
        plugin.setPluginLocale( LOCALE );
        plugin.setPluginDescription( PLUGINDESCRIPTION1 );
        plugin.setPluginAuthor( PLUGINAUTHOR1 );
        plugin.setUrlHomepage( URLHOMEPAGE1 );

        // Create test
        CatalogPluginHome.create( plugin );

        CatalogPlugin catalogPluginStored = CatalogPluginHome.findByPrimaryKey( plugin.getPluginName(  ),
                plugin.getPluginLocale(  ) );
        assertEquals( catalogPluginStored.getPluginName(  ), plugin.getPluginName(  ) );
        assertEquals( catalogPluginStored.getPluginDescription(  ), plugin.getPluginDescription(  ) );
        assertEquals( catalogPluginStored.getPluginLocale(  ), plugin.getPluginLocale(  ) );
        assertEquals( catalogPluginStored.getPluginAuthor(  ), plugin.getPluginAuthor(  ) );
        assertEquals( catalogPluginStored.getUrlHomepage(  ), plugin.getUrlHomepage(  ) );

        // Update test
        plugin.setPluginDescription( PLUGINDESCRIPTION2 );
        plugin.setPluginAuthor( PLUGINAUTHOR2 );
        plugin.setUrlHomepage( URLHOMEPAGE2 );
        CatalogPluginHome.update( plugin );
        catalogPluginStored = CatalogPluginHome.findByPrimaryKey( plugin.getPluginName(  ), plugin.getPluginLocale(  ) );
        assertEquals( catalogPluginStored.getPluginName(  ), plugin.getPluginName(  ) );
        assertEquals( catalogPluginStored.getPluginDescription(  ), plugin.getPluginDescription(  ) );
        assertEquals( catalogPluginStored.getPluginLocale(  ), plugin.getPluginLocale(  ) );
        assertEquals( catalogPluginStored.getPluginAuthor(  ), plugin.getPluginAuthor(  ) );
        assertEquals( catalogPluginStored.getUrlHomepage(  ), plugin.getUrlHomepage(  ) );

        // List test
        CatalogPluginHome.findAll(  );

        // Delete test
        CatalogPluginHome.remove( plugin.getPluginName(  ), plugin.getPluginLocale(  ) );
        catalogPluginStored = CatalogPluginHome.findByPrimaryKey( plugin.getPluginName(  ), plugin.getPluginLocale(  ) );
        assertNull( catalogPluginStored );
    }
}
