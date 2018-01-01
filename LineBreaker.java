/*
 * Harjoitustyˆ I
 *
 * Lausekielinen ohjelmointi II, syksy 2016.
 *
 * Jirka Lilja, lilja.jirka.j@student.uta.fi.
 *
 * LineBreaker
 *
 */

public class LineBreaker {
   public static void main(String[] args) {
      
      // Vakioiden m‰‰rittely
      final int MINALUEENLEVEYS = 3;
      final char EROTIN = ' ';
      final char RIVINPAATOS = '/';
      final char KYLLA = 'y';
      final char EI = 'n';
      final String VIRHEILMOITUS = "Error!";
      
      // Kerrotaan k‰ytt‰j‰lle mit‰ ohjelma tekee.
      System.out.println("Hello! I break lines.");
      
      // Syˆtett‰v‰n leveyden m‰‰rittely ennen silmukkaa.
      int leveys = 0;
      
      // Lippujen m‰‰rittely.
      boolean jatketaan = true;
      boolean alueOK;
      
      do {
         do {
            // Kysyt‰‰n ja luetaan alueen leveys.
            System.out.println("Enter area width:");
            leveys = In.readInt();
            
            // Oletetaan annettu arvo v‰‰r‰ksi.
            alueOK = false;
            
            // Leveyden oltava v‰hint‰‰n 3 ja korkeintaan annettu leveys.
            if (leveys >= MINALUEENLEVEYS) {
               // K‰‰nnet‰‰n lippu jos toteutuu.
               alueOK = true;
            }
            else {
               // Tulostetaan virheilmoitus jos annettu arvo ei ole halutunlainen.
               System.out.println(VIRHEILMOITUS);
            }
         }
         // Jatketaan kysymist‰ kunnes saadaan haluttu arvo.
         while (!alueOK);
         
         // Lippu tekstin tarkistamiseen (k‰ytet‰‰n silmukan lopussa).
         boolean tekstiOK = false;
         
         // Muuttujien m‰‰rittely.
         String teksti;
         int tekstinPituus = 0;
         char nykyinenMerkki;
         
         do {
            // Pyydet‰‰n k‰ytt‰j‰‰ syˆtt‰m‰‰n teksti‰.
            System.out.println("Enter a line:");
            teksti = In.readString();
            
            // Muunnetaan annettu teksti numeroiksi.
            tekstinPituus = teksti.length();
            
            // Luetaan apumuuttujien avulla tekstin ensimm‰inen ja viimeinen merkki.
            char ekaMerkki = teksti.charAt(0);
            char vikaMerkki = teksti.charAt(tekstinPituus - 1);
            
            // M‰‰ritell‰‰n muuttujat tekstin pituuden arvioimiseksi.
            int nykyisenOsanPituus = 0;
            int pisimmanOsanPituus = 0;
            int indeksiarvo = 0;
            
            // Tarkistetaan onko annetussa tekstiss‰ pitempi osa kuin mit‰ alueen leveys on.
            while (indeksiarvo < tekstinPituus){
               // Asetetaan merkkijonon nykyinen merkki apumuuttujaan.
               nykyinenMerkki = teksti.charAt(indeksiarvo);
               if (nykyinenMerkki == EROTIN || indeksiarvo == tekstinPituus - 1){
                  // Rivin lopussa ei ole v‰limerkki‰.
                  if (indeksiarvo == tekstinPituus - 1){
                  nykyisenOsanPituus++;
                  }
                  // Lˆydettiin pisint‰ osaa pidempi osa.
                  if (nykyisenOsanPituus > pisimmanOsanPituus){
                     // Muistetaan pituus.
                     pisimmanOsanPituus = nykyisenOsanPituus;
                  }
                  // Nollataan nykyisen osan pituus ja aloitetaan seuraavan osan laskeminen.
                  nykyisenOsanPituus = 0;
               }
               // Jatketaan yhden osan pituuden laskemista.
               else {
                  nykyisenOsanPituus++;
               }
               // Siirryt‰‰n seuraavaan merkkiin.
               indeksiarvo++;
            }

            // Tutkitaan toistuuko v‰limerkki annetussa tekstiss‰.
            // Muuttuja v‰limerkille.
            char vali = ' ';

            // Tosi, jos v‰limerkki toistuu. Arvataan ettei merkki toistu.
            boolean toistuu = false;
      
            // Haetaan, jos merkki voi toistua.
            if (tekstinPituus > 1) {
               // Laskuri merkkijonon indeksiarvoille.
               int ind = 0;
         
               // Haetaan korkeintaan toiseksi viimeiseen merkkiin saakka,
               // jotta ohjelma ei kaadu, kun tutkitaan seuraavaa merkki‰.
               // Silmukka pys‰htyy ennen yl‰rajaansa, jos lˆydet‰‰n toistuva
               // merkki.
               while (ind < tekstinPituus - 1 && !toistuu) {
                  // Apumuuttujat nykyiselle ja seuraavalle merkille.
                  nykyinenMerkki = teksti.charAt(ind);
                  char seuraavaMerkki = teksti.charAt(ind + 1);
            
                  // K‰‰nnet‰‰n lippu, jos lˆydet‰‰n kaksi v‰li‰ per‰kk‰in.
                  if (nykyinenMerkki == vali && seuraavaMerkki == vali) {
                     toistuu = true;
                  }
                  // Siirryt‰‰n seuraavaan merkkiin.
                  else {
                     ind++;
                  }
               }
            }
            
            // Annettu teksti on hyv‰ksytt‰v‰ jos:
            // tekstiss‰ ei ole kahta v‰li‰ per‰kk‰in
            // JA teksti ei ala v‰limerkill‰
            // JA teksti ei lopu v‰limerkkiin.
            if (!toistuu && ekaMerkki != EROTIN && vikaMerkki != EROTIN && pisimmanOsanPituus <= leveys) {
               // K‰‰nnet‰‰n lippu.
               tekstiOK = true;
            }
            else {
               // Tulostetaan virhe jos annettu teksti ei ole sallituissa rajoissa.
               System.out.println(VIRHEILMOITUS);
            }
         }
         // Jatketaan kysymist‰ kunnes saadaan halutunlainen teksti.
         while (!tekstiOK);

         // Katkotaan rivi‰, kunnes se on sovitettu alueeseen.
         boolean jatketaanRivitysta = false;
         
         // M‰‰ritell‰‰n osa tulostuksessa tarvittavista muuttujista.
         tekstinPituus = teksti.length();
         int katkaisu = 0;
         int indeksi = 0;
         int viimeinenIndeksi = 0;
         char nykyinenMerkk;
         
         do {
            
            // Etsit‰‰n tekstin katkaisukohdat ja muistetaan tekstin pituus
            // (sek‰ sen j‰lkeen tulostettava tyhj‰ tila).
            while (indeksi < tekstinPituus && indeksi <= leveys){
               // Sijoitetaan merkkijonon nykyinen merkki apumuuttujaan.
               nykyinenMerkk = teksti.charAt(indeksi);
               // Osa lˆydet‰‰n kun kohdataan v‰limerkki tai ollaan tekstin lopussa.
               if (nykyinenMerkk == EROTIN || indeksi == tekstinPituus - 1){
                  katkaisu = indeksi;
                  // Rivin lopussa ei ole v‰limerkki‰.
                  if (indeksi == tekstinPituus - 1) {
                     katkaisu++;
                  }
               }
               // Siirryt‰‰n seuraavaan merkkiin.
               indeksi++;
            }
            
            // M‰‰ritell‰‰n tulostettavat rivin alku ja rivin loppu tyhjiksi.
            String rivinAlku = "";
            String rivinLoppu = "";
            
            // Etsit‰‰n tulostettavat merkit.
            for (int a = 0; a < katkaisu; a++) {
               rivinAlku = rivinAlku + teksti.charAt(a);
            }
            
            for (int b = (katkaisu + 1); b < tekstinPituus; b++){
               rivinLoppu = rivinLoppu + teksti.charAt(b);
            }
            
            // Tulostetaan rivin alku eli sanat (ennen mahdollisia v‰lilyˆntej‰).
            System.out.print(rivinAlku);
            
            for (int c = rivinAlku.length(); c < leveys; c++){
               // Tulostetaan v‰limerkit sanojen j‰lkeen jos tarvitaan.
               System.out.print(EROTIN);
            }
            // Tulostetaan rivinp‰‰tˆs eli / ja rivinvaihto.
            System.out.print(RIVINPAATOS);
            System.out.println();
            
            teksti = rivinLoppu;
            
            // P‰ivitet‰‰n rivin/tekstin pituus.
            tekstinPituus = teksti.length();
            
            // Jos teksti‰ ei en‰‰ tulostettavana niin lopetetaan tulostus.
            if (tekstinPituus == 0){
               jatketaanRivitysta = true;
            }
            // Nollataan indeksin arvo.
            indeksi = 0;
         }
         // Jatketaan rivitt‰mist‰ jos teksti‰ j‰ljell‰.
         while (!jatketaanRivitysta);
         
         // Muuttuja k‰ytt‰j‰n antamalle arvolle.
         char valinta;
         
         // Lippumuuttuja.
         boolean syoteOK;
         
         do {
            // Kysyt‰‰n jatketaanko tekstin antamista.
            System.out.println("Continue (y/n)?");
            valinta = In.readChar();
            // Jos k‰ytt‰j‰n valinta on 'y' tai 'n' niin valinta on hyv‰ksytt‰v‰.
            if (valinta == KYLLA || valinta == EI){
               syoteOK = true;
               if (valinta == KYLLA){
                  // Jatketaan ohjelmaa ja palataan alkuun.
                  jatketaan = true;
               }
               else if (valinta == EI){
                  // Jos valinta on 'n' niin k‰‰nnet‰‰n
                  // lippu ja lopetetaan ohjelma
                  jatketaan = false;
               }
            }
            else {
               // Tulostetaan virheilmoitus jos annettu arvo ei ole 'y' tai 'n'.
               System.out.println(VIRHEILMOITUS);
               syoteOK = false;
            }
         }
         // Jatketaan kysymist‰ kunnes saadaan haluttu vastaus.
         while(!syoteOK);
      }
      // Kun k‰ytt‰j‰n antama valinta on 'y', jatketaan ja palataan koodin alkuun.
      while (jatketaan);
      
      // Jos k‰ytt‰j‰n valinta on 'n', hyv‰stell‰‰n ja ohjelma loppuu t‰h‰n.
      System.out.println("See you soon.");
   }
}