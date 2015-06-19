package com.lockscreen;

import android.app.PendingIntent;
import android.app.admin.DevicePolicyManager;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.WindowManager;
import android.widget.RemoteViews;

import com.lockscreen.LockScreen.Controller;

public class MyWidget extends AppWidgetProvider{
	private static final String broadcastString = "com.lockscreen.MyWidget";
	DevicePolicyManager mDPM;
    ComponentName mDeviceAdminSample;
	WindowManager				mWM;		// WindowManager
	WindowManager.LayoutParams	mWMParams;	// WindowManager����
	
	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		super.onDeleted(context, appWidgetIds);
		Log.i(broadcastString, "--------------onDeleted----------------");
	}

	@Override
	public void onDisabled(Context context) {
		super.onDisabled(context);
		Log.i(broadcastString, "--------------onDisabled----------------");
	}

	@Override
	public void onEnabled(Context context) {
		super.onEnabled(context);
		Log.i(broadcastString, "--------------onEnabled----------------");
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		
		Log.i(broadcastString, "--------------onReceive----------------");
		if(broadcastString.equals(intent.getAction())){
			RemoteViews remotesViews = new RemoteViews(context.getPackageName(), R.layout.widget);
			remotesViews.setTextViewText(R.id.btn, "����");
			
			AppWidgetManager manager = AppWidgetManager.getInstance(context);
			ComponentName componentName = new ComponentName(context, MyWidget.class);
			
			//manager.updateAppWidget(componentName, remotesViews);
			closeScreen(context);
		}
		super.onReceive(context, intent);
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		Log.i(broadcastString, "--------------onUpdate----------------");
		Intent i = new Intent();
		i.setAction(broadcastString);
		PendingIntent intent = PendingIntent.getBroadcast(context, 0, i, 0);
		
		   RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);  
	       remoteViews.setOnClickPendingIntent(R.id.btn, intent);  
	          
	       // �������е�appWidget   
	       appWidgetManager.updateAppWidget(appWidgetIds, remoteViews); 
		
	}  
	
	public void closeScreen(Context c){
		mDPM = (DevicePolicyManager) c.getSystemService(Context.DEVICE_POLICY_SERVICE);
        //LockScreen �̳��� DeviceAdminReceiver
        mDeviceAdminSample = new ComponentName(c,
                        LockScreen.class);
        //�õ���ǰ�豸��������û�м���
        boolean active = mDPM.isAdminActive(mDeviceAdminSample);
        if (!active) { 
                //���û�м���Ļ�����ȥ��ʾ�û������һ�����г���ʱ��
       	 	Intent i = new Intent(c,Controller.class); 
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				c.startActivity(i);
        }else{
                //����Ѿ�����Ļ�����ִ����������
                mDPM.lockNow();
        }
	}

	
	
	
}
