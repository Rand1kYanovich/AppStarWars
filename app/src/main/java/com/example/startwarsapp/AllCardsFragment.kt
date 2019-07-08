package com.example.startwarsapp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import com.example.startwarsapp.model.api.NetworkService
import com.example.startwarsapp.model.database.AppDatabase
import com.example.startwarsapp.model.database.FavoriteDao
import com.example.startwarsapp.model.entity.FullInfoCard
import com.example.startwarsapp.model.entity.InfoPageAndResult
import com.example.startwarsapp.util.FragmentUtil
import com.example.startwarsapp.util.PaginationScrollListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllCardsFragment: Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: RecyclerAdapter
    lateinit var layoutManager:LinearLayoutManager

    lateinit var etSearch:EditText
    lateinit var btnSearch:ImageButton
    var isFilter = false

    var clickListener: OnItemClickListener
    var favoriteListener:OnFavoriteClickListener

    lateinit var colorArray: Array<String>
    var cardsList = ArrayList<FullInfoCard>()
    var filterList = ArrayList<FullInfoCard>()

    var page:Int = 1
    var isLoading: Boolean = false

    var db: AppDatabase
    var favoriteDao: FavoriteDao




    init {

        db = App.getInstance().database
        favoriteDao = db.favoriteDao()

        clickListener = object : OnItemClickListener {
            override fun onClick(view: View, position: Int, cardsList: ArrayList<FullInfoCard>) {
                FragmentUtil.replaceWithBackStack(
                    activity!!.supportFragmentManager,
                    R.id.container,
                    FullCardFragment.newInstance(getString(R.string.bundle_argument_name), cardsList.get(position))
                )
            }
        }


        favoriteListener = object :OnFavoriteClickListener{
            override fun onFavoriteClickListener(position:Int,favoriteList:ArrayList<FullInfoCard>,btnFavorite:ImageButton) {
                if(favoriteDao.getById(cardsList.get(position).name) != null) favoriteDao.delete(cardsList.get(position))
                    else if(favoriteDao.getById(cardsList.get(position).name) == null) favoriteDao.insert(cardsList.get(position))
            }
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView:View = inflater.inflate(R.layout.fragment_all_cards,container,false)
        etSearch = rootView.findViewById(R.id.etSearch)
        btnSearch = rootView.findViewById(R.id.btnSearch)
        addSearchListener()
        colorArray  = resources.getStringArray(R.array.card_color)

        recyclerView = rootView.findViewById(R.id.recyclerView)
        layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        addScrollListener()

        recyclerView.setNestedScrollingEnabled(false)
        recyclerView.setHasFixedSize(true)
        setAdapter()
        if(page ==1) loadData("","1")


        return rootView
    }


    fun addSearchListener(){
        btnSearch.setOnClickListener {
            if(!etSearch.text.toString().equals("")) {
                loadData(etSearch.text.toString(),"")
                isFilter = true
            }else{
                isFilter = false
                adapter.setList(cardsList)
                isLoading = false

            }
        }
    }


    fun loadData(filter: String,pageNumber:String) {
        NetworkService.getInstance()
            .getJSONApi()
            .getCards(pageNumber, filter)
            .enqueue(object : Callback<InfoPageAndResult> {
                override fun onFailure(call: Call<InfoPageAndResult>, t: Throwable) {}

                override fun onResponse(call: Call<InfoPageAndResult>, response: Response<InfoPageAndResult>) {
                    if (response.isSuccessful) {
                        val infoPageAndResult: InfoPageAndResult = response.body()!!
                        isLoading = false
                        if(!isFilter) {
                            addCard(ArrayList(infoPageAndResult.results))
                            adapter.setList(cardsList)
                            page++
                        }
                        else{
                            addFilterList(ArrayList(infoPageAndResult.results))
                            adapter.setList(filterList)
                        }
                    }
                }
            })
    }


    fun addScrollListener(){
        recyclerView.addOnScrollListener(object : PaginationScrollListener(layoutManager){

            override fun isLoading() = isLoading

            override fun loadMoreItems() {
                isLoading = true
                if(!isFilter) {
                    loadData("",page.toString())
                    isLoading = true
                }
                else isLoading = false
            }
        })
    }




    fun addCard(cardList:ArrayList<FullInfoCard>){ this.cardsList.addAll(cardList)}


    fun addFilterList(filterList:ArrayList<FullInfoCard>){
        this.filterList.removeAll(this.filterList)
        this.filterList.addAll(filterList)
    }


    fun setAdapter(){
        adapter = RecyclerAdapter(cardsList, activity!!.applicationContext)
        adapter.setColorArray(colorArray)
        adapter.setClickListener(clickListener)
        adapter.setFavoriteListener(favoriteListener)
        recyclerView.setAdapter(adapter)
    }


}