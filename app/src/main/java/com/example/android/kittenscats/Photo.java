package com.example.android.kittenscats;

import java.io.Serializable;

/**
 * Created by Leshii on 6/29/2015.
 * Flickr photo item
 */
public class Photo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String owner;
    private String secret;
    private String server;
    private String farm;
    private String title;

    public Photo(String id, String owner, String secret, String server, String farm, String title) {
        this.id = id;
        this.owner = owner;
        this.secret = secret;
        this.server = server;
        this.farm = farm;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getOwner() {
        return owner;
    }

    public String getId() {
        return id;
    }

    public String toUrlString() {
        return "https://farm" + farm +
                ".staticflickr.com/" + server +
                "/" + id +
                "_" + secret + ".jpg";
    }

    //The letter suffixes are as follows:
//    s	small square 75x75
//    q	large square 150x150
//    t	thumbnail, 100 on longest side
//    m	small, 240 on longest side
//    n	small, 320 on longest side
//    -	medium, 500 on longest side
//    z	medium 640, 640 on longest side
//    c	medium 800, 800 on longest side†
//    b	large, 1024 on longest side*
//    h	large 1600, 1600 on longest side†
//    k	large 2048, 2048 on longest side†
//    o	original image, either a jpg, gif or png, depending on source format

    public String toUrlStringBySize(String size) {
        return "https://farm" + farm +
                ".staticflickr.com/" + server +
                "/" + id +
                "_" + secret +
                "_" + size + ".jpg";
    }

    // It's wrong. Original picture has own secret code
//    public String toUrlStringOriginal(String originalFormat) {
//        return "https://farm" + farm +
//                ".staticflickr.com/" + server +
//                "/" + id +
//                "_" + "o-" + secret +
//                "_o." + originalFormat;
//
//    }

    public String toUrlUserProfile() {
        return "https://www.flickr.com/people/" + owner + "/";
    }

    public String toUrlUserPhotostream() {
        return "https://www.flickr.com/photos/" + owner + "/";
    }

    public String toUrlPhotoPage() {
        return "https://www.flickr.com/photos/" + owner + "/" + id;
    }

    public String toUrlUserPhotosets() {
        return "https://www.flickr.com/photos/" + owner + "/sets/";
    }

    public String toShortUrl() {
        return "https://flic.kr/p/" + Utils.Base58.encode(Long.parseLong(id));
    }

}
