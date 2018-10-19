package com.bilibili.live.base.rx;


import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * RxBus
 * <p/>
 * Subject同时充当了Observer和Observable的角色，Subject是非线程安全的，
 * 要避免该问题，需要将 Subject转换为一个 SerializedSubject ，
 * 上述RxBus类中把线程非安全的PublishSubject包装成线程安全的Subject。
 * <p/>
 * PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者
 * <p/>
 * ofType操作符只发射指定类型的数据，其内部就是filter+cast
 */
public class RxBus {
    private final Subject<Object> _bus;


    private static class RxHolder {
        private static volatile RxBus sRxBus = new RxBus();
    }

    private RxBus() {
        _bus = PublishSubject.create().toSerialized();
    }

    public static RxBus getInstance() {
        return RxHolder.sRxBus;
    }

    public void send(Object o) {
        if (hasObserve()) {
            _bus.onNext(o);
        }
    }

    private boolean hasObserve() {
        return _bus.hasObservers();
    }

    public Observable<Object> toObservable() {
        return _bus;
    }

    // 根据传入的事件类型,返回特定类型的eventType的被观察者
    public <T extends Object> Observable<T> toObservable(Class<T> event) {
        return _bus.ofType(event);

    }
}
