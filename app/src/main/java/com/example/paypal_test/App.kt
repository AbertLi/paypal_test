package com.example.paypal_test

import android.app.Application
import com.paypal.checkout.PayPalCheckout
import com.paypal.checkout.config.CheckoutConfig
import com.paypal.checkout.config.Environment
import com.paypal.checkout.config.SettingsConfig
import com.paypal.checkout.createorder.CurrencyCode
import com.paypal.checkout.createorder.UserAction

class App : Application() {
    val CLIENT_ID = "At4vgzkGOFO5ApmdWY0PXXZE5GEOAuN5gpnoWOdc-P6dz3woIvk5gbgh"
    override fun onCreate() {
        super.onCreate()
        val config = CheckoutConfig(
            application = this,
            clientId = CLIENT_ID,//YOUR_CLIENT_ID
            environment = Environment.SANDBOX,
            currencyCode = CurrencyCode.USD,//选择美元
            userAction = UserAction.PAY_NOW,
            settingsConfig = SettingsConfig(
                loggingEnabled = true,
                shouldFailEligibility = true
            )
        )
        PayPalCheckout.setConfig(config)
    }
}