package me.luzhuo.alibabademo;

import androidx.appcompat.app.AppCompatActivity;
import me.luzhuo.lib_alibaba.alipay.AliPayManager;
import me.luzhuo.lib_alibaba.alipay.IPayCallback;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private AliPayManager alipay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alipay = AliPayManager.getInstance();
    }

    public void alipay(View view){
        alipay.setOnPayCallback(new IPayCallback() {
            @Override
            public void onSuccess(String s) {
                Log.e(TAG, "" + s);
            }

            @Override
            public void onError(String s) {
                Log.e(TAG, "" + s);
            }
        });
        alipay.pay(this, "alipay_sdk=alipay-sdk-java-4.7.12.ALL&app_id=2019043064380553&biz_content=%7B%22body%22%3A%22%E9%B1%BC%E7%BC%B8%E7%A7%9F%E8%B5%81%E6%94%AF%E4%BB%98%E8%AE%A2%E5%8D%95%22%2C%22out_trade_no%22%3A%221208253541%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E9%B1%BC%E7%BC%B8%E7%A7%9F%E8%B5%81%E6%94%AF%E4%BB%98%E8%AE%A2%E5%8D%95%22%2C%22time_expire%22%3A%222020-05-25+23%3A34%22%2C%22total_amount%22%3A%220.07%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2F47.96.78.243%2Fmall%2Fv1%2FfishTank%2FzfbPay&sign=Q3ltqmRi06r1rhS9Jl4wjM2OGVwOq69Tw7YKDA10hhokoDunx7d9wGpOzJeRGxnYOKDbgwhFscQeappTqR4hwILNGkXXZKv5nlg3nLJYuc2u%2FFjuZ8DpNPFKLYizOO2YI1C97dy%2BqzVxLGzhKHrCLt1YE8ut1CeB%2B5Dhr5X2Y0xdL6wfTjKrSkBl3DXrQ2TO0lKtyf7Lh4HKHi%2BQDQ7JniZwTpP1y4o9EQtRvnNkO0H4G8BcvjNgqTLxVPDiamYWRtw6pPbA6ZJYLyEZtaFZX25IxVqaKWCySUlIlBrtdpESjhJO6TsHYKvCb84IvdEsPHnjsDse3cojJofe3razrQ%3D%3D&sign_type=RSA2&timestamp=2020-05-25+23%3A19%3A06&version=1.0");
    }
}