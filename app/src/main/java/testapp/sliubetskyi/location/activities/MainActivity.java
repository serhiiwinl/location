package testapp.sliubetskyi.location.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import testapp.sliubetskyi.location.R;
import testapp.sliubetskyi.location.core.presenters.MainActivityPresenter;
import testapp.sliubetskyi.location.ui.MainActivityView;

public class MainActivity extends BaseActivity<MainActivityPresenter, MainActivityView> implements
        MainActivityView, View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.open_maps);
        button.setOnClickListener(this);
    }

    @Override
    MainActivityPresenter createPresenter() {
        return new MainActivityPresenter(getClientContext());
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.open_maps)
            presenter.openMapsActivity();
    }

    @Override
    public void openMapsActivity() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}
