package com.android.Gus.data

import com.android.Gus.data.remote.model.User
import com.android.Gus.data.remote.model.UsersList
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubUserRestService {
    @GET("/search/users?per_page=2")
    fun searchGithubUsers(@Query("q") searchTerm: String): Observable<UsersList>

    @GET("/users/{username}")
    fun getUser(@Path("username") username: String): Observable<User>
}
