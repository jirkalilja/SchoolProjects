/*
 * Harjoitusty� II
 *
 * Lausekielinen ohjelmointi II, syksy 2016.
 *
 * Jirka Lilja 90411
 *
 * lilja.jirka.j@student.uta.fi
 *
 * ASCII Art-ohjelma, jonka avulla tekstitiedosto ladataan
 * komentoriviparametri� k�ytt�en. Tekstitiedosto
 * saa sis�lt�� m��riteltyj� eri "harmaan" s�vyisi�
 * merkkej�. Tekstitiedoston voi tulostaa sellaisenaan
 * merkkimuotoisena, numeromuotoisena, sen voi filtter�id�
 * ja taas resetoida alkuper�iseen muotoon. Tiedostosta voi
 * tiedustella my�s sen sis�ll�n informaatiota.
 * Jokaisen komennon j�lkeen palataan kysym��n uutta
 * komentoa kunnes komento on "quit" ja ohjelma loppuu.
 *
 */

import java.io.*;

public class ASCIIArt {
   
   // Vakioiden m��rittely.
      
   // M��ritell��n harmaan eri s�vyt ja luodaan niist� vakioidut taulukot.
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
      
   // Vakio kysymykselle ja hyv�stelemiseen.
   public static final String KYSYMYSRIVI = "printa/printi/info/filter [n]/reset/quit?";
   public static final String HYVASTELE = "Bye, see you soon.";
      
   // M��ritell��n komentovakiot.
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
      
      // Tarkistetaan, ett� komentoriviparametrej� on yksi.
      if (args.length == 1) {
         
         // Ladattava tiedosto on ensimm�isess� komentoriviparametriss�.
         String tiedosto = args[0];
         
         // Tallennetaan tiedosto tarkastusta varten.
         File f = new File(tiedosto);
         
         // Tarkistetaan l�ytyyk� tiedosto.
         if (!f.exists()) {
            // Tulostetaan virhe jos tiedostoa ei l�ydy.
            System.out.println("Invalid command-line argument!");
            // Lopetetaan ohjelma.
            System.out.println(HYVASTELE);
         }
         // Jos tiedosto l�ytyy:
         else if (f.exists()) {
            // Operaation kutsu, kysyt��n komentoa.
            kysyKomento(tiedosto);
         }
      }
      else {
         // Tulostetaan virhe jos komentoriviparametrej� != 1.
         System.out.println("Invalid command-line argument!");
         // Lopetetaan ohjelma.
         System.out.println(HYVASTELE);
      }
   }

   /* P��operaatio, joka kysyy k�ytt�j�lt� uuden komennon
    * suoritettavaksi kunnes komento on "quit" ja ohjelma loppuu.
    * T�nne palataan aina kun annettu komento on suoritettu loppuun.
    */
   public static void kysyKomento(String tiedosto) {
      
      // Kutsutaan operaatiota, t�ytet��n taulukko.
      char[][] asciiTaulu = taytaTaulu(tiedosto);
            
      // Muuttuja k�ytt�j�n antamalle komennolle.
      String komento = "";
      
      // Muuttuja filtterin sivulle (sivu * sivu).
      int filtterinSivu = 0;
      
      do {
         // Tulostetaan kysymys.
         System.out.println(KYSYMYSRIVI);
         
         // Kysyt��n komentoa.
         komento = In.readString();
      
         // Kun halutaan tulostaa tiedosto sellaisenaan merkkimuodossa:
         if (komento.equals(TULOSTAMERKKEINA)) {
            // Operaation kutsu. Tulostetaan t�ytetty taulukko.
            tulostaKuva(asciiTaulu, tiedosto);
         }
         // Kun halutaan tulostaa tiedosto numero/lukumuodossa:
         else if (komento.equals(TULOSTALUKUINA)) {
            // Operaation kutsu. Tulostetaan numeromuotoinen taulukko.
            tulostaNumeroina(asciiTaulu, tiedosto);
         }
         // Kun halutaan informaatiota tiedoston sis�ll�st�:
         else if (komento.equals(TULOSTATIEDOT)) {
            // Operaation kutsu. Tulostetaan tiedoston sis�ll�n informaatio.
            tulostaTiedot(asciiTaulu, tiedosto);
         }
         // Kun komento on "filter",
         // suodatetaan "kuva" filtterin oletuskoolla (3 * 3):
         else if (komento.equals(SUODATA)) {
            // Filtteri saa oletuskoon jos sille ei anneta komennon per��n numeroa.
            filtterinSivu = OLETUSFILTTERI;
            
            // Kutsutaan operaatioita, lasketaan ja suodatetaan taulukon sis�lt�.
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
            
            // Kutsutaan operaatioita, lasketaan ja suodatetaan taulukon sis�lt�.
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
            // Kutsutaan operaatiota, t�ytet��n taulukko alkuper�isell� "kuvalla".
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
         
         // Luetaan rivej� niin kauan kun niit� l�ytyy.
         while (puskuroituLukija.ready()) {
            // Luetaan rivit tiedostosta.
            String rivi = puskuroituLukija.readLine();

            // P�ivitet��n laskuri.
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
   
   /* Luodaan ja t�ytet��n char-tyyppinen kaksiulotteinen taulukko.
    * Palautetaan taulukko jos alkiot saatiin sijoitettua.
    * Virhetilanteessa taulukko m��ritell��n null-arvoiseksi.
    */
   public static char[][] taytaTaulu(String tiedosto) {
      
      // Kutsutaan operaatiota.
      int luku = laskeRivitTiedostosta(tiedosto);
      
      // M��ritell��n taulukko null-arvoiseksi oletuksena.
      char[][] asciiTaulu = null;
      
      // M��ritell��n muuttuja pituus nollaksi.
      int pituus = 0;
      
      // Operaation kutsu. Lasketaan rivit tiedostosta.
      int rivit = laskeRivitTiedostosta(tiedosto);
      
      try {
         // Avataan tiedosto lukemista varten olion avulla.
         FileInputStream syotevirta = new FileInputStream(tiedosto);
         InputStreamReader lukija = new InputStreamReader(syotevirta);
         BufferedReader puskuroituLukija = new BufferedReader(lukija);
         
         // Rivi-indeksimuuttujan m��rittely.
         int j = 0;
         
         // Luetaan rivej� niin kauan kun niit� l�ytyy.
         while (puskuroituLukija.ready()) {
            // Luetaan rivit tiedostosta.
            String rivi = puskuroituLukija.readLine();
            pituus = rivi.length();
            
            // Ensimm�isell� kierroksella luodaan taulukko.
            if (j == 0) {
               asciiTaulu = new char[rivit][pituus];
            }
            // Sijoitetaan taulukkoon.
            for (int i = 0; i < pituus; i++) {
               asciiTaulu[j][i] = rivi.charAt(i);
            }
            // P�ivitet��n laskuri.
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
    * kaksiulotteisen taulukon sellaisenaan riveitt�in.
    */
   public static void tulostaKuva(char[][] asciiTaulu, String tiedosto) {
      
      // Tulostetaan taulukko jos varattu muistia.
      if (asciiTaulu != null) {
         for (int i = 0; i < asciiTaulu.length; i++) {
            for (int j = 0; j < asciiTaulu[i].length; j++) {
               System.out.print(asciiTaulu[i][j]);
         }
         // Vaihdetaan rivi� aina kun rivi on tulostettu.
         System.out.println();
         }
      }
   }
   
   /* Operaatio, joka tulostaa kaksiulotteisen taulukon
    * luku/numeromuodossa riveitt�in.
    */
   public static void tulostaNumeroina(char[][] asciiTaulu, String tiedosto) {
      
      // Tarkistetaan, ett� taulukolle on varattu muistia.
      if (asciiTaulu != null) {
         // Luodaan sarakkeiden apumuuttuja.
         int a = asciiTaulu[0].length;
         // Tulostetaan taulukko.
         for (int rivi = 0; rivi < asciiTaulu.length; rivi++) {
            for (int sarake = 0; sarake < asciiTaulu[0].length; sarake++) {
               for (int i = 0; i < merkkiTaulu.length; i++) {
                  if (asciiTaulu[rivi][sarake] == merkkiTaulu[i]) {
                     
                     // Jos merkin j�rjestysnumero on pienempi kuin 10 ja
                     // ollaan viimeisess� merkiss�, tulostetaan v�li ennen numeroa.
                     if (i < 10 && (sarake + 1) == a) {
                        System.out.print(" " + i);
                     }
                     // Jos edelleen j�rjestysnumero < 10, mutta ei
                     // olla viimeisess� merkiss�, tulostetaan v�li ennen ja j�lkeen numeron.
                     if (i < 10 && (sarake + 1) != a) {
                        System.out.print(" " + i + " ");
                     }
                     // Jos j�rjestysnumero on 10 tai suurempi
                     // ja ollaan viimeisess� merkiss�, tulostetaan vain merkki.
                     if (i >= 10 && (sarake + 1) == a) {
                        System.out.print(i);
                     }
                     // Jos j�rjestysnumero on 10 tai suurempi
                     // ja ei olla viimeisess� merkiss�, tulostetaan merkki ja v�li.
                     if (i >= 10 && (sarake + 1) != a) {
                        System.out.print(i + " ");
                     }
                  }
               }
            }
            // Vaihdetaan rivi�.
            System.out.println();
         }
      }
   }
   
   /* Operaatio, joka tulostaa haetun tiedoston pituuden ja leveyden
    * sek� informaation tiedoston merkkien esiintymist�.
    */
   public static void tulostaTiedot(char[][] asciiTaulu, String tiedosto) {
      
      // M��ritell��n muuttujat.
      int rivit = 0;
      int sarakkeet = asciiTaulu[0].length;
      
      // Haetaan tekstitiedoston rivit ja sarakkeet.
      for (int rivi = 0; rivi < asciiTaulu.length; rivi++) {
         for (int sarake = 0; sarake < asciiTaulu[0].length; sarake++) {
            
         }
         // P�ivitet��n rivilaskuria.
         rivit++;
      }
      // Tulosteaan tekstitiedoston koko.
      System.out.println(rivit + " x " + sarakkeet);
      
      // Haetaan merkkien esiintym�t.
      for (int i = 0; i < merkkiTaulu.length; i++) {
         // Nollataan esiintymien m��r� aina kierroksen alussa.
         int esiintymat = 0;
         
         for (int rivi = 0; rivi < asciiTaulu.length; rivi++) {
            for (int sarake = 0; sarake < asciiTaulu[0].length; sarake++) {
               // Jos l�ydettiin merkki...
               if (asciiTaulu[rivi][sarake] == merkkiTaulu[i]) {
                  // ...p�ivitet��n esiintymien laskuria.
                  esiintymat++;
            }
         }   
      }
      // Tulostetaan merkki ja sen esiintymien m��r�.
      System.out.println(merkkiTaulu[i] + " " + esiintymat);
      }
   }
   
   /* Operaatio, jossa tehd��n uusi kaksiulotteinen taulukko ladatun tiedoston perusteella
    * suodatusta varten. Operaatio palautta tehdyn taulukon.
    */
   public static int[][] laskeNumeroTaulu(char[][] asciiTaulu) {
      
      // Luodaan uusi taulukko.
      int[][] filtteriTaulu = new int [asciiTaulu.length][asciiTaulu[0].length];
      
      // Tarkistetaan, ett� muistia on varattu.
      if (asciiTaulu != null) {
         
         // Sijoitetaan ladatusta kuvasta l�ydetyt merkit uuteen
         // filtteritauluun siten, ett� kun l�ydet��n merkki,
         // sijoitetaan se uuteen tauluun sit� vastaavana numerona.
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
   
   /* Operaatio, joka filtter�i ohjelmalle annetun "kuva"tiedoston.
    * Filtteri toimii niin, ett� se liikkuu kuvan p��ll� 
    * ja yksinkertaistaa sit� poistamalla kuvasta "turhat" osat
    * ja korvaa ne mediaanin laskemalla kuvamerkill�.
    */
   public static int[][] filtteroiTaulu(int[][] filtteriTaulu, int filtterinSivu, char[][] asciiTaulu) {
      
      // Luodaan taulukot suodatusta varten.
      // T�h�n taulukkoon sijoitetaan mediaani.
      int[][] mediaaniTaulu = new int [filtteriTaulu.length][filtteriTaulu[0].length];
      // Ikkuna, joka l�pik�y "kuvaa".
      int[] ikkuna = new int [filtterinSivu * filtterinSivu];
      
      // Tarkistetaan, ett� taulukolle on varattu muistia.
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
         // Poistetaan filtterin ylim��r�iset reunat jakamalla
         // suotimen sivu kahdella.
         int reunanLeveys = filtterinSivu / 2;
         
            // Suodatetaan poistamalla ensimm�inen (tyhj�)rivi,
            // samoin sarakkeiden kanssa.
            for (int rivi = reunanLeveys; rivi < filtteriTaulu.length - reunanLeveys; rivi++) {
               for (int sarake = reunanLeveys; sarake < filtteriTaulu[0].length - reunanLeveys; sarake++) {
                  
                  // Nollataan apumuuttuja kierrosten v�liss�.
                  int i = 0;
                  
                  // Sijoitetaan ikkunaan filtter�idyn taulukon merkki.
                  for (int rivi1 = rivi - reunanLeveys; rivi1 < rivi + reunanLeveys + 1; rivi1++) {
                     for (int sarake1 = sarake - reunanLeveys; sarake1 < sarake + reunanLeveys + 1; sarake1++) {
                        ikkuna[i] = filtteriTaulu[rivi1][sarake1];
                        // Laskurin p�ivitys.
                        i++;
                     }
                  }
                  // Operaation kutsu. Lasketaan mediaani j�rjest�m�ll�
                  // ikkunan numerot ja sijoittamalla ne uuteen taulukkoon.
                  int mediaani = jarjestaNumerot(ikkuna);
                  mediaaniTaulu[rivi][sarake] = mediaani;
               }
            }
      }
      // Palautetaan taulukko mainiin.
      return mediaaniTaulu;
   }
   
   /* Operaatio, joka j�rjest�� annetut int-tyyppiset
    * numerot pienimm�st� suurimpaan ja laskee sek� palauttaa mediaanin.
    */
   public static int jarjestaNumerot(int[] ikkuna) {
      
      // Vakiomuuttuja ikkunataulukon pituudelle
      final int taulukonKoko = ikkuna.length;
      // Apumuuttuja merkkien paikkojen vaihtamiseen.
      int a;
      // Muuttuja mediaanin laskemista varten.
      int katkaistu = ikkuna.length / 2;
      
      // Tarkistetaan, ett� taulukolle on varattu muistia.
      if (ikkuna != null) {
         
         // Haetaan numerot j�rjestyksess� taulukosta
         // ja tarkistetaan aina, onko seuraava numero suurempi
         // kuin edellinen numero. T�m�n j�lkeen sijoitetaan
         // numero oikeaan kohtaan.
         for (int i = 0; i < taulukonKoko; i++) {
            for (int j = i + 1; j < taulukonKoko; j++) {
               // Nollataan apumuuttuja.
               a = 0;
               
               // Vaihdetaan numeroiden paikkoja (jos tarvitsee).
               if (ikkuna[i] > ikkuna[j]) {
                  // Apumuuttuja saa ensimm�isen indeksin arvon.
                  a = ikkuna[i];
                  // Ensimm�inen indeksi saa taas seuraavan indeksin arvon.
                  ikkuna[i] = ikkuna [j];
                  // Toinen indeksi saa apumuuttujan arvon.
                  ikkuna[j] = a;
               }
            }
         }
      }
      // Mediaani on taulukon keskimm�inen luku, joka palautetaan.
      int mediaani = ikkuna[katkaistu];
      return mediaani;
   }
}