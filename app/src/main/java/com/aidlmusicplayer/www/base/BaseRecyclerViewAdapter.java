package com.aidlmusicplayer.www.base;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private OnItemClickListener<T> mOnItemClickListener;
    private OnItemLongClickListener<T> mOnLongClickListener;
    private List<T> mDatum = new ArrayList<>();

    public BaseRecyclerViewAdapter(List<T> mDatas) {
        if (!mDatas.isEmpty()) {
            this.mDatum = mDatas;
        }
    }


    public List<T> getDatum() {
        return mDatum;
    }

    public interface OnItemClickListener<T> {
        void onItemClick(View view, int position, T info);
    }

    public interface OnItemLongClickListener<T> {
        void onLongClick(View view, int position, T info);
    }


    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        this.mOnItemClickListener = listener;
    }

    public void setOnLongClickListener(OnItemLongClickListener<T> listener) {
        this.mOnLongClickListener = listener;
    }

    public void addAll(List<T> datas) {
        mDatum.addAll(datas);
        this.notifyDataSetChanged();
    }

    public void removeAll() {
        if (mDatum.size() != 0) {
            mDatum.clear();
        }
        this.notifyDataSetChanged();
    }


    public int getItemCount() {
        return mDatum.size();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(getItemLayoutId(), parent, false));
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final T data = mDatum.get(position);
        if (data == null) {
            return;
        }
        BaseViewHolder baseViewHolder = (BaseViewHolder) holder;
        onBind(baseViewHolder.getViewHolder(), position, data);
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v, position, data);
                }
            });
        }
        if (mOnLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnLongClickListener.onLongClick(v, position, data);
                    return true;
                }
            });
        }
    }

    protected abstract int getItemLayoutId();

    protected abstract void onBind(ViewHolder holder, int position, T data);


    public class BaseViewHolder extends RecyclerView.ViewHolder {

        private final ViewHolder viewHolder;

        public BaseViewHolder(View itemView) {
            super(itemView);
            viewHolder = ViewHolder.getViewHolder(itemView);
        }

        public ViewHolder getViewHolder() {
            return viewHolder;
        }
    }

    public static class ViewHolder {
        private final SparseArray<View> viewHolder;
        private final View view;

        private ViewHolder(View view) {
            this.view = view;
            viewHolder = new SparseArray<>();
            view.setTag(viewHolder);
        }

        public <T extends View> T get(int id) {
            View childView = viewHolder.get(id);
            if (childView == null) {
                childView = view.findViewById(id);
                viewHolder.put(id, childView);
            }
            return (T) childView;
        }

        public static ViewHolder getViewHolder(View view) {
            ViewHolder viewHolder = (ViewHolder) view.getTag();
            if (viewHolder == null) {
                viewHolder = new ViewHolder(view);
                view.setTag(viewHolder);
            }
            return viewHolder;
        }

        public View getConvertView() {
            return view;
        }

        public TextView getTextView(int id) {
            return get(id);
        }

        public ImageView getImageView(int id) {
            return get(id);
        }

        public void setTextView(int id, CharSequence charSequence) {
            getTextView(id).setText(charSequence);
        }
    }
}
