package testapp.sliubetskyi.core.presenters;

import java.lang.ref.WeakReference;

import testapp.sliubetskyi.core.model.modules.IAppState;
import testapp.sliubetskyi.core.model.modules.IClientContext;
import testapp.sliubetskyi.core.model.modules.location.ILocationManager;
import testapp.sliubetskyi.core.model.modules.IPermissionsManager;
import testapp.sliubetskyi.core.model.modules.IPersistentData;
import testapp.sliubetskyi.core.ui.IView;


public class Presenter<V extends IView> implements IPresenter<V> {

    final IClientContext clientContext;
    private WeakReference<V> view;
    private boolean isViewBound;

    public interface ViewActionRunnable<V extends IView> {
        void run(V view);
    }

    Presenter(IClientContext clientContext) {
        this.clientContext = clientContext;
    }

    final protected V view() {
        return (view == null) ? null : view.get();
    }

    @Override
    public final void bindView(V view) {
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

    /**
     * Just executes a {@link Runnable} object if view is bounded.
     * @param runnable executable runnable
     * @return true if action executed
     */
    protected boolean runViewAction(ViewActionRunnable<V> runnable) {
        V view = view();
        if (view != null)
            executeRunnable(runnable, view);
        return view != null && isViewBound;
    }

    /**
     * Executes a {@link ViewActionRunnable} object on UI tread if view is bounded.
     * @param runnable executable runnable
     * @return true if action executed
     */
    protected boolean runViewUIAction(final ViewActionRunnable<V> runnable) {
        final V view = view();
        if (view != null)
            view.postUIThread(() -> executeRunnable(runnable, view));
        return view != null;
    }

    /**
     * Executes runnable action on view if view is bound
     * @param runnable runnable action
     * @param view on this view will be executed runnable
     */
    private void executeRunnable(ViewActionRunnable<V> runnable, V view) {
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
