package farrukh.remotely

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load

class ViewPagerAdapter(val image: List<String>) : RecyclerView.Adapter<ViewPagerAdapter.MyHolder>() {
    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image_item: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_img, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return image.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        var item = image.get(position)

        holder.image_item.load(item)
    }
}