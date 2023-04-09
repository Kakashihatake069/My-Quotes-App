package adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quotesapp.R
import com.example.quotesapp.activity.ModelClass

class CategroryAdapter(var ModelClass: ArrayList<ModelClass>, var click : (ModelClass) -> Unit) : RecyclerView.Adapter<CategroryAdapter.MyViewHolder>() {
    class MyViewHolder(itemview: View):RecyclerView.ViewHolder(itemview) {
        var txtcategoryname: TextView = itemview.findViewById(R.id.txtcategoryname)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var v =LayoutInflater.from(parent.context).inflate(R.layout.categoryitemfile,parent,false)
        var view = MyViewHolder(v)
      return view
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.txtcategoryname.text=ModelClass[position].name
            holder.txtcategoryname.setOnClickListener {
                click.invoke(ModelClass[position])
            }
    }

    override fun getItemCount(): Int {
        return  ModelClass.size
    }
}