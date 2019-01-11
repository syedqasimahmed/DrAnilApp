package mmcom.ui.dranil.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.nav_item_row.view.*
import mmcom.ui.dranil.R
import mmcom.ui.dranil.model.NavigationItemModel

/**
 * Created by Qasim Ahmed on 10/01/2019.
 */

class NavigationAdapter(var navItemList: ArrayList<NavigationItemModel>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.nav_item_row, parent, false))
    }

    override fun getItemCount(): Int {
        if (navItemList != null)
            return navItemList.count()
        return 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var current = navItemList.get(position)
        holder.tvNavItem.text = current.itemName
        if (current.isChecked) {
            holder.tvNavItem.setTextColor(context.resources.getColor(R.color.logoutGreen))
            holder.view_selected.visibility = View.VISIBLE
        } else {
            holder.tvNavItem.setTextColor(context.resources.getColor(R.color.colorWhite))
            holder.view_selected.visibility = View.INVISIBLE
        }
        holder.rootView.setOnClickListener({
            if (current.isChecked) {

            } else {

                for (i in 0 until navItemList.count()) {

                    navItemList[i].isChecked = false
                }
                navItemList[position].isChecked = true

                notifyDataSetChanged()
            }
        })


    }

    fun swapItems(navItemList: ArrayList<NavigationItemModel>) {
        this.navItemList = navItemList
        notifyItemRangeChanged(0, navItemList.size)
        notifyDataSetChanged()
    }

}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val rootView = view.nav_item_root_view
    val tvNavItem = view.tv_nav_item
    val view_selected = view.view_selected
}