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

import java.util.ArrayList;
import java.util.List;


/**
 * This is the business class for the object PluginRelease
 */
public class PluginRelease implements Comparable
{
    // Variables declarations 
    private int _nIdRelease;
    private String _strPluginName;
    private String _strPluginVersion;
    private String _strUrlDownload;
    private String _strCoreVersionMin;
    private String _strCoreVersionMax;
    private List<ReleaseUpgrade> _listUpgrades = new ArrayList<ReleaseUpgrade>(  );

    /**
     * Returns the IdRelease
     * @return The IdRelease
     */
    public int getId(  )
    {
        return _nIdRelease;
    }

    /**
     * Sets the IdRelease
     * @param nIdRelease The IdRelease
     */
    public void setId( int nIdRelease )
    {
        _nIdRelease = nIdRelease;
    }

    /**
     * Returns the PluginName
     * @return The PluginName
     */
    public String getPluginName(  )
    {
        return _strPluginName;
    }

    /**
     * Sets the PluginName
     * @param strPluginName The PluginName
     */
    public void setPluginName( String strPluginName )
    {
        _strPluginName = strPluginName;
    }

    /**
     * Returns the PluginVersion
     * @return The PluginVersion
     */
    public String getPluginVersion(  )
    {
        return _strPluginVersion;
    }

    /**
     * Sets the PluginVersion
     * @param strPluginVersion The PluginVersion
     */
    public void setPluginVersion( String strPluginVersion )
    {
        _strPluginVersion = strPluginVersion;
    }

    /**
     * Returns the UrlDownload
     * @return The UrlDownload
     */
    public String getUrlDownload(  )
    {
        return _strUrlDownload;
    }

    /**
     * Sets the UrlDownload
     * @param strUrlDownload The UrlDownload
     */
    public void setUrlDownload( String strUrlDownload )
    {
        _strUrlDownload = strUrlDownload;
    }

    /**
     * Returns the CoreVersionMin
     * @return The CoreVersionMin
     */
    public String getCoreVersionMin(  )
    {
        return _strCoreVersionMin;
    }

    /**
     * Sets the CoreVersionMin
     * @param strCoreVersionMin The CoreVersionMin
     */
    public void setCoreVersionMin( String strCoreVersionMin )
    {
        _strCoreVersionMin = strCoreVersionMin;
    }

    /**
     * Returns the CoreVersionMax
     * @return The CoreVersionMax
     */
    public String getCoreVersionMax(  )
    {
        return _strCoreVersionMax;
    }

    /**
     * Sets the CoreVersionMax
     * @param strCoreVersionMax The CoreVersionMax
     */
    public void setCoreVersionMax( String strCoreVersionMax )
    {
        _strCoreVersionMax = strCoreVersionMax;
    }

    /**
     * Add an upgrade to the release
     * @param upgrade The upgrade to add
     */
    void addUpgrade( ReleaseUpgrade upgrade )
    {
        _listUpgrades.add( upgrade );
    }

    /**
     * Gets upgrades from the release
     * @return The list of upgrades
     */
    public List<ReleaseUpgrade> getUpgrades(  )
    {
        return _listUpgrades;
    }

    /**
     * Compare deux plugins releases in order to sort them
     * @param o The Plugin to compare
     * @return The comparaison
     */
    public int compareTo( Object o )
    {
        PluginRelease plugin = (PluginRelease) o;

        int nCompare = getPluginName(  ).compareTo( plugin.getPluginName(  ) );

        if ( nCompare == 0 )
        {
            nCompare = getPluginVersion(  ).compareTo( plugin.getPluginVersion(  ) );
        }

        return nCompare;
    }
}
