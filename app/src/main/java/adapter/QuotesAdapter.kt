package adapter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.quotesapp.R
import com.example.quotesapp.activity.quotesmodel

class QuotesAdapter( var context: Context,
                     var displayimg: (quotesmodel) -> Unit,
                     var like: (Int, Int)-> Unit) : RecyclerView.Adapter<QuotesAdapter.MyQuotesViewHolder>() {
    var quotesmodel= ArrayList<quotesmodel>()
    class MyQuotesViewHolder(view:View) : RecyclerView.ViewHolder(view) {
            var txtquotes : TextView = itemView.findViewById(R.id.txtquotes)
            var displaylayout : LinearLayout = itemView.findViewById(R.id.displaylayout)
            var imgquotesfavourite : ImageView = itemView.findViewById(R.id.imgquotesfavourite)
            var imgquotesshare : ImageView = itemView.findViewById(R.id.imgquotesshare)
            var imgquotescopy : ImageView = itemView.findViewById(R.id.imgquotescopy)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyQuotesViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.quotesitemfile,parent,false)
        return MyQuotesViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyQuotesViewHolder, position: Int) {
            holder.txtquotes.text = quotesmodel[position].quotes

            holder.displaylayout.setOnClickListener {
            displayimg.invoke(quotesmodel[position])
        }

        if(quotesmodel[position].status == 1)
        {
            holder.imgquotesfavourite.setImageResource(R.drawable.heartfill)
        }

        holder.imgquotesfavourite.setOnClickListener {

            if (quotesmodel[position].status == 1)
            {
                like.invoke(quotesmodel[position].id,0)
                holder.imgquotesfavourite.setImageResource(R.drawable.heart)
                quotesmodel[position].status=0
                Log.e("text","display:"+quotesmodel[position].status.toString()+ " "+quotesmodel[position].id.toString())
            }
            else
            {
                like.invoke(quotesmodel[position].id,1)
                holder.imgquotesfavourite.setImageResource(R.drawable.heartfill)
                quotesmodel[position].status=1
            }

        }
        // share method
        holder.imgquotesshare.setOnClickListener(View.OnClickListener {s: View? ->
            val i = Intent(Intent.ACTION_SEND)
            i.setType("text/plain")
            i.putExtra(Intent.EXTRA_TEXT,quotesmodel[position].quotes)
            context.startActivity(i)
        })
        //copy text method
        holder.imgquotescopy.setOnClickListener {
            val clipboard = context.getSystemService(CLIPBOARD_SERVICE)as ClipboardManager
            val clip : ClipData = ClipData.newPlainText("simple text","hello world!")
            clipboard.setPrimaryClip(clip)
            Toast.makeText(context,"copy",Toast.LENGTH_SHORT).show()
        }

    }



    override fun getItemCount(): Int {
return quotesmodel.size
    }
    fun updatefunction(quotesmodel: ArrayList<quotesmodel>)
    {
        this.quotesmodel = ArrayList()
        this.quotesmodel.addAll(quotesmodel)
        notifyDataSetChanged()
    }

}