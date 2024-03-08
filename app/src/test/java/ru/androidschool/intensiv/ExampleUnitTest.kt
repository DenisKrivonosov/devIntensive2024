package ru.androidschool.intensiv

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val source: Observable<String> = Observable.create { emitter ->
            emitter.onNext("kotlin")
            emitter.onNext("Java")
            emitter.onNext("Go")
            emitter.onNext("Hello")
            emitter.onNext("Hello")
        }
        source.subscribe(object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
                println("onSubscribe")
            }

            override fun onError(e: Throwable) {
                println("onError")
            }

            override fun onComplete() {
                println("onComplete")
            }

            override fun onNext(t: String) {
                println("onNext")
                println(t)
            }
        })
    }
}
