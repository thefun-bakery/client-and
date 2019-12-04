package thefun.bakery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_feeling_select.view.*

class FeelingSelectAdapter: RecyclerView.Adapter<FeelingSelectAdapter.ViewHolder>() {

    val itemList = ArrayList<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_feeling_select, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(itemList[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val feelingImg: ImageView = itemView.im_feeling

        fun setData(resId: Int) {
            feelingImg.setBackgroundResource(resId)

            itemView.setOnClickListener {
                
            }
        }
    }
}