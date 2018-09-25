package testapp.sliubetskyi.location.core.presenters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

import testapp.sliubetskyi.location.core.model.ClientContext;
import testapp.sliubetskyi.location.core.model.modules.IAppState;
import testapp.sliubetskyi.location.core.model.modules.ILocationManager;
import testapp.sliubetskyi.location.core.model.modules.IPermissionsManager;
import testapp.sliubetskyi.location.core.model.modules.IPersistentData;
import testapp.sliubetskyi.location.core.ui.IView;


public class Presenter<V extends IView> implements IPresenter<V> {

    final ClientContext clientContext;
    private WeakReference<V> view;
    private boolean isViewBound;

    public interface ViewActionRunnable<V extends IView> {
        void run(V view);
    }

    public Presenter(ClientContext clientContext) {
        this.clientContext = clientContext;
    }

    @Nullable
    final protected V view() {
        return (view == null) ? null : view.get();
    }

    @Override
    public final void bindView(@NonNull V view) {
        this.view = new WeakReference<>(view);
        isViewBound = true;
        onViewBound(view);
    }

    @Override
    public final void unbindView() {
        if (isViewBound) {
            onViewUnbound(view());
            isViewBound = false;
            view.clear();
        }
    }

    protected void onViewBound(V view) { }

    protected void onViewUnbound(V view) { }

    final protected boolean isViewBound() {
        return isViewBound;
    }

    protected boolean runViewAction(ViewActionRunnable<V> runnable) {
        V view = view();
        if (view != null)
            executeRunnable(runnable, view);
        return view != null && isViewBound;
    }

    /**
     * Executes runnable action on view if view is bound
     * @param runnable runnable action
     * @param view on this view will be executed runnable
     */
    private void executeRunnable(@NonNull ViewActionRunnable<V> runnable, @NonNull V view) {
        if (isViewBound)
            runnable.run(view);
    }

    IPersistentData getPersistentStorage() {
        return clientContext.getPersistentStorage();
    }

    IAppState getAppState() {
        return clientContext.getAppState();
    }

    ILocationManager getLocationManager() {
        return clientContext.getLocationManager();
    }

    IPermissionsManager getPermissionsManager() {
        return clientContext.getPermissionsManager();
    }
}
