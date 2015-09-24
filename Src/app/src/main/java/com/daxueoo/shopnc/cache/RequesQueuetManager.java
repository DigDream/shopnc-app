package com.daxueoo.shopnc.cache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.daxueoo.shopnc.DaxueooApplication;


/**
 * Created by user on 15/8/27.
 */
public class RequesQueuetManager {
	public static RequestQueue mRequestQueue = Volley.newRequestQueue(DaxueooApplication.getInstance());

	public static void addRequest(Request<?> request, Object object){
		if (object != null){
			request.setTag(object);
		}
		mRequestQueue.add(request);
	}

	public static void cancelAll(Object tag) {
		mRequestQueue.cancelAll(tag);
	}
}
