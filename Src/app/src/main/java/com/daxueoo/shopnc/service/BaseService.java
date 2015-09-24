package com.daxueoo.shopnc.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by user on 15-8-17.
 */
public class BaseService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
