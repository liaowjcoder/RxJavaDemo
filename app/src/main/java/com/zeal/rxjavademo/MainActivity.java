package com.zeal.rxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.concurrent.Callable;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //helloRxJava();
        //helloRxJava2();
        //helloRxJava3();
        //        helloRxJava4();
        helloRaJava2();
    }

    /**
     * MainActivity: onSubscribe
     * MainActivity: onNext:1
     * MainActivity: onNext:2
     * MainActivity: onComplete
     */
    public void helloRxJava() {
        //初始化一个Observable
        Observable<Integer> observale = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onNext(4);
                e.onComplete();
            }
        });

        //初始化一个Observer
        Observer<Integer> observer = new Observer<Integer>() {
            private Disposable disposable;

            @Override
            public void onSubscribe(Disposable d) {
                //Disposable 用于订阅和取消订阅
                disposable = d;
                Log.e(TAG, "onSubscribe");
            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG, "onNext:" + integer);
                if (integer == 3) {
                    //当接受到异常数据3时就取消订阅
                    disposable.dispose();
                }

            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError:" + e.toString());
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete");
            }
        };

        //建立订阅关系
        observale.subscribe(observer);
    }

    /**
     * accept: 1
     * accept: 2
     * accept: 3
     * accept: 4
     * onComplete
     */
    public void helloRxJava2() {
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onNext(4);
                e.onComplete();//标记结束
            }
        });

        observable.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {//onNext 可能会被多次调用
                Log.e(TAG, "accept: " + integer);
            }
        }, new Consumer<Throwable>() {//onError
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                Log.e(TAG, "accept: " + throwable.toString());
            }
        }, new Action() {//onComplete
            @Override
            public void run() throws Exception {
                Log.e(TAG, "onComplete");
            }
        });
    }

    /**
     * Flowable 的入门用法
     * Flowable提供了许多工厂方法，它实现了Publisher，泛型表示要发布的类型。
     * hello world
     */
    public void helloRxJava3() {
        Flowable.just("hello world").subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                Log.e("zeal", s);//hello world
            }
        });
    }

    /**
     * RxJava更加通用的方式是去实现计算操作，网络请求，后台线程执行操作并且在UI线程展示结果。
     * 流式调用类似与Builder模式，每一次调用都返回一个新的Flowable
     * fromCallable:只有在订阅者订阅该Publisher之后，callable中的代码才会去执行，然后emit返回的结果给订阅者。
     */
    public void helloRxJava4() {
        Log.e(TAG, Thread.currentThread().getName());
        Flowable<String> observable = Flowable.fromCallable(new Callable<String>() {
            //该方法只有Subscriber订阅Publisher之后才会被调用。
            @Override
            public String call() throws Exception {
                //模拟耗时操作
                Thread.sleep(2000);
                Log.e(TAG, "call: " + Thread.currentThread().getName());
                return "Job Done...";
            }
        });

        observable.subscribeOn(Schedulers.io()).observeOn(Schedulers.single()).subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                Log.e(TAG, "accept: " + s + Thread.currentThread().getName());
            }
        });
    }

    /**
     * Publisher emit Future.get()的结果
     */
    public void helloRxJava5() {
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter e) throws Exception {
                e.onNext("hello world");//our Observale emit "hello world" then complete
                e.onComplete();
            }
        });

        Observer<String> subscriber = new Observer<String>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Log.e(TAG, "onNext: " + s);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete");
            }
        };

        //建立订阅关系
        observable.subscribe(subscriber);

        observable.just("hello", "");
    }

    /**
     * 只有上游和下游建立连接之后才可以发送事件，也就是调用 observable.subscribe(observer)
     * onSubscribe
     * onNext: 1
     * onNext: 2
     * onNext: 3
     * onNext: 4
     * onComplete
     */
    public void helloRaJava2() {
        //创建一个上游 Observable
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onNext(4);
                e.onComplete();
            }
        });

        Observer<Integer> observer = new Observer<Integer>() {
            private Disposable disposable;

            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, "onSubscribe");
                disposable = d;
            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG, "onNext: " + integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e.toString());
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete ");
            }
        };
        observable.subscribe(observer);

        //转化链式编程
        /*Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {

            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });*/
    }
}
