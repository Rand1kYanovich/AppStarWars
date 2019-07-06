package com.example.startwarsapp

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.startwarsapp.model.entity.FullInfoCard
import com.example.startwarsapp.util.FragmentUtil

class AllCardsFragment: Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: RecyclerAdapter

    val clickListener: OnItemClickListener
    lateinit var colorArray: Array<String>

    var factory:MyPositionalDataSourceFactory = MyPositionalDataSourceFactory()
    var config: PagedList.Config? = null
    var cardsList: LiveData<PagedList<FullInfoCard>>? = null


    init {
        clickListener = object : OnItemClickListener {
            override fun onClick(view: View, position: Int) {
                val fullCardFragment:FullCardFragment = FullCardFragment()
                val bundle:Bundle = Bundle()
                bundle.putSerializable("fullInfo", cardsList!!.value!![position])
                fullCardFragment.arguments = bundle
                FragmentUtil.replaceWithBackStack(activity!!.supportFragmentManager,R.id.container,fullCardFragment)
            }
        }

        config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(5)
            .setPrefetchDistance(9)
            .build()
    }




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView:View = inflater.inflate(R.layout.fragment_all_cards,container,false)
        cardsList = LivePagedListBuilder<String,FullInfoCard>(factory,config!!).build()
        colorArray = resources.getStringArray(R.array.card_color)
        recyclerView = rootView.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity!!.applicationContext)
        recyclerView.setNestedScrollingEnabled(false)
        recyclerView.setHasFixedSize(true)

        cardsList!!.observe(this, Observer {
            it.let { adapter.submitList(it) }
        })

        adapter = RecyclerAdapter()
        adapter.setColorArray(colorArray)
        adapter.setClickListener(clickListener)
        recyclerView.setAdapter(adapter)

        return rootView
    }


}