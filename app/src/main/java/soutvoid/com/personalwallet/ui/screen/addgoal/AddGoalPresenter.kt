package soutvoid.com.personalwallet.ui.screen.addgoal

import com.arellomobile.mvp.InjectViewState
import com.github.salomonbrys.kodein.Kodein
import soutvoid.com.personalwallet.ui.base.BasePresenter

@InjectViewState
class AddGoalPresenter(kodein: Kodein, goalLocalId: Long? = 0)
    : BasePresenter<AddGoalView>(kodein) {


}