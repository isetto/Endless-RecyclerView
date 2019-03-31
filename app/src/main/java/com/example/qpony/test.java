package com.example.qpony;

import android.media.Image;

import com.example.qpony.Network.Model.Currencies;

import java.util.List;

public class test {

    List<Image> imageList;

    public void add(List<Image> images){

        for(Image im: images){
            imageList.add(im);
        }

    }
}
