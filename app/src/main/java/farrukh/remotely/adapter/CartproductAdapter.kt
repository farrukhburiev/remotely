package farrukh.remotely.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import coil.load
import farrukh.remotely.R
import farrukh.remotely.model.CartProduct
import farrukh.remotely.model.Product

class CartproductAdapter(
    var array: MutableList<CartProduct>,
//    var product: Product,
    var listener: ItemClick
) : RecyclerView.Adapter<CartproductAdapter.MyHolder>() {

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name = itemView.findViewById<TextView>(R.id.title)
        var layout = itemView.findViewById<CardView>(R.id.cart_item_card)
        //        var discount = itemView.findViewById<TextView>(R.id.discount_discount)
        var price = itemView.findViewById<TextView>(R.id.price)
        var quantity = itemView.findViewById<TextView>(R.id.quantity)
        var total = itemView.findViewById<TextView>(R.id.total)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.product_layout, parent, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return array.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {

        val item = array.get(position)

        holder.name.text = item.title

//        holder.discount.text = item.discountPercentage.toString()
//        holder.rating.text = product.rating.toString()
        holder.price.text = item.price.toString() +" $ "
        holder.total.text = item.total.toString() +" $ "
        holder.quantity.text = item.quantity.toString()
//        holder.img.load(product.thumbnail)



        holder.layout.setOnClickListener {
            listener.OnItemClick(item)
        }

    }

    interface ItemClick {
        fun OnItemClick(product: CartProduct)
    }

}