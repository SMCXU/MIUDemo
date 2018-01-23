package com.example.customrecycle.view;


import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.TextView;

import com.example.customrecycle.R;
import com.example.customrecycle.activitys.setting.SettingsActivity;

import java.util.HashMap;

/**
 * 一个自定义的TextView控件.
 * 加载 /system/font/** 下面的字库.
 * @author hailongqiu
 *
 */
public class TextViewWithTTF extends TextView {

    private static final TypeFaceMgr SFontMgr = new TypeFaceMgr();
    
	public TextViewWithTTF(Context context) {
		super(context);
	}

    public TextViewWithTTF(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TextViewWithTTF);
        setTypeface(SFontMgr.getTypeface(array.getString(R.styleable.TextViewWithTTF_ttf_name)));
        array.recycle();
    }

//    @Override
//    public boolean dispatchKeyEvent(KeyEvent event) {
//        final boolean uniqueDown = event.getAction() == KeyEvent.ACTION_DOWN;
//        int keyCode = event.getKeyCode();
//        //点击tabHost的上建，跳转到设置界面
//        if (uniqueDown&&keyCode==KeyEvent.KEYCODE_DPAD_UP){
//            getContext().startActivity(new Intent(getContext(), SettingsActivity.class));
//        }
//
//        return super.dispatchKeyEvent(event);
//    }

    public TextViewWithTTF(Context context, String aTTFName)
    {
        super(context);
        setTypeface(SFontMgr.getTypeface(aTTFName));
    }
    
    public void setFont(String aTTFName) {
    	setTypeface(SFontMgr.getTypeface(aTTFName));
    }
}


class TypeFaceMgr
{
    HashMap<String, Typeface> mTypefaces;
    public TypeFaceMgr()
    {
        mTypefaces = new HashMap<String, Typeface>();
    }
    public Typeface getTypeface(String aTTFName)
    {
        if(mTypefaces.containsKey(aTTFName))
        {
            return mTypefaces.get(aTTFName);
        }
        else
        {
            Typeface font;
            try
            {
                font = Typeface.createFromFile("/system/fonts/"+aTTFName);
            }
            catch (Exception e) 
            {
                e.printStackTrace();
                return null;
            }
           
            mTypefaces.put(aTTFName, font);
            return font;
        }
    }
}