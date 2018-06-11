package com.tvnsoftware.drcare.api;

/**
 * Created by Thieusike on 7/10/2017.
 */

public class CommonInterface<T> {
    public interface ModelResponse<T> {
        void onSuccess(T result);

        void onFail();
    }
}
