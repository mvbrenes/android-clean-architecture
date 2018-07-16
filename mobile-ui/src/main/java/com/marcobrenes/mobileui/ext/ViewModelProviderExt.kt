package com.marcobrenes.mobileui.ext

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

internal inline fun <reified VM: ViewModel> ViewModelProvider.get(): VM {
    return get(VM::class.java)
}