package testapp.sliubetskyi.location.core.presenters;

import android.support.annotation.CallSuper;

import testapp.sliubetskyi.location.core.model.ClientContext;
import testapp.sliubetskyi.location.core.model.modules.IAppState;
import testapp.sliubetskyi.location.core.model.modules.IPersistentData;
import testapp.sliubetskyi.location.core.ui.IView;

public class Presenter<V extends IView> {

    protected final ClientContext clientContext;
    private V view;

    public Presenter(ClientContext clientContext) {
        this.clientContext = clientContext;
    }

    @CallSuper
    public void onViewBound(V view) {
        this.view = view;
    }

    @CallSuper
    public void onViewUnbound(V view) {
        this.view = null;
    }

    public V getView() {
        return view;
    }

    IPersistentData getPersistentStorage() {
        return clientContext.getPersistentStorage();
    }

    IAppState getAppState() {
        return clientContext.getAppState();
    }
}
