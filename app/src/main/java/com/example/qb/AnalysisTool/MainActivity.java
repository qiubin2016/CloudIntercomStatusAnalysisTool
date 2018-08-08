package com.example.qb.AnalysisTool;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ReplacementSpan;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private EditText editText;
    private TextView displayTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        displayTextView = findViewById(R.id.displayTextView);

        editText = findViewById(R.id.editText);
        editText.addTextChangedListener(new TextWatcher() {
            private EditText et = editText;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber()
                        + "输入过程中执行该方法," + "start:" + start + ",count:" + count + ",after:" + after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber()
                        + "输入前确认执行该方法," + "start:" + start + ",before:" + before + ",count:" + count);
                //当输入为小写字母时，自动转换为大写字母

                et.removeTextChangedListener(this);//解除文字改变事件
                et.setText(s.toString().toUpperCase());//转换
                et.setSelection(s.toString().length());//重新设置光标位置
                et.addTextChangedListener(this);//重新绑
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber()
                        + "输入结束执行该方法" + ",str:" + s.toString() + ",int:" );

                long longData;
                String result;
                if (!s.toString().isEmpty()) {
                    longData = Long.valueOf(s.toString(), 16);   //d=255
                    result = Long.toBinaryString(longData);
                    Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber()
                            + ",result:" + result);
                } else {
                    result = "";
                }

                String displayStr = analyString(result);
                Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",display:" + displayStr);
                //解析并显示
                displayTextView.setText(displayStr);
            }
        });

    }

    public String analyString(String string) {
        int i = 0;
        char charText;
        String resultStr = "";

        for  (i = 0; i < string.length(); i++) {
            charText = string.charAt(string.length() - i - 1);
            if ('0' != charText) {
                switch (i) {
                    case 0:
                        resultStr += String.format("%d-门禁通行记录预满\n", i);
                        break;
                    case 1:
                        resultStr += String.format("%d-电话通行记录预满\n", i);
                        break;
                    case 5:
                        resultStr += String.format("%d-下载PID文件失败\n", i);
                        break;
                    case 6:
                        resultStr += String.format("%d-后台返回业务云通讯地址为空\n", i);
                        break;
                    case 7:
                        resultStr += String.format("%d-参数错误（后台返回）\n", i);
                        break;
                    case 8:
                        resultStr += String.format("%d-设备不存在（后台未登记设备）\n", i);
                        break;
                    case 9:
                        resultStr += String.format("%d-设备已禁用（后台将项目/设备禁用\n", i);
                        break;
                    case 10:
                        resultStr += String.format("%d-项目型号错误\n", i);
                        break;
                    case 11:
                        resultStr += String.format("%d-云后台系统故障\n", i);
                        break;
                    case 12:
                        resultStr += String.format("%d-Token无效（云后台返回的Token已过期）\n", i);
                        break;
                    case 13:
                        resultStr += String.format("%d-与QQ物联云服务器通讯异常\n", i);
                        break;
                    case 14:
                        resultStr += String.format("%d-设备未绑定\n", i);
                        break;
                    case 15:
                        resultStr += String.format("%d-与SIP服务器通讯故障\n", i);
                        break;
                    case 16:
                        resultStr += String.format("%d-网线未插入\n", i);
                        break;
                    case 17:
                        resultStr += String.format("%d-网络异常\n", i);
                        break;
                    case 18:
                        resultStr += String.format("%d-与一卡通服务器通讯故障\n", i);
                        break;
                    case 24:
                        resultStr += String.format("%d-未读管理卡\n", i);
                        break;
                }
            }
        }
        return resultStr;
    }
}
