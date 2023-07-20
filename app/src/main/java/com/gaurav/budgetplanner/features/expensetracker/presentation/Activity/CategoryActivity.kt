package com.gaurav.budgetplanner.features.expensetracker.presentation.Activity

import android.app.PendingIntent.getActivity
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfDocument.PageInfo
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gaurav.budgetplanner.R
import com.gaurav.budgetplanner.Utils.Utils
import com.gaurav.budgetplanner.databinding.ActivityCategoryBinding
import com.gaurav.budgetplanner.databinding.ItemIndividualRecordBinding
import com.gaurav.budgetplanner.features.expensetracker.domain.model.Account
import com.gaurav.budgetplanner.features.expensetracker.presentation.Adapters.IndividualRecordAdapter
import com.gaurav.budgetplanner.features.expensetracker.presentation.ViewModel.RecordViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


@AndroidEntryPoint
class CategoryActivity : AppCompatActivity() {
    private var _binding: ActivityCategoryBinding?=null
    private val binding get() = _binding!!
    private lateinit var adapter: IndividualRecordAdapter
    private lateinit var viewModel:RecordViewModel
    private lateinit var category: String
    private lateinit var currency: String
    private lateinit var bundle:Bundle
    private var searchEnabled:Boolean = false
    private var customDataForPdf:List<Account> ?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.constraintToolbar.toolbar)
        binding.constraintToolbar.clGenericBar.visibility= View.GONE
        binding.constraintToolbar.clCategoryBar.visibility=View.VISIBLE
        init()
        setUpRecyclerView()
        setSearchFieldListener()
        viewModel = ViewModelProvider(this)[RecordViewModel::class.java]

        viewModel.getAllRecords().observe(this) { list ->
            list?.let { it ->
                val data = getRespectiveData(it)
                adapter.updateList(data)
                customDataForPdf = data
                val sum = data.sumOf {
                    it.amount.toInt()
                }
                binding.constraintToolbar.totalAmount.text = sum.toString()
                binding.constraintToolbar.tvCurrencySymbol.text = currency
                if(data.isEmpty()) finish()
            }

        }

        binding.constraintToolbar.searchButton.setOnClickListener {
                binding.constraintToolbar.apply {
                    clCategoryBar.visibility=View.GONE
                    inputSearchField.visibility=View.VISIBLE
                    inputSearchField.requestFocus()
                    Utils.showKeyboard(inputSearchField)
                searchEnabled=true
            }
        }

        binding.constraintToolbar.downloadButton.setOnClickListener {
            showDownloadDialog()
        }


    }

    private fun showDownloadDialog(){
        val builder = MaterialAlertDialogBuilder(this, R.style.CustomAlertDialogStyle)
        builder.setTitle("Export data as:")
        builder.setSingleChoiceItems(arrayOf("PDF"), 0, null)
        builder.setPositiveButton("EXPORT") { dialog: DialogInterface, _: Int ->
            dialog.dismiss()
            generateAndSharePDF()
        }
        builder.setNegativeButton("CANCEL") { dialog: DialogInterface, _: Int ->
            Toast.makeText(this, "Export canceled", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        builder.show()
    }


    private fun calculateFullPageHeight(): Int {
        var height = 0
        for (i in 0 until adapter.itemCount) {
            val itemView = LayoutInflater.from(this).inflate(R.layout.item_individual_record, null, false)
            adapter.onBindViewHolder(IndividualRecordAdapter.ViewHolder(ItemIndividualRecordBinding.bind(itemView)), i)
            itemView.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            )
            height += itemView.measuredHeight
        }
        return height
    }

    private fun generateAndSharePDF() {
        // Step 1: Create a PDF document
        val pdfDocument = PdfDocument()

        //  Display display = wm.getDefaultDisplay();
        val displaymetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displaymetrics)
        val hight = displaymetrics.heightPixels.toFloat()
        val width = displaymetrics.widthPixels.toFloat()

        val convertHeight = hight.toInt()
        val convertWidth = width.toInt()

        // Step 2: Create a page and get its canvas
        val pageInfo = PageInfo.Builder(convertWidth, convertHeight, 1).create() // A4 size
        val page = pdfDocument.startPage(pageInfo)
        val canvas: Canvas = page.canvas

        val backgroundColor = Color.parseColor("#323232") // Replace with your desired background color
        val paint = Paint()
        paint.color = backgroundColor
        canvas.drawRect(0f, 0f, pageInfo.pageWidth.toFloat(), pageInfo.pageHeight.toFloat(), paint)


        // Step 3: Create a LinearLayout to hold the custom views (items from the adapter)
        val linearLayout = LinearLayout(this)
        linearLayout.orientation = LinearLayout.VERTICAL

        // Step 4: Inflate the custom views for each item from the adapter and add them to the LinearLayout
        val itemCount = adapter.itemCount
        for (i in 0 until itemCount) {
            val itemView = LayoutInflater.from(this).inflate(R.layout.item_individual_record, linearLayout, false)
            adapter.onBindViewHolder(IndividualRecordAdapter.ViewHolder(ItemIndividualRecordBinding.bind(itemView)), i)
            linearLayout.addView(itemView)
        }

        // Step 5: Draw the LinearLayout on the canvas
        linearLayout.measure(
            View.MeasureSpec.makeMeasureSpec(
                page.canvas.width,
                View.MeasureSpec.EXACTLY
            ),
            View.MeasureSpec.makeMeasureSpec(
                page.canvas.height,
                View.MeasureSpec.EXACTLY
            )
        )
        linearLayout.layout(
            0,
            0,
            page.canvas.width,
            page.canvas.height
        )
        linearLayout.draw(canvas)

        // Step 6: Finish the page
        pdfDocument.finishPage(page)

        // Step 7: Save the PDF to a file
        val pdfFile = File(getExternalFilesDir(null), "testfile.pdf")
        try {
            val fileOutputStream = FileOutputStream(pdfFile)
            pdfDocument.writeTo(fileOutputStream)
            pdfDocument.close()

            // Show a toast indicating successful export
            Toast.makeText(this, "PDF exported successfully", Toast.LENGTH_SHORT).show()

            val pdfUri: Uri = FileProvider.getUriForFile(
                this,
                "$packageName.fileprovider", pdfFile
            )

            // Step 9: Prompt the user to share the PDF using the content URI
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_STREAM, pdfUri)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // Grant read permission to other apps
            intent.setDataAndType(pdfUri, "application/pdf")
            startActivity(Intent.createChooser(intent, "Share PDF"))
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "Failed to export PDF", Toast.LENGTH_SHORT).show()
        }
    }

    private fun init(){
        category = intent?.extras?.getString("category")!!
        currency = intent?.extras?.getString("currency")!!

        binding.constraintToolbar.apply {
            tvCategory.text = category
        }
    }


    private fun setSearchFieldListener() {
        binding.constraintToolbar.inputSearchField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().isEmpty()) {
                    // buttonClose.setVisibility(View.GONE);
                    searchItem("")
                } else {
                    searchItem(s)
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }


    private fun searchItem(query: CharSequence) {
        adapter.let {
            Handler().postDelayed({
               adapter.getFilter().filter(query)
                                              }, 80)
        }
    }


    private fun getRespectiveData(data:List<Account>):List<Account>{
        return data.filter { transaction ->  transaction.category==category }
    }

    private fun setUpRecyclerView(){
        adapter = IndividualRecordAdapter(category,currency)
        binding.rvIndividualData.layoutManager = LinearLayoutManager(this)
        binding.rvIndividualData.adapter = adapter
        adapter.onItemClick = {
            bundle = Bundle()
            bundle.putSerializable("account",it)
            val intent = Intent(this,TransactionDetailActivity::class.java)
            intent.putExtras(bundle)
            intent.putExtra("currency",currency)
            startActivity(intent)
        }

        adapter.onEmptyData = {
            if(it){
                binding.noDataTv.visibility=View.VISIBLE
                binding.rvIndividualData.visibility=View.GONE
            }
            else{
                binding.noDataTv.visibility=View.GONE
                binding.rvIndividualData.visibility=View.VISIBLE
            }

        }
    }


    override fun onSupportNavigateUp(): Boolean {
        if(searchEnabled){
            binding.constraintToolbar.apply {
                inputSearchField.setText("")
                inputSearchField.visibility=View.GONE
                clCategoryBar.visibility=View.VISIBLE
            }
            searchEnabled=false
            Utils.hideSoftKeyboard(this,binding.constraintToolbar.inputSearchField)
            return false
        }
        onBackPressed()
        return true;
    }
}