package ru.androidschool.intensiv.data.repository

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

abstract class CacheProvider<T>(
    val uiScheduler: Scheduler = AndroidSchedulers.mainThread(),
    val backgroundScheduler: Scheduler = Schedulers.io()
) {
    fun getObservable(type: CachePolicy = CachePolicy.CACHE_THEN_REMOTE): Observable<T> {
        when (type) {
            CachePolicy.CACHE -> return createCacheObservable()
            CachePolicy.REMOTE -> return createRemoteObservable()
            CachePolicy.CACHE_ELSE_REMOTE -> {
                val remoteObservable = createRemoteObservable()
                return createCacheObservable()
                    .onErrorResumeNext(remoteObservable)
                    .switchIfEmpty(remoteObservable)
            }

            CachePolicy.CACHE_THEN_REMOTE -> {
                val remoteObservable = createRemoteObservable()
                return createCacheObservable()
                    .onErrorResumeNext(remoteObservable)
                    .concatWith(remoteObservable)
            }
        }
    }

    protected abstract fun createRemoteObservable(): Observable<T>

    protected abstract fun createCacheObservable(): Observable<T>
}
