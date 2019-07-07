package com.example.startwarsapp

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.startwarsapp.model.api.NetworkService
import com.example.startwarsapp.model.entity.FullInfoCard
import com.example.startwarsapp.model.entity.InfoPageAndResult
import com.example.startwarsapp.util.FragmentUtil
import com.example.startwarsapp.util.RequestsUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllCardsFragment: Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: RecyclerAdapter
    lateinit var etSearch:EditText

    val clickListener: OnItemClickListener
    lateinit var colorArray: Array<String>
    var layoutManager = LinearLayoutManager(context)
    var isLastPage: Boolean = false
    var isLoading: Boolean = false
    var page:Int = 1
    var filter:String = ""

    var cardsList: ArrayList<FullInfoCard>?=null



    init {
        clickListener = object : OnItemClickListener {
            override fun onClick(view: View, position: Int) {
                val fullCardFragment: FullCardFragment = FullCardFragment()
                val bundle: Bundle = Bundle()
                bundle.putSerializable(getString(R.string.bundle_argument_name), cardsList!!.get(position))
                fullCardFragment.arguments = bundle
                FragmentUtil.replaceWithBackStack(activity!!.supportFragmentManager, R.id.container, fullCardFragment)
            }
        }
    }




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView:View = inflater.inflate(R.layout.fragment_all_cards,container,false)


        etSearch = rootView.findViewById(R.id.etSearch)
        colorArray = resources.getStringArray(R.array.card_color)

        recyclerView = rootView.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager
        addScrollListener()

        recyclerView.setNestedScrollingEnabled(false)
        recyclerView.setHasFixedSize(true)
        loadFirstData()



        return rootView
    }





    fun addScrollListener(){

        recyclerView.addOnScrollListener(object :PaginationScrollListener(layoutManager){
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                loadData()
            }

        })
    }



    fun loadFirstData() {

        NetworkService.getInstance()
            .getJSONApi()
            .getCards(page.toString(), "")
            .enqueue(object : Callback<InfoPageAndResult> {
                override fun onFailure(call: Call<InfoPageAndResult>, t: Throwable) {
                }

                override fun onResponse(call: Call<InfoPageAndResult>, response: Response<InfoPageAndResult>) {
                    if (response.isSuccessful) {
                        val infoPageAndResult: InfoPageAndResult = response.body()!!
                        cardsList = ArrayList(infoPageAndResult.results)

                        adapter = RecyclerAdapter(cardsList!!)
                        adapter.setColorArray(colorArray)
                        adapter.setClickListener(clickListener)
                        recyclerView.setAdapter(adapter)
                    }
                }
            })
        page++


    }





    fun loadData() {
        NetworkService.getInstance()
            .getJSONApi()
            .getCards(page.toString(), "")
            .enqueue(object : Callback<InfoPageAndResult> {
                override fun onFailure(call: Call<InfoPageAndResult>, t: Throwable) {
                }

                override fun onResponse(call: Call<InfoPageAndResult>, response: Response<InfoPageAndResult>) {
                    if (response.isSuccessful) {
                        Log.e("Req", call.request().toString())
                        val infoPageAndResult: InfoPageAndResult = response.body()!!
                        cardsList = ArrayList(infoPageAndResult.results)
                        adapter.addData(cardsList!!)
                        isLoading = false
                    }
                }
            })
        page++
    }


    fun loadDataWithFilter(filter: String) {

        NetworkService.getInstance()
            .getJSONApi()
            .getCards("", filter)
            .enqueue(object : Callback<InfoPageAndResult> {
                override fun onFailure(call: Call<InfoPageAndResult>, t: Throwable) {
                }

                override fun onResponse(call: Call<InfoPageAndResult>, response: Response<InfoPageAndResult>) {
                    if (response.isSuccessful) {
                        Log.e("Req", call.request().toString())
                        val infoPageAndResult: InfoPageAndResult = response.body()!!
                        cardsList = ArrayList(infoPageAndResult.results)


                    }
                }
            })
        page = 1

    }




}