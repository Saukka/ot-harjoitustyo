# Käyttöohje

Lataa tiedosto

## Ohjelman käynnistäminen

Siirrä tiedosto Tetris-1.0-SNAPSHOT.jar projektin hakemistoon, ja käynnistä sovellus komennolla

```
java -jar Tetris-1.0-SNAPSHOT.jar
```

## Aloitusruutu

Nappia painamalla ja antamalla inputin voi vaihtaa pelissä käytettäviä näppäimiä. Kun olet valmis, paina start aloittaaksesi pelin.

<img width="599" alt="Aloitusruutu" src="https://user-images.githubusercontent.com/80990021/116949805-f2456d80-ac8b-11eb-9979-bd0a548d6dd8.png">

## Pelaaminen

Keskellä ylhäällä on ohjattava palikka, jota voi liikuttaa sekä käännellä. Palikasta suoraan alhaalla näkyy haamu-palikka, joka näyttää mihin palikka on laskeutumassa. 
Oikeassa yläkulmassa näkyy palikka, mikä ilmestyy nykyisen palikan asetettua. Vasemmassa yläkulmassa näkyy "hold-palikka", johon nykyisen palikan voi vaihtaa painamalla hold-napilla asetettua näppäintä. 
Hold palikkaa voi vaihtaa kerran jokaisen palikan kohdalla. Vasemmassa alakulmassa näkyy taso, pisteet ja sorrutetut rivit. Pisteitä saa sorruttamalla rivejä, hard droppaamalla, sekä liikuttamalla palikkaa alaspäin (soft drop). Taso nousee aina kun 8 riviä on sorrutettu. Tason noustessa pisteitä saa enemmän ja palikat tippuvat nopeammin, ja joillain tasoilla palikoiden värit vaihtuvat.
Jos palikat kasaantuvat pelialueen kattoon ja palikkaa ei saada asetettua pelialueelle, peli loppuu.

<img width="606" alt="Peliruutu" src="https://user-images.githubusercontent.com/80990021/116951262-068b6980-ac90-11eb-99db-172350e46f0a.png">

Peliruutu.

### Rivien sorruttaminen

<img width="607" alt="Rivien sorruttaminen" src="https://user-images.githubusercontent.com/80990021/116952155-9f22e900-ac92-11eb-95ed-dd7c8906e8be.png">

Tulossa on rivien sorruttaminen, ja vieläpä paras mahdollinen sellainen, nimeltään Tetris, jolloin neljä riviä sorrutetaan samanaikaisesti. Mitä enemmän rivejä sorruttaa samalla, sitä enemmän pisteitä saa, verrattuna jos rivit sorrutettaisiin pienemmissä osissa.

<img width="605" alt="Tilanne sorruttamisen jälkeen" src="https://user-images.githubusercontent.com/80990021/116952426-515ab080-ac93-11eb-8e89-f234e6bc63dc.png">

Tilanne rivien sorruttamisen jälkeen. 

### Pelin loppuminen

Pelin loppuessa voit vielä ihailla keräämiäsi pisteitä ja halutessasi siirtyä takaisin aloitusruudulle, josta voi aloittaa uuden pelin.

<img width="698" alt="Näyttökuva 2021-5-4 kello 4 59 14" src="https://user-images.githubusercontent.com/80990021/116953278-87009900-ac95-11eb-9b80-03c28e4c8dc0.png">

