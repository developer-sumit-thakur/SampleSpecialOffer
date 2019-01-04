package st.prestoq.viewmodel.model

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * @author sumit.T
 * */
class ManagerSpecial {
    @JsonProperty("display_name")
    val display_name: String? = null
    @JsonProperty("height")
    val height: Int? = null
    @JsonProperty("imageUrl")
    val imageUrl: String? = null
    @JsonProperty("original_price")
    val original_price: String? = null
    @JsonProperty("price")
    val price: String? = null
    @JsonProperty("width")
    val width: Int? = null
}