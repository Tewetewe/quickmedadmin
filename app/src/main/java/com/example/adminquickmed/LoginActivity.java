package com.example.adminquickmed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ViewPager viewPager = findViewById(R.id.viewPager);
        LoginActivity.AuthenticationPagerAdapter pagerAdapter = new LoginActivity.AuthenticationPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new com.example.adminquickmed.LoginFragment());
        pagerAdapter.addFragment(new com.example.adminquickmed.RegisterFragment());
        viewPager.setAdapter(pagerAdapter);

    }
    class AuthenticationPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragmentList = new ArrayList<>();

        public AuthenticationPagerAdapter( FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int i) {
            return fragmentList.get(i);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        void addFragment(Fragment fragment){
            fragmentList.add(fragment);
        }
    }

}
