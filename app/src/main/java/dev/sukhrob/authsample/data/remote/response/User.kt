package dev.sukhrob.authsample.data.remote.response

data class User(
    val access_token: String? = null,
    val created_at: String,
    val email: String,
    val email_verified_at: Any,
    val id: Int,
    val name: String,
    val updated_at: String
)
