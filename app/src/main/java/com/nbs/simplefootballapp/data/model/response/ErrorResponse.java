package com.nbs.simplefootballapp.data.model.response;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.nbs.simplefootballapp.data.model.entity.ApiError;

import okhttp3.ResponseBody;

/**
 * Created by ghiyatshanif on 4/10/17.
 */

public class ErrorResponse {
    @SerializedName("error")
    private ApiError error;

    public ApiError getError() {
        return error;
    }

    public void setError(ApiError error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "ErrorMessageResponse{" +
                "error=" + error +
                '}';
    }

    public static String getErrorMessage(ResponseBody errorBody) {
        try {
            String errorResponse = errorBody.string();
            Log.d("getErrorMessage(): ", errorResponse);
            return new Gson().fromJson(errorResponse, ApiError.class).getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
