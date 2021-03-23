/* Copyright 2020 Luzhuo. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.luzhuo.lib_alibaba.alipay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import com.alipay.sdk.app.PayTask;

import java.util.Map;

/**
 * Description:
 *
 * Example:
 * <pre>
 * public void alipay(View view){
 *     zhifubaoPay.setOnZhifubaoPayCallback(new IPayCallback() {
 *         @Override
 *         public void onSuccess(String message) {
 *             Log.e(TAG, "支付宝支付成功");
 *         }
 *
 *         @Override
 *         public void onError(String message) {
 *             Log.e(TAG, "支付宝支付失败");
 *         }
 *     });
 *     zhifubaoPay.pay(this, "alipay_sdk=alipay-sdk-java-4.7.12.ALL&app_id=2019043064380553&biz_content=%7B%22body%22%3A%22%E9%B1%BC%E7%BC%B8%E7%A7%9F%E8%B5%81%E6%94%AF%E4%BB%98%E8%AE%A2%E5%8D%95%22%2C%22out_trade_no%22%3A%221208253541%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E9%B1%BC%E7%BC%B8%E7%A7%9F%E8%B5%81%E6%94%AF%E4%BB%98%E8%AE%A2%E5%8D%95%22%2C%22time_expire%22%3A%222020-05-25+23%3A34%22%2C%22total_amount%22%3A%220.07%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2F47.96.78.243%2Fmall%2Fv1%2FfishTank%2FzfbPay&sign=Q3ltqmRi06r1rhS9Jl4wjM2OGVwOq69Tw7YKDA10hhokoDunx7d9wGpOzJeRGxnYOKDbgwhFscQeappTqR4hwILNGkXXZKv5nlg3nLJYuc2u%2FFjuZ8DpNPFKLYizOO2YI1C97dy%2BqzVxLGzhKHrCLt1YE8ut1CeB%2B5Dhr5X2Y0xdL6wfTjKrSkBl3DXrQ2TO0lKtyf7Lh4HKHi%2BQDQ7JniZwTpP1y4o9EQtRvnNkO0H4G8BcvjNgqTLxVPDiamYWRtw6pPbA6ZJYLyEZtaFZX25IxVqaKWCySUlIlBrtdpESjhJO6TsHYKvCb84IvdEsPHnjsDse3cojJofe3razrQ%3D%3D&sign_type=RSA2&timestamp=2020-05-25+23%3A19%3A06&version=1.0");
 * }
 * </pre>
 *
 * @Author: Luzhuo
 * @Creation Date: 2020/5/25 22:33
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public class AliPayManager {
    private IPayCallback payCallback;

    private AliPayManager(){}
    public static AliPayManager getInstance(){
        return Instance.instance;
    }
    private static class Instance{
        private static final AliPayManager instance = new AliPayManager();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            Map<String,String> message = (Map<String,String>) msg.obj;
            String resultStatus = message.get("resultStatus"); // error 6001, success 9000
            String result = message.get("result"); // success info
            String memo = message.get("memo"); // error message
            if(resultStatus != null && resultStatus.equals("9000")){
                // Successful transaction
                if(payCallback != null) payCallback.onSuccess("交易成功");
            }else{
                // transaction failed
                if(payCallback != null) payCallback.onError("交易失败");
            }
        }
    };

    public void pay(final Activity activity, final String orderInfo){
        // Alipay must be executed in an childer thread.
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(activity);
                Map<String,String> result = alipay.payV2(orderInfo,true);

                Message msg = new Message();
                msg.what = 666;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    public void setOnPayCallback(IPayCallback callback){
        this.payCallback = callback;
    }
}
