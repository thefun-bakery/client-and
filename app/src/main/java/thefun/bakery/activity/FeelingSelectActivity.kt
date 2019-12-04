package thefun.bakery.activity

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import thefun.bakery.FeelingSelectAdapter
import thefun.bakery.R

class FeelingSelectActivity: AppCompatActivity() {

    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_feeling_select)

        findViewById<ImageView>(R.id.feeling_close_btn).setOnClickListener {
            finish()
        }

        val adapter = FeelingSelectAdapter()
        adapter.itemList.add(R.drawable.happy_sample_on)
        adapter.itemList.add(R.drawable.happy_sample_on)
        adapter.itemList.add(R.drawable.happy_sample_on)
        adapter.itemList.add(R.drawable.happy_sample_on)
        adapter.itemList.add(R.drawable.happy_sample_on)
        adapter.itemList.add(R.drawable.happy_sample_on)
        adapter.itemList.add(R.drawable.happy_sample_on)
        adapter.itemList.add(R.drawable.happy_sample_on)
        adapter.itemList.add(R.drawable.happy_sample_on)
        adapter.itemList.add(R.drawable.happy_sample_on)
        adapter.itemList.add(R.drawable.happy_sample_on)
        adapter.itemList.add(R.drawable.happy_sample_on)
        adapter.itemList.add(R.drawable.happy_sample_on)
        adapter.itemList.add(R.drawable.happy_sample_on)
        adapter.itemList.add(R.drawable.happy_sample_on)
        adapter.itemList.add(R.drawable.happy_sample_on)
        adapter.itemList.add(R.drawable.happy_sample_on)


        recyclerView = findViewById(R.id.recycler_view)

        recyclerView?.adapter = adapter
        val gridLayoutManager = GridLayoutManager(this, 3)
        recyclerView?.layoutManager = gridLayoutManager
    }
}