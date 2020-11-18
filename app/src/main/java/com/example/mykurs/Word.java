package com.example.mykurs;


import java.util.ArrayList;
import java.util.Random;

public class Word {

    private int index;

    private int score;

    ArrayList<String> dictionary = new ArrayList<>();

    String testWord;

    public ArrayList<Character> organizedCharacters = new ArrayList<>();

    public ArrayList<Character> wordLetters = new ArrayList<>();

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Word(int index, int score) {
        this.index = index;
        this.score = score;
    }

    public ArrayList<Character> decompose(String word) {
        for (int i = 0; i < word.length(); i++) {
            wordLetters.add(i, word.charAt(i));
        }
        return wordLetters;
    }

    public ArrayList<Character> generateWord(ArrayList<Character> decomposedWord, int startIndex) {

        if (index == 1) {
            if (startIndex == 0) {
                for (int i = 0; i < decomposedWord.size(); i++) {
                    if (i % 2 == 0) {
                        organizedCharacters.add(i, decomposedWord.get(i));
                    } else {
                        organizedCharacters.add(i, '-');
                    }
                }
            }

            if (startIndex == 1) {
                for (int i = 0; i < decomposedWord.size(); i++) {
                    if (i % 2 != 0) {
                        organizedCharacters.add(i, decomposedWord.get(i));
                    } else {
                        organizedCharacters.add(i, '-');
                    }
                }
            }
            if (startIndex == 2) {
                for (int i = 0; i < decomposedWord.size(); i++) {
                    if (i == 1 || i == 4 || i == 5 || i == 8 || i == 9) {
                        organizedCharacters.add(i, decomposedWord.get(i));
                    } else {
                        organizedCharacters.add(i, '-');
                    }
                }
            }

            if (startIndex == 3) {
                for (int i = 0; i < decomposedWord.size(); i++) {
                    if (i == 0 || i == 5 || i == 6 || i == 7 || i == 11) {
                        organizedCharacters.add(i, decomposedWord.get(i));
                    } else {
                        organizedCharacters.add(i, '-');
                    }
                }
            }
        }
        if (index == 2) {
            int hiden = new Random().nextInt(decomposedWord.size());
            for (int i = 0; i < decomposedWord.size(); i++) {
                if (i == hiden) {
                    organizedCharacters.add(i, decomposedWord.get(i));
                } else {
                    organizedCharacters.add(i, '-');
                }
            }
        }
        return organizedCharacters;

    }

    public boolean guessingCharachter(String c, int type) {

        boolean valeur = false;

        if (c.length() == 1) {

            if (wordLetters.contains(c.charAt(0)) && organizedCharacters.contains(c.charAt(0))) {
                int count = 0;
                for (Character wordLetter : wordLetters) {
                    if (wordLetter.equals(c.charAt(0))) count++;
                }
                if (count > 1) {
                    for (int i = 0; i < organizedCharacters.size(); i++) {
                        if (wordLetters.get(i).equals(c.charAt(0)) && !organizedCharacters.get(i).equals(c.charAt(0))) {
                            organizedCharacters.set(i, c.charAt(0));
                            if (type == 2) score += 5;
                            else score += 3;
                            valeur = true;
                        }
                    }
                }
            }
            if (wordLetters.contains(c.charAt(0)) && !organizedCharacters.contains(c.charAt(0))) {
                valeur = true;
                for (int i = 0; i < wordLetters.size(); i++) {
                    if (wordLetters.get(i).equals(c.charAt(0))) {
                        organizedCharacters.set(i, c.charAt(0));
                        if (type == 2) score += 5;
                        else score += 3;
                    }
                }

            }
        } else if (c.length() < wordLetters.size() || c.length() > wordLetters.size()) {
            valeur = false;
        } else {
            int delta = 0;
            for (int i = 0; i < wordLetters.size(); i++) {
                if (wordLetters.get(i).equals(c.charAt(i))) {
                    valeur = true;
                    if (!organizedCharacters.get(i).equals(c.charAt(i))) delta++;
                } else {
                    return false;
                }
            }
            if (type == 2) score += delta * 5;
            else score += delta * 3;
            if (valeur) {
                for (int i = 0; i < wordLetters.size(); i++) {
                    organizedCharacters.set(i, c.charAt(i));
                }
            }

        }
        return valeur;
    }

    public String help1(String organised, String word, int choice) {
        String found = null;

        if (choice == 2) {
            Random r = new Random();
            int letter;
            do {
                letter = r.nextInt(word.length());
            } while (!(organised.charAt(letter) == '-'));

            found = String.valueOf(word.charAt(letter));

        }

        return found;
    }
}
