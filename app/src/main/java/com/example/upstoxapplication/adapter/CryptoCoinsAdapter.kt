package com.example.upstoxapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.upstoxapplication.R
import com.example.upstoxapplication.data.Cryptocurrencies


class CryptoCoinsAdapter(context: Context?, private var listToShow: List<Cryptocurrencies>): RecyclerView.Adapter<CryptoCoinsAdapter.CryptoCoinsViewHolder>() {


    inner class CryptoCoinsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private val txtView1: TextView = itemView.findViewById(R.id.tv_coin_full_name)
        private val txtView2: TextView = itemView.findViewById(R.id.tv_coin_short_name)
        private val imgView: ImageView = itemView.findViewById(R.id.iv_coin_img)
        private val imgViewNewCoin: FrameLayout = itemView.findViewById(R.id.fl_new_icon)

        fun bind(cryptocurrency: Cryptocurrencies) {
            txtView1.text = cryptocurrency.name
            txtView2.text = cryptocurrency.symbol
            imgView.setImageResource(R.drawable.faq_svgrepo_com)
            imgViewNewCoin.visibility = View.GONE

            if(cryptocurrency.isNew == true){
                imgViewNewCoin.visibility = View.VISIBLE
            }

            if(cryptocurrency.isActive == true){
                when(cryptocurrency.type){
                    "coin" -> {imgView.setImageResource(R.drawable.img_type_coin)}
                    "token" -> {imgView.setImageResource(R.drawable.img_type_token)}
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoCoinsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cryptocoins , parent , false)
        return CryptoCoinsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CryptoCoinsViewHolder, position: Int) {
        holder.bind(listToShow[position])
    }

    override fun getItemCount(): Int {
       return listToShow.size
    }

    fun setCryptocurrencies(cryptocurrencies: List<Cryptocurrencies>) {
        this.listToShow = cryptocurrencies
        notifyDataSetChanged()
    }

}