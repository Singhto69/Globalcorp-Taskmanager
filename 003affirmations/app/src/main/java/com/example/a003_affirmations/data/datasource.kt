package com.example.a003_affirmations.data

import com.example.a003_affirmations.R
import com.example.a003_affirmations.model.affirmation

class datasource {


    fun loadAffirmations(): List<affirmation> {
        return listOf<affirmation>(
            affirmation(R.string.affirmation1, R.drawable.image1),
            affirmation(R.string.affirmation2, R.drawable.image2),
            affirmation(R.string.affirmation3, R.drawable.image3),
            affirmation(R.string.affirmation4, R.drawable.image4),
            affirmation(R.string.affirmation5, R.drawable.image5),
            affirmation(R.string.affirmation6, R.drawable.image6),
            affirmation(R.string.affirmation7, R.drawable.image7),
            affirmation(R.string.affirmation8, R.drawable.image8),
            affirmation(R.string.affirmation9, R.drawable.image9),
            affirmation(R.string.affirmation10, R.drawable.image10)
        )
    }
}
