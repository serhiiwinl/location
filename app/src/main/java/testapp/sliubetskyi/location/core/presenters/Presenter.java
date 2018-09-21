package testapp.sliubetskyi.location.core.presenters;

import testapp.sliubetskyi.location.core.ClientContext;
import testapp.sliubetskyi.location.core.IView;

public class Presenter<V extends IView> {

    protected final ClientContext clientContext;
    protected V view;

    public Presenter(ClientContext clientContext) {
        this.clientContext = clientContext;
    }

    public void onViewBound(V view) {
        this.view = view;
    }

    public void onViewUnbound(V view) {
        this.view = null;
    }
}
