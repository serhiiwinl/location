package testapp.sliubetskyi.location.android.components;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import testapp.sliubetskyi.location.R;
import testapp.sliubetskyi.location.android.App;

public class NotificationHelper extends ApplicationComponent {

    /**
     * The Id of the default notification channel used by this application to display all
     * notifications.
     * <p>The value of this constant is {@value}.</p>
     */
    private static final String CHANNEL_ID = "default_channel_id";

    private final NotificationManager notificationManager;

    public NotificationHelper(App app) {
        super(app);
        this.notificationManager = ((NotificationManager) app.getSystemService(Context.NOTIFICATION_SERVICE));
    }

    public void showNotificationAtNotificationBar(Context context, String contentTitle, int iconId,
                                                  String contentText, PendingIntent intent,
                                                  int notificationId) {
        Notification notification = createBuilder(context, iconId, contentTitle, contentText, intent)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setAutoCancel(true)
                .build();
        notificationManager.notify(notificationId, notification);
    }

    /**
     * Creates a notification that can be used with foreground service.
     * <p>This method will also create a default notification channel on the platforms that
     * require it.</p>
     *
     * @param context the context of the service.
     * @param intent a {@link PendingIntent} to send when the notification is clicked.
     * @param distance tracked distance
     * @return newly-created notification instance, never {@code null}.
     */
    public Notification buildForegroundNotification(Context context, PendingIntent intent, long distance) {
        final String appName = context.getString(R.string.app_name);

        createNotificationChannel(context);
        return createBuilder(context, R.drawable.notification_icon, appName,
                context.getString(R.string.service_status_online, appName, distance), intent).build();
    }

    /**
     * Updates foreground service notification if already visible or shows new notification.
     * @param notificationId the updated notification id.
     * @param context the context of the service.
     * @param intent a {@link PendingIntent} to send when the notification is clicked.
     * @param distance current tracked distance.
     */
    public void updateForegroundNotification(int notificationId, Context context, PendingIntent intent, long distance) {
        this.notificationManager.notify(notificationId, buildForegroundNotification(context, intent, distance));
    }

    /**
     * Creates default notification channel that will be used to display all notifications of the
     * application.
     * <p>The notification channel is only created when the application is running on platform with
     * API level 26 or higher (Android 8.0 Oreo). This method is a no-op on earlier platforms.</p>
     *
     * @param context the context to retrieve string resources.
     */
    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            final NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    context.getString(R.string.notification_channel_name),
                    NotificationManager.IMPORTANCE_DEFAULT);

            notificationManager.createNotificationChannel(channel);
        }
    }

    /**
     * Helper method to create notification builder with common parameters specified as method
     * arguments.
     * <p>This method will also create a default notification channel on the platforms that
     * require it.</p>
     *
     * @param context
     *         the context that the notifications built by the builder being created will use.
     * @param iconId
     *         a resource Id in the application's package of the drawable to use when displaying
     *         notification.
     * @param title
     *         text message to be used as a title (first row) of the notification.
     * @param text
     *         text (second row) of the notification, in a standard notification.
     * @param intent
     *         a {@link PendingIntent} to send when the notification is clicked.
     * @return returns the base version of the notification builder, which can either be used to
     * build the notification right away, or customize it further before building it.
     */
    private NotificationCompat.Builder createBuilder(Context context, int iconId, String title,
                                                     String text, PendingIntent intent) {
        createNotificationChannel(context);
        return new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(iconId)
                .setContentTitle(title)
                .setContentText(text)
                .setContentIntent(intent);
    }
}
