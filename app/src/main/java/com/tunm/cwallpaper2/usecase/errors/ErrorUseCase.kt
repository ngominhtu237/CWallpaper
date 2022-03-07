package com.tunm.cwallpaper2.usecase.errors

import com.tunm.cwallpaper2.data.error.Error

interface ErrorUseCase {
    fun getError(errorCode: Int): Error
}
