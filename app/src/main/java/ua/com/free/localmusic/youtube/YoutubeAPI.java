package ua.com.free.localmusic.youtube;
/*
 * Copyright (C) 2017 Funambol.
 * All Rights Reserved.  No use, copying or distribution of this
 * work may be made except in accordance with a valid license
 * agreement from Funambol.  This notice must be
 * included on all copies, modifications and derivatives of this
 * work.
 *
 * Funambol MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY
 * OF THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE, OR NON-INFRINGEMENT. Funambol SHALL NOT BE LIABLE FOR ANY DAMAGES
 * SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING
 * THIS SOFTWARE OR ITS DERIVATIVES.
 */

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.common.io.BaseEncoding;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import io.reactivex.Observable;

/**
 * @author Anton Musiienko on 7/9/2017.
 */

public class YoutubeAPI {

    private static final String YOUTUBE_API_KEY = "AIzaSyBEmcUz19tz4R_HnV6aqMs-2VRBGNbQLK4";
    private static final String SEARCH_PARTS = "id,snippet";
    private static final String SEARCH_QUERY = "items(id/videoId,snippet/title,snippet/thumbnails/default/url)";
    private static final String SEARCH_TYPE = "video";
    private static final String HEADER_NAME_PACKAGE = "X-Android-Package";
    private static final String HEADER_NAME_CERTIFICATE = "X-Android-Cert";
    private static final long SEARCH_MAX_RESULT = 10L;
    private static final String ALGORITHM_TYPE = "SHA-1";

    public Observable<List<SearchResult>> search(final Context context, String searchQuery) {
        YouTube youTube = buildYouTube(context);
        YouTube.Search.List search;
        try {
            search = youTube.search().list(SEARCH_PARTS);
            search.setKey(YOUTUBE_API_KEY);
            search.setQ(searchQuery);
            search.setFields(SEARCH_QUERY);
            search.setMaxResults(SEARCH_MAX_RESULT);
            search.setType(SEARCH_TYPE);


        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return Observable.create(e -> {
            SearchListResponse searchResponse = search.execute();
            List<SearchResult> items = searchResponse.getItems();
            e.onNext(items);
            e.onComplete();
        });
    }

    @NonNull
    private YouTube buildYouTube(final Context context) {
        return new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), request -> {
            String packageName = context.getPackageName();
            String SHA1 = getSHA1(context, packageName);

            request.getHeaders().set(HEADER_NAME_PACKAGE, packageName);
            request.getHeaders().set(HEADER_NAME_CERTIFICATE, SHA1);
        }).setApplicationName(context.getApplicationInfo().packageName).build();
    }

    private String getSHA1(Context context, String packageName) {
        try {
            Signature[] signatures = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES).signatures;
            Signature signature = signatures[0];
            MessageDigest md;
            md = MessageDigest.getInstance(ALGORITHM_TYPE);
            md.update(signature.toByteArray());
            return BaseEncoding.base16().encode(md.digest());
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}
