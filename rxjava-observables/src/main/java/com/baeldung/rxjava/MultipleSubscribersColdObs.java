package com.baeldung.rxjava;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rx.Observable;
import rx.Subscription;
import rx.observables.ConnectableObservable;
import rx.subscriptions.Subscriptions;

public class MultipleSubscribersColdObs {
    private static final Logger LOGGER = LoggerFactory.getLogger(MultipleSubscribersColdObs.class);

    public static void main(String[] args) throws InterruptedException {
        defaultBehaviour();
        // subscribeBeforeConnect();

    }

    private static void defaultBehaviour() {
        LOGGER.info("defaultBehaviour()");

        Observable<Object> obs = getObservable();

        LOGGER.info("defaultBehaviour() - Subscribing");
        Subscription s1 = obs.subscribe(i -> LOGGER.info("subscriber#1 is printing " + i));
        Subscription s2 = obs.subscribe(i -> LOGGER.info("subscriber#2 is printing " + i));

        LOGGER.info("defaultBehaviour() - Unsubscribing");
        s1.unsubscribe();
        s2.unsubscribe();

        LOGGER.info("end of defaultBehaviour()");
    }

    private static void subscribeBeforeConnect() throws InterruptedException {

        ConnectableObservable<Object> obs = getObservable().publish();

        LOGGER.info("subscribeBeforeConnect()");
        obs.subscribe(i -> LOGGER.info("subscriber #1 is printing " + i));
        obs.subscribe(i -> LOGGER.info("subscriber #2 is printing " + i));

        LOGGER.info("sleep(1000)");
        Thread.sleep(1000);

        LOGGER.info("Connecting");
        Subscription s = obs.connect();

        LOGGER.info("unsubscribe()");
        s.unsubscribe();

    }

    private static Observable<Object> getObservable() {
        LOGGER.info("getObservable()");

        return Observable.create(subscriber -> {
            subscriber.onNext(gettingValue(1));
            subscriber.onNext(gettingValue(2));

            subscriber.add(Subscriptions.create(() -> {
                LOGGER.info("getObservable- added cleanup");
            }));
        });
    }

    private static Integer gettingValue(int i) {
        LOGGER.info("gettingValue() i={}", i);
        return i;
    }
}
