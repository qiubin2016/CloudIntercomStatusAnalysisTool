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
    private Handler handler;
    private TextView displayTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String str = "我是谁";
        SpannableString spannableString = new SpannableString("#" + str + "#" + "我来自哪里");
        spannableString.setSpan(new RoundBackgroundColorSpan(Color.parseColor("#12DBD1"), Color.parseColor("#FFFFFF")),
                0, str.length() + 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        textView = findViewById(R.id.textView2);
//        textView.setText(spannableString);


        int[] ids = new int[]{R.id.lTextView0, R.id.lTextView1, R.id.lTextView2, R.id.lTextView3, R.id.lTextView4, R.id.lTextView5,
                R.id.lTextView6, R.id.lTextView7, R.id.lTextView8, R.id.lTextView9, R.id.lTextView10, R.id.lTextView11,
                R.id.lTextView12, R.id.lTextView13, R.id.lTextView14, R.id.lTextView15, R.id.lTextView16, R.id.lTextView17,
                R.id.lTextView18, R.id.lTextView19, R.id.lTextView20, R.id.lTextView21, R.id.lTextView22, R.id.lTextView23,
                R.id.lTextView24, R.id.lTextView25, R.id.lTextView26, R.id.lTextView27, R.id.lTextView28, R.id.lTextView29,
                R.id.lTextView30, R.id.lTextView31};
        final TextView[] txs = new TextView[ids.length];
        for (int i = 0; i < txs.length; i++) {
            txs[i] = findViewById(ids[i]);
            txs[i].setTextSize(20);
            txs[i].setText("0");
            txs[i].setGravity(Gravity.CENTER);
        }

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
                Message msg = handler.obtainMessage();
                msg.obj = "111";
                //sendMessage()方法，无论是在主线程中还是WorkThread中发送都是可以的
                handler.sendMessage(msg);

                int integer;
                long longData;
                String result;
                if (!s.toString().isEmpty()) {
//                    integer = Integer.valueOf(s.toString(), 16);   //d=255
                    longData = Long.valueOf(s.toString(), 16);   //d=255
//                    result = Integer.toBinaryString(integer);
                    result = Long.toBinaryString(longData);
                    Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber()
                            + ",result:" + result);
                } else {
                    result = "";
                }

//                txs[0].setText("1");
                char charText;
                for (int i = 0; i < 32; i++){
                    if (i < result.length()) {
                        charText = result.charAt(result.length() - i - 1);
                        Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber()
                                + ",char:" +  charText + ",color:" + txs[i].getCurrentTextColor());

                        if ('0' != charText) {
                            txs[i].setTextColor(Color.RED);
                        }
                        else
                        {
                            txs[i].setTextColor(Color.GRAY);
                        }
                        txs[i].setText(String.valueOf(result.charAt(result.length() - i - 1)));
                    }
                    else
                    {
                        txs[i].setText("0");
                        txs[i].setTextColor(Color.GRAY);
                        Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber());

                    }
                }

                String displayStr = analyString(result);
                Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",display:" + displayStr);
                //解析并显示
                displayTextView.setText(displayStr);
            }
        });

        handler = new MyHandler();

        byte[] byteStr = new byte[]{12,13};
        String str1 = "1234";

        byte[] byteStr1 = hexString2Bytes(str1);
        String str2 = "";
        try {
            str2 = bytes2String(byteStr1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int d = Integer.valueOf("ABCF",16);   //d=255
        String result = Integer.toBinaryString(d);

        Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber()
                + "输入结束执行该方法" + ",encode:" + encode(str1) + ",byte[]:" + byteStr.toString()
                + ",str2:" + str2 + ",d:" + d + ",result:" + result);




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

    /*
     * 字符转换为字节
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /*
     * 16进制字符串转字节数组
     */
    public static byte[] hexString2Bytes(String hex) {

        if ((hex == null) || (hex.equals(""))){
            return null;
        }
        else if (hex.length()%2 != 0){
            return null;
        }
        else{
            hex = hex.toUpperCase();
            int len = hex.length()/2;
            byte[] b = new byte[len];
            char[] hc = hex.toCharArray();
            for (int i=0; i<len; i++){
                int p=2*i;
                b[i] = (byte) (charToByte(hc[p]) << 4 | charToByte(hc[p+1]));
            }
            return b;
        }

    }
    /*
     * 字节数组转字符串
     */
    public static String bytes2String(byte[] b) throws Exception {
        String r = new String (b,"UTF-8");
        return r;
    }
    /*
     * 16进制数字字符集
     */

    /*
     * 将字符串编码成16进制数字,适用于所有字符（包括中文）
     * "1234"==>"31323334"
     */
    public static String encode(String str)
    {
        String hexString="0123456789ABCDEF";
        //根据默认编码获取字节数组
        byte[] bytes=str.getBytes();
        StringBuilder sb=new StringBuilder(bytes.length*2);
        //将字节数组中每个字节拆解成2位16进制整数
        for(int i=0;i<bytes.length;i++)
        {
            sb.append(hexString.charAt((bytes[i]&0xf0)>>4));
            sb.append(hexString.charAt((bytes[i]&0x0f)>>0));
        }
        return sb.toString();
    }
    /*
     * 将16进制数字解码成字符串,适用于所有字符（包括中文）
     * "31323334"==>"1234"
     */
    public static String decode(String bytes)
    {
        String hexString="0123456789ABCDEF";
        ByteArrayOutputStream baos=new ByteArrayOutputStream(bytes.length()/2);
        //将每2位16进制整数组装成一个字节
        for(int i=0;i<bytes.length();i+=2)
            baos.write((hexString.indexOf(bytes.charAt(i))<<4 |hexString.indexOf(bytes.charAt(i+1))));
        return new String(baos.toByteArray());
    }

    /**
     * 将指定字符串src，以每两个字符分割转换为16进制形式
     * 如："2B44EFD9" --> byte[]{0x2B, 0x44, 0xEF, 0xD9}
     * @param src String
     * @return byte[]
     */
//    public static byte[] HexString2Bytes(String src){
//        byte[] ret = new byte[8];
//        byte[] tmp = src.getBytes();
//        for(int i=0; i<8; i++){
//            ret[i] = unitBytes(tmp[i*2], tmp[i*2+1]);
//        }
//        return ret;
//    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            String s = (String)msg.obj;
//            textView.setText(s);
        }
    }

    public class RoundBackgroundColorSpan extends ReplacementSpan {
        private int bgColor;
        private int textColor;
        public RoundBackgroundColorSpan(int bgColor, int textColor) {
            super();
            this.bgColor = bgColor;
            this.textColor = textColor;
        }
        @Override
        public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
            return ((int)paint.measureText(text, start, end)+60);
        }

        @Override
        public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
            int color1 = paint.getColor();
            paint.setColor(this.bgColor);
            canvas.drawRoundRect(new RectF(x, top+1, x + ((int) paint.measureText(text, start, end)+40), bottom-1), 15, 15, paint);
            paint.setColor(this.textColor);
            canvas.drawText(text, start, end, x+20, y, paint);
            paint.setColor(color1);
        }
    }
}
