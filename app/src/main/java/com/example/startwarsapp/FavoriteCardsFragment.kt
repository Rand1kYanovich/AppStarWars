package com.example.startwarsapp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.example.startwarsapp.model.database.AppDatabase
import com.example.startwarsapp.model.database.FavoriteDao
import com.example.startwarsapp.model.entity.FullInfoCard
import com.example.startwarsapp.util.FragmentUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers


class FavoriteCardsFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: DataAdapter
    lateinit var layoutManager: LinearLayoutManager

    var favoriteList: ArrayList<FullInfoCard>
    var db: AppDatabase
    var favoriteDao: FavoriteDao

    init {
        db = App.getInstance().getDB()
        favoriteDao = db.favoriteDao()
        favoriteList = ArrayList()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_favorite_cards, container, false)

        favoriteDao.getAll()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { t ->
                adapter.setList(ArrayList(t))
                Log.e("Hi","f")
            }
        recyclerView = rootView.findViewById(R.id.recyclerView)
        layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager

        recyclerView.setNestedScrollingEnabled(false)
        recyclerView.setHasFixedSize(true)

        adapter = DataAdapter(favoriteList, context!!)
        adapter.setColorArray(resources.getStringArray(R.array.card_color))
        adapter.setClickListener(object : OnItemClickListener {
            override fun onClick(view: View, position: Int, cardsList: ArrayList<FullInfoCard>) {
                FragmentUtil.replaceWithBackStack(
                    activity!!.supportFragmentManager,
                    R.id.container,
                    FullCardFragment.newInstance(getString(R.string.bundle_argument_name), cardsList.get(position))
                )
            }
        })
        adapter.setFavoriteListener(object : OnFavoriteClickListener {
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

    companion object {
        fun newInstance(): FavoriteCardsFragment {
            val fragment = FavoriteCardsFragment()
            return fragment
        }
    }

}