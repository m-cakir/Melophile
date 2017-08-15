package com.vpaliy.melophile.ui.playlists;

import com.vpaliy.domain.model.Playlist;
import com.vpaliy.melophile.App;
import com.vpaliy.melophile.R;
import com.vpaliy.melophile.di.component.DaggerViewComponent;
import com.vpaliy.melophile.di.module.PresenterModule;
import com.vpaliy.melophile.ui.base.BaseFragment;
import java.util.List;
import android.os.Bundle;
import android.support.v4.graphics.drawable.TintAwareDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import butterknife.BindView;
import javax.inject.Inject;
import static com.vpaliy.melophile.ui.playlists.PlaylistsContract.Presenter;

public class PlaylistsFragment extends BaseFragment
        implements PlaylistsContract.View{

    private static final String TAG=PlaylistsFragment.class.getSimpleName();

    private Presenter presenter;
    private CategoryAdapter adapter;

    @BindView(R.id.categories)
    protected RecyclerView categories;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_music,container,false);
        bind(root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(view!=null){
            adapter=new CategoryAdapter(getContext(),rxBus);
            categories.setAdapter(adapter);
            presenter.start();
        }
    }

    @Override
    public void showEmptyMessage() {
        //TODO show empty message
    }

    @Override
    public void showErrorMessage() {
        //TODO show error message
    }

    @Override
    public void showPlaylists(@NonNull String category, @NonNull List<Playlist> playlists) {
        Log.d(TAG,Integer.toString(playlists.size()));
        PlaylistAdapter playlistAdapter=new PlaylistAdapter(getContext(),rxBus);
        playlistAdapter.setData(playlists);
        adapter.addItem(CategoryAdapter.CategoryWrapper.wrap(category,playlistAdapter,1));
    }

    @Inject @Override
    public void attachPresenter(@NonNull Presenter presenter) {
        this.presenter=presenter;
        presenter.attachView(this);
    }

    @Override
    public void initializeDependencies() {
        DaggerViewComponent.builder()
                .applicationComponent(App.appInstance().appComponent())
                .presenterModule(new PresenterModule())
                .build().inject(this);
    }
}