package thefun.bakery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.subjects.PublishSubject
import thefun.bakery.data.EmotionImg
import kotlinx.android.synthetic.main.layout_feeling_select.view.*


class FeelingSelectAdapter: RecyclerView.Adapter<FeelingSelectAdapter.ViewHolder>() {

    val itemList = ArrayList<Int>()
    val imgItemList = ArrayList<EmotionImg>()
    val clickSubject: PublishSubject<Int> = PublishSubject.create()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_feeling_select, parent, false)
        return ViewHolder(view, clickSubject)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(itemList[position])
    }

    class ViewHolder(itemView: View, private val clickSubject: PublishSubject<Int>) : RecyclerView.ViewHolder(itemView) {
        private val feelingImg: ImageView = itemView.im_feeling

        fun setData(resId: Int) {
            feelingImg.setImageResource(resId)

            itemView.setOnClickListener {
                clickSubject.onNext(resId)
            }
        }

//        fun fetchSvg(context: Context, url: Url, imageView: ImageView) {
//            if (httpClient == null) {
//                // Use cache for performance and basic offline capability
//                httpClient = OkHttpClient.Builder()
//                    .cache(Cache(context.cacheDir, 5 * 1024 * 1014))
//                    .build()
//            }
//
//            val request = Request.Builder().url(url).build()
//            httpClient?.newCall(request)?.enqueue(object : Callback {
//
//                override fun onFailure(call: Call, e: IOException) {
//                    Log.e("###", "")
//                }
//
//                override fun onResponse(call: Call, response: Response) {
//                    val stream = response.body()?.byteStream()
//                    Sharp.loadInputStream(stream).into(imageView)
//                    stream?.close()
//                }
//            })
//        }
    }
}