package com.anton.veretinsky.weatherapp.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.anton.veretinsky.weatherapp.data.Status.ERROR;
import static com.anton.veretinsky.weatherapp.data.Status.LOADING;
import static com.anton.veretinsky.weatherapp.data.Status.SUCCESS;

public class Resource<T> {

    @NonNull
    public final Status status;

    @Nullable
    public final String message;

    @Nullable
    public final T data;

    public Resource(@NonNull Status status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> Resource<T> success(@Nullable T data) {
        return new Resource<>(SUCCESS, data, null);
    }

    public static <T> Resource<T> error(String msg, @Nullable T data) {
        return new Resource<>(ERROR, data, msg);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(LOADING, data, null);
    }
}