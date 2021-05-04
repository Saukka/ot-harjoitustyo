# TetrisGame

Toteutukseni Tetris-pelistä. 

Sovelluksen avulla voi pelata klassisen tyyppistä Tetristä. Ylhäältä tippuu vuorollaan palikoita, mitä korkeampi taso, sitä nopeammin. Palikoita voi liikuttaa vasemmalle ja oikealle pelialueen sisällä, sekä kääntää (tällä hetkellä vain vasemmalle). Palikan voi myös tiputtaa alas suoraan. Tavoitteena on kerätä mahdollisimman paljon pisteitä. Pisteitä saa sorruttamalla rivejä, eniten sorruttaessa neljä riviä samalla. Peli loppuu, kun palikkaa ei saa asetettua pelialueen sisälle. 


### Releaset

[Viikko 5](https://github.com/Saukka/ot-harjoitustyo/releases/tag/viikko5)

Ladattuasi projektin lähdekoodin, siirrä tiedosto Tetris-1.0-SNAPSHOT.jar projektin hakemistoon, ja käynnistä sovellus komennolla java -jar Tetris-1.0-SNAPSHOT.jar

### Komentorivikomennot

Ohjelman voi suorittaa komennolla

```
mvn compile exec:java -Dexec.mainClass=tetris.ui.UserInterface
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

## Dokumentaatio

[Vaatimusmäärittely](https://github.com/Saukka/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)

[Työaikakirjanpito](https://github.com/Saukka/ot-harjoitustyo/blob/master/dokumentaatio/työaikakirjanpito.md) 

[Käyttöohje](https://github.com/Saukka/ot-harjoitustyo/blob/master/dokumentaatio/Käyttöohje.md)

[Arkkitehtuurikuvaus](https://github.com/Saukka/ot-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)

