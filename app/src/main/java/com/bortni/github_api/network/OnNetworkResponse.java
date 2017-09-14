package com.bortni.github_api.network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by andrewbortnichuk on 14/09/2017.
 */

public interface OnNetworkResponse<T> {
    void success(@NonNull final T success);

    void error(@Nullable final Throwable throwable);
}
