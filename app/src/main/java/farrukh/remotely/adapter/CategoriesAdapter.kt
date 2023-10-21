package farrukh.remotely.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.card.MaterialCardView
import farrukh.remotely.R
import farrukh.remotely.model.Product

class CategoriesAdapter(
    var array: MutableList<String>,
    var context: Context,
    var listener: ItemClick
) : RecyclerView.Adapter<CategoriesAdapter.MyHolder>() {
    var current = 0

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var text = itemView.findViewById<TextView>(R.id.category_name1)
        var cardView = itemView.findViewById<MaterialCardView>(R.id.category_mcv)
//        //        var discount = itemView.findViewById<TextView>(R.id.discount_discount)
//        var price = itemView.findViewById<TextView>(R.id.price)
//        var rating = itemView.findViewById<TextView>(R.id.rating)
//        var img = itemView.findViewById<ImageView>(R.id.img)
//        var like = itemView.findViewById<ImageView>(R.id.like)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.category_layout, parent, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return array.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {

        val item = array.get(position)


        if (position == 0) {
            holder.text.text = "All"
        } else {
            holder.text.text = array[position - 1].capitalize()
        }
        if (current == position) {
            holder.cardView.setCardBackgroundColor(context.resources.getColor(R.color.purple))
            holder.text.setTextColor(context.resources.getColor(R.color.white))
        } else {
            holder.cardView.setCardBackgroundColor(context.resources.getColor(R.color.white))
            holder.text.setTextColor(context.resources.getColor(R.color.purple))
        }
        holder.cardView.setOnClickListener {
            if (position != current) {
                notifyItemChanged(current)
                current = position
                notifyItemChanged(current)
                if (position == 0) listener.OnItemClick("")
                else listener.OnItemClick(array[position - 1])
            }
//            categoryRecyclerView.visibility = View.GONE
        }
    }
    interface ItemClick {
        fun OnItemClick(category: String)
    }

}