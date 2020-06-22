package pl.android.licznikkalorii.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.List;

import pl.android.licznikkalorii.R;
import pl.android.licznikkalorii.model.FoodItem;

//przeczytaj o adapterze i viewholder pattern
public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    private List<FoodItem> items;
    private final CounterClickListener listener;

    public FoodAdapter(List<FoodItem> items, CounterClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_row, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.product.setText(items.get(position).getProduct().getProdukt());
        holder.counter.setText(items.get(position).getCounter().toString());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView product, counter;
        Button minus, plus;
        private WeakReference<CounterClickListener> listenerRef;

        ViewHolder(View itemView, CounterClickListener listener) {
            super(itemView);
            listenerRef = new WeakReference<>(listener);
            product = itemView.findViewById(R.id.tvProduct);
            counter = itemView.findViewById(R.id.tvCounter);
            minus = itemView.findViewById(R.id.btnMinus);
            plus = itemView.findViewById(R.id.btnPlus);
            plus.setOnClickListener(this);
            minus.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == plus.getId()) {
                listenerRef.get().onAddClicked(getAdapterPosition());
            } else {
                listenerRef.get().onMinusClicked(getAdapterPosition());
            }
        }
    }
}

