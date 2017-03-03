package com.aidlmusicplayer.www.image;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.FitCenter;

public class ImageLoaderProxy  {

    static ImageLoaderProxy mProxy;
    final GlideLoaderStrategy mStrategy;

    ImageLoaderProxy() {
        mStrategy = new GlideLoaderStrategy();
    }

    public static ImageLoaderProxy getInstance() {
        if (mProxy == null) {
            synchronized (ImageLoaderProxy.class) {
                mProxy = new ImageLoaderProxy();
            }
        }
        return mProxy;
    }

    public void load(Context context, ImageLoader var1) {
        mStrategy.load(context, var1);
    }



    public void animate(Context context, ImageLoader var2) {
        mStrategy.animate(context, var2);
    }


    public void load(Context context, String ivUrl, ImageView iv) {
        mStrategy.load(context, new ImageLoader.Builder()
                .load(ivUrl)
                .into(iv)
                .transform(new FitCenter(context))
                .build());
    }
    public void animate(Context context, String ivUrl, ImageView iv) {
        mStrategy.load(context, new ImageLoader.Builder()
                .load(ivUrl)
                .into(iv)
                .transform(new FitCenter(context))
                .build());
    }
    public void transform(Context context, ImageLoader var3) {
        mStrategy.transform(context, var3);
    }
    public void transform(Context context, String ivUrl, ImageView iv) {
        mStrategy.load(context, new ImageLoader.Builder()
                .load(ivUrl)
                .into(iv)
                .transform(new FitCenter(context))
                .build());
    }
}
