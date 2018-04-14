package soutvoid.com.personalwallet.interactor.util

import io.reactivex.Observable

/**
 * Since RxJava2 does not allow emitting null, throw [NoSuchElementException] if result of [action] is null
 */
fun <T> observableFromCallableOrError(action: () -> T?): Observable<T> = Observable.create {
    val result = action()
    if (result != null) {
        it.onNext(result)
    } else {
        it.onError(NoSuchElementException())
    }
    it.onComplete()
}

/**
 * Since RxJava2 does not allow emitting null, do not emit anything if result of [action] is null
 */
fun <T> observableFromCallableOrEmpty(action: () -> T?): Observable<T> = Observable.create {
    val result = action()
    if (result != null) {
        it.onNext(result)
    }
    it.onComplete()
}
