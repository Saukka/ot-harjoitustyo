package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void saldoAlussaOikein() {
        assertEquals("saldo: 0.10", kortti.toString());
    }
    @Test
    public void rahanLataaminenToimii() {
        kortti.lataaRahaa(10);
        assertEquals("saldo: 0.20", kortti.toString());
    }
 
    
    // Jos tässä on @Test, ohjelman suorittaminen tuottaa errorin sillä alla oleva metodi ei ole void-metodi
    public boolean rahanOttaminenToimii() {
        kortti.otaRahaa(5);
        if (kortti.saldo()<10) {
            assertEquals("saldo: 0.05", kortti.toString());
            return true;
        } 
        assertEquals("saldo: 0.10", kortti.toString());
        return false;
    }
    
    
    
    
    
}
