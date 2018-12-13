package com.android.testing

import com.android.testing.models.Topics
import io.reactivex.observers.TestObserver
import io.reactivex.subscribers.TestSubscriber
import okhttp3.mockwebserver.MockWebServer


class RemoteDataSourceTest {
    var mResultList: List<Topics>? = null
    var mMockWebServer: MockWebServer? = null
    var mSubscriber: TestObserver<List<Topics>>? = null
}