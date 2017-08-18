package com.vpaliy.melophile.ui.user;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vpaliy.melophile.R;
import com.vpaliy.melophile.ui.base.BaseAdapter;
import com.vpaliy.melophile.ui.base.bus.RxBus;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressWarnings("WeakerAccess")
public class MediaAdapter extends BaseAdapter<MediaAdapter.CategoryWrapper>{

    private static final int BLANK_TYPE=0;
    private static final int MEDIA_TYPE=2;

    private View blank;

    public MediaAdapter(@NonNull Context context, @NonNull RxBus rxBus){
        super(context,rxBus);
    }


    public class BlankViewHolder extends GenericViewHolder{
        BlankViewHolder(View itemView) {
            super(itemView);
        }
        @Override
        public void onBindData(){}
    }

    class TypeViewHolder extends GenericViewHolder
            implements View.OnClickListener{

        @BindView(R.id.playlists) RecyclerView list;
        @BindView(R.id.title) TextView title;
        @BindView(R.id.more) TextView more;

        TypeViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
            list.addItemDecoration(new DividerItemDecoration(itemView.getContext(), LinearLayoutManager.VERTICAL));
            list.setNestedScrollingEnabled(false);
            title.setOnClickListener(this);
            more.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
        }

        @Override
        public void onBindData(){
            CategoryWrapper wrapper=at(getAdapterPosition()-1);
            list.setAdapter(wrapper.adapter);
            title.setText(wrapper.text);
            more.setTextColor(wrapper.color);
        }
    }

    @Override
    public void onBindViewHolder(GenericViewHolder holder, int position) {
        holder.onBindData();
    }

    @Override
    public GenericViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case BLANK_TYPE:
                return new BlankViewHolder(blank=inflate(R.layout.layout_blank,parent));
            default:
                return new TypeViewHolder(inflate(R.layout.adapter_user_media,parent));
        }
    }

    @Override
    public BaseAdapter<CategoryWrapper> addItem(CategoryWrapper item) {
        data.add(item);
        notifyDataSetChanged();
        return this;
    }

    @Override
    public int getItemViewType(int position) {
        return position==0 ? BLANK_TYPE:MEDIA_TYPE;
    }

    public View getBlank() {
        return blank;
    }

    @Override
    public int getItemCount() {
        return data.size()+1;
    }

    public static class CategoryWrapper {
        private final String text;
        private final RecyclerView.Adapter<?> adapter;
        private final int color;

        private CategoryWrapper(@NonNull String text, @NonNull RecyclerView.Adapter<?> adapter, int color){
            this.text=text;
            this.adapter=adapter;
            this.color=color;
        }

        public static CategoryWrapper wrap(@NonNull String text, @NonNull RecyclerView.Adapter<?> adapter, int color){
            return new CategoryWrapper(text,adapter,color);
        }
    }
}