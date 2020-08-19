package com.example.jobseeker;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.jobseeker.helperClasses.SliderAdapter;
import com.example.jobseeker.recruiter.RecruiterLogin;
import com.example.jobseeker.student.StudentLogin;

public class IntroSlider extends AppCompatActivity {
    ViewPager viewPager;
    LinearLayout dots_layout;
    SliderAdapter sliderAdapter;
    TextView[] dots;
    Button getStarted,nextBtn;
    Animation animation;
    int CurrentPos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_slider);
        //hooks
        viewPager = findViewById(R.id.slider);
        dots_layout = findViewById(R.id.dots);
        getStarted = findViewById(R.id.get_start_btn);
        nextBtn = findViewById(R.id.next_btn);
        // call adapter
        sliderAdapter = new SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);
        addDots(0);
        viewPager.addOnPageChangeListener(changeListener);
    }

    public void getStart(View view){


    }
    public void next(View view){
        viewPager.setCurrentItem(CurrentPos + 1);
    }

    private void addDots(int position){
        dots = new TextView[4];
        dots_layout.removeAllViews();
        for(int i=0;i<dots.length;i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);

            dots_layout.addView(dots[i]);
        }
        if(dots.length>0){
            dots[position].setTextColor(getResources().getColor(R.color.lightblue));
        }
    }
    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDots(position);
            CurrentPos =position;
            if(position ==0){
                getStarted.setVisibility(viewPager.INVISIBLE);

                nextBtn.setVisibility(viewPager.VISIBLE);
            }else if(position==1){
                getStarted.setVisibility(viewPager.INVISIBLE);
                nextBtn.setVisibility(viewPager.VISIBLE);
            }else if(position==2){
                animation = AnimationUtils.loadAnimation(IntroSlider.this,R.anim.button_anim);
                getStarted.setAnimation(animation);
                getStarted.setVisibility(viewPager.VISIBLE);
                getStarted.setText("I WANT TO HIRE");
                if(getStarted.getText().equals("I WANT TO HIRE"))
                {
                    getStarted.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(IntroSlider.this, RecruiterLogin.class));
                        }
                    });
                }
                nextBtn.setVisibility(viewPager.VISIBLE);
            }else{
                animation = AnimationUtils.loadAnimation(IntroSlider.this,R.anim.button_anim);
                getStarted.setAnimation(animation);
                getStarted.setText("I WANT TO WORK");
                if(getStarted.getText().equals("I WANT TO WORK"))
                {
                    getStarted.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(IntroSlider.this, StudentLogin.class));
                        }
                    });
                }
                nextBtn.setVisibility(viewPager.INVISIBLE);
                getStarted.setVisibility(viewPager.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
