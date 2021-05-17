# Arkkitehtuuri

### Pakkausrakenne

<img width="150" alt="Pakkausrakenne" src="https://user-images.githubusercontent.com/80990021/118414607-48fc6f80-b6ae-11eb-8a0b-2645680c3f69.png">


### Käyttöliittymä

Käyttöliittymä sisältää kolme eri näkymää, joilla jokaisella on oma scene-olio. 
Käyttöliittymä-näkymiä ovat menu, pelinäkymä, ja tulostaulu. Menu-näkymässä asetetaan pelin asetukset kuten käytettävät näppäimet, aloitustaso ja pelimuoto. Nämä asetukset välitetään Tetris-luokkaan ja peli aloitetaan halutuilla asetuksilla.

### Sovelluslogiikka

Tetris-luokka luo Board-olion ja käynnistää pelin kutsumalla newPiece-metodia. Board-luokassa luodaan CurrentPiece-olio, jota ohjataan suoraan Tetris-luokasta käyttäjän inputeilla. CurrentPiece sisältää nykyiseen palikkaan kuuluvat ominaisuudet kuten palikan keskipisteen x- ja y-koordinaatit, sekä neljä Square-oliota. 

Jokainen tetris-palikka muodostuu neljästä Square-oliosta. Square-oliolla on yksi isompi Rectangle, joka on palikan väri. Tämän Rectanglen sisällä on kolme pienempää valkoista Rectanglea, joilla palikalle annetaan hehkuva tyyli. PlacedSquare-olio sisältää asetetun Square-olion sekä sen pelikenttä-koordinaatit. Board-luokassa on PlacedSquare-lista, jonka avulla katsotaan onko jollain rivillä kymmenen neliötä, milloin rivi sorrutetaan. Listan avulla myös uudelleen-väritetään asetetut palikat tason vaihtuessa.

Tetromino-luokassa on eri palikoiden ominaisuudet kuten koordinaatit ja värit. 

Pelin loppuessa pelin pisteet kirjataan ylös scores.txt tiedostoon HighScores-luokan avulla.

Pääluokkien päätoiminnallisuudet, mihin pelin pelaaminen perustuu ja miten se toimii:

<img width="600" alt="Päätoiminnallisuudet" src="https://user-images.githubusercontent.com/80990021/118416078-9b418e80-b6b6-11eb-889a-2b2696687009.png">


### Heikkoudet

Pelin asetukset ja käyttöliittymäkomponentit eivät ole välitetty Tetris-luokkaan parhaalla tavalla. Nyt jokainen asetus ja komponentti tuodaan omana oliona, joka ei näytä kovin kauniilta Tetris-olion luomisessa ja pelin aloittamisessa. 

CurrentPiecellä on piece niminen Tetromino-olio, ja joskus piecen tetromino-luokan ominaisuuksia haetaan Board-luokassa, mikä voi olla epäselvää koodissa.
