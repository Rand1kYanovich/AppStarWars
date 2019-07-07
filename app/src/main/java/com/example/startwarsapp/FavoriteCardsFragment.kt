package com.example.startwarsapp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.example.startwarsapp.model.entity.FullInfoCard
import com.example.startwarsapp.util.FragmentUtil

class FavoriteCardsFragment: Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter:RecyclerAdapter
    lateinit var layoutManager: LinearLayoutManager


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView:View = inflater.inflate(R.layout.fragment_favorite_cards,container,false)
        recyclerView = rootView.findViewById(R.id.recyclerView)
        layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager

        recyclerView.setNestedScrollingEnabled(false)
        recyclerView.setHasFixedSize(true)

        adapter = RecyclerAdapter(AllCardsFragment.favoriteList,context!!)
        adapter.setColorArray(resources.getStringArray(R.array.card_color))
        adapter.setClickListener(object: OnItemClickListener{
            override fun onClick(view: View, position: Int, cardsList: ArrayList<FullInfoCard>) {
                FragmentUtil.replaceWithBackStack(
                    activity!!.supportFragmentManager,
                    R.id.container,
                    FullCardFragment.newInstance(getString(R.string.bundle_argument_name), cardsList.get(position))
                )
            }
        })
        adapter.setFavoriteListener(object:OnFavoriteClickListener{
            override fun onFavoriteClickListener(
                position: Int,
                favoriteList: ArrayList<FullInfoCard>,
                btnFavorite: ImageButton
            ) {

            }
        })
        recyclerView.adapter = adapter



        return rootView
    }

    companion object{
        fun newInstance():FavoriteCardsFragment{
            val fragment = FavoriteCardsFragment()
            return fragment
        }
    }

}