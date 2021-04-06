package com.example.rentcar.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.rentcar.R;
import com.example.rentcar.ui.CarModelsFragment;
import com.example.rentcar.ui.SharedPrefUtil;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    SharedPrefUtil sharedPrefUtil;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        sharedPrefUtil=SharedPrefUtil.getInstance();
        final CardView minicardView=root.findViewById(R.id.mini_card);
        final CardView sedancardView=root.findViewById(R.id.seadan_card);
        final CardView backcardView=root.findViewById(R.id.backcar_card);

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
                minicardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sharedPrefUtil.saveString(SharedPrefUtil.CAR_TYPE,"Mini Cars");
                        CarModelsFragment fragment = new CarModelsFragment();
                        getFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).addToBackStack(null).commit();

                    }
                });
                sedancardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sharedPrefUtil.saveString(SharedPrefUtil.CAR_TYPE,"Hatchback Cars");
                        CarModelsFragment fragment = new CarModelsFragment();
                        getFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).addToBackStack(null).commit();

                    }
                });
                backcardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sharedPrefUtil.saveString(SharedPrefUtil.CAR_TYPE,"Mini Car");
                        CarModelsFragment fragment = new CarModelsFragment();
                        getFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).addToBackStack(null).commit();

                    }
                });
            }
        });
        return root;
    }
}