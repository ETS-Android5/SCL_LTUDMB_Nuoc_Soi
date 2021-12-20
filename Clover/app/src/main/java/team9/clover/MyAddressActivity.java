package team9.clover;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import team9.clover.Model.AddressesMOdel;
import team9.clover.Module.AddressesAdapter;

public class MyAddressActivity extends AppCompatActivity {

    RecyclerView myAddressRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(getColor(R.color.violet_deep));
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Địa chỉ của tôi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myAddressRecyclerView = findViewById(R.id.addresses_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myAddressRecyclerView.setLayoutManager(layoutManager);

        List<AddressesMOdel> addressesMOdelList = new ArrayList<>();
        addressesMOdelList.add(new AddressesMOdel("Mạnh Cường", "224, Nguyễn Văn Cừ phường 4 quận 5", "1234567890"));
        addressesMOdelList.add(new AddressesMOdel("Mạnh Cường", "224, Nguyễn Văn Cừ phường 4 quận 5", "1234567890"));
        addressesMOdelList.add(new AddressesMOdel("Mạnh Cường", "224, Nguyễn Văn Cừ phường 4 quận 5", "1234567890"));
        addressesMOdelList.add(new AddressesMOdel("Mạnh Cường", "224, Nguyễn Văn Cừ phường 4 quận 5", "1234567890"));
        addressesMOdelList.add(new AddressesMOdel("Mạnh Cường", "224, Nguyễn Văn Cừ phường 4 quận 5", "1234567890"));
        addressesMOdelList.add(new AddressesMOdel("Mạnh Cường", "224, Nguyễn Văn Cừ phường 4 quận 5", "1234567890"));
        addressesMOdelList.add(new AddressesMOdel("Mạnh Cường", "224, Nguyễn Văn Cừ phường 4 quận 5", "1234567890"));
        addressesMOdelList.add(new AddressesMOdel("Mạnh Cường", "224, Nguyễn Văn Cừ phường 4 quận 5", "1234567890"));
        addressesMOdelList.add(new AddressesMOdel("Mạnh Cường", "224, Nguyễn Văn Cừ phường 4 quận 5", "1234567890"));

        AddressesAdapter adapter = new AddressesAdapter(addressesMOdelList);
        myAddressRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}