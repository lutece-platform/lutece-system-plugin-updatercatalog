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

import fr.paris.lutece.test.LuteceTestCase;


public class PluginReleaseTest extends LuteceTestCase
{
    private final static String PLUGINNAME1 = "PluginName1";
    private final static String PLUGINNAME2 = "PluginName2";
    private final static String PLUGINVERSION1 = "PluginVersion1";
    private final static String PLUGINVERSION2 = "PluginVersion2";
    private final static String URLDOWNLOAD1 = "UrlDownload1";
    private final static String URLDOWNLOAD2 = "UrlDownload2";
    private final static String COREVERSIONMIN1 = "CoreVersionMin1";
    private final static String COREVERSIONMIN2 = "CoreVersionMin2";
    private final static String COREVERSIONMAX1 = "CoreVersionMax1";
    private final static String COREVERSIONMAX2 = "CoreVersionMax2";
    private final static String VERSION_FROM_1 = "2.0.1";
    private final static String VERSION_FROM_2 = "2.0.2";

    public void testBusiness(  )
    {
        // Initialize an object
        PluginRelease release = new PluginRelease(  );
        release.setPluginName( PLUGINNAME1 );
        release.setPluginVersion( PLUGINVERSION1 );
        release.setUrlDownload( URLDOWNLOAD1 );
        release.setCoreVersionMin( COREVERSIONMIN1 );
        release.setCoreVersionMax( COREVERSIONMAX1 );

        // Create test
        PluginReleaseHome.create( release );

        // Upgrades test
        ReleaseUpgrade upgrade1 = new ReleaseUpgrade(  );
        upgrade1.setVersionFrom( VERSION_FROM_1 );
        upgrade1.setCritical( 1 );
        upgrade1.setUrlDownload( URLDOWNLOAD1 );
        PluginReleaseHome.addUpgrade( release.getId(  ), upgrade1 );

        ReleaseUpgrade upgrade2 = new ReleaseUpgrade(  );
        upgrade2.setVersionFrom( VERSION_FROM_2 );
        upgrade2.setCritical( 1 );
        upgrade2.setUrlDownload( URLDOWNLOAD1 );
        PluginReleaseHome.addUpgrade( release.getId(  ), upgrade2 );

        PluginReleaseHome.deleteUpgrade( release.getId(  ), VERSION_FROM_1 );

        PluginRelease releaseStored = PluginReleaseHome.findByPrimaryKey( release.getId(  ) );
        assertEquals( releaseStored.getPluginName(  ), release.getPluginName(  ) );
        assertEquals( releaseStored.getPluginVersion(  ), release.getPluginVersion(  ) );
        assertEquals( releaseStored.getUrlDownload(  ), release.getUrlDownload(  ) );
        assertEquals( releaseStored.getCoreVersionMin(  ), release.getCoreVersionMin(  ) );
        assertEquals( releaseStored.getCoreVersionMax(  ), release.getCoreVersionMax(  ) );
        assertTrue( releaseStored.getUpgrades(  ).size(  ) == 1 );

        // Update test
        release.setPluginName( PLUGINNAME2 );
        release.setPluginVersion( PLUGINVERSION2 );
        release.setUrlDownload( URLDOWNLOAD2 );
        release.setCoreVersionMin( COREVERSIONMIN2 );
        release.setCoreVersionMax( COREVERSIONMAX2 );
        PluginReleaseHome.update( release );
        releaseStored = PluginReleaseHome.findByPrimaryKey( release.getId(  ) );
        assertEquals( releaseStored.getPluginName(  ), release.getPluginName(  ) );
        assertEquals( releaseStored.getPluginVersion(  ), release.getPluginVersion(  ) );
        assertEquals( releaseStored.getUrlDownload(  ), release.getUrlDownload(  ) );
        assertEquals( releaseStored.getCoreVersionMin(  ), release.getCoreVersionMin(  ) );
        assertEquals( releaseStored.getCoreVersionMax(  ), release.getCoreVersionMax(  ) );

        // List test
        PluginReleaseHome.findAll(  );

        // Delete test
        PluginReleaseHome.remove( release.getId(  ) );
        releaseStored = PluginReleaseHome.findByPrimaryKey( release.getId(  ) );
        assertNull( releaseStored );
    }
}
