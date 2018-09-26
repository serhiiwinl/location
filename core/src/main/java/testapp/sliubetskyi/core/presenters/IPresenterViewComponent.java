package testapp.sliubetskyi.core.presenters;

public interface IPresenterViewComponent<P,V> {
    V getIView();
    P createPresenter();
}
