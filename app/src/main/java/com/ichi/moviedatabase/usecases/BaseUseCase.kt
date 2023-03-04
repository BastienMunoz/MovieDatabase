package com.ichi.moviedatabase.usecases

import com.ichi.moviedatabase.core.Result as Result
import kotlinx.coroutines.flow.Flow

abstract class BaseUseCase<ParamsType : BaseUseCase.BaseParameters, ReturnType> {
    interface BaseParameters

    protected abstract suspend fun run(params: ParamsType): Flow<Result<ReturnType>>
    suspend operator fun invoke(params: ParamsType) = run(params)
}
