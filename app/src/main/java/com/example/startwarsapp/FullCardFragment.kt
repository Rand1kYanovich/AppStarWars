package com.example.startwarsapp

import android.graphics.Color
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.example.startwarsapp.model.database.AppDatabase
import com.example.startwarsapp.model.database.FavoriteDao
import com.example.startwarsapp.model.entity.FullInfoCard
import java.io.Serializable

class FullCardFragment: Fragment() {

    var fullCardObject:FullInfoCard?=null
    lateinit var clCard:ConstraintLayout
    lateinit var btnFavorite:ImageButton
    lateinit var tvName:TextView
    lateinit var tvHeight:TextView
    lateinit var tvMass:TextView
    lateinit var tvHairColor:TextView
    lateinit var tvSkinColor:TextView
    lateinit var tvEyeColor:TextView
    lateinit var tvBirthYear:TextView
    lateinit var tvGender:TextView

    var db:AppDatabase
    var favoriteDao:FavoriteDao

    init {
        db = App.getInstance().getDB()
        favoriteDao = db.favoriteDao()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView:View = inflater.inflate(R.layout.fragment_full_card,container,false)

        fullCardObject = arguments!!.getSerializable(getString(R.string.bundle_argument_name)) as FullInfoCard?

        clCard = rootView.findViewById(R.id.clCard)
        clCard.setBackgroundColor(Color.parseColor(fullCardObject!!.color))

        btnFavorite = rootView.findViewById(R.id.btnFavorite)
        btnFavorite.setBackgroundColor(Color.parseColor(fullCardObject!!.color))

        btnFavorite.setOnClickListener {
            if(favoriteDao.getById(fullCardObject!!.name) != null) {
                favoriteDao.delete(fullCardObject!!)
                btnFavorite.setImageResource(R.drawable.ic_favorite_false)
            }
            else if(favoriteDao.getById(fullCardObject!!.name) == null){
                favoriteDao.insert(fullCardObject!!)
                btnFavorite.setImageResource(R.drawable.ic_favorite_true)
            }
        }

        tvName = rootView.findViewById(R.id.tvName)
        tvName.setText("Имя: "+fullCardObject!!.name)

        tvHeight = rootView.findViewById(R.id.tvHeight)
        tvHeight.setText("Рост: "+fullCardObject!!.height)

        tvMass = rootView.findViewById(R.id.tvMass)
        tvMass.setText("Вес: "+fullCardObject!!.mass)

        tvHairColor = rootView.findViewById(R.id.tvHairColor)
        tvHairColor.setText("Цвет волос: "+fullCardObject!!.hair_color)

        tvSkinColor = rootView.findViewById(R.id.tvSkinColor)
        tvSkinColor.setText("Цвет кожи: "+fullCardObject!!.skin_color)

        tvEyeColor = rootView.findViewById(R.id.tvEyeColor)
        tvEyeColor.setText("Цвет глаз: "+fullCardObject!!.eye_color)

        tvBirthYear = rootView.findViewById(R.id.tvBirthYear)
        tvBirthYear.setText("День рождения: "+fullCardObject!!.birth_year)

        tvGender = rootView.findViewById(R.id.tvGender)
        tvGender.setText("Пол: "+fullCardObject!!.gender)

        return rootView
    }

    companion object{

        fun newInstance(key:String,card:FullInfoCard):FullCardFragment{
            val fullCardFragment = FullCardFragment()
            val bundle = Bundle()
            bundle.putSerializable(key, card)
            fullCardFragment.arguments = bundle
            return fullCardFragment
        }
    }
}