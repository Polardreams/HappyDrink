package polardreams.de.happydrink.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import polardreams.de.happydrink.R;


public class LocalDataAdapter extends RecyclerView.Adapter<LocalDataAdapter.ViewHolder> {
    /**
    private int[] images = {R.drawable.item1, R.drawable.item2, R.drawable.item3,
            R.drawable.item4, R.drawable.item5, R.drawable.item6, R.drawable.item7,
            R.drawable.item8, R.drawable.item9, R.drawable.item10};
**/

    private int[] images = {R.drawable.thema1, R.drawable.thema2, R.drawable.thema3, R.drawable.thema4, R.drawable.thema5, R.drawable.thema6, R.drawable.thema7, R.drawable.thema8, R.drawable.thema9,
            R.drawable.thema10, R.drawable.thema11, R.drawable.thema12, R.drawable.thema13, R.drawable.thema14, R.drawable.thema15, R.drawable.thema15};

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_over, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.imageView.setImageResource(images[position]);
        holder.imageView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(v.getContext(), "点击了" + v.getTag(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
