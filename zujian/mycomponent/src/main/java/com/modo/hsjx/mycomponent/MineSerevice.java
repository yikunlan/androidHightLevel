package com.modo.hsjx.mycomponent;

import android.content.Context;
import android.content.Intent;

import com.modo.hsjx.componentlibrary.IMineService;

/**
 * Created by hyk on 2019/4/28.
 */

public class MineSerevice implements IMineService {
    @Override
    public void launch(Context context, int userId) {
        Intent intent = new Intent(context,MineActivity.class);
        intent.putExtra("id",userId);
        context.startActivity(intent);
    }
}
