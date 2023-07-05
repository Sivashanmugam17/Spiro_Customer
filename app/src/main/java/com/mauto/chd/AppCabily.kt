package com.mauto.chd

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.zeugmasolutions.localehelper.LocaleHelperApplicationDelegate
import io.realm.Realm
import io.realm.RealmConfiguration


open class AppCabily : Application() {
    private val localeAppDelegate = LocaleHelperApplicationDelegate()


    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(localeAppDelegate.attachBaseContext(base))
    }



    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        localeAppDelegate.onConfigurationChanged(this)
    }

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)
        val realmConfig = RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .name("zojek.driver.realm")
                .schemaVersion(0)
                .build()
        Realm.setDefaultConfiguration(realmConfig)

        /* if (LeakCanary.isInAnalyzerProcess(this)) {
             // This process is dedicated to LeakCanary for heap analysis.
             // You should not init your app in this process.
             return;
         }

         refWatcher = LeakCanary.install(this)*/
       /* AutoErrorReporter.get(this)
                .setEmailAddresses("pandiyan@teamtweaks.com")
                .setEmailSubject("Auto Crash Report")
                .start()*/
    }
}