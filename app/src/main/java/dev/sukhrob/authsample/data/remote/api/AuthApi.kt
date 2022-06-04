package dev.sukhrob.authsample.data.remote.api

import dev.sukhrob.authsample.data.remote.response.LoginResponse
import dev.sukhrob.authsample.data.remote.response.User
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi {

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

}