package com.karfield.automatrix;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.protobuf.ByteString;

import java.util.HashMap;
import java.util.List;

public class AmTaskResult {
    private String direction;
    private ByteString data;
    private String error;

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public ByteString getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = ByteString.copyFrom(data);
    }

    public void setData(JsonObject data) {
        this.data = ByteString.copyFrom(new Gson().toJson(data).getBytes());
    }

    public void setData(JsonArray data) {
        this.data = ByteString.copyFrom(new Gson().toJson(data).getBytes());
    }

    public void setData(HashMap<String, Object> data) {
        this.data = ByteString.copyFrom(new Gson().toJson(data).getBytes());
    }

    public void setData(List<HashMap<String, Object>> data) {
        this.data = ByteString.copyFrom(new Gson().toJson(data).getBytes());
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
