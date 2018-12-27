package com.android.Gus.data

import com.android.Gus.data.remote.model.User
import io.reactivex.Observable

interface UserRepository {
    fun searchUsers(searchTerm: String): Observable<User>

}