package demo.com.takeout.ui.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import demo.com.takeout.presenter.net.bean.Seller;
import demo.com.takeout.ui.fragment.BaseFragment;
import demo.com.takeout.ui.fragment.GoodsFragment;
import demo.com.takeout.ui.fragment.SellerFragment;
import demo.com.takeout.ui.fragment.SuggestFragment;

/**
 * Created by HASEE on 2017/1/10.
 */
public class BusinessFragmentPagerAdapter extends FragmentPagerAdapter{

    private Seller seller;
    private  String[] mStringArry;
    private  ArrayList<Fragment> fragmentList;

    public BusinessFragmentPagerAdapter(FragmentManager fm, String[] stringArry, Seller seller) {
        super(fm);
        this.mStringArry = stringArry;
        fragmentList = new ArrayList<>();
        this.seller = seller;
    }

    @Override
    public Fragment getItem(int position) {
        BaseFragment baseFragment = null;
        switch (position){
            case 0:
                baseFragment = new GoodsFragment();
                break;
            case 1:
                baseFragment = new SuggestFragment();
                break;
            case 2:
                baseFragment = new SellerFragment();
                break;
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable("seller",seller);
        baseFragment.setArguments(bundle);

        if (!fragmentList.contains(baseFragment)){
            fragmentList.add(baseFragment);
        }
        return baseFragment;
    }

    @Override
    public int getCount() {
        return mStringArry.length;
    }

    //viewpagerIndiator+viewpager

    @Override
    public CharSequence getPageTitle(int position) {
        return mStringArry[position];
    }
}
