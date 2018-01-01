/*
 * Harjoitustyö II
 *
 * Lausekielinen ohjelmointi II, syksy 2016.
 *
 * Jirka Lilja 90411
 *
 * lilja.jirka.j@student.uta.fi
 *
 * ASCII Art-ohjelma, jonka avulla tekstitiedosto ladataan
 * komentoriviparametriä käyttäen. Tekstitiedosto
 * saa sisältää määriteltyjä eri "harmaan" sävyisiä
 * merkkejä. Tekstitiedoston voi tulostaa sellaisenaan
 * merkkimuotoisena, numeromuotoisena, sen voi filtteröidä
 * ja taas resetoida alkuperäiseen muotoon. Tiedostosta voi
 * tiedustella myös sen sisällön informaatiota.
 * Jokaisen komennon jälkeen palataan kysymään uutta
 * komentoa kunnes komento on "quit" ja ohjelma loppuu.
 *
 */

import java.io.*;

public class ASCIIArt {
   
   // Vakioiden määrittely.
      
   // Määritellään harmaan eri sävyt ja luodaan niistä vakioidut taulukot.
   public static final char MUSTA0 = '#';
   public static final char HARMAA1 = '@';
   public static final char HARMAA2 = '&';
   public static final char HARMAA3 = '$';
   public static final char HARMAA4 = '%';
   public static final char HARMAA5 = 'x';
   public static final char HARMAA6 = '*';
   public static final char HARMAA7 = 'o';
   public static final char HARMAA8 = '|';
   public static final char HARMAA9 = '!';
   public static final char HARMAA10 = ';';
   public static final char HARMAA11 = ':';
   public static final char HARMAA12 = '\'';
   public static final char HARMAA13 = ',';
   public static final char HARMAA14 = '.';
   public static final char VALKOINEN15 = ' ';
      
   public static final char[] merkkiTaulu = { MUSTA0, HARMAA1, HARMAA2, HARMAA3,
   HARMAA4, HARMAA5, HARMAA6, HARMAA7, HARMAA8, HARMAA9,
   HARMAA10, HARMAA11, HARMAA12, HARMAA13, HARMAA14, VALKOINEN15 };
   
   public static final char[] asciiMerkit =
   { '#', '@', '&', '$', '%', 'x', '*', 'o', '|', '!', ';', ':', '\'', ',', '.', ' ' };
      
   // Vakio kysymykselle ja hyvästelemiseen.
   public static final String KYSYMYSRIVI = "printa/printi/info/filter [n]/reset/quit?";
   public static final String HYVASTELE = "Bye, see you soon.";
      
   // Määritellään komentovakiot.
   public static final String TULOSTAMERKKEINA = "printa";
   public static final String TULOSTALUKUINA = "printi";
   public static final String TULOSTATIEDOT = "info";
   public static final String SUODATA = "filter";
   public static final String NOLLAA = "reset";
   public static final String LOPETA = "quit";
   
   // Vakio filtterin oletuskoolle.
   public static final int OLETUSFILTTERI = 3;

   public static void main (String[] args) {
      
      // Tulostetaan aloitusviesti.
      System.out.println("-------------------");
      System.out.println("| A S C I I A r t |");
      System.out.println("-------------------");
      
      // Tarkistetaan, että komentoriviparametrejä on yksi.
      if (args.length == 1) {
         
         // Ladattava tiedosto on ensimmäisessä komentoriviparametrissä.
         String tiedosto = args[0];
         
         // Tallennetaan tiedosto tarkastusta varten.
         File f = new File(tiedosto);
         
         // Tarkistetaan löytyykö tiedosto.
         if (!f.exists()) {
            // Tulostetaan virhe jos tiedostoa ei löydy.
            System.out.println("Invalid command-line argument!");
            // Lopetetaan ohjelma.
            System.out.println(HYVASTELE);
         }
         // Jos tiedosto löytyy:
         else if (f.exists()) {
            // Operaation kutsu, kysytään komentoa.
            kysyKomento(tiedosto);
         }
      }
      else {
         // Tulostetaan virhe jos komentoriviparametrejä != 1.
         System.out.println("Invalid command-line argument!");
         // Lopetetaan ohjelma.
         System.out.println(HYVASTELE);
      }
   }

   /* Pääoperaatio, joka kysyy käyttäjältä uuden komennon
    * suoritettavaksi kunnes komento on "quit" ja ohjelma loppuu.
    * Tänne palataan aina kun annettu komento on suoritettu loppuun.
    */
   public static void kysyKomento(String tiedosto) {
      
      // Kutsutaan operaatiota, täytetään taulukko.
      char[][] asciiTaulu = taytaTaulu(tiedosto);
            
      // Muuttuja käyttäjän antamalle komennolle.
      String komento = "";
      
      // Muuttuja filtterin sivulle (sivu * sivu).
      int filtterinSivu = 0;
      
      do {
         // Tulostetaan kysymys.
         System.out.println(KYSYMYSRIVI);
         
         // Kysytään komentoa.
         komento = In.readString();
      
         // Kun halutaan tulostaa tiedosto sellaisenaan merkkimuodossa:
         if (komento.equals(TULOSTAMERKKEINA)) {
            // Operaation kutsu. Tulostetaan täytetty taulukko.
            tulostaKuva(asciiTaulu, tiedosto);
         }
         // Kun halutaan tulostaa tiedosto numero/lukumuodossa:
         else if (komento.equals(TULOSTALUKUINA)) {
            // Operaation kutsu. Tulostetaan numeromuotoinen taulukko.
            tulostaNumeroina(asciiTaulu, tiedosto);
         }
         // Kun halutaan informaatiota tiedoston sisällöstä:
         else if (komento.equals(TULOSTATIEDOT)) {
            // Operaation kutsu. Tulostetaan tiedoston sisällön informaatio.
            tulostaTiedot(asciiTaulu, tiedosto);
         }
         // Kun komento on "filter",
         // suodatetaan "kuva" filtterin oletuskoolla (3 * 3):
         else if (komento.equals(SUODATA)) {
            // Filtteri saa oletuskoon jos sille ei anneta komennon perään numeroa.
            filtterinSivu = OLETUSFILTTERI;
            
            // Kutsutaan operaatioita, lasketaan ja suodatetaan taulukon sisältö.
            int filtteriTaulu[][] = laskeNumeroTaulu(asciiTaulu); 
            int mediaaniTaulu[][] = filtteroiTaulu(filtteriTaulu, filtterinSivu, asciiTaulu);
            
            // Muutetaan suodatettu taulukko asciimerkeiksi.
            for (int rivi = 0; rivi < filtteriTaulu.length; rivi++) {
               for (int sarake = 0; sarake < filtteriTaulu[0].length; sarake++) {
                  asciiTaulu[rivi][sarake] = asciiMerkit[mediaaniTaulu[rivi][sarake]];
               }
            }
         }
         // Kun komento on "filter n" suodatetaan
         // "kuva" itse valitulla filtterin koolla (n * n):
         // (Oletuksena voi antaa vain parittomia lukuja.)
         else if (komento.startsWith(SUODATA)) {
            // Katkaistaan komento, jotta saadaan filtterin koko.
            String[] katkaistu = komento.split(" ");
            filtterinSivu = Integer.parseInt(katkaistu [katkaistu.length - 1]);
            
            // Kutsutaan operaatioita, lasketaan ja suodatetaan taulukon sisältö.
            int filtteriTaulu[][] = laskeNumeroTaulu(asciiTaulu); 
            int mediaaniTaulu[][] = filtteroiTaulu(filtteriTaulu, filtterinSivu, asciiTaulu);
            
            // Muutetaan suodatettu taulukko asciimerkeiksi.
            for (int rivi = 0; rivi < filtteriTaulu.length; rivi++) {
               for (int sarake = 0; sarake < filtteriTaulu[0].length; sarake++) {
                  asciiTaulu[rivi][sarake] = asciiMerkit[mediaaniTaulu[rivi][sarake]];
               }
            }
         }
         // Kun halutaan nollata tehdyt muutokset:
         else if (komento.equals(NOLLAA)) {
            // Kutsutaan operaatiota, täytetään taulukko alkuperäisellä "kuvalla".
            asciiTaulu = taytaTaulu(tiedosto);
         }
      }
      // Kun komento on "quit", ohjelma loppuu:
      while (!(komento.equals(LOPETA)));
         // Tulostetaan heipat.
         System.out.println(HYVASTELE);
   }
   
   /* Haetaan tiedosto ja luetaan rivit tiedostosta.
    * Lasketaan rivien toistumat ja palautetaan toistumat jos voitiin
    * laskea. Muutoin palautetaan negatiivinen arvo.
    */
   public static int laskeRivitTiedostosta(String tiedosto) {
      
      // Alustetaan muuttuja luku nollaksi.
      int luku = 0;
      
      try {
         // Avataan tiedosto lukemista varten olion avulla.
         FileInputStream syotevirta = new FileInputStream(tiedosto);
         InputStreamReader lukija = new InputStreamReader(syotevirta);
         BufferedReader puskuroituLukija = new BufferedReader(lukija);
         
         // Luetaan rivejä niin kauan kun niitä löytyy.
         while (puskuroituLukija.ready()) {
            // Luetaan rivit tiedostosta.
            String rivi = puskuroituLukija.readLine();

            // Päivitetään laskuri.
            luku++;
         }
         // Suljetaan lukija.
         puskuroituLukija.close();
      }
      // Virhetilanteessa palautetaan negatiivinen arvo.
      catch (Exception e) {
         return - 1;
      }
      // Palautetaan toistumat.
      return luku;
   }
   
   /* Luodaan ja täytetään char-tyyppinen kaksiulotteinen taulukko.
    * Palautetaan taulukko jos alkiot saatiin sijoitettua.
    * Virhetilanteessa taulukko määritellään null-arvoiseksi.
    */
   public static char[][] taytaTaulu(String tiedosto) {
      
      // Kutsutaan operaatiota.
      int luku = laskeRivitTiedostosta(tiedosto);
      
      // Määritellään taulukko null-arvoiseksi oletuksena.
      char[][] asciiTaulu = null;
      
      // Määritellään muuttuja pituus nollaksi.
      int pituus = 0;
      
      // Operaation kutsu. Lasketaan rivit tiedostosta.
      int rivit = laskeRivitTiedostosta(tiedosto);
      
      try {
         // Avataan tiedosto lukemista varten olion avulla.
         FileInputStream syotevirta = new FileInputStream(tiedosto);
         InputStreamReader lukija = new InputStreamReader(syotevirta);
         BufferedReader puskuroituLukija = new BufferedReader(lukija);
         
         // Rivi-indeksimuuttujan määrittely.
         int j = 0;
         
         // Luetaan rivejä niin kauan kun niitä löytyy.
         while (puskuroituLukija.ready()) {
            // Luetaan rivit tiedostosta.
            String rivi = puskuroituLukija.readLine();
            pituus = rivi.length();
            
            // Ensimmäisellä kierroksella luodaan taulukko.
            if (j == 0) {
               asciiTaulu = new char[rivit][pituus];
            }
            // Sijoitetaan taulukkoon.
            for (int i = 0; i < pituus; i++) {
               asciiTaulu[j][i] = rivi.charAt(i);
            }
            // Päivitetään laskuri.
            j++;
         }
         // Suljetaan lukija.
         puskuroituLukija.close();
      }
      // Virhetilanteessa annetaan taulukolle null-arvo.
      catch (Exception e) {
         asciiTaulu = null;
      }
      // Palautetaan taulukko.
      return asciiTaulu;
   }
   
   /* Operaatio, joka tulostaa
    * kaksiulotteisen taulukon sellaisenaan riveittäin.
    */
   public static void tulostaKuva(char[][] asciiTaulu, String tiedosto) {
      
      // Tulostetaan taulukko jos varattu muistia.
      if (asciiTaulu != null) {
         for (int i = 0; i < asciiTaulu.length; i++) {
            for (int j = 0; j < asciiTaulu[i].length; j++) {
               System.out.print(asciiTaulu[i][j]);
         }
         // Vaihdetaan riviä aina kun rivi on tulostettu.
         System.out.println();
         }
      }
   }
   
   /* Operaatio, joka tulostaa kaksiulotteisen taulukon
    * luku/numeromuodossa riveittäin.
    */
   public static void tulostaNumeroina(char[][] asciiTaulu, String tiedosto) {
      
      // Tarkistetaan, että taulukolle on varattu muistia.
      if (asciiTaulu != null) {
         // Luodaan sarakkeiden apumuuttuja.
         int a = asciiTaulu[0].length;
         // Tulostetaan taulukko.
         for (int rivi = 0; rivi < asciiTaulu.length; rivi++) {
            for (int sarake = 0; sarake < asciiTaulu[0].length; sarake++) {
               for (int i = 0; i < merkkiTaulu.length; i++) {
                  if (asciiTaulu[rivi][sarake] == merkkiTaulu[i]) {
                     
                     // Jos merkin järjestysnumero on pienempi kuin 10 ja
                     // ollaan viimeisessä merkissä, tulostetaan väli ennen numeroa.
                     if (i < 10 && (sarake + 1) == a) {
                        System.out.print(" " + i);
                     }
                     // Jos edelleen järjestysnumero < 10, mutta ei
                     // olla viimeisessä merkissä, tulostetaan väli ennen ja jälkeen numeron.
                     if (i < 10 && (sarake + 1) != a) {
                        System.out.print(" " + i + " ");
                     }
                     // Jos järjestysnumero on 10 tai suurempi
                     // ja ollaan viimeisessä merkissä, tulostetaan vain merkki.
                     if (i >= 10 && (sarake + 1) == a) {
                        System.out.print(i);
                     }
                     // Jos järjestysnumero on 10 tai suurempi
                     // ja ei olla viimeisessä merkissä, tulostetaan merkki ja väli.
                     if (i >= 10 && (sarake + 1) != a) {
                        System.out.print(i + " ");
                     }
                  }
               }
            }
            // Vaihdetaan riviä.
            System.out.println();
         }
      }
   }
   
   /* Operaatio, joka tulostaa haetun tiedoston pituuden ja leveyden
    * sekä informaation tiedoston merkkien esiintymistä.
    */
   public static void tulostaTiedot(char[][] asciiTaulu, String tiedosto) {
      
      // Määritellään muuttujat.
      int rivit = 0;
      int sarakkeet = asciiTaulu[0].length;
      
      // Haetaan tekstitiedoston rivit ja sarakkeet.
      for (int rivi = 0; rivi < asciiTaulu.length; rivi++) {
         for (int sarake = 0; sarake < asciiTaulu[0].length; sarake++) {
            
         }
         // Päivitetään rivilaskuria.
         rivit++;
      }
      // Tulosteaan tekstitiedoston koko.
      System.out.println(rivit + " x " + sarakkeet);
      
      // Haetaan merkkien esiintymät.
      for (int i = 0; i < merkkiTaulu.length; i++) {
         // Nollataan esiintymien määrä aina kierroksen alussa.
         int esiintymat = 0;
         
         for (int rivi = 0; rivi < asciiTaulu.length; rivi++) {
            for (int sarake = 0; sarake < asciiTaulu[0].length; sarake++) {
               // Jos löydettiin merkki...
               if (asciiTaulu[rivi][sarake] == merkkiTaulu[i]) {
                  // ...päivitetään esiintymien laskuria.
                  esiintymat++;
            }
         }   
      }
      // Tulostetaan merkki ja sen esiintymien määrä.
      System.out.println(merkkiTaulu[i] + " " + esiintymat);
      }
   }
   
   /* Operaatio, jossa tehdään uusi kaksiulotteinen taulukko ladatun tiedoston perusteella
    * suodatusta varten. Operaatio palautta tehdyn taulukon.
    */
   public static int[][] laskeNumeroTaulu(char[][] asciiTaulu) {
      
      // Luodaan uusi taulukko.
      int[][] filtteriTaulu = new int [asciiTaulu.length][asciiTaulu[0].length];
      
      // Tarkistetaan, että muistia on varattu.
      if (asciiTaulu != null) {
         
         // Sijoitetaan ladatusta kuvasta löydetyt merkit uuteen
         // filtteritauluun siten, että kun löydetään merkki,
         // sijoitetaan se uuteen tauluun sitä vastaavana numerona.
         for (int rivi = 0; rivi < asciiTaulu.length; rivi++) {
            for (int sarake = 0; sarake < asciiTaulu[0].length; sarake++) {
               if (asciiTaulu[rivi][sarake] == MUSTA0) {
                  filtteriTaulu[rivi][sarake] = 0;
               }
               else if (asciiTaulu[rivi][sarake] == HARMAA1) {
                  filtteriTaulu[rivi][sarake] = 1;
               }
               else if (asciiTaulu[rivi][sarake] == HARMAA2) {
                  filtteriTaulu[rivi][sarake] = 2;
               }
               else if (asciiTaulu[rivi][sarake] == HARMAA3) {
                  filtteriTaulu[rivi][sarake] = 3;
               }
               else if (asciiTaulu[rivi][sarake] == HARMAA4) {
                  filtteriTaulu[rivi][sarake] = 4;
               }
               else if (asciiTaulu[rivi][sarake] == HARMAA5) {
                  filtteriTaulu[rivi][sarake] = 5;
               }
               else if (asciiTaulu[rivi][sarake] == HARMAA6) {
                  filtteriTaulu[rivi][sarake] = 6;
               }
               else if (asciiTaulu[rivi][sarake] == HARMAA7) {
                  filtteriTaulu[rivi][sarake] = 7;
               }
               else if (asciiTaulu[rivi][sarake] == HARMAA8) {
                  filtteriTaulu[rivi][sarake] = 8;
               }
               else if (asciiTaulu[rivi][sarake] == HARMAA9) {
                  filtteriTaulu[rivi][sarake] = 9;
               }
               else if (asciiTaulu[rivi][sarake] == HARMAA10) {
                  filtteriTaulu[rivi][sarake] = 10;
               }
               else if (asciiTaulu[rivi][sarake] == HARMAA11) {
                  filtteriTaulu[rivi][sarake] = 11;
               }
               else if (asciiTaulu[rivi][sarake] == HARMAA12) {
                  filtteriTaulu[rivi][sarake] = 12;
               }
               else if (asciiTaulu[rivi][sarake] == HARMAA13) {
                  filtteriTaulu[rivi][sarake] = 13;
               }
               else if (asciiTaulu[rivi][sarake] == HARMAA14) {
                  filtteriTaulu[rivi][sarake] = 14;
               }
               else if (asciiTaulu[rivi][sarake] == VALKOINEN15) {
                  filtteriTaulu[rivi][sarake] = 15;
               }
            }
         }
      }
      // Palautetaan tehty uusi taulukko.
      return filtteriTaulu;
   }
   
   /* Operaatio, joka filtteröi ohjelmalle annetun "kuva"tiedoston.
    * Filtteri toimii niin, että se liikkuu kuvan päällä 
    * ja yksinkertaistaa sitä poistamalla kuvasta "turhat" osat
    * ja korvaa ne mediaanin laskemalla kuvamerkillä.
    */
   public static int[][] filtteroiTaulu(int[][] filtteriTaulu, int filtterinSivu, char[][] asciiTaulu) {
      
      // Luodaan taulukot suodatusta varten.
      // Tähän taulukkoon sijoitetaan mediaani.
      int[][] mediaaniTaulu = new int [filtteriTaulu.length][filtteriTaulu[0].length];
      // Ikkuna, joka läpikäy "kuvaa".
      int[] ikkuna = new int [filtterinSivu * filtterinSivu];
      
      // Tarkistetaan, että taulukolle on varattu muistia.
      if (filtteriTaulu != null) {
         // Muutetaan merkit numeroiksi ja sijoitetaan ne.
         for (int i = 0; i < filtteriTaulu.length; i++) {
            for (int j = 0; j < filtteriTaulu[0].length; j++) {
               for (int k = 0; k < merkkiTaulu.length; k++) {
                  // Sijoitus int-tauluun.
                  if (asciiTaulu[i][j] == merkkiTaulu[k]) {
                     mediaaniTaulu[i][j] = k;
                  }
               }
            }
         }
         // Poistetaan filtterin ylimääräiset reunat jakamalla
         // suotimen sivu kahdella.
         int reunanLeveys = filtterinSivu / 2;
         
            // Suodatetaan poistamalla ensimmäinen (tyhjä)rivi,
            // samoin sarakkeiden kanssa.
            for (int rivi = reunanLeveys; rivi < filtteriTaulu.length - reunanLeveys; rivi++) {
               for (int sarake = reunanLeveys; sarake < filtteriTaulu[0].length - reunanLeveys; sarake++) {
                  
                  // Nollataan apumuuttuja kierrosten välissä.
                  int i = 0;
                  
                  // Sijoitetaan ikkunaan filtteröidyn taulukon merkki.
                  for (int rivi1 = rivi - reunanLeveys; rivi1 < rivi + reunanLeveys + 1; rivi1++) {
                     for (int sarake1 = sarake - reunanLeveys; sarake1 < sarake + reunanLeveys + 1; sarake1++) {
                        ikkuna[i] = filtteriTaulu[rivi1][sarake1];
                        // Laskurin päivitys.
                        i++;
                     }
                  }
                  // Operaation kutsu. Lasketaan mediaani järjestämällä
                  // ikkunan numerot ja sijoittamalla ne uuteen taulukkoon.
                  int mediaani = jarjestaNumerot(ikkuna);
                  mediaaniTaulu[rivi][sarake] = mediaani;
               }
            }
      }
      // Palautetaan taulukko mainiin.
      return mediaaniTaulu;
   }
   
   /* Operaatio, joka järjestää annetut int-tyyppiset
    * numerot pienimmästä suurimpaan ja laskee sekä palauttaa mediaanin.
    */
   public static int jarjestaNumerot(int[] ikkuna) {
      
      // Vakiomuuttuja ikkunataulukon pituudelle
      final int taulukonKoko = ikkuna.length;
      // Apumuuttuja merkkien paikkojen vaihtamiseen.
      int a;
      // Muuttuja mediaanin laskemista varten.
      int katkaistu = ikkuna.length / 2;
      
      // Tarkistetaan, että taulukolle on varattu muistia.
      if (ikkuna != null) {
         
         // Haetaan numerot järjestyksessä taulukosta
         // ja tarkistetaan aina, onko seuraava numero suurempi
         // kuin edellinen numero. Tämän jälkeen sijoitetaan
         // numero oikeaan kohtaan.
         for (int i = 0; i < taulukonKoko; i++) {
            for (int j = i + 1; j < taulukonKoko; j++) {
               // Nollataan apumuuttuja.
               a = 0;
               
               // Vaihdetaan numeroiden paikkoja (jos tarvitsee).
               if (ikkuna[i] > ikkuna[j]) {
                  // Apumuuttuja saa ensimmäisen indeksin arvon.
                  a = ikkuna[i];
                  // Ensimmäinen indeksi saa taas seuraavan indeksin arvon.
                  ikkuna[i] = ikkuna [j];
                  // Toinen indeksi saa apumuuttujan arvon.
                  ikkuna[j] = a;
               }
            }
         }
      }
      // Mediaani on taulukon keskimmäinen luku, joka palautetaan.
      int mediaani = ikkuna[katkaistu];
      return mediaani;
   }
}