package first.nestedsliding.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.GlideModule;

import java.io.File;

/**
 * Created by dell on 2016/11/22.
 */
public class GlideConfiguration implements GlideModule {
    private static final String TAG = "GlideConfiguration";

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        MemorySizeCalculator calculator = new MemorySizeCalculator(context);

        int defaultCacheSize = calculator.getMemoryCacheSize();
        int defaultBitmapSize = calculator.getBitmapPoolSize();

        int customMemoryCacheSize = (int) (1.2 * defaultCacheSize);
        int customBitmapPoolSize  = (int)(1.2*defaultBitmapSize);

        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);

        builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));
        builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));

        builder.setDiskCache(new DiskLruCacheFactory(getDiskFileString(context,"glideCache"),1024*1024*30));

    }

    private String getDiskFileString(Context context, String glideCache) {
        File dirFile = new File(context.getExternalCacheDir().getAbsolutePath().toString() + glideCache);
        File tempFile = new File(dirFile,"bitmaps");
        if(!tempFile.exists()) {
            tempFile.mkdirs();
        }
        return tempFile.getAbsolutePath().toString();
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
