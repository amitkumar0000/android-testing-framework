package com.android.testing;

import android.content.Context;
import android.support.test.rule.ActivityTestRule;

import com.android.testing.interfaces.Service;
import com.android.testing.models.Topics;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


    private static final String TEST_STRING = "HELLO WORLD!";
    //As we don't have access to Context in our JUnit test classes, we need to mock it
    @Mock
    Context mMockContext;


    @Mock
    Service service;

    @Before
    public void setup(){

    }


    @Test
    public void testServiceApi(){

        List<Topics> topics = new ArrayList<>();
        Topics topics1 = new Topics(1,"topics1");
        Topics topics2 = new Topics(1,"topics2");
        topics.add(topics1);
        topics.add(topics2);
        Observable<List<Topics>> observable = Observable.just(topics);
        when(service.getTopicsRx()).thenReturn(observable);

        Assert.assertEquals(service.getTopicsRx(),observable);

        TestObserver<List<Topics>> observer = service.getTopicsRx().test();

        observer.awaitTerminalEvent();
        observer.assertNoErrors();

        observer.assertValue(l->l.size() == 2);
        observer.assertValue(l-> l.get(0).getId() == 1);

        verify(service,times(2)).getTopicsRx();
    }



    @Test
    public void testMockMethod(){
        List mockList = Mockito.mock(ArrayList.class);
        mockList.add("hello world");
        assertEquals(0, mockList.size());
        verify(mockList).add("hello world");
    }

    @Test
    public void readStringFromContext() {
        //Returns the TEST_STRING when getString(R.string.hello_world) is called
        when(mMockContext.getString(R.string.app_name)).thenReturn(TEST_STRING);
        //Creates an object of the ClassUnderTest with the mock context
        ClassUnderTest objectUnderTest = new ClassUnderTest(mMockContext);
        //Stores the return value of getHelloWorldString() in result
        String result = objectUnderTest.getHelloWorldString();
        //Asserts that result is the value of TEST_STRING
        assertThat(result, is(TEST_STRING));
    }

    class ClassUnderTest{

        Context mContext;
        public ClassUnderTest(Context context) {
            mContext = context;
        }
        public String getHelloWorldString() {
            return mContext.getString(R.string.app_name);
        }
    }
}