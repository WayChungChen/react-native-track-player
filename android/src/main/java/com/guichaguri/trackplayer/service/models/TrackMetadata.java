package com.guichaguri.trackplayer.service.models;

import static android.support.v4.media.MediaMetadataCompat.METADATA_KEY_ALBUM;
import static android.support.v4.media.MediaMetadataCompat.METADATA_KEY_ARTIST;
import static android.support.v4.media.MediaMetadataCompat.METADATA_KEY_ART_URI;
import static android.support.v4.media.MediaMetadataCompat.METADATA_KEY_DATE;
import static android.support.v4.media.MediaMetadataCompat.METADATA_KEY_DURATION;
import static android.support.v4.media.MediaMetadataCompat.METADATA_KEY_GENRE;
import static android.support.v4.media.MediaMetadataCompat.METADATA_KEY_RATING;
import static android.support.v4.media.MediaMetadataCompat.METADATA_KEY_TITLE;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.RatingCompat;

import androidx.media3.common.util.UnstableApi;

import com.guichaguri.trackplayer.service.Utils;

@UnstableApi
public abstract class TrackMetadata {
    public Uri artwork;

    public String title;
    public String artist;
    public String album;
    public String date;
    public String genre;
    public long duration;

    public String lyricsWhole;


    public RatingCompat rating;

    public void setMetadata(Context context, Bundle bundle, int ratingType) {
        artwork = Utils.getUri(context, bundle, "artwork");

        title = bundle.getString("title");
        artist = bundle.getString("artist");
        album = bundle.getString("album");
        date = bundle.getString("date");
        genre = bundle.getString("genre");
        duration = Utils.toMillis(bundle.getDouble("duration", 0));
        lyricsWhole = bundle.getString("lrc");

        rating = Utils.getRating(bundle, "rating", ratingType);
    }

    public MediaMetadataCompat.Builder toMediaMetadata() {
        MediaMetadataCompat.Builder builder = new MediaMetadataCompat.Builder();

        builder.putString(METADATA_KEY_TITLE, title);
        builder.putString(METADATA_KEY_ARTIST, artist);
        builder.putString(METADATA_KEY_ALBUM, album);
        builder.putString(METADATA_KEY_DATE, date);
        builder.putString(METADATA_KEY_GENRE, genre);

        if (duration > 0) {
            builder.putLong(METADATA_KEY_DURATION, duration);
        }

        if (artwork != null) {
            builder.putString(METADATA_KEY_ART_URI, artwork.toString());
        }

        if (rating != null) {
            builder.putRating(METADATA_KEY_RATING, rating);
        }

        if (lyricsWhole != null) {
          builder.putString("ucar.media.metadata.LYRICS_WHOLE", lyricsWhole);
        }

        return builder;
    }

}
