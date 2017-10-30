package demo.com.takeout.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.flipboard.bottomsheet.BottomSheetLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;
import demo.com.takeout.R;
import demo.com.takeout.presenter.net.bean.Seller;
import demo.com.takeout.ui.adapter.BusinessFragmentPagerAdapter;

/**
 * Created by HASEE on 2017/1/10.
 */
public class BusinessActivity extends BaseActivity {

    @InjectView(R.id.ib_back)
    ImageButton ibBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.ib_menu)
    ImageButton ibMenu;
    @InjectView(R.id.tabs)
    TabLayout tabs;
    @InjectView(R.id.vp)
    ViewPager vp;
    @InjectView(R.id.bottomSheetLayout)
    BottomSheetLayout bottomSheetLayout;
    @InjectView(R.id.imgCart)
    ImageView imgCart;
    @InjectView(R.id.tvSelectNum)
    TextView tvSelectNum;
    @InjectView(R.id.tvCountPrice)
    TextView tvCountPrice;
    @InjectView(R.id.tvDeliveryFee)
    TextView tvDeliveryFee;
    @InjectView(R.id.tvSendPrice)
    TextView tvSendPrice;
    @InjectView(R.id.tvSubmit)
    TextView tvSubmit;
    @InjectView(R.id.bottom)
    LinearLayout bottom;
    @InjectView(R.id.fl_Container)
    FrameLayout flContainer;

    private String[] stringArry = new String[]{"商品","评价","商家"};
    private Seller seller;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bussiness);

        seller = (Seller) getIntent().getSerializableExtra("seller");

        ButterKnife.inject(this);
        //顶部的tabLayout+viewPager联动效果
        initTab();
        //填充viewPager
        initViewPager();
        //选项卡和viewpager绑定
        tabs.setupWithViewPager(vp);
    }

    private void initViewPager() {
        //PagerAdapger----->viewpager中直接指定添加的view对象
        //FragmentPagerAdapter----->viewpager中添加的是fragment oncreateView方法中返回的view对象
        BusinessFragmentPagerAdapter businessFragmentPagerAdapter
                = new BusinessFragmentPagerAdapter(getSupportFragmentManager(),stringArry,seller);
        vp.setAdapter(businessFragmentPagerAdapter);
    }

    private void initTab() {

        for (int i = 0; i < stringArry.length; i++) {
            tabs.addTab(tabs.newTab().setText(stringArry[i]));
        }
    }

    /**
     * @param imageView 添加在帧布局中的图片,添加位置已经通过setX和setY指定过了
     * @param width 添加控件宽度
     * @param height    添加控件的高度
     */
    public void addImageView(ImageView imageView, int width, int height) {
        flContainer.addView(imageView,width,height);
    }

    /**
     * @return  返回购物图片所在屏幕中的x,y的坐标
     */
    public int[] getShopCartLocation(){
        int[] shopCart = new int[2];
        imgCart.getLocationInWindow(shopCart);
        return shopCart;
    }

    /**
     * @param imageView 动画结束以后,移除图片
     */
    public void removeImageView(ImageView imageView) {
        if (imageView!=null){
            flContainer.removeView(imageView);
        }
    }
}
