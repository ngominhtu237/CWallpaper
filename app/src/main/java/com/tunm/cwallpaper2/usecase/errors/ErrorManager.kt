package com.tunm.cwallpaper2.usecase.errors

import com.tunm.cwallpaper2.data.error.Error
import com.tunm.cwallpaper2.data.error.mapper.ErrorMapper
import javax.inject.Inject

class ErrorManager @Inject constructor(private val errorMapper: ErrorMapper) : ErrorUseCase {
    override fun getError(errorCode: Int): Error {
        return Error(
            code = errorCode,
            description = errorMapper.errorsMap.getValue(errorCode)
        )
    }
}
