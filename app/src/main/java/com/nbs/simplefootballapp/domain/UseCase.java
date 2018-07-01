package com.nbs.simplefootballapp.domain;

import com.nbs.simplefootballapp.data.libs.ApiCallback;
import com.nbs.simplefootballapp.data.libs.ApiManager;
import com.nbs.simplefootballapp.data.model.request.RequestModel;
import com.nbs.simplefootballapp.data.model.response.ResponseModel;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public abstract class UseCase<T extends RequestModel, D extends ResponseModel>{

    private T requestModel;

    private final static String TAG = "UseCase";

    private Observable<D> apiCall;

    private Subscriber<D> subscriber;

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

    public Subscriber<D> getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber<D> subscriber) {
        this.subscriber = subscriber;
    }

    public Observable<D> getApiCall() {
        return apiCall;
    }

    public void setApiCall(Observable<D> apiCall) {
        this.apiCall = apiCall;
    }

    protected abstract Observable<D> getApi();

    protected abstract void onResponseEmpty();

    private CompositeSubscription compositeSubscription;

    protected abstract void callApi();

    public void execute(Subscriber subscriber) {
        setSubscriber(subscriber);

        if (getRequestModel() == null) {
            throw new IllegalStateException("Request Model cannot be null");
        }

        if (compositeSubscription == null){
            compositeSubscription = new CompositeSubscription();
        }

        compositeSubscription.add(getApi()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getSubscriber()));
    }

    public void cancel() {
        if (getSubscriber() != null){
            getSubscriber().unsubscribe();
        }

        if (compositeSubscription != null && compositeSubscription.hasSubscriptions()){
            compositeSubscription.unsubscribe();
            if (subscriber instanceof ApiCallback){
                ((ApiCallback) subscriber).onRequestCancelled();
            }
        }
    }
}
