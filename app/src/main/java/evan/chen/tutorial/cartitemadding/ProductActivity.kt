package evan.chen.tutorial.cartitemadding

import android.graphics.Point
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_product.*
import android.view.View
import android.widget.TextView
import android.widget.LinearLayout

class ProductActivity : AppCompatActivity(), CartAnimationView.OnAnimationListener {

    private var cartBadge: TextView? = null
    private var cartCount: Int = 0

    override fun onFinishAnimation() {
        setUpBadge()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        send.setOnClickListener {

            cartCount += number_select.textValue

            //商品圖片為 Start Point
            val position = IntArray(2)
            productImageView.getLocationInWindow(position)
            val startPoint = Point(position[0], position[1])

            //購物車Icon 為 End Point
            val endPosition = IntArray(2)
            findViewById<View>(R.id.item_cart).getLocationOnScreen(endPosition)
            val endPoint = Point(endPosition[0], endPosition[1])

            val cartAnimView = CartAnimationView(this)
            cartAnimView.setImageResource(R.mipmap.product)
            cartAnimView.layoutParams = LinearLayout.LayoutParams(80, 80)
            cartAnimView.startPosition = startPoint
            cartAnimView.endPosition = endPoint
            cartAnimView.listener = ProductActivity@ this

            val rootView = this.window.decorView as ViewGroup
            rootView.addView(cartAnimView)

            cartAnimView.startAnimation()

        }

    }

    private fun setUpBadge() {

        if (cartCount == 0) {
            cartBadge?.visibility = View.INVISIBLE
        } else {
            cartBadge?.visibility = View.VISIBLE
            cartBadge?.text = cartCount.toString()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_layout, menu)
        val menuItem = menu!!.findItem(R.id.item_cart)

        cartBadge = menuItem.actionView.findViewById(R.id.cart_badge) as TextView

        setUpBadge()

        return super.onCreateOptionsMenu(menu)

    }

}
