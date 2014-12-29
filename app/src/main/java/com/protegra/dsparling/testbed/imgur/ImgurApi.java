package com.protegra.dsparling.testbed.imgur;

import com.protegra.dsparling.testbed.BuildConfig;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;
import rx.Observable;

public interface ImgurApi {

    // reify response type for GSON because... Java
    public class MemeResponse extends ImgurResponse<List<Meme>> {}


    @GET("/g/memes/time/{page}")
    @Headers({
            "Authorization: Client-ID " + BuildConfig.IMGUR_CLIENT_ID,
            "Accept: application/json"
    })
    Observable<MemeResponse> latestMemes(@Path("page") long page);
}