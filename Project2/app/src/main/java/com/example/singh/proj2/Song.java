package com.example.singh.proj2;

import android.content.Context;
import android.content.res.Resources;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by singh on 02/23/18.
 */

public class Song {
    public String songName;
    public String artist;
    public String songUrl;
    public String songWikiURL;
    public String artistWiki;


    public static ArrayList<Song>ShowSong(){
        final ArrayList<Song> songsList = new ArrayList<>();
        //details
        ArrayList<String> loadSongList = new ArrayList<String>(Arrays.asList("Comfortably numb","Perfect Strangers","What about us","Cant stop the feeling","Stay","Ready for it"));
        ArrayList<String> loadArtistList = new ArrayList<String>(Arrays.asList("Pink Floyd","Jonas blue","Pink","Justin Timberlake","Zedd","Taylor Swift"));
        ArrayList<String> loadSongURLList = new ArrayList<String>(Arrays.asList("https://www.youtube.com/watch?v=_FrOQC-zEog","https://www.youtube.com/watch?v=Ey_hgKCCYU4","https://www.youtube.com/watch?v=ClU3fctbGls","https://www.youtube.com/watch?v=ru0K8uYEZWw","https://www.youtube.com/watch?v=LS2ifrLAadU","https://www.youtube.com/watch?v=wIft-t-MQuE"));
        ArrayList<String> loadSongWikiList = new ArrayList<String>(Arrays.asList("https://en.wikipedia.org/wiki/Comfortably_Numb","https://en.wikipedia.org/wiki/Perfect_Strangers_(Jonas_Blue_song)","https://en.wikipedia.org/wiki/What_About_Us_(Pink_song)","https://en.wikipedia.org/wiki/Can%27t_Stop_the_Feeling!","https://en.wikipedia.org/wiki/Stay_(Zedd_and_Alessia_Cara_song)","https://en.wikipedia.org/wiki/...Ready_for_It%3F"));
        ArrayList<String> loadArtistWikiList = new ArrayList<String>(Arrays.asList("https://en.wikipedia.org/wiki/Pink_Floyd","https://en.wikipedia.org/wiki/Jonas_Blue","https://en.wikipedia.org/wiki/Pink_(singer)","https://en.wikipedia.org/wiki/Justin_Timberlake","https://en.wikipedia.org/wiki/Zedd","https://en.wikipedia.org/wiki/Taylor_Swift"));


        //Creating song object for each item in arraylist
        for (int i=0;i< loadSongList.size();i++) {
            Song songDetail = new Song();
            songDetail.songName = loadSongList.get(i);
            songDetail.artist = loadArtistList.get(i);
            songDetail.songUrl = loadSongURLList.get(i);
            songDetail.songWikiURL=loadSongWikiList.get(i);
            songDetail.artistWiki=loadArtistWikiList.get(i);
            songsList.add(songDetail);
        }
        return songsList;




        }


    }




