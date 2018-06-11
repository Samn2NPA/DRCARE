package com.tvnsoftware.drcare.api;

import com.tvnsoftware.drcare.model.users.UsersResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Thieusike on 7/10/2017.
 */

public interface RestApiEndpointInterface {
    @GET("users")
    Call<UsersResponse> getUsers();

}
