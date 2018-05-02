package soutvoid.com.personalwallet.interactor.goal.local

import soutvoid.com.personalwallet.domain.goal.Goal
import soutvoid.com.personalwallet.interactor.BaseRepository

class GoalRepository : BaseRepository<Goal>(Goal::class.java), IGoalRepository