package com.example.zlq_pc.miudemo.base;

import com.tencent.tinker.loader.app.TinkerApplication;

/**
 * Created by ZLQ-PC on 2018/5/10.
 */

public class BaseApp extends TinkerApplication {

    public BaseApp() {
        super(7, "tinker.sample.android.app.SampleApplicationLike", "com.tencent.tinker.loader.TinkerLoader", false);
    }

}
