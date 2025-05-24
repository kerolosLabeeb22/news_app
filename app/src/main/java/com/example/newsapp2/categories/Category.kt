package com.example.newsapp2.categories

import com.example.newsapp2.R

data class Category(
    val titleResId: Int? = null,
    val imageResId: Int? = null,
    val apiId: String? = null
) {
    companion object {

        private val BUSINESS = "business"
        private val ENTERTAINMENT = "entertainment"
        private val GENERAL = "General"
        private val HEALTH = "health"
        private val SCIENCE = "science"
        private val SPORTS = "sports"
        private val TECHNOLOGY = "technology"

        private fun fromId(id: String): Category {
            return when(id){
                GENERAL -> Category(R.string.general,R.drawable.general,GENERAL)
                BUSINESS -> Category(R.string.business,R.drawable.business,BUSINESS)
                SPORTS -> Category(R.string.sports,R.drawable.sports,SPORTS)
                TECHNOLOGY -> Category(R.string.technology,R.drawable.technology,TECHNOLOGY)
                ENTERTAINMENT -> Category(R.string.entertainment,R.drawable.entertainment,ENTERTAINMENT)
                HEALTH -> Category(R.string.health,R.drawable.health,HEALTH)
                SCIENCE -> Category(R.string.science,R.drawable.science,SCIENCE)
                else -> Category()
            }
        }

        fun getCategoriesList(): List<Category> {
            return listOf(
                fromId(GENERAL),
                fromId(BUSINESS),
                fromId(SPORTS),
                fromId(TECHNOLOGY),
                fromId(ENTERTAINMENT),
                fromId(HEALTH),
                fromId(SCIENCE)
            )
        }


    }
}