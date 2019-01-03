package st.prestoq.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Url
import st.prestoq.viewmodel.model.ApiResponse

interface ApiServiceInterface {
    @GET
    fun getManagerSpecials(@Url url: String): Observable<ApiResponse>
}