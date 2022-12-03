package com.example.paypal_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.paypal.checkout.approve.OnApprove
import com.paypal.checkout.cancel.OnCancel
import com.paypal.checkout.createorder.CreateOrder
import com.paypal.checkout.createorder.CurrencyCode
import com.paypal.checkout.createorder.OrderIntent
import com.paypal.checkout.createorder.UserAction
import com.paypal.checkout.error.OnError
import com.paypal.checkout.order.Amount
import com.paypal.checkout.order.AppContext
import com.paypal.checkout.order.Order
import com.paypal.checkout.order.PurchaseUnit
import com.paypal.checkout.paymentbutton.PaymentButtonContainer
import com.paypal.checkout.shipping.OnShippingChange

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    val paymentButtonContainer by lazy { findViewById<PaymentButtonContainer>(R.id.payment_button_container) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        paymentButtonContainer.setup(
            createOrder =
            CreateOrder { createOrderActions ->
                val order =
                    Order(
                        intent = OrderIntent.CAPTURE,
                        appContext = AppContext(userAction = UserAction.PAY_NOW),
                        purchaseUnitList =
                        listOf(
                            PurchaseUnit(
                                amount =
                                Amount(currencyCode = CurrencyCode.USD, value = "0.01")//0.01美元
                            )
                        )
                    )
                createOrderActions.create(order)
            },
            onApprove =
            OnApprove { approval ->
                approval.orderActions.capture { captureOrderResult ->
                    Log.i(TAG, "OnApprove CaptureOrderResult: $captureOrderResult")
                }
            },
            onShippingChange = OnShippingChange { shippingChangeData, shippingChangeActions ->
                Log.i(
                    TAG,
                    "OnShippingChange shippingChangeData payToken: ${shippingChangeData.payToken}"
                )
            },
            onCancel = OnCancel {
                Log.d(TAG, "OnCancel Buyer canceled the PayPal experience.")
            },
            onError = OnError { errorInfo ->
                Log.d(TAG, "Error: $errorInfo")
            }
        )
    }
}