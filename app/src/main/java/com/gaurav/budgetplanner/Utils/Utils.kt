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


        fun storePinSecurely(context: Context,pinToStore:String?){
            val editor = getSecuredSharedPref(context).edit()
            editor.putString("encrypted_pin", pinToStore)
            editor.apply()
        }


        fun retrievePinSecurely(context: Context): String? {
            return try{
                getSecuredSharedPref(context).getString("encrypted_pin", null)
            } catch (e:java.lang.Exception){
                null
            }
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

        fun showWelcomeMessageBasedOnTime(context: Context): String? {
            val c = Calendar.getInstance()
            val timeOfDay = c[Calendar.HOUR_OF_DAY]
            return if (timeOfDay in 0..11) {
                context.getString(R.string.good_morning)
            } else if (timeOfDay in 12..15) {
                context.getString(R.string.good_afternoon)
            } else if (timeOfDay in 16..23) {
                context.getString(R.string.good_evening)
            } else ""
        }

        fun generateUniqueRequestCode(reminderId: Int): Int {
            // Use a constant or a prime number to create unique request codes
            val primeMultiplier = 31
            return reminderId * primeMultiplier
        }


        fun checkIfPastDate(date:Long,hour:Int,minute:Int):Boolean{

            val dateString = getFormattedDateFromMillis(date,"MMMM dd, yyyy")

            val dateTimeFormat = DateTimeFormatter.ofPattern("MMMM dd, yyyy HH:mm")

            //converting in case of single digit

            val paddedHour = String.format("%02d", hour)
            val paddedMinute = String.format("%02d",minute)

            // Parse date and time strings into LocalDateTime object
            val givenDateTime = LocalDateTime.parse("$dateString $paddedHour:$paddedMinute", dateTimeFormat)

            // Adjust the parsed LocalDateTime object with the given hour and minute values
            val adjustedDateTime = givenDateTime.withHour(hour).withMinute(minute)

            // Get current date and time
            val currentDateTime = LocalDateTime.now()

            val comparisonResult = currentDateTime.compareTo(adjustedDateTime)

            if(comparisonResult > 0)
                return false

            return true
        }
    }
}