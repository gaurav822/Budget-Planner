package com.gaurav.budgetplanner.Views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.gaurav.budgetplanner.BudgetPlannerApp
import com.gaurav.budgetplanner.Utils.Constants
import com.gaurav.budgetplanner.Utils.Utils
import com.gaurav.budgetplanner.features.Onboarding.presentation.Views.Activities.OnBoardActivity
import com.gaurav.budgetplanner.databinding.ActivitySplashScreenBinding
import com.gaurav.budgetplanner.features.settings.activities.PinSetActivity

class SplashScreen : AppCompatActivity() {
    private lateinit var splashAnimation: Animation
    private lateinit var secondAnimation: Animation
    private var _binding:ActivitySplashScreenBinding?=null
    private val binding get() = _binding!!
    private lateinit var splashIcon: ImageView
    val scaleDownFactor = 0.7f
    private var isCurrencySelected = false
    var pin:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        splashIcon = binding.splashIcon
        init()
    }

    private fun init(){
        pin = Utils.retrievePinSecurely(this)
        isCurrencySelected = BudgetPlannerApp.getStorage().getBoolean(Constants.PREF_CURRENCY_SELECTION,false)
        val width = splashIcon.width
        val height = splashIcon.height
        splashAnimation = AnimationUtils.loadAnimation(
            this,
            com.gaurav.budgetplanner.R.anim.splash_icon_animation
        )
        secondAnimation = AnimationUtils.loadAnimation(
            this,
            com.gaurav.budgetplanner.R.anim.splash_second_animation
        )
        Handler(Looper.getMainLooper()).postDelayed({
            splashIcon.startAnimation(splashAnimation)

        }, 1000)

        Handler(Looper.getMainLooper()).postDelayed({

            val currentWidth = splashIcon.width
            val currentHeight = splashIcon.height

            val scaledWidth = (currentWidth * scaleDownFactor).toInt()
            val scaledHeight = (currentHeight * scaleDownFactor).toInt()

            // Set the scaled dimensions for the view
            splashIcon.layoutParams.width = scaledWidth
            splashIcon.layoutParams.height = scaledHeight
            splashIcon.requestLayout()

            splashIcon.startAnimation(secondAnimation)

            Handler(Looper.getMainLooper()).postDelayed({
                pin?.let {
                    intent = Intent(this,PinSetActivity::class.java)
                    intent.putExtra("pin",it)
                    intent.putExtra("pinRequestedFrom","splash")
                }?:kotlin.run {
                    intent = Intent(this,if(!isCurrencySelected) OnBoardActivity::class.java else HomeScreenActivity::class.java)
                }
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }, 300)

        }, 4000)


    }
}