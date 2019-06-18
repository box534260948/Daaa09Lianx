package com.bawei.daaa09lianx;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bawei.daaa09lianx.api.UserApi;
import com.bawei.daaa09lianx.entity.UploadEntity;
import com.bawei.daaa09lianx.net.RetrofitUtils;

import java.io.File;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void upload(View view) {
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            File file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/hello.jpg");

            RequestBody requestBody =RequestBody.create(MediaType.parse("image/*"),file);

            MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", file.getName(), requestBody);

            HashMap<String,String> headers=new HashMap<>();
            headers.put("userId","3358");
            headers.put("sessionId","15608628030173358");

            RetrofitUtils.getInstance().createService(UserApi.class)
                    .uploadHeadPic(headers,imagePart).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<UploadEntity>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(UploadEntity uploadEntity) {
                            Log.e("000", "onNext: "+uploadEntity.headPath );


                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });


        }
    }
}
