==================================================================================
A forrás könyvtár szerkezete:
-----------------------------
- .classpath és .project eclipse paraméter fájlok
  - fejlesztés során eclipse-et használtam 
- src/snake - java források
- src/log4j.xml - logger beállításai
- src/readme.txt - program információk
- lib - külső library-k (log4j és derby)

==================================================================================
A program fordítása:
--------------------

==================================================================================
Egyéb információk:
------------------
A kígyó színe zöld - az alma piros, a banán sárga, ha
elindult a kígyó tartja az irányt.

A program indítása:

java sneak <width> <height>

10 X 10 - es négyzetnél kisebb játékteret nem fogad el,
a felső  határ a képernyő mérete paraméterként definiál
keret meghagyásával.

A Játék paraméterei fordítás előtt a 
sneak.game.configuration.GameParameters - osztályban állíthatóak be:
- cella és játéktér paraméterek
- színek
- időzítés értékek
- adatbázis név.

Az AWT panelek tulajdonságai a
sneak.game.configuration.PanelParameters - osztályban vannak definiálva.

A szöveges üzeneteket a 
TextParameters
sneak.game.configuration.PanelParameters - osztály tartalmazza.
Kivéve a Main üzeneteit hagytam a helyükön, hogy az alap működés
értehetőbb legyen.

A játék az egyes játékosok adatait lokális Derby adatbázisba menti.

==================================================================================
Általános információk:
----------------------
- Minimum Java level: 1.8
- A megoldást egy github repository-ban kell átadni
- Tetszőleges freeware framework használható, amennyiben az mellékelve van a repository-ban
- A program fordul és fut mind Windows 10, illetve valamely tetszőleges, népszerű linux disztribución

- A programot le lehessen fordítani git checkout után egyéb szoftver használata nélkül
  (kivételt képez a Java SDK)
- A program futtatása nem igényelhet extra hardware-t (pl. komolyabb videókártya)
- A megoldás kövesse a S.O.L.I.D. elveket

==================================================================================
Snake
-----
A népszerű kígyós játék megvalósítása Java nyelven. Karakteres vagy
grafikus felületen is implementálható a megoldás.

A játék menete röviden:

- A játékos a játszma elején egy stilizált kígyófejet (1x1-es négyzet) irányít egy
  fix méretű téglalap alakú pályán, ahol a pálya falai határozottan ábrázolva vannak.
  A kígyó a játszma elején a pálya tetszőleges részére véletlenszerűen kerül és
  az első beavatkozásig nyugalmi helyzetben van.
  A játékos a kígyót a kurzormozgató billentyűk segítségével irányíthatja.
  A megvalósításban nem kell lekezelni, ha a kígyó mozgása közben 
  a mozgás irányára ellentétes parancsot kap. 
  (Felfele mozgás közben a játékos LE billentyűt nyom.)
- Természetesen a kígyó testének minden szegmense az előző szegmens útját követi, 
  egészen elérve a fejig.
- Ha a kígyó falba vagy saját magába ütközik, akkor a játékos életet veszít. 
  A játékos a játék elején öt élettel kezdi a játékot, ha az életek száma nullára ér,
  a játék véget ér.
- A játékos pontot érhet el az által, hogy megeszi a pályán véletlenszerűen keletkező gyümölcsöket.
  Egy-egy gyümölcs elfogyasztása a kígyó hosszát megnöveli egy egységgel. 
  Egyszerre csak egy gyümölcs lehet a pályán. A következő gyümölcs az előző étel
  elfogyasztása után kerül a pályára.
- A játék, játszma közben mutassa a játékos aktuális pontszámát, élete(i) számát,
  illetve a rekordpontszámot.
- A játszma végeztével kérje be a játékos nevét és mentse el a pontszámmal együtt.
  Közölje a játék, ha a játékos rekordot döntött. Legyen lehetőség
  az első 10 játékos és pontszámának megtekintésére.
- A játékot lehessen tetszőleges méretű pályán indítani. Ha a megadott paraméter
  fizikai lehetetlenségbe ütközik (pl. nem fér ki a képernyőre),
  azt jelezze hibaüzenet formájában, majd lépjen ki. 

JButton javaButton = new JButton("Submit", icon);
javaButton.addActionListener(new ActionListener() {
  public void actionPerformed(ActionEvent e) {
  statusLabel.setText("Submit Button clicked.");
  }
});