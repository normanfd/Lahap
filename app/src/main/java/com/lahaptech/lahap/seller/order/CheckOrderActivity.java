package com.lahaptech.lahap.seller.order;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.lahaptech.lahap.R;
import com.lahaptech.lahap.user.menuproduct.viewpager.SelectMenuViewPager;
import com.lahaptech.lahap.user.orderlocation.directorder.DirectOrderFormActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckOrderActivity extends AppCompatActivity {

    @BindView(R.id.tab_layout_order)
    TabLayout tabLayout;
    @BindView(R.id.view_pager_order)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_order);
        ButterKnife.bind(this);

        OrderViewPager adapter = new OrderViewPager(getSupportFragmentManager());
        adapter.addFragment(new DirectOrderFragment(), "Direct Order");
        adapter.addFragment(new IndirectOrderFragment(), "Indirect Order");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public class OrderViewPager extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragments = new ArrayList<>();
        private ArrayList<String> fragmentsTitle = new ArrayList<>();

        public OrderViewPager(FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentsTitle.get(position);
        }

        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            fragmentsTitle.add(title);
        }
    }


}

