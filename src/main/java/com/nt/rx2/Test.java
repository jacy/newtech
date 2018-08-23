package com.nt.rx2;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

public class Test {
	public static void main(String[] args) throws Exception {
		testJavaMultipleThread(20);
		testRxJavaAsynchronously(20);
	}

	public static void testRxJavaAsynchronously(int count) throws Exception {
		long start = System.currentTimeMillis();
		CountDownLatch finishedLatch = new CountDownLatch(1);
		System.out.println("Main Thread: " + Thread.currentThread().getName());
		Observable.range(0, count).subscribeOn(Schedulers.io()).flatMap(c -> {
			return Observable.just(c).subscribeOn(Schedulers.io()).map(i -> {
				try {
					System.out.println("Producer " + i + " thread: " + Thread.currentThread().getName());
					Thread.sleep(100);
					return i;
				} catch (Exception e) {
					return -1;
				}
			});
		}).subscribe(i -> {
			System.out.println(i);
		}, e -> {
			e.printStackTrace();
		}, () -> {
			System.out.println("Notification Thread: " + Thread.currentThread().getName());
			finishedLatch.countDown();
		});
		finishedLatch.await();
		long end = System.currentTimeMillis();
		System.out.println("RxJava time elapsed: " + (end - start));
	}

	public static void testJavaMultipleThread(int count) throws Exception {
		long start = System.currentTimeMillis();
		CountDownLatch finishedLatch = new CountDownLatch(count);
		IntStream.range(0, count).forEach(i -> {
			new Thread(() -> {
				try {
					Thread.sleep(100);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					finishedLatch.countDown();
				}
			}).start();
		});
		finishedLatch.await();
		long end = System.currentTimeMillis();
		System.out.println("Java time elapsed: " + (end - start));
	}
}
