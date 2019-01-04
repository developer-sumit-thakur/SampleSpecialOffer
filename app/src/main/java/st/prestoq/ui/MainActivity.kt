package st.prestoq.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import st.prestoq.R

/**
 * @author sumit.T
 * */
class MainActivity : AppCompatActivity() {

    lateinit var errorText: TextView

    lateinit var container: FrameLayout

    lateinit var progress_container: FrameLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        errorText = findViewById(R.id.error_text)
        container = findViewById(R.id.fragment_container)
        progress_container = findViewById(R.id.loadingBar)

        findViewById<View>(R.id.managers_specials_btn).setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                Toast.makeText(this@MainActivity, "Hello pal!!", Toast.LENGTH_SHORT).show()
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                        DiscoverFragment(), DiscoverFragment.TAG).commit()
            }
        })
    }
}
