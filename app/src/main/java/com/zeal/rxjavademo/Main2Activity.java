package com.zeal.rxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class Main2Activity extends AppCompatActivity {

    private static final String TAG = Main2Activity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        hello7();
    }

    private void hello1() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                Log.e(TAG, "emit: " + 1);
                e.onNext(1);
                Log.e(TAG, "emit: " + 2);
                e.onNext(2);
                Log.e(TAG, "emit: " + 3);
                e.onNext(3);
                Log.e(TAG, "emit: onComplete");
                e.onComplete();
            }
        }, BackpressureStrategy.ERROR).map(new Function<Integer, Integer>() {
            @Override
            public Integer apply(@NonNull Integer integer) throws Exception {

                return integer *= 10;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                Log.e(TAG, "accept: " + integer);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                Log.e(TAG, "accept: " + throwable.getMessage());
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                Log.e(TAG, "complete ");
            }
        });
    }

    /*
    doOnNext在emit
     */
    private void hello2() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                Log.e(TAG, "emit: " + 1);
                e.onNext(1);
                Log.e(TAG, "emit: " + 2);
                e.onNext(2);
                Log.e(TAG, "emit: " + 3 / 0);
                e.onNext(3);
                //throw new RuntimeException("aaa");
                Log.e(TAG, "emit: onComplete");
                e.onComplete();
            }
        }, BackpressureStrategy.ERROR).doOnNext(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                Log.e(TAG, "doOnNext2 accept: " + integer);
            }
        }).map(new Function<Integer, Integer>() {
            @Override
            public Integer apply(@NonNull Integer integer) throws Exception {

                return integer *= 10;
            }
        }).doOnNext(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                Log.e(TAG, "doOnNext accept: " + integer);
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                Log.e(TAG, "accept: " + integer);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                Log.e(TAG, "accept Throwable: " + throwable.getMessage());
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                Log.e(TAG, "complete ");
            }
        });
    }

    private void hello3() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                Log.e(TAG, "emit: " + 1);
                e.onNext(1);
                Log.e(TAG, "emit: " + 2);
                e.onNext(2);
                Log.e(TAG, "emit: " + 3 / 0);//出现异常
                e.onNext(3);
                Log.e(TAG, "emit: onComplete");
                e.onComplete();
            }
        }, BackpressureStrategy.ERROR).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                Log.e(TAG, "accept: " + integer);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                Log.e(TAG, "accept Throwable: " + throwable.getMessage());
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                Log.e(TAG, "complete ");
            }
        });
    }

    private void hello4() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                Log.e(TAG, "emit: " + 1);
                e.onNext(1);
                Log.e(TAG, "emit: " + 2);
                e.onNext(2);
                Log.e(TAG, "emit: " + 3 / 0);//出现异常
                e.onNext(3);
                Log.e(TAG, "emit: onComplete");
                e.onComplete();
            }
        }, BackpressureStrategy.ERROR).onErrorResumeNext(new Publisher<Integer>() {
            @Override
            public void subscribe(Subscriber<? super Integer> s) {
                s.onNext(100);
                s.onNext(200);
                s.onNext(300);
            }

        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                Log.e(TAG, "accept: " + integer);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                Log.e(TAG, "accept Throwable: " + throwable.getMessage());
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                Log.e(TAG, "complete ");
            }
        });
    }

    private void hello4_1() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                Log.e(TAG, "emit: " + 1);
                e.onNext(1);
                Log.e(TAG, "emit: " + 2);
                e.onNext(2);
                Log.e(TAG, "emit: " + 3);
                e.onNext(3);
                Log.e(TAG, "emit: onComplete");
                e.onComplete();
            }
        }, BackpressureStrategy.ERROR).map(new Function<Integer, Integer>() {
            @Override
            public Integer apply(@NonNull Integer integer) throws Exception {
                Log.e(TAG, "apply: 开始转化数据:" + integer);
                integer /= 0;
                return integer;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                Log.e(TAG, "accept: " + integer);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                Log.e(TAG, "accept Throwable: " + throwable.getMessage());
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                Log.e(TAG, "complete ");
            }
        });
    }

    private void hello4_2() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                Log.e(TAG, "emit: " + 1);
                e.onNext(1);
                Log.e(TAG, "emit: " + 2);
                e.onNext(2);
                Log.e(TAG, "emit: " + 3);
                e.onNext(3);
                Log.e(TAG, "emit: onComplete");
                e.onComplete();
            }
        }, BackpressureStrategy.ERROR).map(new Function<Integer, Integer>() {
            @Override
            public Integer apply(@NonNull Integer integer) throws Exception {
                try {
                    return integer / 0;
                } catch (Throwable t) {
                    throw Exceptions.propagate(t);
                }
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                Log.e(TAG, "accept: " + integer);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                Log.e(TAG, "accept Throwable: " + throwable.getMessage());
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                Log.e(TAG, "complete ");
            }
        });
    }

    //flatMap操作符中出现检查型异常（所有非RuntionException派生的异常）
    private void hello4_3() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                Log.e(TAG, "emit: " + 1);
                e.onNext(1);
                Log.e(TAG, "emit: " + 2);
                e.onNext(2);
                Log.e(TAG, "emit: " + 3);
                e.onNext(3);
                Log.e(TAG, "emit: onComplete");
                e.onComplete();
            }
        }, BackpressureStrategy.ERROR).flatMap(new Function<Integer, Publisher<Integer>>() {
            @Override
            public Publisher<Integer> apply(@NonNull Integer integer) throws Exception {
                try {
                    integer /= 0;
                    return Flowable.just(100000);
                } catch (Throwable t) {
                    return Flowable.error(t);
                }
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                Log.e(TAG, "accept: " + integer);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                Log.e(TAG, "accept Throwable: " + throwable.getMessage());
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                Log.e(TAG, "complete ");
            }
        });
    }

    //onErrorReturn
    private void hello5() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                Log.e(TAG, "emit: " + 1);
                e.onNext(1);
                Log.e(TAG, "emit: " + 2);
                e.onNext(2);
                Log.e(TAG, "emit: " + 3 / 0);//出现异常
                e.onNext(3);
                Log.e(TAG, "emit: onComplete");
                e.onComplete();
            }
        }, BackpressureStrategy.ERROR).onErrorReturn(new Function<Throwable, Integer>() {
            @Override
            public Integer apply(@NonNull Throwable throwable) throws Exception {
                Log.e(TAG, "apply: throwable:" + throwable.getMessage());
                return 1111111111;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                Log.e(TAG, "accept: " + integer);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                Log.e(TAG, "accept Throwable: " + throwable.getMessage());
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                Log.e(TAG, "complete ");
            }
        });
    }

    private void hello6() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                Log.e(TAG, "emit: " + 1);
                e.onNext(1);
                Log.e(TAG, "emit: " + 2);
                e.onNext(2);
                Log.e(TAG, "emit: " + 3 / 0);//出现异常
                e.onNext(3);
                Log.e(TAG, "emit: onComplete");
                e.onComplete();
            }
        }, BackpressureStrategy.ERROR).onExceptionResumeNext(new Publisher<Integer>() {
            @Override
            public void subscribe(Subscriber<? super Integer> s) {
                s.onNext(100);
                s.onNext(200);
                s.onNext(300);
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                Log.e(TAG, "accept: " + integer);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                Log.e(TAG, "accept Throwable: " + throwable.getMessage());
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                Log.e(TAG, "complete ");
            }
        });
    }

    //返回一个Flowable，他发送指定的某些数据在数据源开始发送数据之前。
    private void hello7() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                Log.e(TAG, "emit: " + 1);
                e.onNext(1);
                Log.e(TAG, "emit: " + 2);
                e.onNext(2);
                Log.e(TAG, "emit: " + 3);
                e.onNext(3);
                Log.e(TAG, "emit: onComplete");
                e.onComplete();
            }
        }, BackpressureStrategy.ERROR).startWithArray(100, 200, 300).onExceptionResumeNext(new Publisher<Integer>() {
            @Override
            public void subscribe(Subscriber<? super Integer> s) {
                s.onNext(100);
                s.onNext(200);
                s.onNext(300);
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                Log.e(TAG, "accept: " + integer);
            }
        });  
    }
}
