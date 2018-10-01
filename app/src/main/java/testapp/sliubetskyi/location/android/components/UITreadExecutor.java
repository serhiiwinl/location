package testapp.sliubetskyi.location.android.components;

import android.os.Handler;
import android.os.Looper;


import testapp.sliubetskyi.core.model.modules.IUITreadExecutor;
import testapp.sliubetskyi.location.android.App;

public class UITreadExecutor extends ApplicationComponent implements IUITreadExecutor {

    private final Handler handler;

    UITreadExecutor(App app) {
        super(app);
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void postUIThread(Runnable runnable) {
        postUIThread(runnable, 0);
    }

    @Override
    public void postUIThread(Runnable runnable, long delay) {
        handler.postDelayed(runnable, delay);
    }

    @Override
    public void cancelUIThreadAction(Runnable runnable) {
        handler.removeCallbacks(runnable);
    }
}
