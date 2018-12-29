package com.android.Gus.data;


import com.android.Gus.data.remote.model.User;

import java.io.IOException;

import io.reactivex.Observable;


public class UserRepositoryImpl implements UserRepository {

    private GithubUserRestService githubUserRestService;

    public UserRepositoryImpl(GithubUserRestService githubUserRestService) {
        this.githubUserRestService = githubUserRestService;
    }

    @Override
    public Observable<User> searchUsers(final String searchTerm) {
        return Observable.defer(() -> githubUserRestService.searchGithubUsers(searchTerm).concatMap(
                usersList -> Observable.fromIterable(usersList.getItems())
                        .concatMap(user -> githubUserRestService.getUser(user.getLogin()))))
                .retryWhen(observable -> observable.flatMap(o -> {
                    if (o instanceof IOException) {
                        return Observable.just(o);
                    }
                    return Observable.error(o);
                }));
    }

}