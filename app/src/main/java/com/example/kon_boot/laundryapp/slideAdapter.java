package com.example.kon_boot.laundryapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class slideAdapter extends PagerAdapter{

    Context context;
    LayoutInflater layoutInflater;

    public slideAdapter(Context context) {
        this.context = context;
    }

    public int[] sliderImages =
            {
                    R.drawable.order,
                    R.drawable.summary,
                    R.drawable.orderlist,


            };
    public String[] sliderHeadings =
            {
                    "Placing Order has never been this easy",
                    "Check out what you have ordered.",
                    "All the lists are uphere",


            };

    public String[] sliderDescription =
            {
                    "Choose From the list of services that we provide.We are here to help",
                    " Check the detailed Summary of what you have ordered and review the details",
                    "All the Items You hAve ordered are uphere",
                    "Some  text here"


            };

    @Override
    public int getCount() {
        return sliderHeadings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout) object;
    }

    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slides,container,false);

        ImageView slider_img = (ImageView) view.findViewById(R.id.introImg1);
        TextView headings = (TextView) view.findViewById(R.id.heading);
        TextView para = (TextView) view.findViewById(R.id.parag);
        Bitmap image = BitmapFactory.decodeResource(context.getResources(),sliderImages[position]);
        slider_img.setImageBitmap(image);
        headings.setText(sliderHeadings[position]);
        para.setText(sliderDescription[position]);

        container.addView(view);


        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}

