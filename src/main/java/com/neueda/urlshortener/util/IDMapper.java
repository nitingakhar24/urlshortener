package com.neueda.urlshortener.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IDMapper 
{
    public static final IDMapper INSTANCE = new IDMapper();

    private IDMapper() 
    {
        populatecharToIndexMap();
        populateindexToCharList();
    }

    private static HashMap<Character, Integer> charToIndexMap;
    private static List<Character> indexToCharList;

    private void populatecharToIndexMap() 
    {
        charToIndexMap = new HashMap<>();
        // 0->a, 1->b, ..., 25->z, ..., 52->0, 61->9
        for (int i = 0; i < 26; ++i) 
        {
            char c = 'a';
            c += i;
            charToIndexMap.put(c, i);
        }
        for (int i = 26; i < 52; ++i) 
        {
            char c = 'A';
            c += (i-26);
            charToIndexMap.put(c, i);
        }
        for (int i = 52; i < 62; ++i) 
        {
            char c = '0';
            c += (i - 52);
            charToIndexMap.put(c, i);
        }
    }

    private void populateindexToCharList() 
    {
        // 0->a, 1->b, ..., 25->z, ..., 52->0, 61->9
        indexToCharList = new ArrayList<>();
        for (int i = 0; i < 26; ++i) {
            char c = 'a';
            c += i;
            indexToCharList.add(c);
        }
        for (int i = 26; i < 52; ++i) {
            char c = 'A';
            c += (i-26);
            indexToCharList.add(c);
        }
        for (int i = 52; i < 62; ++i) {
            char c = '0';
            c += (i - 52);
            indexToCharList.add(c);
        }
    }

    public static String createUniqueID(Long id) {
        List<Integer> base62ID = convertBase10ToBase62ID(id);
        StringBuilder uniqueURLID = new StringBuilder();
        for (int digit: base62ID) 
        {
            uniqueURLID.append(indexToCharList.get(digit));
        }
        return uniqueURLID.toString();
    }

    private static List<Integer> convertBase10ToBase62ID(Long id) 
    {
        List<Integer> digits = new ArrayList<>();
        while(id > 0) {
            int remainder = (int)(id % 62);
            digits.add(remainder);
            id /= 62;
        }
        return digits;
    }

    public static Long getDictionaryKeyFromUniqueID(String uniqueID) 
    {
        List<Character> base62IDs = new ArrayList<>();
        for (int i = 0; i < uniqueID.length(); ++i) 
        {
            base62IDs.add(uniqueID.charAt(i));
        }
        Long dictionaryKey = convertBase62ToBase10ID(base62IDs);
        return dictionaryKey;
    }

    private static Long convertBase62ToBase10ID(List<Character> ids) 
    {
        long id = 0L;
        for (int i = 0, exp = ids.size() - 1; i < ids.size(); ++i, --exp) 
        {
            int base10 = charToIndexMap.get(ids.get(i));
            id += (base10 * Math.pow(62.0, exp));
        }
        return id;
    }
}