package demo.com.takeout.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import demo.com.takeout.R;
import demo.com.takeout.presenter.net.bean.HomeInfo;
import demo.com.takeout.presenter.net.bean.HomeItem;
import demo.com.takeout.presenter.net.bean.Promotion;
import demo.com.takeout.presenter.net.bean.Seller;
import demo.com.takeout.ui.activity.BusinessActivity;

/**
 * Created by HASEE on 2017/1/9.
 */
public class HomeRecyclerViewAdapter extends RecyclerView.Adapter {


    private Context mCtx;

    public HomeRecyclerViewAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    public static final int ITEM_HEAD = 0;
    public static final int ITEM_SELLER = 1;
    public static final int ITEM_DIV = 2;
    private HomeInfo data;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_HEAD) {
            //头条目
            View view = View.inflate(mCtx, R.layout.item_title, null);
            TitleViewHolder titleViewHolder = new TitleViewHolder(view);
            return titleViewHolder;
        } else if (viewType == ITEM_SELLER) {
            //商家条目
            View view = View.inflate(mCtx, R.layout.item_seller, null);
            SellerHolder sellerHolder = new SellerHolder(view);
            return sellerHolder;
        } else {
            //分割线条目
            View view = View.inflate(mCtx, R.layout.item_division, null);
            DivHolder divHolder = new DivHolder(view);
            return divHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //给holder中的所有的控件绑定数据
        if (position == 0) {
            //头
        } else if (data.getBody().get(position - 1).type == 0) {
            //一般条目,指向商家
//            ((SellerHolder)holder).setData(data.getBody().get(position-1));
            setSellerData(holder, data.getBody().get(position - 1));
            ((SellerHolder) holder).setPosition(position-1);
        } else {
            setDivData(holder, data.getBody().get(position - 1));
        }
    }

    private void setDivData(RecyclerView.ViewHolder holder, HomeItem homeItem) {
        ((DivHolder)holder).tv1.setText(homeItem.recommendInfos.get(0));
        ((DivHolder)holder).tv2.setText(homeItem.recommendInfos.get(1));
        ((DivHolder)holder).tv3.setText(homeItem.recommendInfos.get(2));
        ((DivHolder)holder).tv4.setText(homeItem.recommendInfos.get(3));
        ((DivHolder)holder).tv5.setText(homeItem.recommendInfos.get(4));
        ((DivHolder)holder).tv6.setText(homeItem.recommendInfos.get(5));
    }

    private void setSellerData(RecyclerView.ViewHolder holder, HomeItem homeItem) {
        //设置商家的名称
        ((SellerHolder)holder).tvTitle.setText(homeItem.seller.getName());
    }

    @Override
    public int getItemCount() {
        //依赖于此方法中返回的条目总数,集合.
        //列表中一共有3中条目类型,头条目,一般条目,分割线条目,条目总数 = 列表条目总数+1(头)
        if (data != null && data.getBody() != null && data.getHead() != null
                && data.getBody().size() > 0) {
            return data.getBody().size() + 1;
        }
        return 0;
    }
    //区分每一个索引位位置条目类型

    public void setData(HomeInfo data) {
        this.data = data;
        //通知数据适配器刷新
        notifyDataSetChanged();
    }

    //通过索引,获取服务器返回的type值,判断条目类型的状态码
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ITEM_HEAD;//返回头部view的状态码
        } else if (data.getBody().get(position - 1).type == 0) {
            return ITEM_SELLER;
        } else {
            return ITEM_DIV;
        }
    }

    class TitleViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.slider)
        SliderLayout slider;//实现顶部轮播图自定义控件

        public TitleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);

            ArrayList<Promotion> promotionList = data.getHead().getPromotionList();
            for (int i = 0; i < promotionList.size(); i++) {
                TextSliderView textSliderView = new TextSliderView(mCtx);
                // initialize a SliderLayout
                textSliderView
                        .description(promotionList.get(i).getInfo())//给轮播图的每一个图片,添加描述文字
                        .image(promotionList.get(i).getPic())//指定图片
                        .setScaleType(BaseSliderView.ScaleType.Fit);//ScaleType设置图片展示方式(fitxy  centercrop)

                //向SliderLayout控件的内部添加条目
                slider.addSlider(textSliderView);
            }
            //viewpager--->ImageView放置图片
            // viewpager 等价于 SliderLayout
            // TextSliderView 等价于 ImageView,只不过添加了一个显示文本功能

            //默认指定的动画类型
//            slider.setPresetTransformer(SliderLayout.Transformer.Accordion);
            slider.setPresetTransformer(SliderLayout.Transformer.Stack);
            //指定指示器的位置
            slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            //定义一个描述动画
            slider.setCustomAnimation(new DescriptionAnimation());
            //动画时长
            slider.setDuration(4000);
        }
    }

    class SellerHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.tvCount)
        TextView tvCount;
        @InjectView(R.id.tv_title)
        TextView tvTitle;
        @InjectView(R.id.ratingBar)
        RatingBar ratingBar;
        private int position;

        public SellerHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //区分点中的是那个条目,获取每一个条目的索引值
                    Intent intent = new Intent(mCtx, BusinessActivity.class);
                    //需要传递的对象所在的类,需要实现序列化接口
                    Seller seller = data.getBody().get(position).getSeller();
                    intent.putExtra("seller",seller);
                    mCtx.startActivity(intent);
                }
            });
        }
        //已经减过1的索引值,直接用此索引获取集合中的数据
        public void setPosition(int position) {
            this.position = position;
        }
    }

    class DivHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.tv_division_title)
        TextView tvDivisionTitle;
        @InjectView(R.id.tv1)
        TextView tv1;
        @InjectView(R.id.tv2)
        TextView tv2;
        @InjectView(R.id.tv3)
        TextView tv3;
        @InjectView(R.id.tv4)
        TextView tv4;
        @InjectView(R.id.tv5)
        TextView tv5;
        @InjectView(R.id.tv6)
        TextView tv6;

        public DivHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
