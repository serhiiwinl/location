package testapp.sliubetskyi.location.core.presenters;

import testapp.sliubetskyi.location.core.ClientContext;
import testapp.sliubetskyi.location.ui.MainActivityView;

public class MainActivityPresenter extends Presenter<MainActivityView> {
    public MainActivityPresenter(ClientContext clientContext) {
        super(clientContext);
    }

    public void openMapsActivity() {
        view.openMapsActivity();
    }
}
