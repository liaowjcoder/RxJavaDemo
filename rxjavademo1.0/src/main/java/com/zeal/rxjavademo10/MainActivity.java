package com.zeal.rxjavademo10;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import java.util.function.BiFunction;
import java.util.function.Consumer;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        demo6();
    }


    /*
    订阅者：Observer/Subscriber
    Observer是继承至Subscriber
    Observer在Observable.subscribe(Observer)时，会将Observer对象转化为Subscriber对象。
     */
    public void demo1() {
        Observer observer = new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
            }
        };

        Subscriber<Integer> subscriber = new Subscriber<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {

            }
        };
    }

    public void demo2() {
        Observable<Integer> observable = rx.Observable.create(new Observable.OnSubscribe<Integer>
                () {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {

            }
        });

        Observable.just(1, 1, 1);

        Observable.from(new String[]{});
    }

    public void demo3() {
        //这是一个同步的处理过程
        //RxJava：可以是数据流流向不同的部分
        //简洁的实现方式
        final int drawableRes = R.mipmap.ic_launcher;
        final ImageView imageView = (ImageView) findViewById(R.id.imageView);
        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                Drawable drawable = getTheme().getDrawable(drawableRes);
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
        }).map(new Func1<Drawable, Drawable>() {
            @Override
            public Drawable call(Drawable drawable) {
                return drawable;
            }
        }).subscribe(new Subscriber<Drawable>() {
            @Override
            public void onCompleted() {
                Log.e("zeal", "onComplete");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Drawable drawable) {
                imageView.setImageDrawable(drawable);
            }
        });
    }

    public void demo4() {
        Observable.just(1, 2, 3, 4).lift(new Observable.Operator<String, Integer>() {
            @Override
            public Subscriber<? super Integer> call(final Subscriber<? super String> subscriber) {
                return new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.e("zeal", "新的onCompleted");
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        subscriber.onError(e);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.e("zeal", "新的onNext:" + integer);
                        subscriber.onNext(integer + "我转化啦");
                    }
                };
            }
        }).subscribe(new Subscriber<String>() {//这个是新的Observable的subscribe方法
            @Override
            public void onCompleted() {
                Log.e("zeal", "旧的onCompleted");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.e("zeal", "旧的onNext:" + s);
            }
        });
    }

    public void demo5() {
        Observable<Integer> observable = Observable.just(1, 2, 3);
        Observable<Integer> observable2 = Observable.just(4, 5, 6);

        Observable.zip(observable, observable2, new Func2<Integer, Integer, String>() {
            @Override
            public String call(Integer integer, Integer integer2) {
                return integer + "--" + integer2;
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.e("zeal", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("zeal", "onError");
            }

            @Override
            public void onNext(String s) {
                Log.e("zeal", "onNext:" + s);
            }
        });
    }


    public void demo6() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(1);
                subscriber.onNext(2);
                subscriber.onNext(3);
                subscriber.onCompleted();
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.e("zeal", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("zeal", "onError");
                    }

                    @Override
                    public void onNext(Integer s) {
                        Log.e("zeal", "onNext:" + s);
                    }
                });

    }

    public void demo7() {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

            }
        });
        Observable.zip(create(1, 2, 3), create(4, 5, 6), new Func2<String, String, String>() {

            @Override
            public String call(String s, String s2) {
                return null;
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {

            }
        });
    }

    public Observable<String> create(final int... args) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                for (int i = 0; i < args.length; i++) {
                    Log.e("zeal", "onNext:" + args[i]);
                    subscriber.onNext("" + args[i]);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
