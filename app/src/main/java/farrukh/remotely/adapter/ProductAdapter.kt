package farrukh.remotely.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import coil.load
import farrukh.remotely.R
import farrukh.remotely.model.Product

class ProductAdapter(
    var array: MutableList<Product>,
    var listener: ItemClick
) : RecyclerView.Adapter<ProductAdapter.MyHolder>() {

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name = itemView.findViewById<TextView>(R.id.name)
        var layout = itemView.findViewById<ConstraintLayout>(R.id.card_mini)
//        var discount = itemView.findViewById<TextView>(R.id.discount_discount)
        var price = itemView.findViewById<TextView>(R.id.price)
        var rating = itemView.findViewById<TextView>(R.id.rating)
        var img = itemView.findViewById<ImageView>(R.id.img)
        var like = itemView.findViewById<ImageView>(R.id.like)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.discount_layout, parent, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return array.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {

        val item = array.get(position)

        holder.name.text = item.title
        holder.rating.text = item.rating.toString()
//        holder.discount.text = item.discountPercentage.toString()
        holder.price.text = item.price.toString()
        holder.img.load(item.thumbnail)

        holder.like.setOnClickListener {
//            listener.OnItemClick(item)

        }

        holder.layout.setOnClickListener {
            listener.OnItemClick(item)
        }

        }

    interface ItemClick {
        fun OnItemClick(product: Product)
    }

    }