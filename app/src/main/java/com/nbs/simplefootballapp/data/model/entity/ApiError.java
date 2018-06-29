package com.nbs.simplefootballapp.data.model.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ghiyatshanif on 2/20/17.
 */

public class ApiError {
    @SerializedName("name")
    private String name;
    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private String status;
    @SerializedName("code")
    private int code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "ApiError{" +
                ", name='" + name + '\'' +
                ", message='" + message + '\'' +
                ", status=" + status +
                ", code='" + code + '\'' +
                '}';
    }
}
