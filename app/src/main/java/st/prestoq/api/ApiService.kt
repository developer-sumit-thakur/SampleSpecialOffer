package st.prestoq.api

import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import st.prestoq.viewmodel.model.ApiResponse

class ApiService {
    interface ResponseListener {
        fun onSuccess(respones: ApiResponse)
        fun onError(errorMsg: String)
    }

    companion object {
        private val baseUrl = "https://prestoq.com/"
        private val fullUrl = "https://prestoq.com/android-coding-challenge"

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
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        dataService = retrofit.create<ApiServiceInterface>(ApiServiceInterface::class.java)
    }

    fun callApi(responseListener: ResponseListener): Observable<ApiResponse>? {
        var retVal: Observable<ApiResponse>? = null

        try {
            dataService.getManagerSpecials(fullUrl)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : DisposableObserver<ApiResponse>() {
                        override fun onComplete() {
                            Log.d(ApiService::class.java.name, "success response complete!")
                        }

                        override fun onNext(value: ApiResponse) {
                            Log.d(ApiService::class.java.name, "success response: " + value)
                            if (responseListener != null) {
                                responseListener.onSuccess(value)
                            }
                        }

                        override fun onError(e: Throwable) {
                            Log.e(ApiService::class.java.name, "error response: " + e)
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