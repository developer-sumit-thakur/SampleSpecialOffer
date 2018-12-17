package st.prestoq.api

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import st.prestoq.viewmodel.model.ApiResponse

class ApiService {
    interface ResponseListener {
        fun onSuccess(respones: ApiResponse)
        fun onError(errorMsg: String)
    }

    companion object {
        private val baseUrl = "https://prestoq.com/android-coding-challenge"

        lateinit var dataService: ApiServiceInterface
        private var instance: ApiService? = null

        fun newInstance(): ApiService {
            if (instance == null) instance = ApiService()
            return instance as ApiService
        }
    }

    fun initService() {
        val clientBuilder = OkHttpClient.Builder()
        val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(clientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        dataService = retrofit.create<ApiServiceInterface>(ApiServiceInterface::class.java)
    }

    fun callApi(responseListener: ResponseListener): Observable<ApiResponse>? {
        var retVal: Observable<ApiResponse>? = null

        try {
            dataService.getManagerSpecials()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : DisposableObserver<ApiResponse>() {
                        override fun onComplete() {}

                        override fun onNext(value: ApiResponse) {
                            if (responseListener != null) {
                                responseListener.onSuccess(value)
                            }
                        }

                        override fun onError(e: Throwable) {
                            if (responseListener != null) {
                                responseListener.onError("error: ${e.message}")
                            }
                        }
                    })
        } catch (ex: Exception) {
            if (responseListener != null) {
                responseListener.onError("error: ${ex.message}")
            }
        }

        return retVal
    }
}