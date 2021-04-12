/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 */
public class KassapaateTest {
    
    Kassapaate paate;
    
    public KassapaateTest() {
    }
    
    @Before
    public void setUp() {
        paate = new Kassapaate();
    }
    
    @Test
    public void maaraJaMyydytOikein() {
       assertEquals(100000, paate.kassassaRahaa());
       assertEquals(0, paate.maukkaitaLounaitaMyyty() + paate.edullisiaLounaitaMyyty());
    }
    @Test
    public void kateisOstoMaukas() {
        assertEquals(0, paate.syoMaukkaasti(400));
        assertEquals(100400, paate.kassassaRahaa());
        assertEquals(1,paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void kateisOstoEdullinen() {
        assertEquals(0, paate.syoEdullisesti(240));
        assertEquals(100240, paate.kassassaRahaa());
        assertEquals(1,paate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void maksuEiRiittava() {
        assertEquals(95, paate.syoEdullisesti(95));
        assertEquals(100000, paate.kassassaRahaa());
        assertEquals(0,paate.edullisiaLounaitaMyyty());
    }
    // aika alkaa loppua joten en ehdi tehdä tehtäviä loppuun
    
}
