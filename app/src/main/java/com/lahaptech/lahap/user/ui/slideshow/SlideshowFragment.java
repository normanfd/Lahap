package com.lahaptech.lahap.user.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.lahaptech.lahap.R;
import com.lahaptech.lahap.user.menuproduct.DrinkFragment;
import com.lahaptech.lahap.user.menuproduct.FoodFragment;
import com.lahaptech.lahap.user.menuproduct.SelectMenuViewPager;
import com.lahaptech.lahap.user.menuproduct.SnackFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SlideshowFragment extends Fragment {

    @BindView(R.id.tab_layout_test)
    TabLayout tabLayout;
    @BindView(R.id.view_page_test)
    ViewPager viewPager;
    @BindView(R.id.buttonku)
    Button vu;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vu.setOnClickListener(view1 -> setFragment());

    }

    private void setFragment() {
        SelectMenuViewPager adapter = new SelectMenuViewPager(getChildFragmentManager());
        adapter.addFragment(new FoodFragment(), getResources().getString(R.string.food));
        adapter.addFragment(new DrinkFragment(), getResources().getString(R.string.drink));
        adapter.addFragment(new SnackFragment(), getResources().getString(R.string.snack));
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }


}