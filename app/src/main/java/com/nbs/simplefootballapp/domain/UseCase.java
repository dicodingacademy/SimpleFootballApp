package com.nbs.simplefootballapp.domain;

import android.util.Log;

import com.google.gson.JsonSyntaxException;
import com.nbs.simplefootballapp.data.libs.ApiManager;
import com.nbs.simplefootballapp.data.libs.ErrorListener;
import com.nbs.simplefootballapp.data.model.request.RequestModel;
import com.nbs.simplefootballapp.data.model.response.ErrorResponse;
import com.nbs.simplefootballapp.data.model.response.ResponseModel;

import java.io.IOException;
import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class UseCase<T extends RequestModel, D extends ResponseModel>
        implements ErrorListener{

    private T requestModel;

    private final static String TAG = "UseCase";

    public final String GENERAL_ERROR_UNABLE_TO_CONNECT_TO_SERVER = "Unable to connect to server";

    public final String GENERAL_ERROR_NO_DATA = "No data";

    private Call<D> apiCall;

    private ApiManager apiManager;

    public UseCase(ApiManager apiManager) {
        this.apiManager = apiManager;
    }

    public ApiManager getApiManager() {
        return apiManager;
    }

    public T getRequestModel() {
        return requestModel;
    }

    public void setRequestModel(T requestModel) {
        this.requestModel = requestModel;
        apiCall = null;
    }

    public Call<D> getApiCall() {
        return apiCall;
    }

    public void setApiCall(Call<D> apiCall) {
        this.apiCall = apiCall;
    }

    protected abstract Call<D> getApi();

    protected abstract D getCache(T request);

    protected abstract void onCacheLoaded(D response);

    protected abstract void onResponseLoaded(D response);

    protected abstract void onResponseEmpty();

    //General Error Response passed here
    protected abstract void onErrorResponse(String message);

    protected abstract void onRequestCancelled();

    public void execute() {
        if (getRequestModel() == null) {
            throw new IllegalStateException("Request Model cannot be null");
        }
        if (getCache(getRequestModel()) != null) {
            // TODO: 4/23/17 check if cache is valid and expiration here
            onCacheLoaded(getCache(getRequestModel()));
        } else {
            setApiCall(getApi().clone());
            getApi().enqueue(new Callback<D>() {
                @Override
                public void onResponse(Call<D> call, Response<D> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null && (response.code() != 204)) {
                            onResponseLoaded(response.body());
                        } else if (response.body() == null || response.code() == 204) {
                            onResponseEmpty();
                        }
                    } else {
                        String errorMessage = ErrorResponse.getErrorMessage(response.errorBody());
                        //catch general error
                        onErrorResponse(errorMessage);

                        //catch specific error
                        switch (response.code()) {
                            case 400:
                                onBadRequestError(errorMessage);
                                break;
                            case 401:
                                onUnauthorizeRequestError(errorMessage);
                                break;
                            case 403:
                                onForbiddenRequestError(errorMessage);
                                break;
                            case 404:
                                onNotFoundError(errorMessage);
                                break;
                            default:
                                break;
                        }
                    }
                }

                @Override
                public void onFailure(Call<D> call, Throwable t) {
                    //check if failure cause is because request canceled
                    boolean isCancelled = "Socket closed".equalsIgnoreCase(t.getMessage());

                    //catch general failure cause
                    if (isCancelled) {
                        onRequestCancelled();
                        Log.d("UseCase", "Request is cancelled");
                    } else {
                        onErrorResponse(GENERAL_ERROR_UNABLE_TO_CONNECT_TO_SERVER);
                    }


                    //catch specific failure cause
                    if (t instanceof SocketTimeoutException) {
                        onTimeoutError();
                    } else if (t instanceof IOException && !isCancelled) {
                        onNoInternetError();
                    } else if (t instanceof JsonSyntaxException) {
                        onJsonSyntaxException(t.getMessage());
                    } else {
                        if (!isCancelled) onUnknownError();
                    }
                }
            });
        }
    }

    public void cancel() {
        if (getApiCall() != null){
            if (getApiCall().isExecuted()) {
                Log.d("UseCase", "Request is going to be cancelled");
                getApiCall().cancel();
            }
        }
    }

    //    region spesific error
    @Override
    public void onBadRequestError(String message) {
        Log.e(TAG, "onBadRequestError(): "+message);
    }

    @Override
    public void onUnauthorizeRequestError(String message) {
        Log.e(TAG, "unathorized(): "+message);
    }

    @Override
    public void onForbiddenRequestError(String message) {
        Log.e(TAG, "ForbiddenRequest(): "+message);
    }

    @Override
    public void onNotFoundError(String message) {
        Log.e(TAG, "onNotFoundError(): "+message);
    }

    @Override
    public void onTimeoutError() {
        Log.e(TAG, "onTimeoutError(): Timeout");
    }

    @Override
    public void onNoInternetError() {
        Log.e(TAG, "onNoInternetError()");
    }

    @Override
    public void onUnknownError() {
        Log.e(TAG, "onUnknownError()");
    }

    @Override
    public void onJsonSyntaxException(String cause) {
        Log.e(TAG, "onJsonSyntaxException(): " + cause);
    }

    //endregion
}
