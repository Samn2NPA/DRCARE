package com.tvnsoftware.drcare.api.restservice;

import android.content.Context;

import com.tvnsoftware.drcare.api.CommonInterface;
import com.tvnsoftware.drcare.api.RetrofitManager;
import com.tvnsoftware.drcare.model.users.UsersResponse;

/**
 * Created by Thieusike on 7/23/2017.
 */

public class UserService extends BaseService<String, UsersResponse> {

    @Override
    public void request(Context context, CommonInterface.ModelResponse<UsersResponse> callBack) {
        RetrofitManager.getInstance().sendApiRequest(RetrofitManager.getInstance()
                .getRestApiEndpointInterface().getUsers(), callBack);
    }

    @Override
    public void setRequest(String r) {

    }
}
