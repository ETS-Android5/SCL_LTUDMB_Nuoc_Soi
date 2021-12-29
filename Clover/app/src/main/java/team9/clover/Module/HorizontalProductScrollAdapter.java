package team9.clover.Module;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

import team9.clover.Model.HorizontalProductScroll;
import team9.clover.ProductDetailActivity;
import team9.clover.R;

public class HorizontalProductScrollAdapter extends RecyclerView.Adapter<HorizontalProductScrollAdapter.ViewHolder> {

    List<HorizontalProductScroll> horizontalProductScrollList;

    public HorizontalProductScrollAdapter(List<HorizontalProductScroll> horizontalProductScrollList) {
        this.horizontalProductScrollList = horizontalProductScrollList;
    }

    @NonNull
    @Override
    public HorizontalProductScrollAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalProductScrollAdapter.ViewHolder holder, int position) {
        String resource = horizontalProductScrollList.get(position).getImage();
        String title = horizontalProductScrollList.get(position).getTitle();
        String stuff = horizontalProductScrollList.get(position).getStuff();
        String price = horizontalProductScrollList.get(position).getPrice();

        holder.setProductImage(resource);
        holder.setProductTitle(title);
        holder.setProductStuff(stuff);
        holder.setProductPrice(price);
    }

    @Override
    public int getItemCount() {
        if (horizontalProductScrollList.size() > Config.NUMBER_PRODUCT_HORIZONTAL_VIEW) {
            return 8;
        } else {
            return horizontalProductScrollList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private MaterialTextView title, stuff, price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.ivProduct);
            title = itemView.findViewById(R.id.mtvProductTitle);
            stuff = itemView.findViewById(R.id.mtvSize);
            price = itemView.findViewById(R.id.mtvTotal);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent productDetailIntent = new Intent(itemView.getContext(), ProductDetailActivity.class);
                    itemView.getContext().startActivity(productDetailIntent);
                }
            });
        }

        private void setProductImage(String resorce) {
            Glide.with(itemView.getContext())
                    .load(resorce)
                    .apply(new RequestOptions().placeholder(R.drawable.product1))
                    .into(image);
        }

        private void setProductTitle(String productTitle) {
            title.setText(productTitle);
        }

        private void setProductStuff(String productStuff) {
            stuff.setText(productStuff);
        }

        private void setProductPrice(String productPrice) {
            price.setText(productPrice + " đ");
        }
    }
}
