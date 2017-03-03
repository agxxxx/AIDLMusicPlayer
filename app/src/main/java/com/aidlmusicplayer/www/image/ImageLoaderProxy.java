package com.aidlmusicplayer.www.image;

import android.content.Context;

public class ImageLoaderProxy implements LoaderI{

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

    @Override
    public void load(Context context, ImageLoader var1) {
        mStrategy.load(context, var1);
    }

    @Override
    public void animate(Context context, ImageLoader var2) {
        mStrategy.animate(context,var2);
    }

    @Override
    public void transform(Context context, ImageLoader var3) {
        mStrategy.transform(context,var3);
    }

}
