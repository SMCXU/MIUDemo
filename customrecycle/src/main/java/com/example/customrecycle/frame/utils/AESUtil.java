package com.example.customrecycle.frame.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Base64;


import com.example.customrecycle.base.BaseApp;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 加密方式:Aes,模式 CBC 填充模式 0
 */
public class AESUtil {
    private static final String transformation = "AES/CBC/NoPadding";//

    /**
     * 加密
     *
     * @param data
     * @return
     */
    public static String encrypt(String data) {
        ///加密key
        String key = PreferencesUtils.getString(BaseApp.getContext(), KEY.FLAG_KEY, "");
        //向量
        String iv = PreferencesUtils.getString(BaseApp.getContext(), KEY.FLAG_IV, "");
        if (StringUtils.isEmpty(key) && StringUtils.isEmpty(key)) {
            MyToast.showToast("加密key为空，请重新登录");
            Intent intent = new Intent();
            ComponentName cn = new ComponentName("com.zbxn", "com.zbxn.main.activity.MainActivity");
            intent.setComponent(cn);
            intent.putExtra("exit", "exit");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//这句必须加
            Context context = BaseApp.getContext();
            context.startActivity(intent);
            return data;
        }
        try {
            Cipher cipher = Cipher.getInstance(transformation);
            int blockSize = cipher.getBlockSize();
            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);
            return Base64.encodeToString(encrypted, Base64.DEFAULT).replace("\0", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 解密
     *
     * @param data
     * @return
     */
    public static String desEncrypt(String data) {
        ///加密key
        String key = PreferencesUtils.getString(BaseApp.getContext(), KEY.FLAG_KEY, "");
        //向量
        String iv = PreferencesUtils.getString(BaseApp.getContext(), KEY.FLAG_IV, "");
        if (StringUtils.isEmpty(key) && StringUtils.isEmpty(key)) {
            MyToast.showToast("加密key为空，请重新登录");
            Intent intent = new Intent();
            ComponentName cn = new ComponentName("com.zbxn", "com.zbxn.main.activity.MainActivity");
            intent.setComponent(cn);
            intent.putExtra("exit", "exit");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//这句必须加
            Context context = BaseApp.getContext();
            context.startActivity(intent);
            return data;
        }
        try {
            byte[] encrypted1 = Base64.decode(data, Base64.DEFAULT);
            Cipher cipher = Cipher.getInstance(transformation);
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original).replace("\0", "");
            return originalString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
