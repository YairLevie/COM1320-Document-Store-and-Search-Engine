package edu.yu.cs.com1320.project.stage4.impl;

import edu.yu.cs.com1320.project.HashTable;
import edu.yu.cs.com1320.project.impl.HashTableImpl;
import edu.yu.cs.com1320.project.stage4.Document;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set; 

public class DocumentImpl implements Document{
    private URI uri;
    private String txt;
    private byte[] binaryData;
    private HashTable <String,String> MetaValue;
    private HashMap <String, Integer> wordTracker;


    public DocumentImpl(URI uri, String txt){ 
        if (txt == null || uri == null || txt.isBlank() || uri.toString().isBlank() ){
        throw new IllegalArgumentException();
    }
    else{
        this.uri = uri;
        this.txt = txt;
        this.MetaValue = new HashTableImpl<>();
        this.wordTracker = new HashMap<>();
        //use regex to get a string without punctuation
        String strippedWord = txt.replaceAll("\\p{Punct}", "");
        String[] words = strippedWord.split(" ");
        for(int i = 0; i < words.length; i++) {
            //if the word already exists in the map, increment the count of the word by one
            if(this.wordTracker.containsKey(words[i])) {
                int count = this.wordTracker.get(words[i]) + 1;
                this.wordTracker.put(words[i], count);
            }
            //if the word does not exist in the map, put the word in the map with a starting count of one
            else {
                this.wordTracker.put(words[i], 1);
            }
        }
    }
}
    public DocumentImpl(URI uri, byte[] binaryData){ 
    if (uri == null || binaryData == null || uri.toString().isBlank()  || binaryData.length==0){
        throw new IllegalArgumentException();
    }
    else{
        this.uri = uri;
        this.binaryData = binaryData;
        this.MetaValue = new HashTableImpl<>();
    }
}

    public String setMetadataValue(String key, String value){
        if (key == null || key.isBlank()){
            throw new IllegalArgumentException();
        }
        else {
           return MetaValue.put(key, value);
        }
    }

    public String getMetadataValue(String key){
        if (key == null || key.isBlank()){
            throw new IllegalArgumentException();
        }
        return MetaValue.get(key);
    }

    public HashTable<String, String> getMetadata(){
        HashTableImpl <String,String> copiedHashTable = new HashTableImpl<>();
        for (String key: this.MetaValue.keySet()){
            copiedHashTable.put(key,MetaValue.get(key));
        }
        return copiedHashTable;
    }

    public String getDocumentTxt(){
        return this.txt;
    }

    public byte[] getDocumentBinaryData(){
        return this.binaryData;
    }

    public URI getKey(){
        return this.uri;
    }

    public int wordCount(String word){
        String strippedWord = word.replaceAll("\\p{Punct}", "");
        if (this.binaryData != null){
            return 0;
        }
        else if (this.wordTracker.containsKey(strippedWord)){
            return this.wordTracker.get(strippedWord);
        }
        else{
            return 0;
        }
    }

    public Set<String> getWords(){
        Set<String> words = new HashSet<>();
        if (this.txt == null){
            return words;
        }
        else{
            words = this.wordTracker.keySet();
            return words;
        }
    }

    @Override
    public boolean equals(Object other){
        if (this == other){
            return true;
        }
        if (other == null){
            return false;
        }
        else if (!(other instanceof DocumentImpl)){
            return false;
        }
        DocumentImpl comparing = (DocumentImpl) other; 
        if (comparing.hashCode()==this.hashCode()){
            return true;
        }
        else{
            return false;
        }
        
    }
    @Override
    public int hashCode() {
        int result = uri.hashCode();
        result = 31 * result + (this.txt != null ? this.txt.hashCode() : 0); 
        result = 31 * result + Arrays.hashCode(binaryData);
        return Math.abs(result);
    }
}