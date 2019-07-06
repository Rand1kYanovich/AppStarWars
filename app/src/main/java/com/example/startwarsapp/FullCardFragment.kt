package com.example.startwarsapp

import android.graphics.Color
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.startwarsapp.model.entity.FullInfoCard

class FullCardFragment: Fragment() {

    var fullCardObject:FullInfoCard?=null
    lateinit var clCard:ConstraintLayout
    lateinit var tvName:TextView
    lateinit var tvHeight:TextView
    lateinit var tvMass:TextView
    lateinit var tvHairColor:TextView
    lateinit var tvSkinColor:TextView
    lateinit var tvEyeColor:TextView
    lateinit var tvBirthYear:TextView
    lateinit var tvGender:TextView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView:View = inflater.inflate(R.layout.fragment_full_card,container,false)

        fullCardObject = arguments!!.getSerializable("fullInfo") as FullInfoCard?

        clCard = rootView.findViewById(R.id.clCard)
        clCard.setBackgroundColor(Color.parseColor(fullCardObject!!.color))

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
}