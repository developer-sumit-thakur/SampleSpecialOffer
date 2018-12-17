package st.prestoq.viewmodel.model

import com.fasterxml.jackson.annotation.JsonProperty

class ManagerSpecial {
    @JsonProperty("display_name")
    val displayName: String? = null
    @JsonProperty("height")
    val height: Int? = null
    @JsonProperty("imageUrl")
    val imageUrl: String? = null
    @JsonProperty("original_price")
    val originalPrice: String? = null
    @JsonProperty("price")
    val price: String? = null
    @JsonProperty("width")
    val width: Int? = null
}