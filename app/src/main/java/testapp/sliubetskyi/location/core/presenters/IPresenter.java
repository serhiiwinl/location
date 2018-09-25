package testapp.sliubetskyi.location.core.presenters;

import android.support.annotation.NonNull;

import testapp.sliubetskyi.location.core.ui.IView;

public interface IPresenter<V extends IView> {
    void bindView(@NonNull V view);
    void unbindView();
}