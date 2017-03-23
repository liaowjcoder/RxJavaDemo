package com.zeal.rxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observables.GroupedObservable;
import io.reactivex.schedulers.Schedulers;

public class Main3Activity extends AppCompatActivity {

    private static final String TAG = Main3Activity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        hello5();
    }

    private void hello() {
        Observable.just(2, 3, 4, 5, 6, 7, 8, 9, 10, 11).flatMap(new Function<Integer,
                ObservableSource<Integer>>() {
            @Override
            public ObservableSource<Integer> apply(@NonNull Integer integer) throws Exception {
                return Observable.just(integer * integer).subscribeOn(Schedulers.io());
            }
        }).subscribeOn(Schedulers.io()).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                Log.e("zeal", "accept: " + integer);
            }
        });
    }

    private void hello2() {
        Observable.create(new ObservableOnSubscribe<Singer>() {
            @Override
            public void subscribe(ObservableEmitter<Singer> e) throws Exception {
                //查询周杰伦的所有歌曲
                Singer singer = new Singer();
                singer.id = 100L;
                singer.name = "周杰伦";
                singer.songs = null;
                e.onNext(singer);

                Singer singer2 = new Singer();
                singer2.id = 200L;
                singer2.name = "侧田";
                singer2.songs = null;
                e.onNext(singer2);

                Singer singer3 = new Singer();
                singer3.id = 300L;
                singer3.name = "李宇春";
                singer3.songs = null;
                e.onNext(singer3);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io()).concatMap(new Function<Singer,
                ObservableSource<List<Song>>>() {
            @Override
            public ObservableSource<List<Song>> apply(Singer singer) throws Exception {

                return fetchSongs(singer);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<Song>>() {
            @Override
            public void accept(@NonNull List<Song> songs) throws Exception {
                Log.e("zeal", "accept: " + songs);
            }
        });
    }

    private Observable<List<Song>> fetchSongs(final Singer singer) {
        return Observable.create(new ObservableOnSubscribe<List<Song>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Song>> e) throws Exception {
                //根据singerId查询对应的歌曲列表
                List<Song> songs = new ArrayList<Song>();
                long singerId = singer.id;
                if (singerId == 100L) {//周杰伦
                    Song song = new Song();
                    song.id = 1L;
                    song.singerId = singerId;
                    song.name = "安静";
                    songs.add(song);

                    Song song2 = new Song();
                    song2.id = 2L;
                    song2.name = "七里香";
                    song2.singerId = singerId;
                    songs.add(song2);
                } else if (singerId == 200L) {//侧田
                    Song song = new Song();
                    song.id = 3L;
                    song.name = "命硬";
                    song.singerId = singerId;
                    songs.add(song);
                } else if (singerId == 300L) {//李宇春
                    Song song = new Song();
                    song.id = 4L;
                    song.singerId = singerId;
                    song.name = "why me";
                    songs.add(song);

                    Song song2 = new Song();
                    song2.id = 5L;
                    song2.name = "和你一样";
                    song2.singerId = singerId;
                    songs.add(song2);
                }

                e.onNext(songs);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io());//在子线程中去查询歌曲列表
    }

    private void hello3() {
        Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9).groupBy(new Function<Integer, String>() {
            @Override
            public String apply(@NonNull Integer integer) throws Exception {
                return integer % 2 == 0 ? "偶数" : "奇数";//分为两组
            }
            //GroupedObservable:继承至Observable，mKey用于标识分组Observable
        }).subscribe(new Consumer<GroupedObservable<String, Integer>>() {
            //accept中接受分组后的多个Observable类型的GroupedObservable对象，多个对象使用mKey去标识
            @Override
            public void accept(@NonNull final GroupedObservable<String, Integer>
                                       stringIntegerGroupedObservable) throws Exception {
                Disposable subscribe = stringIntegerGroupedObservable.subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        Log.e(TAG, stringIntegerGroupedObservable.getKey() + ":" + integer);//分组名称
                    }
                });
                //subscribe.dispose();
            }
        });
    }

    private void hello4() {
        Observable.just(1, 2, 3, 4, 5, 6, 7).scan(new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(@NonNull Integer integer, @NonNull Integer integer2) throws
                    Exception {
                Log.e(TAG, "apply----------- " + integer + ":" + integer2);
                return integer + integer2;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                Log.e(TAG, "accept: " + integer);
            }
        });
    }


    //Observable使用的好处可以将耗时操作的方法转化为Obsevable
    private void hello5() {
        Observable.just(getString()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers
                .mainThread()).subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                Log.e(TAG, "accept: " + s);
            }
        });
    }

    private String getString() {
        try {
            Thread.sleep(3000);
            return "我是耗时方法返回的字符串";
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "";
        }
    }
}
