package st.prestoq.viewmodel.model

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * @author sumit.T
 * */
class ApiResponse {
    @JsonProperty("canvasUnit")
    val canvasUnit: Int? = null
    @JsonProperty("managerSpecials")
    val managerSpecials: List<ManagerSpecial>? = null
}