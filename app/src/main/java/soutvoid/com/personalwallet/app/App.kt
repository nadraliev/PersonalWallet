package soutvoid.com.personalwallet.app

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.birbit.android.jobqueue.JobManager
import com.birbit.android.jobqueue.config.Configuration
import com.facebook.stetho.Stetho
import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.android.autoAndroidModule
import com.google.gson.Gson
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.uphyca.stetho_realm.RealmInspectorModulesProvider
import io.realm.Realm
import io.realm.RealmConfiguration
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import soutvoid.com.personalwallet.interactor.transactionentry.local.CategoryRepository
import soutvoid.com.personalwallet.interactor.transactionentry.local.ICategoryRepository
import soutvoid.com.personalwallet.interactor.transactionentry.local.ITransactionEntryRepository
import soutvoid.com.personalwallet.interactor.transactionentry.local.TransactionEntryRepository
import soutvoid.com.personalwallet.interactor.transactionentry.server.CategoryApi
import soutvoid.com.personalwallet.util.BASE_URL


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

        bind<ICategoryRepository>() with singleton { CategoryRepository() }
        bind<ITransactionEntryRepository>() with singleton { TransactionEntryRepository() }
        bind<JobManager>() with singleton { JobManager(Configuration.Builder(this@App).build()) }
        bind<OkHttpClient>() with singleton {
            OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor()).build()
        }
        bind<Retrofit>() with singleton {
            Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(instance())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(Gson()))
                    .build()
        }
        bind<CategoryApi>() with singleton { instance<Retrofit>().create(CategoryApi::class.java) }
    }
}