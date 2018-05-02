package soutvoid.com.personalwallet.app

import android.app.Application
import android.content.Context
import android.os.Build
import android.support.multidex.MultiDex
import com.birbit.android.jobqueue.JobManager
import com.birbit.android.jobqueue.config.Configuration
import com.birbit.android.jobqueue.scheduling.FrameworkJobSchedulerService
import com.birbit.android.jobqueue.scheduling.GcmJobSchedulerService
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.stetho.Stetho
import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.android.autoAndroidModule
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
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
import soutvoid.com.personalwallet.interactor.authorization.local.AuthorizationRepository
import soutvoid.com.personalwallet.interactor.authorization.local.IAuthorizationRepository
import soutvoid.com.personalwallet.interactor.authorization.server.AuthorizationApi
import soutvoid.com.personalwallet.interactor.authorization.server.AuthorizationInterceptor
import soutvoid.com.personalwallet.interactor.transactionentry.local.CategoryRepository
import soutvoid.com.personalwallet.interactor.transactionentry.local.ICategoryRepository
import soutvoid.com.personalwallet.interactor.transactionentry.local.ITransactionEntryRepository
import soutvoid.com.personalwallet.interactor.transactionentry.local.TransactionEntryRepository
import soutvoid.com.personalwallet.interactor.transactionentry.server.CategoryApi
import soutvoid.com.personalwallet.interactor.util.GcmJobService
import soutvoid.com.personalwallet.interactor.util.JobService
import soutvoid.com.personalwallet.ui.util.SharedPreferencesWrapper
import soutvoid.com.personalwallet.util.BASE_URL
import java.util.concurrent.TimeUnit


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
        Fresco.initialize(this)
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
        bind<JobManager>() with singleton {
            val builder = Configuration.Builder(this@App)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                builder.scheduler(FrameworkJobSchedulerService
                        .createSchedulerFor(this@App, JobService::class.java))
            else {
                val enableGcm = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this@App)
                if (enableGcm == ConnectionResult.SUCCESS) {
                    builder.scheduler(GcmJobSchedulerService.createSchedulerFor(this@App,
                            GcmJobService::class.java))
                }
            }
            return@singleton JobManager(builder.build())
        }
        bind<OkHttpClient>() with singleton {
            OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS)
                    .addInterceptor(AuthorizationInterceptor())
                    .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
                    .build()
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
        bind<SharedPreferencesWrapper>() with singleton { SharedPreferencesWrapper(this@App) }
        bind<AuthorizationApi>() with singleton { instance<Retrofit>().create(AuthorizationApi::class.java) }
        bind<IAuthorizationRepository>() with singleton { AuthorizationRepository() }
    }
}