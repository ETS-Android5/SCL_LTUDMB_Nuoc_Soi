package team9.clover.Fragment;

import static team9.clover.Model.DatabaseModel.masterUser;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import team9.clover.Adapter.FavoriteAdapter;
import team9.clover.Model.DatabaseModel;
import team9.clover.Model.ProductModel;
import team9.clover.R;

public class FavoriteFragment extends Fragment {

    public static int ID = 4;
    public static boolean isChanged;

    RecyclerView mContainer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_favorite, container, false);

        isChanged = false;
        mContainer = view.findViewById(R.id.rvContainer);
        setData();

        return view;
    }

    public void setData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mContainer.setLayoutManager(layoutManager);

        DatabaseModel.loadProduct("id", DatabaseModel.masterUser.getFavorite())
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<ProductModel> products = new ArrayList<>();
                            for (QueryDocumentSnapshot snapshot : task.getResult())
                                products.add(snapshot.toObject(ProductModel.class));

                            FavoriteAdapter adapter = new FavoriteAdapter(products);
                            mContainer.setAdapter(adapter);
                        }
                    }
                });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isChanged)
            DatabaseModel.updateMasterUser();
    }
}