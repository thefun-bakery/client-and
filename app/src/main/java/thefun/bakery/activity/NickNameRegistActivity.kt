package thefun.bakery.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import thefun.bakery.BuildConfig
import thefun.bakery.Const
import thefun.bakery.R
import thefun.bakery.api.ApiClient
import thefun.bakery.data.MyData
import thefun.bakery.data.NickName

class NickNameRegistActivity : AppCompatActivity() {

    var nickNameEditor: EditText? = null
    var nickNameCounter: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_nickname_regist)

        nickNameCounter = findViewById(R.id.nickname_counter)
        nickNameEditor = findViewById(R.id.nickname_edit_text)
        nickNameEditor?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                nickNameEditor?.text?.let {

                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        findViewById<ImageView>(R.id.btn_delete_nickname).setOnClickListener {
            nickNameEditor?.let {
                it.setText("")
            }
        }

        findViewById<TextView>(R.id.btn_nickname_done).setOnClickListener{
            val nickName = nickNameEditor?.text.toString()

            if (!nickName.isNullOrEmpty()) {
                ApiClient.api?.updateNickName(NickName(nickName))?.enqueue(object : Callback<MyData> {
                    override fun onResponse(call: Call<MyData>, response: Response<MyData>) {
                        response.body()?.let {
                            Log.e(Const.LOG, "${it.nickname}, ${it.profileImageUrl}")

                            val pref = getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)
                            val editor = pref.edit()
                            editor.putString(Const.NICK_NAME, it.nickname)
                            editor.putString(Const.PROFILE_IMAGE_URL, it.profileImageUrl)
                            editor.commit()
                            editor.apply()

                            startActivity(Intent(this@NickNameRegistActivity, MainActivity::class.java))
                            finish()
                        }
                    }

                    override fun onFailure(call: Call<MyData>, t: Throwable) {
                        Log.e(Const.LOG, t.localizedMessage)
                    }
                })
            }
        }
    }
}