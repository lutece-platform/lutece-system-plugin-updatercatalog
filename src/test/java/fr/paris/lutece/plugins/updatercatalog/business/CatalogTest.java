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

import fr.paris.lutece.test.LuteceTestCase;


public class CatalogTest extends LuteceTestCase
{
    private final static String CATALOGLOCALE1 = "en";
    private final static String CATALOGLOCALE2 = "fr";
    private final static String CATALOGNAME1 = "CatalogName1";
    private final static String CATALOGNAME2 = "CatalogName2";
    private final static String CATALOGDESCRIPTION1 = "CatalogDescription1";
    private final static String CATALOGDESCRIPTION2 = "CatalogDescription2";
    private final static String OUTPUTFILENAME1 = "OutputFilename1";
    private final static String OUTPUTFILENAME2 = "OutputFilename2";
    private final static String URLREPOSITORY1 = "UrlRepository1";
    private final static String URLREPOSITORY2 = "UrlRepository2";

    public void testBusiness(  )
    {
        // Initialize an object
        Catalog catalog = new Catalog(  );
        catalog.setLocale( CATALOGLOCALE1 );
        catalog.setName( CATALOGNAME1 );
        catalog.setDescription( CATALOGDESCRIPTION1 );
        catalog.setOutputFilename( OUTPUTFILENAME1 );
        catalog.setUrlRepository( URLREPOSITORY1 );

        // Create test
        CatalogHome.create( catalog );

        Catalog catalogStored = CatalogHome.findByPrimaryKey( catalog.getId(  ) );
        assertEquals( catalogStored.getId(  ), catalog.getId(  ) );
        assertEquals( catalogStored.getLocale(  ), catalog.getLocale(  ) );
        assertEquals( catalogStored.getName(  ), catalog.getName(  ) );
        assertEquals( catalogStored.getDescription(  ), catalog.getDescription(  ) );
        assertEquals( catalogStored.getOutputFilename(  ), catalog.getOutputFilename(  ) );
        assertEquals( catalogStored.getUrlRepository(  ), catalog.getUrlRepository(  ) );

        // Update test
        catalog.setLocale( CATALOGLOCALE2 );
        catalog.setName( CATALOGNAME2 );
        catalog.setDescription( CATALOGDESCRIPTION2 );
        catalog.setOutputFilename( OUTPUTFILENAME2 );
        catalog.setUrlRepository( URLREPOSITORY2 );
        CatalogHome.update( catalog );
        catalogStored = CatalogHome.findByPrimaryKey( catalog.getId(  ) );
        assertEquals( catalogStored.getId(  ), catalog.getId(  ) );
        assertEquals( catalogStored.getLocale(  ), catalog.getLocale(  ) );
        assertEquals( catalogStored.getName(  ), catalog.getName(  ) );
        assertEquals( catalogStored.getDescription(  ), catalog.getDescription(  ) );
        assertEquals( catalogStored.getOutputFilename(  ), catalog.getOutputFilename(  ) );
        assertEquals( catalogStored.getUrlRepository(  ), catalog.getUrlRepository(  ) );

        // List test
        CatalogHome.findAll(  );

        // Delete test
        CatalogHome.remove( catalog.getId(  ) );
        catalogStored = CatalogHome.findByPrimaryKey( catalog.getId(  ) );
        assertNull( catalogStored );
    }
}
