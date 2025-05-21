//package com.example.newsapp2.XML
//
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import androidx.recyclerview.widget.RecyclerView.Adapter
//import androidx.recyclerview.widget.RecyclerView.ViewHolder
//import com.example.newsapp2.api.model.Category
//
//class CategoriesAdapter : Adapter<RecyclerView.ViewHolder>() {
//
//    val LEFT_VIEW_HOLDER_TYPE_1 = 100
//    val RIGHT_VIEW_HOLDER_TYPE_1 = 200
//
//    override fun getItemViewType(position: Int): Int {
//        if (position % 2 == 0) {
//            return LEFT_VIEW_HOLDER_TYPE_1
//        }
//        return RIGHT_VIEW_HOLDER_TYPE_1
//    }
//
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): ViewHolder {
//        if (viewType == LEFT_VIEW_HOLDER_TYPE_1) {
//            return LeftArrowedCategoryViewHolder(view =)
//        }
//        return RightArrowedCategoryViewHolder(view =)
//    }
//
//    override fun onBindViewHolder(
//        holder: RecyclerView.ViewHolder,
//        position: Int
//    ) {
//        if (holder is LeftArrowedCategoryViewHolder){
//
//        }else if (holder is RightArrowedCategoryViewHolder){
//            holder.
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return Category.getCategoriesList().size
//    }
//
//
//    class LeftArrowedCategoryViewHolder(val view: View) : RecyclerView.ViewHolder(view)
//    class RightArrowedCategoryViewHolder(val view: View) : RecyclerView.ViewHolder(view)
//}