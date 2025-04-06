package com.example.core.data.data_source.RetrofitText

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitApiTest {

    @POST("pet")
    suspend fun postRegistration(
        @Body postRegistrationResponse: PostRegistrationResponse
    ): Response<PostRegistrationResponse>
}

data class PostRegistrationResponse(
    val id: Long? = null,
    val category: Category? = null,
    val name: String? = null,
    val photoUrls: List<String>? = null,
    val tags: List<Tags>? = null,
    val status: String? = null
)
data class Category(
    val id: Int?,
    val name: String?
)
data class Tags(
    val id: Int?,
    val name: String?
)