package com.gaurav.budgetplanner.Features.Converter.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.gaurav.budgetplanner.Features.Converter.Adapter.CountryListAdapter
import com.gaurav.budgetplanner.Features.Converter.Model.Country
import com.gaurav.budgetplanner.Views.Fragment.BaseFragment
import com.gaurav.budgetplanner.databinding.ActivityCountryListBinding
import com.gaurav.budgetplanner.databinding.FragmentCountryListBinding

class CountrySelectFragment: BaseFragment() {

    private var _binding: FragmentCountryListBinding?= null
    private val binding get() = _binding!!
    private var listener: CountryClickListener?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCountryListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpRecyclerView()
    }

    private fun setUpRecyclerView(){
        val adapter = CountryListAdapter()
        binding.rvCountry.layoutManager = LinearLayoutManager(context)
        binding.rvCountry.adapter = adapter
        adapter.onItemClick = {
            listener?.onCountryClick(it)
        }
    }

    interface CountryClickListener {
        fun onCountryClick(country:Country)
    }

    fun setListener(listener: CountryClickListener) {
        this.listener = listener
    }
}