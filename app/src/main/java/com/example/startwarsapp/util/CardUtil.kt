package com.example.startwarsapp.util

import com.example.startwarsapp.model.entity.FullInfoCard

class CardUtil {

    companion object{
        var cardsList = ArrayList<FullInfoCard>()
        var favoriteList = ArrayList<FullInfoCard>()
        var filterList:ArrayList<FullInfoCard> = ArrayList<FullInfoCard>()
        var page:Int = 1



        fun addFavoriteCard(favoriteCard: FullInfoCard){ this.favoriteList.add(favoriteCard) }

        fun addCard(cardList:ArrayList<FullInfoCard>){ this.cardsList.addAll(cardList)}


        fun addFilterList(filterList:ArrayList<FullInfoCard>){
            this.filterList.removeAll(this.filterList)
            this.filterList.addAll(filterList)
        }


    }
}