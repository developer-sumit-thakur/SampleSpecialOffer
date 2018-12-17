package st.prestoq.api

import io.reactivex.Observable
import retrofit2.http.GET
import st.prestoq.viewmodel.model.ApiResponse

interface ApiServiceInterface {
    @GET
    fun getManagerSpecials(): Observable<ApiResponse>
}