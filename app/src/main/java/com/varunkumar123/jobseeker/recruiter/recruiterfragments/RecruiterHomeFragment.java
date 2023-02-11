package com.varunkumar123.jobseeker.recruiter.recruiterfragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.varunkumar123.jobseeker.R;

import java.util.HashMap;


public class RecruiterHomeFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
SliderLayout sliderLayout;
HashMap<String,Integer>hash_file_maps;
CardView profile,searchTech,addjobs,updatejobs;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_recruiter_home, container, false);
        return root;

    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sliderLayout=view.findViewById(R.id.slider);
        hash_file_maps=new HashMap();
        slideImage();
        profile = view.findViewById(R.id.recruiter_profile_home);
        addjobs = view.findViewById(R.id.recruiter_addJobs_home);
        updatejobs = view.findViewById(R.id.recruiter_update_home);
        searchTech = view.findViewById(R.id.recruiter_searchbytech_home);
        //Profile onClickListener
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_nav_home_to_recruiterProfile);
            }
        });
        //AddJobs onClickListener
        addjobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_nav_home_to_addJobs);
            }
        });
        //update onClickListener
        updatejobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_nav_home_to_updateJobs);
            }
        });

        //search by tech onClickListener
        searchTech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_nav_home_to_searchByTechnology);
            }
        });

       /* openProfile=view.findViewById(R.id.home_profile_card);
        openProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecruiterProfile rp = new RecruiterProfile();
                FragmentTransaction transaction= getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment,rp);
                transaction.addToBackStack(null).commit();
            }
        });*/

    }
    private void slideImage(){
        hash_file_maps.put("Search jobs",R.drawable.jobsearch);
        hash_file_maps.put("Search jobs",R.drawable.search);
        hash_file_maps.put("Search jobs",R.drawable.searchjob);
        hash_file_maps.put("Recruiter profile",R.drawable.profile);
        hash_file_maps.put("Student profile ",R.drawable.profilejob);
        hash_file_maps.put("Hire Students",R.drawable.hire);

        for(String name:hash_file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(getActivity());
            textSliderView
                    .description(name)
                    .image(hash_file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra",name);
            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(4000);
        sliderLayout.addOnPageChangeListener(this);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}

