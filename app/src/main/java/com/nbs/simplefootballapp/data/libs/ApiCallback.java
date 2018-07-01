package com.nbs.simplefootballapp.data.libs;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.JsonSyntaxException;
import com.nbs.simplefootballapp.data.model.response.ResponseModel;

import java.io.IOException;
import java.net.SocketTimeoutException;

import retrofit2.HttpException;
import rx.Subscriber;

public abstract class ApiCallback<T extends ResponseModel> extends Subscriber<T> implements ErrorListener {
    private static final String TAG = ApiCallback.class.getSimpleName();

    public final String GENERAL_ERROR_UNABLE_TO_CONNECT_TO_SERVER = "Unable to connect to server";

    public static final String GENERAL_ERROR_NO_DATA = "No data";

    public abstract void onSuccess(T response);

    public abstract void onFailure(String message);

    public abstract void onFinish();

    //    region spesific error
    @Override
    public void onBadRequestError(String message) {
        Log.e(TAG, "onBadRequestError(): "+message);
        onFailure(message);
    }

    @Override
    public void onUnauthorizeRequestError(String message) {
        Log.e(TAG, "unathorized(): "+message);
        onFailure(message);
    }

    @Override
    public void onForbiddenRequestError(String message) {
        Log.e(TAG, "ForbiddenRequest(): "+message);
        onFailure(message);
    }

    @Override
    public void onNotFoundError(String message) {
        Log.e(TAG, "onNotFoundError(): "+message);
        onFailure(message);
    }

    @Override
    public void onTimeoutError() {
        Log.e(TAG, "onTimeoutError(): Timeout");
        onFailure("Timeout");
    }

    @Override
    public void onNoInternetError() {
        Log.e(TAG, "onNoInternetError()");
        onFailure("No internet");
    }

    @Override
    public void onUnknownError() {
        Log.e(TAG, "onUnknownError()");
    }

    @Override
    public void onJsonSyntaxException(String cause) {
        Log.e(TAG, "onJsonSyntaxException(): " + cause);
        onFailure(cause);
    }

    public abstract void onRequestCancelled();

    //endregion

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();

        boolean isCancelled = "Socket closed".equalsIgnoreCase(e.getMessage());

        //catch general failure cause
        if (isCancelled) {
            onRequestCancelled();
            Log.d("UseCase", "Request is cancelled");
        }else if (e instanceof SocketTimeoutException) {
            onTimeoutError();
        } else if (e instanceof IOException && !isCancelled) {
            onNoInternetError();
        } else if (e instanceof JsonSyntaxException) {
            onJsonSyntaxException(e.getMessage());
        }else if (e instanceof HttpException){
            HttpException httpException = (HttpException)e;
            int httpResponseCode = httpException.code();
            String errorMessage = httpException.getMessage();
            Log.d(TAG, ""+httpResponseCode+":"+errorMessage);
            onFailure(errorMessage);
        }else{
            if (TextUtils.isEmpty(e.getMessage())){
                onFailure(GENERAL_ERROR_UNABLE_TO_CONNECT_TO_SERVER);
            }else{
                onFailure(e.getMessage());
            }
        }

        onFinish();
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onCompleted() {
        onFinish();
    }
}
