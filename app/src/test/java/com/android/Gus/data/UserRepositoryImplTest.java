package com.android.Gus.data;

import com.android.Gus.data.remote.model.User;
import com.android.Gus.data.remote.model.UsersList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.TestObserver;
import io.reactivex.subscribers.TestSubscriber;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserRepositoryImplTest {

    private static final String USER_LOGIN_RIGGAROO = "riggaroo";
    private static final String USER_LOGIN_2_REBECCA = "rebecca";
    @Mock
    GithubUserRestService githubUserRestService;
    private UserRepository userRepository;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        userRepository = new UserRepositoryImpl(githubUserRestService);
    }


    @Test
    public void searchUsers_200OkResponse_InvokesCorrectApiCalls(){

        when(githubUserRestService.searchGithubUsers(anyString())).thenReturn(Observable.just(githubUserList()));
        when(githubUserRestService.getUser(anyString()))
                .thenReturn(Observable.just(user1FullDetails()), Observable.just(user2FullDetails()));

        //When
        TestObserver<User> subscriber = userRepository.searchUsers(USER_LOGIN_RIGGAROO).test();

        //Then
        subscriber.awaitTerminalEvent();
        subscriber.assertNoErrors();

        List<User> users = subscriber.values();
        Assert.assertEquals(USER_LOGIN_RIGGAROO, users.get(0).getLogin());
        Assert.assertEquals(USER_LOGIN_2_REBECCA, users.get(1).getLogin());

        verify(githubUserRestService).searchGithubUsers(USER_LOGIN_RIGGAROO);
        verify(githubUserRestService).getUser(USER_LOGIN_RIGGAROO);
        verify(githubUserRestService). getUser(USER_LOGIN_2_REBECCA);


    }

    @Test
    public void searchUsers_IOExceptionThenSuccess_SearchUsersRetried() {
        //Given
        when(githubUserRestService.searchGithubUsers(anyString()))
                .thenReturn(getIOExceptionError(), Observable.just(githubUserList()));
        when(githubUserRestService.getUser(anyString()))
                .thenReturn(Observable.just(user1FullDetails()), Observable.just(user2FullDetails()));

        //When
        TestObserver<User> subscriber = userRepository.searchUsers(USER_LOGIN_RIGGAROO).test();


        //Then
        subscriber.awaitTerminalEvent();
        subscriber.assertNoErrors();

        verify(githubUserRestService, times(2)).searchGithubUsers(USER_LOGIN_RIGGAROO);

        verify(githubUserRestService).getUser(USER_LOGIN_RIGGAROO);
        verify(githubUserRestService).getUser(USER_LOGIN_2_REBECCA);
    }

    private UsersList githubUserList() {
        User user = new User();
        user.setLogin(USER_LOGIN_RIGGAROO);

        User user2 = new User();
        user2.setLogin(USER_LOGIN_2_REBECCA);

        List<User> githubUsers = new ArrayList<>();
        githubUsers.add(user);
        githubUsers.add(user2);
        UsersList usersList = new UsersList();
        usersList.setItems(githubUsers);
        return usersList;
    }

    private User user1FullDetails() {
        User user = new User();
        user.setLogin(USER_LOGIN_RIGGAROO);
        user.setName("Rigs Franks");
        user.setAvatarUrl("avatar_url");
        user.setBio("Bio1");
        return user;
    }

    private User user2FullDetails() {
        User user = new User();
        user.setLogin(USER_LOGIN_2_REBECCA);
        user.setName("Rebecca Franks");
        user.setAvatarUrl("avatar_url2");
        user.setBio("Bio2");
        return user;
    }

    private Observable getIOExceptionError() {
        return Observable.error(new IOException());
    }

    private Observable<UsersList> get403ForbiddenError() {
        return Observable.error(new HttpException(
                Response.error(403, ResponseBody.create(MediaType.parse("application/json"), "Forbidden"))));

    }
    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void searchUsers() {
    }
}