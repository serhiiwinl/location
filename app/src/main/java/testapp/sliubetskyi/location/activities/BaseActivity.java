package testapp.sliubetskyi.location.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import testapp.sliubetskyi.location.App;
import testapp.sliubetskyi.location.core.ClientContext;
import testapp.sliubetskyi.location.core.presenters.Presenter;
import testapp.sliubetskyi.location.ui.BaseView;

public abstract class BaseActivity<P extends Presenter<V>, V extends BaseView> extends FragmentActivity implements BaseView {

    protected P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(presenter == null)
            presenter = createPresenter();
    }

    @Override
    protected void onStart() {
        presenter.onViewBound((V) this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        presenter.onViewUnbound((V) this);
        super.onStop();
    }

    abstract P createPresenter();

    App getApp() {
        return (App) getApplication();
    }

    ClientContext getClientContext() {
        return getApp().getClientContext();
    }


}
