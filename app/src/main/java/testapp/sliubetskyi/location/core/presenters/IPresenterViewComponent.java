package testapp.sliubetskyi.location.core.presenters;

import android.support.annotation.NonNull;

public interface IPresenterViewComponent<P,V> {
    @NonNull
    V getIView();
    P createPresenter();
}
