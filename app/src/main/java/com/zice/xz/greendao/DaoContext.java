package com.zice.xz.greendao;

import android.content.Context;
import android.content.ContextWrapper;

import java.io.File;

/**
 * Copyright (c) 2017,xxxxxx All rights reserved.
 * author：Z.kai
 * date：2017/8/21
 * description：
 */

public class DaoContext extends ContextWrapper {
    
    public DaoContext(Context base) {
        super(base);
    }

    @Override
    public File getFilesDir() {
        return super.getFilesDir();
    }

    @Override
    public File getDatabasePath(String name) {

        return super.getDatabasePath(name);
    }
}
