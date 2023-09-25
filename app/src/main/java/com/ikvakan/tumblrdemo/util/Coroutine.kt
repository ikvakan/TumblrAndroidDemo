package com.ikvakan.tumblrdemo.util

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

typealias ProgressChangedCallback = (Coroutine.Progress) -> Unit
typealias StartedCallBack = () -> Unit
typealias ExceptionCallBack = (Exception) -> Unit
typealias CancelCallBack = () -> Unit
typealias FinishedCallBack = () -> Unit

class Coroutine(
    private val coroutineScope: CoroutineScope,
    private val coroutineBlock: suspend CoroutineScope.() -> Unit
) {
    data class Progress(
        val inProgress: Boolean = true,
        val exception: Exception? = null
    )

    private var progress = Progress()
    private var progressChangedCallback: ProgressChangedCallback? = null
    private var startedCallBack: StartedCallBack? = null
    private var exceptionCallback: ExceptionCallBack? = null
    private var cancelCallback: CancelCallBack? = null
    private var finishedCallback: FinishedCallBack? = null
    private var coroutineContext: CoroutineContext = EmptyCoroutineContext
    private var coroutineStart: CoroutineStart = CoroutineStart.DEFAULT

    fun onProgressChanged(callback: ProgressChangedCallback) =
        apply { this.progressChangedCallback = callback }

    fun onStarted(callback: StartedCallBack) = apply { startedCallBack = callback }
    fun onException(callback: ExceptionCallBack) = apply { exceptionCallback = callback }
    fun onCanceled(callback: CancelCallBack) = apply { cancelCallback = callback }
    fun onFinish(callback: FinishedCallBack) = apply { finishedCallback = callback }

    fun withContext(context: CoroutineContext) = apply { coroutineContext = context }
    fun setStart(start: CoroutineStart) = apply { coroutineStart = start }

    @Suppress("TooGenericExceptionCaught", "SwallowedException")
    fun launch() = coroutineScope.launch {
        try {
            startedCallBack?.invoke()
            updateProgress(inProgress = true)
            coroutineBlock.invoke(this)
            updateProgress(inProgress = false)
            finishedCallback?.invoke()
        } catch (e: CancellationException) {
            cancelCallback?.invoke()
            updateProgress(inProgress = false)
        } catch (e: Exception) {
            updateProgress(inProgress = false, exception = e)
            exceptionCallback?.invoke(e)
        }
    }

    private fun updateProgress(inProgress: Boolean = false, exception: Exception? = null) {
        progress = progress.copy(inProgress = inProgress, exception = exception)
        progressChangedCallback?.invoke(progress)
    }
}
