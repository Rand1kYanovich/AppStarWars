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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllCardsFragment: Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: RecyclerAdapter
    lateinit var etSearch:EditText

    val clickListener: OnItemClickListener
    lateinit var colorArray: Array<String>


    var factory:MyPositionalDataSourceFactory = MyPositionalDataSourceFactory()
    var config: PagedList.Config? = null
    var cardsList: LiveData<PagedList<FullInfoCard>>? = null



    init {
        clickListener = object : OnItemClickListener {
            override fun onClick(view: View, position: Int) {
                val fullCardFragment: FullCardFragment = FullCardFragment()
                val bundle: Bundle = Bundle()
                bundle.putSerializable(getString(R.string.bundle_argument_name), cardsList!!.value!![position])
                fullCardFragment.arguments = bundle
                FragmentUtil.replaceWithBackStack(activity!!.supportFragmentManager, R.id.container, fullCardFragment)
            }
        }


        config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(5)
            .setPrefetchDistance(9)
            .setInitialLoadSizeHint(5)
            .build()


    }




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView:View = inflater.inflate(R.layout.fragment_all_cards,container,false)


        etSearch = rootView.findViewById(R.id.etSearch)
        setListenerEditText()
        colorArray = resources.getStringArray(R.array.card_color)
        cardsList = LivePagedListBuilder<String,FullInfoCard>(factory,config!!).build()
        cardsList!!.observe(this, Observer {
            it.let { adapter.submitList(it) }
        })
        recyclerView = rootView.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity!!.applicationContext)
        recyclerView.setNestedScrollingEnabled(false)
        recyclerView.setHasFixedSize(true)
        adapter = RecyclerAdapter()
        adapter.setColorArray(colorArray)
        adapter.setClickListener(clickListener)
        recyclerView.setAdapter(adapter)

        return rootView
    }

    fun setListenerEditText(){
        etSearch.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                MyPositionalDataSource.setFilter(s.toString())
                Log.e("Init",s.toString())
                factory.dataSource.filter = s.toString()
                factory.dataSource.invalidate()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })
    }


}