package com.gaurav.budgetplanner.Utils

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.graphics.Typeface
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.gaurav.budgetplanner.BudgetPlannerApp
import com.gaurav.budgetplanner.R
import com.gaurav.budgetplanner.Utils.CustomTextFormat.CustomTextFormat.FontCache
import com.gaurav.budgetplanner.features.expensetracker.domain.model.Account
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.ceil


class Utils {

    companion object {
        fun getTotalBudget(data:List<Account>):String {
            var expenseSum = 0
            var incomeSum = 0
            var total = 0

            expenseSum += data.filter { transaction ->  transaction.transactionType=="E" }.sumOf {
                it.amount.toInt()
            }
            incomeSum += data.filter { transaction ->  transaction.transactionType=="I" }.sumOf {
                it.amount.toInt()
            }
            total = incomeSum - expenseSum

            return "$total"
        }

        fun calculatePercentage(numbers: List<Int>,position:Int): Int {
            val sum = numbers.sum().toDouble()
            val percentages = numbers.map { (it.toDouble() / sum * 100) }

            val roundedPercentages = percentages.map { ceil(it).toInt() }.toMutableList()
            val sumRounded = roundedPercentages.sum()

            if (sumRounded != 100) {
                var diff = sumRounded - 100
                val sortedDiffIndices = roundedPercentages
                    .mapIndexed { index, value -> index to value }
                    .sortedByDescending { (_, value) -> value - value }
                    .map { it.first }

                var i = 0
                while (diff != 0) {
                    val index = sortedDiffIndices[i]
                    roundedPercentages[index]--
                    i = (i + 1) % sortedDiffIndices.size
                    diff--
                }
            }

            return roundedPercentages[position]
        }

        fun getFormattedDateFromMillis(millis: Long, dateFormat: String?): String? {
            var dateFormat = dateFormat
            return try {
                if (dateFormat == null || dateFormat.isEmpty()) dateFormat = "yyyy-MM-dd"
                val inputFormat: SimpleDateFormat = SimpleDateFormat(
                    dateFormat,
                    getEnglishLocale()
                )
                val date = Date()
                date.time = millis
                inputFormat.format(millis)
            } catch (e: Exception) {
                null
            }
        }

        fun getEnglishLocale(): Locale {
            return Locale.US
        }

        fun getSelectedCurrency(isSymbol:Boolean=true):String{
            val data: String? = BudgetPlannerApp.getStorage().getString(if(isSymbol)Constants.PREF_CURRENCY_SYMBOL else Constants.PREF_CURRENCY_VALUE , "")
            return data!!
        }

        fun getCurrentDateTime(): String {
            val now: LocalDateTime = LocalDateTime.now()
            val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")
            return now.format(formatter)
        }

        fun hideSoftKeyboard(activity: AppCompatActivity,view:View) {
                val inputMethodManager =
                    activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }

        fun showKeyboard(view: View) {
            try {
                val imm =
                    view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
            } catch (e: NullPointerException) {
                Log.d("EXCEPTION", "Null pointer while showing keyboard")
            }
        }

        fun dpToPx1(dp: Float): Float {
            return dp * Resources.getSystem().displayMetrics.density
        }

        fun vibrate(context: Context, duration: Long) {
            val v = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            // Vibrate for 500 milliseconds
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(
                    VibrationEffect.createOneShot(
                        duration,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            } else {
                //deprecated in API 26
                v.vibrate(duration)
            }
        }

        fun showMessageInSnackBar(s: String?, view: View) {
            try {
                val snackBar = Snackbar.make(view, s!!, Snackbar.LENGTH_LONG)
                snackBar.duration = 4000
                val tv = snackBar.view.findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView
                val font = Typeface.createFromAsset(view.context.assets, FontCache.ROBOTO_MEDIUM)
                tv.typeface = font
                tv.textSize = 14f
                snackBar.show()
            } catch (e: java.lang.NullPointerException) {
                showToastWithBlackBackground(
                    view.context,
                    s
                )
            }
        }

        fun showToast(context: Context, Message: String?) {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view: View = inflater.inflate(R.layout.custom_toast_layout, null, false)
            val tvToastMsg = view.findViewById<TextView>(R.id.tv_toast_message)
            tvToastMsg.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.black_semi_transparent
                )
            )
            tvToastMsg.setTextColor(ContextCompat.getColor(context, android.R.color.white))
            tvToastMsg.text = Message
            val customToast = Toast(context)
            customToast.setView(view)
            customToast.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM, 0, 0)
            customToast.duration = Toast.LENGTH_LONG
            customToast.show()
        }


        fun showToastWithBlackBackground(context: Context, Message: String?) {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view: View = inflater.inflate(R.layout.custom_toast_layout_black, null, false)
            val tvToastMsg = view.findViewById<TextView>(R.id.tv_toast_message)
            tvToastMsg.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                   R.color.black_semi_transparent
                )
            )
            tvToastMsg.setTextColor(ContextCompat.getColor(context, android.R.color.white))
            tvToastMsg.text = Message
            val customToast = Toast(context)
            customToast.setView(view)
            customToast.setGravity(
                Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM or Gravity.FILL_HORIZONTAL,
                0,
                0
            )
            customToast.duration = Toast.LENGTH_LONG
            customToast.show()
        }

        fun storePinSecurely(context: Context,pinToStore:String){
            val editor = getSecuredSharedPref(context).edit()
            editor.putString("encrypted_pin", pinToStore)
            editor.apply()
        }


        fun retrievePinSecurely(context: Context): String? {

            return getSecuredSharedPref(context).getString("encrypted_pin", null)
        }

        private fun getSecuredSharedPref(context: Context): SharedPreferences {

            val masterKey = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()

            return EncryptedSharedPreferences.create(
                context,
                "secure_prefs_file",
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        }
    }

}