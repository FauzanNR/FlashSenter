package com.dartoyo.flashsenter;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

/**
 * Implementation of App Widget functionality.
 */


public class ButtonFlash extends AppWidgetProvider {

    public static CaptureResult captureResult;
    public static String ACTION_CLICK = "CLICK";

    Boolean checkFlash;
    CameraManager cameraManager;
    String camID;
    ButtonOnOff buttonOnOff = new ButtonOnOff();


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // CoLog.d("TAG", "onClick: case 1");nstruct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.button_flash);
        views.setTextViewText(R.id.appwidget_text, widgetText);



        Intent intentButton = new Intent(context, ButtonFlash.class);
        intentButton.setAction(ButtonFlash.ACTION_CLICK);
        intentButton.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

        intentButton.putExtra(ACTION_CLICK, 1);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intentButton, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(ACTION_CLICK)) {
            checkFlash = context.getPackageManager().hasSystemFeature(context.getPackageManager().FEATURE_CAMERA_FLASH);
            if (!checkFlash) {
                Toast.makeText(context, "tidak ada flash", Toast.LENGTH_SHORT).show();
            } else {
                cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    try {
                        camID = cameraManager.getCameraIdList()[0];
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }
                Log.d("TAG", "onClick: test getSelect = "+buttonOnOff.getSelect());
                    if (buttonOnOff.OnOff() <= 1) {
                        try {
                            cameraManager.setTorchMode(camID, true);
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                        buttonOnOff.setSelect(1);
                    } else /* if (intent.getExtras().getInt(ACTION_CLICK) == 1 && OnOff() ==2)*/{
                        Log.d("TAG", "onClick: case 2");
                        try {
                            cameraManager.setTorchMode(camID, false);
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                        buttonOnOff.setSelect(2);
                    }
            }

        }
        super.onReceive(context, intent);
    }


}