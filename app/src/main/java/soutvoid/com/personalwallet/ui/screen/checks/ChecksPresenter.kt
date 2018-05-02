package soutvoid.com.personalwallet.ui.screen.checks

import com.arellomobile.mvp.InjectViewState
import com.github.salomonbrys.kodein.Kodein
import soutvoid.com.personalwallet.ui.base.BasePresenter
import java.io.File

@InjectViewState
class ChecksPresenter(kodein: Kodein) : BasePresenter<ChecksView>(kodein) {

    private var checks = mutableListOf<File>()

    override fun attachView(view: ChecksView?) {
        super.attachView(view)
        loadChecks()
    }

    fun onAddCheck() {
        viewState?.takeNewPhoto()
    }

    fun onCheckClick(file: File) {
        viewState?.openCheckFullscreen(file)
    }

    private fun loadChecks() {
        viewState?.getAllChecks {
            checks = it.toMutableList()
            viewState?.showChecks(it)
        }
    }

}