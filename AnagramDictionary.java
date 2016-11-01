package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();

    HashSet<String> wordset=new HashSet<String>();
    ArrayList<String> wordlist=new ArrayList<String>();
    HashMap<String,ArrayList<String>> lettersToWord=new HashMap<String,ArrayList<String>>();
    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordset.add(word);
            wordlist.add(word);
            String sorted=helperFunction(word);
            if(lettersToWord.containsKey(sorted))
            {

                lettersToWord.get(sorted).add(word);
            }
            else
            {
                ArrayList<String> al=new ArrayList<String>();
                al.add(word);
                lettersToWord.put(sorted,al);
            }


        }



    }

    String helperFunction(String word)
    {
        String sortedWord="";
        ArrayList<String> al=new ArrayList<String>();
        for(int i=0;i<word.length();i++)
        {
            al.add(word.charAt(i)+"");
        }
        Collections.sort(al);

        for(int i=0;i<word.length();i++)
        {
        sortedWord=sortedWord+al.get(i);
        }
       return sortedWord;

    }


    public boolean isGoodWord(String word, String base) {


        if(wordlist.contains(word))
        {
            if(word.contains(base))
            {
                return false;
            }
            else
                return true;
        }
        return false;
    }

    public final static String ALPHABET="abcdefghijklmnopqrstuvwxyz";
    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        ArrayList<String>  temp = new ArrayList<String>();
        String key="";
        String newword="";
        for(int i=0;i<26;i++)
        {
            newword=word+ALPHABET.charAt(i);
            key=helperFunction(newword);
            if(lettersToWord.containsKey(key))
            {
                temp=lettersToWord.get(key);


                Iterator<String> iterator=temp.iterator();
                while(iterator.hasNext())
                {
                    String t=iterator.next();
                    if(isGoodWord(t,word))
                    {
                        result.add(t);
                    }
                }
            }


        }


        return result;
    }

    public String pickGoodStarterWord() {

        ArrayList<String> al=new ArrayList<String>();
        String currentword="";
        String key="";

       int index=random.nextInt();

        while(index<0||index>wordlist.size())
        {
             index=random.nextInt();
        }
        for(int i=index;i<(wordlist.size());i=(i+1)%wordlist.size())
        {
            currentword=wordlist.get(i);
            key=helperFunction(currentword);
            if(lettersToWord.containsKey(key))
            {
                al=lettersToWord.get(key);
                if(al.size()== MIN_NUM_ANAGRAMS)
                {
                    return currentword;
                }

            }

        }
        return "foo";
    }
}
