package evan.chen.tutorial.cartitemadding

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Point
import android.util.AttributeSet
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView

class CartAnimationView constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    ImageView(context, attrs, defStyleAttr) {

    var startPosition: Point? = null
    var endPosition: Point? = null
    var listener: OnAnimationListener? = null

    interface OnAnimationListener {
        fun onFinishAnimation()
    }

    fun startAnimation() {
        if (startPosition == null || endPosition == null) return

        val pointX = (startPosition!!.x + endPosition!!.x) / 3 * 1
        val pointY = endPosition!!.y + 100
        val bezierEvaluator = BezierEvaluator(Point(pointX, pointY))

        val animator = ValueAnimator.ofObject(bezierEvaluator, startPosition, endPosition)

        animator.addUpdateListener { valueAnimator ->
            val point = valueAnimator.animatedValue as Point

            //隨著動畫改變Point，重新設定x,y
            x = point.x.toFloat()
            y = point.y.toFloat()
            invalidate()
        }

        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)

                //動畫結束時，移除該View
                val viewGroup = parent as ViewGroup
                viewGroup.removeView(this@CartAnimationView)
                listener?.onFinishAnimation()
            }
        })

        animator.duration = 1000
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.start()

    }
}