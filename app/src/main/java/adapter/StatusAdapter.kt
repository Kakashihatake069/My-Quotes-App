package adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quotesapp.R
import com.example.quotesapp.activity.favouritemodelclass
import com.example.quotesapp.activity.quotesmodel

class StatusAdapter (var like : (Int,Int ) -> Unit): RecyclerView.Adapter<StatusAdapter.MyViewHolder>() {
    var favlist  = ArrayList<favouritemodelclass>()
    class MyViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview) {
        var txtquotes : TextView = itemview.findViewById(R.id.txtquotes)
       var imgquotesfavourite : ImageView = itemView.findViewById(R.id.imgquotesfavourite)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatusAdapter.MyViewHolder {
            var v = LayoutInflater.from(parent.context).inflate(R.layout.quotesitemfile,parent,false)
        return MyViewHolder(v)
    }
    override fun getItemCount(): Int {
        return favlist.size
    }
    override fun onBindViewHolder(holder: StatusAdapter.MyViewHolder, position: Int) {
       holder.txtquotes.text=favlist[position].quotes
        if (favlist[position].status == 1)
        {
            holder.imgquotesfavourite.setImageResource(R.drawable.heartfill)

        }else
        {
            holder.imgquotesfavourite.setImageResource(R.drawable.favouriteheart)
        }
        holder.imgquotesfavourite.setOnClickListener {
            if (favlist[position].status == 1)
            {
                   like.invoke(favlist[position].id,0)
                holder.imgquotesfavourite.setImageResource(R.drawable.favouriteheart)
                favlist[position].status = 0
                Log.e("TAG","working:" +favlist[position].status)
            }else
            {
                like.invoke(favlist[position].id,1)
                holder.imgquotesfavourite.setImageResource(R.drawable.heartfill)
                favlist[position].status = 1
            }
            deleteitem(position)
        }

    }
    fun updatelist (favlist : ArrayList<favouritemodelclass>){
        this.favlist= favlist
        if(favlist == null && favlist.size > 0)
        {
            favlist.addAll(favlist)
            notifyDataSetChanged()
        }

    }
    private fun deleteitem(position: Int)
    {
        favlist.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position,favlist.size)

    }

}