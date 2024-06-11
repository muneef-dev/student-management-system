package com.developerstack.edumanage.util.tools;

import java.util.Random;

public class VerificationCodeGenerator {
    private final String NUMBERS = "0123456789"; // iza private final illamalum veachchalaam but izu poatta nallam

    // izula case ondeera first number 0 aa eendha print aahurulla azukkaana soution
    public int getCode(int length){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            // loop la 1 mura (0) iterate aahakola zero da char value a eduththu azu 48 equal inda
            // aza 0 va eduththu innorum random number a 1-10 random numbers ikkulla eduththu assign seyyanum
            char selectedNumber = NUMBERS.charAt(new Random().nextInt(10));
            if (i==0 && 48 == (int)selectedNumber){
                // selectedNumber++; // ippidi poatta iza gess panna ealum indu manasukku vandhichichii
                selectedNumber = NUMBERS.charAt(new Random().nextInt(10-1+1)+1);
            }
            sb.append(selectedNumber);

        }
        return Integer.parseInt(sb.toString()); // sb a string aakki aza integer ikku maaththeera
    }

    /*public int getCode(int length){ // ethna numbers len code veanum indratha length aa edukkuzu
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            // NUMBERS.charAt(0); // charAt() ikku kudukku index da value a return seyyum
            // charAt ikulla 0-9 varakkimulla numbers aala random numbers a uruvaakkanum
            sb.append(NUMBERS.charAt(new Random().nextInt(10))); // 0-9 number a sb ikku append senjeera
            // for loop da detail
            // naaga getCode(5) ena pass senja azu for loopa 5 mura iterate seyyakola 5 number randoma uruvaahi azu
            // stringbuilder ikku append aahum ex 45215
            // so iza method apa int a return seyyanum so use .toString

            // izula case ondeera first number 0 aa eendha print aahurulla so
        }
        return Integer.parseInt(sb.toString()); // sb a string aakki aza integer ikku maaththeera
    }*/
}
