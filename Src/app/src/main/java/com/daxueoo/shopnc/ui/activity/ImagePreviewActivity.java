package com.daxueoo.shopnc.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.cache.ImageCacheManger;
import com.daxueoo.shopnc.utils.SystemUtils;
import com.daxueoo.shopnc.utils.TitleBuilder;
import com.daxueoo.shopnc.utils.ToastUtils;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by user on 15-8-14.
 */
public class ImagePreviewActivity extends BaseActivity {

    TitleBuilder titleBuilder ;

    private LinearLayout ll_image_preview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);


        titleBuilder = new TitleBuilder(this).setTitleBackGround(new Color().argb(0,0,0,0)).setLeftImage(R.drawable.navigationbar_back_sel).setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePreviewActivity.this.finish();
            }
        });

        //  大图
        Intent intent = getIntent();
        String pic_url = intent.getStringExtra("pic_url");

        PhotoView photoView = (PhotoView) findViewById(R.id.iv_photo);

        ll_image_preview = (LinearLayout) findViewById(R.id.ll_image_preview);


        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showToast(ImagePreviewActivity.this,"exit", Toast.LENGTH_SHORT);
                ImagePreviewActivity.this.finish();

            }
        });

        //  三级缓存
        ImageCacheManger.loadImage(pic_url, photoView, SystemUtils.getBitmapFromResources(this, R.mipmap.ic_no_user), SystemUtils.getBitmapFromResources(this, R.mipmap.ic_no_user));


//        RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
//
//        ImageLoader imageLoader = new ImageLoader(mQueue, new BitmapCache());
//        ImageLoader.ImageListener listener = ImageLoader.getImageListener(photoView, android.R.drawable.ic_menu_rotate, android.R.drawable.ic_menu_rotate);
//        imageLoader.get(pic_url, listener);
    }

}
