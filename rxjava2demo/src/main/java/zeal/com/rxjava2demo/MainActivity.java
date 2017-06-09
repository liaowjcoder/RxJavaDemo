package zeal.com.rxjava2demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        test1();
    }

    private void test1() {

        /*
        ObservableEmitter： Emitter是发射器的意思，那就很好猜了，这个就是用来发
        出事件的，它可以发出三种类型的事件，通过调用emitter的onNext(T value)、
        onComplete()和onError(Throwable error)就可以分别发出next事件、
        complete事件和error事件。


        接下来介绍Disposable, 这个单词的字面意思是一次性用品,用完即可丢弃的.
         那么在RxJava中怎么去理解它呢, 对应于上面的水管的例子, 我们可以把它
         理解成两根管道之间的一个机关, 当调用它的dispose()方法时, 它就会将
         两根管道切断, 从而导致下游收不到事件.
         */
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                e.onNext("a");
                e.onNext("b");
                e.onNext("c");
                e.onComplete();
            }
        });

        observable.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.e("zeal", "onSubscribe");
            }

            @Override
            public void onNext(@NonNull String s) {
                Log.e("zeal", "onNext：" + s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("zeal", "onError" + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e("zeal", "onComplete");
            }
        });

    }
}
