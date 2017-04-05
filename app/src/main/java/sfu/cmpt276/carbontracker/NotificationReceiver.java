package sfu.cmpt276.carbontracker;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import sfu.cmpt276.carbontracker.model.CarbonModel;

/**
 * Used for push notifications
 */

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        //if user has entered a journey for today but has not entered a bill for todays date
        if(CarbonModel.getInstance().numberOfJourneysOnCurrentDay()>=1 && !(CarbonModel.getInstance().enteredBillOnCurrentDay())){
            Intent intent1 = new Intent(context, AddBillActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 100, intent1, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context).
                    setContentIntent(pendingIntent).
                    setSmallIcon(R.drawable.logo).
                    setContentText(context.getString(R.string.pushNotificationBills)).
                    setContentTitle(context.getString(R.string.AddMoreBills)).

                    setAutoCancel(true);
            notificationManager.notify(100, builder.build());


        }
        else {
            Intent intent1 = new Intent(context, SelectVehicleActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 100, intent1, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context).
                    setContentIntent(pendingIntent).
                    setSmallIcon(R.drawable.logo).
                    setContentText(context.getString(R.string.pushNotificationJourneys, CarbonModel.getInstance().numberOfJourneysOnCurrentDay())).
                    setContentTitle(context.getString(R.string.AddMoreJourneys)).

                    setAutoCancel(true);
            notificationManager.notify(100, builder.build());
        }

    }

}