package com.example.umberto.javajoke;

import java.util.Random;

public class Joke {
    Random rn = new Random();

    //Return random joke from 0 to 9 index
    public String getJoke(){
        int index=rn.nextInt(10);
        switch (index){
            case 0:
                return "Teacher: 'Jack, what do you know about the Dead Sea?'\n" +
                        "Jack: 'I didn't even know it was ill'";
            case 1:
                return "A doctor calls his patient and says: 'The check you gave me for my bill came back'.\nThe patient replied: 'So did my cold!'";
            case 2:
                return "Teacher: 'Clara please point to America on the map.'\n" +
                        "Clara: 'This is it.'\n" +
                        "Teacher: 'Well done. Now class, who found America?'\n" +
                        "Class: 'Clara did.'";
            case 3:
                return "The teacher says: “I wish you’d pay a little attention, Victoria.”\n" +
                        "“I am paying as little as I can Mrs. Sanders,” said Victoria.";
            case 4:
                return "What is the longest word in the English language?\n" +
                        "“Smiles”.\n" +
                        "Because there is a mile between its first and last letters!";
            case 5:
                return "Where do zombies go swimming?\n" +
                        "In the Dead Sea!";
            case 6:
                return "Doctor: 'What's your problem?'\n" +
                        "Patient: 'I think I'm a chicken'.\n" +
                        "Doctor: 'How long has this been going on?'\n" +
                        "Patient: 'Ever since I was an egg!'";
            case 7:
                return "Mother: 'Why did you swallow the money I gave you?'\n" +
                        "Son: 'Well, you did say it was my lunch money!'";
            case 8:
                return "Do Apes kiss?\n" +
                        "Yes, but never on the first date!";
            case 9:
                return "What goes 'zzub zzub zzub zzub'?\n" +
                        "A bee flying backwars!";
            default:
                return "";
        }
    }
}
