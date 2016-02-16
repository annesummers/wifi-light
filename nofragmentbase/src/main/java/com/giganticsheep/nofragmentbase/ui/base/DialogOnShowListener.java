package com.giganticsheep.nofragmentbase.ui.base;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.widget.Button;

import com.giganticsheep.nofragmentbase.R;

/**
 * Created by anne on 15/02/16.
 */
public class DialogOnShowListener implements DialogInterface.OnShowListener { private final AlertDialog dialog;
    private final Context context;

    public DialogOnShowListener(Context context, AlertDialog dialog) {
        this.dialog = dialog;
        this.context = context;
    }

    @Override
    public void onShow(DialogInterface dialogInterface) {
      //  FontType.replaceFonts(context, (ViewGroup) dialog.getWindow().getDecorView());

        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        int neededColor = typedValue.data;
        //int neededColor = ContextCompat.getColor(context, R.color.main_primary);

        Button button = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        if (button != null) {
            button.setTextColor(neededColor);
        }

        button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        if (button != null) {
            button.setTextColor(neededColor);
        }
    }
}
