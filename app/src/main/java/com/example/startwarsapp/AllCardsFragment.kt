package com.example.startwarsapp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import com.example.startwarsapp.model.api.NetworkService
import com.example.startwarsapp.model.entity.FullInfoCard
import com.example.startwarsapp.model.entity.InfoPageAndResult
import com.example.startwarsapp.util.CardUtil
import com.example.startwarsapp.util.FragmentUtil
import com.example.startwarsapp.util.PaginationScrollListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllCardsFragment: Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: RecyclerAdapter
    lateinit var etSearch:EditText

    var clickListener: OnItemClickListener
    //var favoriteListener:OnFavoriteClick
    lateinit var colorArray: Array<String>
    lateinit var layoutManager:LinearLayoutManager
    lateinit var btnSearch:ImageButton


    var isLastPage: Boolean = false
    var isLoading: Boolean = false
    var filter:String = ""




init {
    clickListener = object : OnItemClickListener {
        override fun onClick(view: View, position: Int, cardsList: ArrayList<FullInfoCard>) {
            val fullCardFragment = FullCardFragment()
            val bundle = Bundle()
            bundle.putSerializable(getString(R.string.bundle_argument_name), cardsList.get(position))
            fullCardFragment.arguments = bundle
            FragmentUtil.replaceWithBackStack(activity!!.supportFragmentManager, R.id.container, fullCardFragment)
        }
    }
//
//    favoriteListener = object :OnFavoriteClick{
//        override fun onFavoriteClick(btnFavorite:ImageButton, isFavorite: Boolean,position:Int) {
//            if(isFavorite) {
//                btnFavorite.setImageResource(R.drawable.ic_favorite_true)
//                favoriteList = CardUtil.addFavoriteCard()
//            } else if(!isFavorite){
//
//            }
//        }
//
//    }
//    favoriteList = ArrayList()
}







    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView:View = inflater.inflate(R.layout.fragment_all_cards,container,false)
            etSearch = rootView.findViewById(R.id.etSearch)
            btnSearch = rootView.findViewById(R.id.btnSearch)
            addSearchListener()
            colorArray = resources.getStringArray(R.array.card_color)

            recyclerView = rootView.findViewById(R.id.recyclerView)
            layoutManager = LinearLayoutManager(context)
            recyclerView.layoutManager = layoutManager
            addScrollListener()

            recyclerView.setNestedScrollingEnabled(false)
            recyclerView.setHasFixedSize(true)
            loadFirstData()


        return rootView
    }




    fun addSearchListener(){
        btnSearch.setOnClickListener {
            if(!etSearch.text.toString().equals("")) {
                loadData(etSearch.text.toString(),"")
                filter = etSearch.text.toString()
            }else{
                filter = ""
                adapter.setList(CardUtil.cardsList)
                isLastPage = false
                isLoading = false

            }

        }
    }


    fun loadFirstData() {
        if(CardUtil.page == 1) {
            NetworkService.getInstance()
                .getJSONApi()
                .getCards(CardUtil.page.toString(), "")
                .enqueue(object : Callback<InfoPageAndResult> {
                    override fun onFailure(call: Call<InfoPageAndResult>, t: Throwable) {
                    }

                    override fun onResponse(call: Call<InfoPageAndResult>, response: Response<InfoPageAndResult>) {
                        if (response.isSuccessful) {
                            val infoPageAndResult: InfoPageAndResult = response.body()!!
                            CardUtil.addCard(ArrayList(infoPageAndResult.results))
                            adapter = RecyclerAdapter(CardUtil.cardsList, activity!!.applicationContext)
                            adapter.setColorArray(colorArray)
                            adapter.setClickListener(clickListener)
                            recyclerView.setAdapter(adapter)
                            CardUtil.page++


                        }
                    }
                })
        }else{
            adapter = RecyclerAdapter(CardUtil.cardsList, activity!!.applicationContext)
            adapter.setColorArray(colorArray)
            adapter.setClickListener(clickListener)
            recyclerView.setAdapter(adapter)

        }






    }



    fun loadData(filter: String,pageNumber:String) {

        NetworkService.getInstance()
            .getJSONApi()
            .getCards(pageNumber, filter)
            .enqueue(object : Callback<InfoPageAndResult> {
                override fun onFailure(call: Call<InfoPageAndResult>, t: Throwable) {
                }

                override fun onResponse(call: Call<InfoPageAndResult>, response: Response<InfoPageAndResult>) {
                    if (response.isSuccessful) {
                        val infoPageAndResult: InfoPageAndResult = response.body()!!

                        isLoading = false
                        if(filter.equals("")) {
                            CardUtil.addCard(ArrayList(infoPageAndResult.results))
                            adapter.setList(CardUtil.cardsList)
                            CardUtil.page++
                        }
                        else{
                            CardUtil.addFilterList(ArrayList(infoPageAndResult.results))
                            adapter.setList(CardUtil.filterList)
                        }


                    }
                }
            })



    }



    fun addScrollListener(){

        recyclerView.addOnScrollListener(object : PaginationScrollListener(layoutManager){
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                if(filter == "") {
                    loadData("",CardUtil.page.toString())
                    isLoading = true
                }
                else isLoading = false

            }

        })
    }





}