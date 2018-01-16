package glide.fy.com;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;

import java.io.InputStream;

import okhttp3.OkHttpClient;

/**
 * 新建一个 MyGlideModule 类 并实现GlideModule接口，
 * 在registerComponents(方法中将我们刚刚创建的OkHttpGlideUrlLoader和OkHttpFetcher注册到Glide当将原来的HTTP通讯组件给替换掉）
 *
 * Created by Administrator on 2018/1/16.
 */
public class MyGlideModule implements GlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
//        创建了一个OkHttpClient.Builder，然后调用addInterceptor()方法将刚才创建的ProgressInterceptor添加进去，
//        最后将构建出来的新OkHttpClient对象传入到OkHttpGlideUrlLoader.Factory中即可
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new ProgressInterceptor());
        OkHttpClient okHttpClient = builder.build();

        //替换 glide 原来的http通讯组件；并启用okhttp3 的拦截器
        glide.register(GlideUrl.class, InputStream.class, new OkHttpGlideUrlLoader.Factory(okHttpClient));
    }

}
