package com.example.jobseeker.helperClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.jobseeker.R;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    int images[] ={
            R.drawable.jobseekervector,
            R.drawable.notification,
            R.drawable.post_jobs_vector,
            R.drawable.search_job_intro,

    };
    int discription[] ={
            R.string.job_seeker_disc,
            R.string.job_notification_disc,
            R.string.job_post_disc,
            R.string.job_search_disc

    };

    @Override
    public int getCount() {
        return discription.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slides_layout,container,false);
// Hooks
        ImageView imageView = view.findViewById(R.id.slide_image);
        TextView textView = view.findViewById(R.id.slider_disc);
        imageView.setImageResource(images[position]);
        textView.setText(discription[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
