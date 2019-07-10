package com.example.startwarsapp

import android.graphics.Color
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.example.startwarsapp.model.database.AppDatabase
import com.example.startwarsapp.model.database.FavoriteDao
import com.example.startwarsapp.model.entity.FullInfoCard
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableMaybeObserver
import io.reactivex.schedulers.Schedulers


class FullCardFragment : Fragment() {

    lateinit var fullCardObject: FullInfoCard
    lateinit var clCard: ConstraintLayout
    lateinit var btnFavorite: ImageButton
    lateinit var tvName: TextView
    lateinit var tvHeight: TextView
    lateinit var tvMass: TextView
    lateinit var tvHairColor: TextView
    lateinit var tvSkinColor: TextView
    lateinit var tvEyeColor: TextView
    lateinit var tvBirthYear: TextView
    lateinit var tvGender: TextView

    var db: AppDatabase
    var favoriteDao: FavoriteDao

    init {
        db = App.getInstance().getDB()
        favoriteDao = db.favoriteDao()
        fullCardObject = FullInfoCard()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_full_card, container, false)

        fullCardObject = arguments!!.getSerializable(getString(R.string.bundle_argument_name)) as FullInfoCard

        clCard = rootView.findViewById(R.id.clCard)
        clCard.setBackgroundColor(Color.parseColor(fullCardObject.color))

        btnFavorite = rootView.findViewById(R.id.btnFavorite)
        btnFavorite.setBackgroundColor(Color.parseColor(fullCardObject.color))

        btnFavorite.setOnClickListener {
            favoriteDao.getById(fullCardObject.name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DisposableMaybeObserver<FullInfoCard>() {
                    override fun onError(e: Throwable?) {}

                    override fun onSuccess(t: FullInfoCard?) {
                        favoriteDao.delete(fullCardObject)
                        fullCardObject.isFavorite = false
                        btnFavorite.setImageDrawable(ContextCompat.getDrawable(context!!, R.drawable.ic_favorite_false))
                    }

                    override fun onComplete() {
                        favoriteDao.insert(fullCardObject)
                        fullCardObject.isFavorite = true
                        btnFavorite.setImageDrawable(ContextCompat.getDrawable(context!!, R.drawable.ic_favorite_true))
                    }
                })

        }

        tvName = rootView.findViewById(R.id.tvName)
        tvName.text = getString(R.string.tvName).plus(fullCardObject.name)

        tvHeight = rootView.findViewById(R.id.tvHeight)
        tvHeight.text = getString(R.string.tvHeigh).plus(fullCardObject.height)

        tvMass = rootView.findViewById(R.id.tvMass)
        tvMass.text = getString(R.string.tvWeight).plus(fullCardObject.mass)

        tvHairColor = rootView.findViewById(R.id.tvHairColor)
        tvHairColor.text = getString(R.string.tvHairColor).plus(fullCardObject.hairColor)

        tvSkinColor = rootView.findViewById(R.id.tvSkinColor)
        tvSkinColor.text = getString(R.string.tvSkinColor).plus(fullCardObject.skinColor)

        tvEyeColor = rootView.findViewById(R.id.tvEyeColor)
        tvEyeColor.text = getString(R.string.tvEyeColor).plus(fullCardObject.eyeColor)

        tvBirthYear = rootView.findViewById(R.id.tvBirthYear)
        tvBirthYear.text = getString(R.string.tvBirthday).plus(fullCardObject.birthYear)

        tvGender = rootView.findViewById(R.id.tvGender)
        tvGender.text = getString(R.string.tvGender).plus(fullCardObject.gender)

        return rootView
    }

    companion object {

        fun newInstance(key: String, card: FullInfoCard): FullCardFragment {
            val fullCardFragment = FullCardFragment()
            val bundle = Bundle()
            bundle.putSerializable(key, card)
            fullCardFragment.arguments = bundle
            return fullCardFragment
        }
    }
}