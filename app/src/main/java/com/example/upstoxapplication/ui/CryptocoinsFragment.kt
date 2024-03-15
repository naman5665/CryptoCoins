package com.example.upstoxapplication.ui

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.compose.ui.text.toLowerCase
import androidx.core.view.MenuItemCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.upstoxapplication.R
import com.example.upstoxapplication.adapter.CryptoCoinsAdapter
import com.example.upstoxapplication.data.Cryptocurrencies
import com.example.upstoxapplication.databinding.FragmentCryptocoinsBinding
import com.example.upstoxapplication.model.CryptoCurrencyViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class CryptocoinsFragment : Fragment() {

    private var cryptoLayoutManager: RecyclerView.LayoutManager? = null
    private var cryptoAdapter: CryptoCoinsAdapter? = null
    lateinit var binding: FragmentCryptocoinsBinding
    lateinit var cryptoViewModel: CryptoCurrencyViewModel
    var listFromService: List<Cryptocurrencies> = listOf()
    var filteredListToShow: MutableList<Cryptocurrencies> = mutableListOf()
    lateinit var progressDialog: ProgressDialog



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Loading")
        progressDialog.setMessage("Please wait.....")
        progressDialog.setCancelable(false)
        cryptoViewModel = ViewModelProvider(this)[CryptoCurrencyViewModel::class.java]
        cryptoViewModel.fetchCryptocurrencies()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        binding.myToolbar.inflateMenu(R.menu.top_app_bar_menu)
        super.onCreateOptionsMenu(menu, inflater)

        val searchViewItem = menu.findItem(R.id.search_item)
        if(searchViewItem != null){
            val searchView = searchViewItem.actionView as SearchView
            searchView.queryHint = "Search Coins"
            searchView.isFocusable = true
            searchView.setQuery("",false)
            searchView.isIconified = true
            searchView.maxWidth = androidx.constraintlayout.widget.R.attr.maxWidth

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{

                override fun onQueryTextSubmit(query: String): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    filterListToSearch(newText)
                    return true
                }
            })
        }

//        val item_search: MenuItem = binding.myToolbar.menu.findItem(R.id.search_item)
//        val searchView = item_search.actionView as SearchView

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search_item -> {
                Toast.makeText(context,"Clicked",Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun filterListToSearch(newText: String) {
        val filterSearchList: MutableList<Cryptocurrencies> = mutableListOf()
        for(i in listFromService){
            if(i.name?.toLowerCase()?.contains(newText.toLowerCase()) == true){
                filterSearchList.add(i)
            }
        }
        cryptoAdapter?.setCryptocurrencies(filterSearchList)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_cryptocoins, container, false)
//        val toolbar: MaterialToolbar= view.findViewById(R.id.material_toolbar)
//        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCryptocoinsBinding.bind(view)
        val list = createListToShowData()
        binding.cryptoCoinsRv.layoutManager = LinearLayoutManager(context)
        cryptoAdapter = CryptoCoinsAdapter(context, arrayListOf())
        binding.cryptoCoinsRv.adapter = cryptoAdapter
        (activity as? AppCompatActivity)?.setSupportActionBar(binding.myToolbar)
//        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        cryptoViewModel.cryptocurrencies.observe(viewLifecycleOwner,Observer{ cryptocurrencies ->
            cryptocurrencies?.let {
                listFromService = it
                cryptoAdapter?.setCryptocurrencies(it)
            }
        })
        cryptoViewModel.errorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            listFromService = createListToShowData()
            cryptoAdapter?.setCryptocurrencies(listFromService)
        })
        cryptoViewModel.isLoading.observe(viewLifecycleOwner,Observer{isLoading ->
            if(isLoading){
                progressDialog.show()
            }else{
                progressDialog.hide()
            }

        })
//        showBottomSheet()
        setClickListnersForChips()
//        setSearchClickListner()

    }

    fun setSearchClickListner(){
        binding.myToolbar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId){
                R.id.search_item ->{
//                    Toast.makeText(context,"New" , Toast.LENGTH_SHORT).show()
                    true
                }
                else -> {false}
            }

        }
    }

    override fun onResume() {
        super.onResume()
    }


    private fun sendDataToAdapter(cryptocurrencies: List<Cryptocurrencies>){

    }

    private fun setClickListnersForChips(){
        if(binding!=null){
            binding.chipsGroupLayout.chipGroupFilter.setOnCheckedStateChangeListener { grp , checkIds ->
                if(checkIds.isEmpty()){
                    cryptoAdapter?.setCryptocurrencies(listFromService)
                }else{
                    filteredListToShow.clear()
                    checkIds.forEach{ selectedChip ->
                        val chip = grp.findViewById<Chip>(selectedChip)
                        sortListAndSendToAdapter(chip.text.toString())
                    }
                }

            }
        }
    }

    private fun sortListAndSendToAdapter(text: String){

        when(text){

            getString(R.string.chip_txt_active_coins) -> {
                for(i in listFromService){
                    if(i.isActive == true){
                        filteredListToShow.add(i)
                    }
                }
            }

            getString(R.string.chip_txt_inactive_coins) -> {
                for(i in listFromService){
                    if(i.isActive == false){
                        filteredListToShow.add(i)
                    }
                }
            }

            getString(R.string.chip_txt_only_tokens) -> {
                for(i in listFromService){
                    if(i.type == "token"){
                        filteredListToShow.add(i)
                    }
                }
            }

            getString(R.string.chip_txt_only_coins) -> {
                for(i in listFromService){
                    if(i.type == "coin"){
                        filteredListToShow.add(i)
                    }
                }
            }

            getString(R.string.chip_txt_new_coins) -> {
                for(i in listFromService){
                    if(i.isNew == true){
                        filteredListToShow.add(i)
                    }
                }
            }


        }

        cryptoAdapter?.setCryptocurrencies(filteredListToShow)
    }

    private fun showBottomSheet(){
        val bottomSheet = BottomSheetDialog(requireContext())
        bottomSheet.setContentView(R.layout.bottom_sheet_layout)
        val chipGroup = bottomSheet.findViewById<ChipGroup>(R.id.chipGroup)

        val filterChips = arrayOf("Chip 1", "Chip 2", "Chip 3" , "Chip 4" , "Chip 5")
        for (chipText in filterChips) {
            val chip = Chip(requireContext())
            chip.text = chipText
            chipGroup?.addView(chip)
        }

        bottomSheet.behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheet.setCancelable(false)

        bottomSheet.show()
    }

    private fun createListToShowData(): List<Cryptocurrencies>{
        return listOf(
            Cryptocurrencies("Bitcoin", "BTC", false, false, "meType"),
            Cryptocurrencies("Citcoin", "CTC", false, true, "meType"),
            Cryptocurrencies("Naman", "NAM", false, false, "meType"),
            Cryptocurrencies("UpStox", "UPX", false, true, "meType"),
            Cryptocurrencies("Etherium", "ETH", false, false, "meType"),
            Cryptocurrencies("NOWay", "NW", false, true, "meType"),
            Cryptocurrencies("MyWay", "MW", false, false, "meType"),
            Cryptocurrencies("YourWay", "YW", false, true, "meType"),
            Cryptocurrencies("OwnWay", "OW", false, false, "meType"),
            Cryptocurrencies("OwnWay", "OW", false, true, "meType"),
            Cryptocurrencies("OwnWay", "OW", false, false, "meType"),
            Cryptocurrencies("OwnWay", "OW", false, false, "meType"),
            Cryptocurrencies("OwnWay", "OW", false, false, "meType"),
            Cryptocurrencies("OwnWay", "OW", false, false, "meType"),
            Cryptocurrencies("OwnWay", "OW", false, true, "meType"),
            Cryptocurrencies("OwnWay", "OW", false, true, "meType"),
            Cryptocurrencies("OwnWay", "OW", false, true, "meType"),
            Cryptocurrencies("OwnWay", "OW", false, true, "meType"),
            Cryptocurrencies("OwnWay", "OW", false, true, "meType")
        )
    }
}