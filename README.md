# TetrisGame

Toteutukseni Tetris-pelistä. 

Sovelluksen avulla voi pelata klassisen tyyppistä Tetristä. Ylhäältä tippuu vuorollaan palikoita, mitä korkeampi taso, sitä nopeammin palikka putoaa. Palikoita voi liikuttaa vasemmalle ja oikealle pelialueen sisällä, sekä kääntää. Palikan voi myös tiputtaa alas suoraan. Palikan voi asettaa holdiin, jolloin hold-palikka vaihdetaan nykyisen kanssa. Tavoitteena on kerätä mahdollisimman paljon pisteitä. Pisteitä saa sorruttamalla rivejä, eniten sorruttaessa neljä riviä samalla. Peli loppuu, kun palikkaa ei saa asetettua pelialueen sisälle. 


### Releaset

[Viikko 6](https://github.com/Saukka/ot-harjoitustyo/releases/tag/viikko6)

[Viikko 5](https://github.com/Saukka/ot-harjoitustyo/releases/tag/viikko5)

Ladattuasi projektin lähdekoodin, siirrä tiedosto Tetris-1.0-SNAPSHOT.jar projektin hakemistoon, ja käynnistä sovellus komennolla java -jar Tetris-1.0-SNAPSHOT.jar

### Komentorivikomennot

Ohjelman voi suorittaa komennolla

```
mvn compile exec:java -Dexec.mainClass=tetris.ui.MenuUI
```

Sovelluksen testit voi suorittaa komennolla 

```
mvn test
```
Testikattavuusraportin saa komennolla

```
mvn jacoco:report
```
JavaDocin voi generoida komennolla 

```
mvn javadoc:javadoc
```

Checkstyle tarkistukset voi suorittaa komennolla

```
mvn jxr:jxr checkstyle:checkstyle
```

## Dokumentaatio

[Vaatimusmäärittely](https://github.com/Saukka/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)

[Työaikakirjanpito](https://github.com/Saukka/ot-harjoitustyo/blob/master/dokumentaatio/työaikakirjanpito.md) 

[Käyttöohje](https://github.com/Saukka/ot-harjoitustyo/blob/master/dokumentaatio/Käyttöohje.md)

[Arkkitehtuurikuvaus](https://github.com/Saukka/ot-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)
