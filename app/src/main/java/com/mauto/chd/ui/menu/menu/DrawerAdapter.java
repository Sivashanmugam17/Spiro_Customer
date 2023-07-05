package com.mauto.chd.ui.menu.menu;

import android.content.Context;
import android.content.Intent;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.cabilyhandyforalldinedoo.chd.ui.sidemenus.Faq;
import com.mauto.chd.ui.sidemenus.WalletPage;

import org.jetbrains.annotations.NotNull;
//import com.cabilyhandyforalldinedoo.chd.ui.sidemenus.batteryswapping;
//import com.cabilyhandyforalldinedoo.chd.ui.sidemenus.earningspage;


/**
 * Created by yarolegovich on 25.03.2017.
 */

@SuppressWarnings({"rawtypes", "ConstantConditions"})
public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.ViewHolder> {

    private List<DrawerItem> items;
    private Map<Class<? extends DrawerItem>, Integer> viewTypes;
    private SparseArray<DrawerItem> holderFactories;
    private Context context;
    private OnItemSelectedListener listener;

    public DrawerAdapter(List<DrawerItem> items, Context context) {
        this.context=context;
        this.items = items;
        this.viewTypes = new HashMap<>();
        this.holderFactories = new SparseArray<>();

        processViewTypes();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder holder = holderFactories.get(viewType).createViewHolder(parent);
        holder.adapter = this;
        return holder;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        items.get(position).bindViewHolder(holder);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position== 0){
                    Intent intent_otppage = new Intent(context, WalletPage.class);
                    context.startActivity(intent_otppage);
                }
                if (position== 1){
//                    Intent intent_otppage = new Intent(context, batteryswapping.class);
//                    context.startActivity(intent_otppage);
                }



            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return viewTypes.get(items.get(position).getClass());
    }

    private void processViewTypes() {
        int type = 0;
        for (DrawerItem item : items) {
            if (!viewTypes.containsKey(item.getClass())) {
                viewTypes.put(item.getClass(), type);
                holderFactories.put(type, item);
                type++;
            }
        }
    }

    public void setSelected(int position) {
        DrawerItem newChecked = items.get(position);
        if (!newChecked.isSelectable()) {
            return;
        }

        for (int i = 0; i < items.size(); i++) {
            DrawerItem item = items.get(i);
            if (item.isChecked()) {
                item.setChecked(false);
                notifyItemChanged(i);
                break;
            }
        }

        newChecked.setChecked(true);
        notifyItemChanged(position);

        if (listener != null) {
            listener.onItemSelected(position);
        }
    }

    public void setListener(OnItemSelectedListener listener) {
        this.listener = listener;
    }

    static abstract class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private DrawerAdapter adapter;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            adapter.setSelected(getAdapterPosition());
        }
    }

    public interface OnItemSelectedListener {
        @NotNull
//        Object tvShow = new Object();

        void onItemSelected(int position);
    }
}
