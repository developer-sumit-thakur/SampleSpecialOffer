package st.prestoq.viewmodel.model

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.google.gson.Gson
import st.prestoq.api.ApiService

class SpecialsViewModel : ViewModel() {

    companion object {
        private val TAG: String = SpecialsViewModel::class.java.simpleName
    }

    lateinit var dataService: ApiService

    var responseLiveData: MutableLiveData<ApiResponse>? = null
        get() {
            if (field == null) {
                field = MutableLiveData()
            }
            loadData(field)
            return field
        }

    private fun loadData(result: MutableLiveData<ApiResponse>?) {
        result?.apply {
            Log.d(TAG, "loadData()")
            dataService = ApiService.newInstance()
            dataService.initService()
            dataService.callApi(object : ApiService.ResponseListener {
                override fun onSuccess(response: ApiResponse) {
                    Log.d(TAG, "Response : " + Gson().toJson(response.toString()))
                    result?.postValue(response)
                }

                override fun onError(errorMsg: String) {
                    Log.e(TAG, errorMsg)
                    result?.postValue(null)
                }
            })
        }
    }
}