package com.tvnsoftware.drcare.api.restservice;

import android.content.Context;

import com.tvnsoftware.drcare.api.CommonInterface;

/**
 * Created by Thieusike on 7/10/2017.
 */

public abstract class BaseService<Req, Res> {
    public abstract void request(Context context, CommonInterface.ModelResponse<Res> callBack);

    public abstract void setRequest(Req r);
}
