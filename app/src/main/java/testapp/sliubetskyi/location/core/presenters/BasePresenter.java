package testapp.sliubetskyi.location.core.presenters;

import testapp.sliubetskyi.location.core.state.ClientContext;
import testapp.sliubetskyi.location.ui.BaseView;

public class BasePresenter extends Presenter<BaseView> {
    public BasePresenter(ClientContext clientContext) {
        super(clientContext);
    }
}
