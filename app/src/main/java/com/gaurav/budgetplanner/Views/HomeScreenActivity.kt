package com.gaurav.budgetplanner.Views

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gaurav.budgetplanner.R
import com.gaurav.budgetplanner.Utils.Utils
import com.gaurav.budgetplanner.Views.Activity.BaseActivity
import com.gaurav.budgetplanner.databinding.ActivityHomeScreenBinding
import com.gaurav.budgetplanner.features.converter.presentation.activities.CurrencyConvertActivity
import com.gaurav.budgetplanner.features.expensetracker.domain.model.Account
import com.gaurav.budgetplanner.features.expensetracker.presentation.Activity.CategoryActivity
import com.gaurav.budgetplanner.features.expensetracker.presentation.Activity.TransactionActivity
import com.gaurav.budgetplanner.features.expensetracker.presentation.Adapters.RecordAdapter
import com.gaurav.budgetplanner.features.expensetracker.presentation.ViewModel.RecordViewModel
import com.gaurav.budgetplanner.features.reminder.presentation.activites.ReminderLanding
import com.gaurav.budgetplanner.features.settings.activities.SettingsActivity
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeScreenActivity : BaseActivity(),NavigationView.OnNavigationItemSelectedListener {
    private var _binding: ActivityHomeScreenBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: RecordViewModel
    private lateinit var adapter: RecordAdapter
    private var currentList: List<Account> = emptyList()
    private var trxState = "E"
    private var selectedCurrency: String? = null
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[RecordViewModel::class.java]
        init()
        setUpRecyclerView()
        clickEvents()
    }

    private fun init() {
        selectedCurrency = Utils.getSelectedCurrency()
        binding.currencySymbol.text = selectedCurrency


        // Set up the ActionBarDrawerToggle
        toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        setSupportActionBar(binding.homeToolBar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)


        setNavMenuData(R.id.nav_home, getString(R.string.home), R.drawable.icon_home)
        setNavMenuData(R.id.nav_reminders, getString(R.string.reminders), R.drawable.icon_remindeer)
        setNavMenuData(R.id.nav_currency, getString(R.string.converter), R.drawable.icon_currency)
        setNavMenuData(R.id.nav_settings, getString(R.string.settings), R.drawable.img_settings)
        setNavMenuData(
            R.id.nav_share_friends,
            getString(R.string.share_with_friends),
            R.drawable.icon_share
        )
        setNavMenuData(
            R.id.nav_rate_the_app,
            getString(R.string.rate_the_app),
            R.drawable.img_rating
        )
        binding.navigationView.setNavigationItemSelectedListener(this)

    }

    private fun setNavMenuData(navItem: Int, value: String, icon: Int) {
        val menuItem = binding.navigationView.menu.findItem(navItem)
        val title1 = menuItem.actionView?.findViewById<TextView>(R.id.menu_item_title)
        var image1 = menuItem?.actionView?.findViewById<ImageView>(R.id.menu_item_icon)
        image1?.setImageResource(icon)
        title1?.text = value

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUpRecyclerView() {
        adapter = RecordAdapter(selectedCurrency!!)
        binding.rvRecords.layoutManager = LinearLayoutManager(this)
        binding.rvRecords.adapter = adapter

        adapter.onItemClick = {
            val intent = Intent(this, CategoryActivity::class.java)
            intent.putExtra("category", it.category)
            intent.putExtra("currency", selectedCurrency)
            startActivity(intent)
        }

        viewModel.getAllRecords().observe(this) { list ->
            list?.let {
                val data = splitAndMergedData(it)
                Utils.getTotalBudget(it)
                adapter.updateList(data)
                currentList = it
                binding.totalAmount.text = Utils.getTotalBudget(it)
            }
        }
    }

    private fun clickEvents() {
        binding.addIcon.setOnClickListener {
            startActivity(Intent(this, TransactionActivity::class.java))
        }

        binding.tabLayoutIncomeExpenses.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        trxState = "E"
                    }
                    1 -> {
                        trxState = "I"
                    }

                }
                adapter.updateList(splitAndMergedData(currentList))
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

    }

    private fun splitAndMergedData(data: List<Account>): List<Account> {
        val newData: List<Account>
        if (trxState == "E") {
            newData = data.filter { transaction -> transaction.transactionType == "E" }
            checkIfDataEmpty(newData)
        } else {
            newData = data.filter { transaction -> transaction.transactionType == "I" }
            checkIfDataEmpty(newData)
        }
        val mergedList = newData.groupBy {
            it.category
        }.map { (category, accounts) ->
            val totalAmount = accounts.sumOf { it.amount.toInt() }
            Account(totalAmount.toString(), category, "", "", "", 0)
        }
        return mergedList
    }

    private fun checkIfDataEmpty(data: List<Account>) {
        if (data.isEmpty()) {
            binding.noDataTv.visibility = View.VISIBLE
            if (trxState == "E") {
                binding.noDataTv.text = getString(R.string.no_expenses)
            } else {
                binding.noDataTv.text = getString(R.string.no_income)
            }
        } else {
            binding.noDataTv.visibility = View.GONE
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_rate_the_app -> {
                openPlayStoreForRating()
                return false
            }
            R.id.nav_home -> {

            }

            R.id.nav_reminders -> {
//                showCustomDialog()
                startActivity(Intent(this, ReminderLanding::class.java))
            }
            R.id.nav_settings -> {
                startActivity(Intent(this,SettingsActivity::class.java))

            }
            R.id.nav_share_friends -> {
                shareApp()
                return false
            }
            R.id.nav_currency -> {
                startActivity(Intent(this, CurrencyConvertActivity::class.java))
            }
        }
        binding.drawerLayout.closeDrawers()
        return true
    }

    private fun shareApp() {
        val playStoreLink = "https://play.google.com/store/apps/details?id=com.gaurav.budgetplanner" // Replace with your actual package name

        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Check out this awesome app!")
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Hey, I have been using budget planner app that I think you'll love!\n\n$playStoreLink")

        startActivity(Intent.createChooser(shareIntent, "Share via"))
    }


    private fun openPlayStoreForRating() {
        val packageName = "com.gaurav.budgetplanner" // Replace with your actual package name
        val playStoreUri = Uri.parse("market://details?id=$packageName")

        val intent = Intent(Intent.ACTION_VIEW, playStoreUri)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            // Handle the case where the Play Store app is not installed
            // You can also consider opening the Play Store link in a browser as an alternative
            val webPlayStoreUri = Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
            val webIntent = Intent(Intent.ACTION_VIEW, webPlayStoreUri)
            startActivity(webIntent)
        }
    }

    private fun showCustomDialog(){
        val alertDialogBuilder= AlertDialog.Builder (this)

        alertDialogBuilder.setTitle("Feature Coming Soon")
        alertDialogBuilder.setMessage("This feature will be available in a future update.")
        alertDialogBuilder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    override fun onResume() {
        super.onResume()
        binding.navigationView.setCheckedItem(R.id.nav_home)
    }

    override fun onBackPressed() {
        finishAffinity()
    }


}