package com.angopa.aad.utilities

import androidx.annotation.IntDef

/**
 *  Created by Andres Gonzalez on 02/06/2020.
 */
@IntDef(
    REPORT,
    DELETE,
    MORE
)
@Retention(AnnotationRetention.RUNTIME)
annotation class SettingOption

const val REPORT = 0
const val DELETE = 1
const val MORE = 2