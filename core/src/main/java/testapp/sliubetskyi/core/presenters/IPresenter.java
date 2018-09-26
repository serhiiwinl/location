package testapp.sliubetskyi.core.presenters;

import testapp.sliubetskyi.core.ui.IView;

public interface IPresenter<V extends IView> {
    void bindView(V view);
    void unbindView();
}