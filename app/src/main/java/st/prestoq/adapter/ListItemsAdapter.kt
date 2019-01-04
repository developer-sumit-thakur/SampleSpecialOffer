package st.prestoq.adapter

import android.app.Activity
import android.graphics.Paint
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.android.flexbox.AlignSelf
import com.google.android.flexbox.FlexboxLayoutManager
import st.prestoq.R
import st.prestoq.viewmodel.model.ManagerSpecial


/**
 * @author sumit.T
 * */
class ListItemsAdapter(private var managerSpecials: List<ManagerSpecial>) : RecyclerView.Adapter<ListItemsAdapter.ViewHolder>() {
    var canvasUnit: Int = 1

    override fun getItemCount(): Int {
        return if (managerSpecials != null) {
            managerSpecials?.size
        } else 0
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView
        var originalPrice: TextView
        var price: TextView
        var itemImage: ImageView

        init {
            name = view.findViewById(R.id.display_name)
            originalPrice = view.findViewById(R.id.original_price)
            price = view.findViewById(R.id.price)
            itemImage = view.findViewById(R.id.item_image)
        }
    }

    fun setSpecials(unit: Int?, items: List<ManagerSpecial>) {
        this.managerSpecials = items
        unit?.apply { canvasUnit = this }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemsAdapter.ViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.item_list_view, parent, false)
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val params = holder.itemView.layoutParams as RecyclerView.LayoutParams
        params.bottomMargin = 20
        setWidhtAndHeight(holder.itemView, managerSpecials[position].width, managerSpecials[position].height)
        with(managerSpecials[position]) {
            holder.name.setText(display_name)
            holder.originalPrice.setText("$" + original_price)
            holder.originalPrice.setPaintFlags(holder.originalPrice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
            holder.price.setText("$" + price)
            Glide.with(holder.itemView.context).load(imageUrl).into(holder.itemImage);
        }
    }

    private fun setWidhtAndHeight(itemView: View, widthUnit: Int?, heightUnit: Int?) {
        val displaymetrics = DisplayMetrics()
        (itemView.context as Activity).windowManager.defaultDisplay.getMetrics(displaymetrics)
        val perUnitSize = displaymetrics.widthPixels / canvasUnit
        widthUnit?.apply { itemView.getLayoutParams().width = this * perUnitSize }
        heightUnit?.apply { itemView.getLayoutParams().height = (this * perUnitSize) + perUnitSize }
        Log.d(ListItemsAdapter::class.java.name, "widhtUnit $widthUnit, heighUnit $heightUnit, perUnitSize $perUnitSize")

        val flexboxLp = itemView.layoutParams
        if (flexboxLp is FlexboxLayoutManager.LayoutParams) {
            flexboxLp.flexGrow = 1.0f
            flexboxLp.alignSelf = AlignSelf.FLEX_START
        }
    }
}