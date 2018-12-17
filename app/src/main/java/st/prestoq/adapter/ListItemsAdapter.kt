package st.prestoq.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import st.prestoq.R
import st.prestoq.viewmodel.model.ManagerSpecial


class ListItemsAdapter(private var managerSpecials: List<ManagerSpecial>) : RecyclerView.Adapter<ListItemsAdapter.ViewHolder>() {

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

    fun setSpecials(items: List<ManagerSpecial>) {
        this.managerSpecials = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemsAdapter.ViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.item_list_view, parent, false)
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(managerSpecials[position]) {
            holder.name.setText(displayName)
            holder.originalPrice.setText(originalPrice)
            holder.price.setText(price)
            Glide.with(holder.itemView.context).load(imageUrl).into(holder.itemImage);
        }

    }
}