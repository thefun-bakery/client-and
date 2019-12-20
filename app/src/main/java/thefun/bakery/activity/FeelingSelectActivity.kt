package thefun.bakery.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import thefun.bakery.FeelingSelectAdapter
import thefun.bakery.R
import thefun.bakery.api.ApiManager

class FeelingSelectActivity: AppCompatActivity() {

    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_feeling_select)

        ApiManager.api?.getEmotionImages()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                Log.e("###", it.toString())
            }, {
                Log.e("###", it.localizedMessage)
            })

        findViewById<ImageView>(R.id.feeling_close_btn).setOnClickListener {
            finish()
        }

        val adapter = FeelingSelectAdapter()
        adapter.itemList.add(R.drawable.ic_feel_hug)
        adapter.itemList.add(R.drawable.ic_feel_nervous)
        adapter.itemList.add(R.drawable.ic_feel_embarrassment)
        adapter.itemList.add(R.drawable.ic_feel_surprise)
        adapter.itemList.add(R.drawable.ic_feel_scared)
        adapter.itemList.add(R.drawable.ic_feel_2)
        adapter.itemList.add(R.drawable.ic_feel_1)
        adapter.itemList.add(R.drawable.ic_feel_3)
        adapter.itemList.add(R.drawable.ic_feel_4)
        adapter.itemList.add(R.drawable.ic_feel_5)
        adapter.itemList.add(R.drawable.ic_feel_6)
        adapter.itemList.add(R.drawable.ic_feel_7)
        adapter.itemList.add(R.drawable.ic_feel_8)
        adapter.itemList.add(R.drawable.ic_feel_9)
        adapter.itemList.add(R.drawable.ic_feel_10)
        adapter.itemList.add(R.drawable.ic_feel_11)
        adapter.itemList.add(R.drawable.ic_feel_12)
        adapter.itemList.add(R.drawable.ic_feel_13)
        adapter.itemList.add(R.drawable.ic_feel_14)
        adapter.itemList.add(R.drawable.ic_feel_15)
        adapter.itemList.add(R.drawable.ic_feel_16)
        adapter.itemList.add(R.drawable.ic_feel_17)
        adapter.itemList.add(R.drawable.ic_feel_18)
        adapter.itemList.add(R.drawable.ic_feel_19)
        adapter.itemList.add(R.drawable.ic_feel_20)
        adapter.itemList.add(R.drawable.ic_feel_21)
        adapter.itemList.add(R.drawable.ic_feel_22)
        adapter.itemList.add(R.drawable.ic_feel_23)
        adapter.itemList.add(R.drawable.ic_feel_24)
        adapter.itemList.add(R.drawable.ic_feel_26)
        adapter.itemList.add(R.drawable.ic_feel_27)
        adapter.itemList.add(R.drawable.ic_feel_28)
        adapter.itemList.add(R.drawable.ic_feel_29)
        adapter.itemList.add(R.drawable.ic_feel_30)
        adapter.itemList.add(R.drawable.ic_feel_31)
        adapter.itemList.add(R.drawable.ic_feel_32)
        adapter.itemList.add(R.drawable.ic_feel_33)
        adapter.itemList.add(R.drawable.ic_feel_34)
        adapter.itemList.add(R.drawable.ic_feel_35)
        adapter.itemList.add(R.drawable.ic_feel_36)
        adapter.itemList.add(R.drawable.ic_feel_37)
        adapter.itemList.add(R.drawable.ic_feel_38)
        adapter.itemList.add(R.drawable.ic_feel_40)
        adapter.itemList.add(R.drawable.ic_feel_41)

        recyclerView = findViewById(R.id.recycler_view)

        recyclerView?.adapter = adapter
        val gridLayoutManager = GridLayoutManager(this, 3)
        recyclerView?.layoutManager = gridLayoutManager


        adapter.clickSubject.subscribe {
            intent = Intent(this@FeelingSelectActivity, ImageSelectActivity::class.java)
            intent.putExtra("resId", it)
            startActivity(intent)
        }
    }
}