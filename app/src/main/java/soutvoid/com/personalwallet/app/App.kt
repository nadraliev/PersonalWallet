package soutvoid.com.personalwallet.app

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.facebook.stetho.Stetho
import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.android.autoAndroidModule
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.uphyca.stetho_realm.RealmInspectorModulesProvider
import io.realm.Realm
import io.realm.RealmConfiguration
import soutvoid.com.personalwallet.interactor.transactionentry.CategoryRepository
import soutvoid.com.personalwallet.interactor.transactionentry.ICategoryRepository
import soutvoid.com.personalwallet.interactor.transactionentry.ITransactionEntryRepository
import soutvoid.com.personalwallet.interactor.transactionentry.TransactionEntryRepository
import soutvoid.com.personalwallet.ui.util.PresenterScope


/**
 * Created by andrew on 16.03.18.
 */
class App : Application(), KodeinAware {

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initLogger()
        initRealm()
        initStetho()
    }

    private fun initRealm() {
        Realm.init(this)
        val configuration = RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded().build()
        Realm.setDefaultConfiguration(configuration)
    }

    private fun initStetho() {
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build()
        )
    }

    private fun initLogger() {
        Logger.addLogAdapter(AndroidLogAdapter())
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override val kodein by Kodein.lazy {
        import(autoAndroidModule(this@App))

        bind<Realm>() with scopedSingleton(PresenterScope) { Realm.getDefaultInstance() }
        bind<ICategoryRepository>() with scopedSingleton(PresenterScope) { CategoryRepository(with(it).instance()) }
        bind<ITransactionEntryRepository>() with scopedSingleton(PresenterScope) { TransactionEntryRepository(with(it).instance()) }
    }
}