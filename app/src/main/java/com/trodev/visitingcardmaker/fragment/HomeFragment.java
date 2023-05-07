package com.trodev.visitingcardmaker.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.trodev.visitingcardmaker.DigitalVisitingCardActivity;
import com.trodev.visitingcardmaker.MainActivity;
import com.trodev.visitingcardmaker.MakeDigitalVisitingCardActivity;
import com.trodev.visitingcardmaker.MakeVisitingCardActivity;
import com.trodev.visitingcardmaker.R;


public class HomeFragment extends Fragment {
    private TextView bvcmTv, portfolioTv, digitalTv, cvTv;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_home, container, false);

        bvcmTv = view.findViewById(R.id.bvcmTv);
     //   portfolioTv = view.findViewById(R.id.portfolioTv);
        digitalTv = view.findViewById(R.id.digitalTv);
      //  cvTv = view.findViewById(R.id.cvTv);

        bvcmTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MakeVisitingCardActivity.class));
                Toast.makeText(getActivity(), "Business Visiting Card Maker", Toast.LENGTH_SHORT).show();
            }
        });

/*        portfolioTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), DigitalVisitingCardActivity.class));
                Toast.makeText(getActivity(), "Portfolio QR Maker", Toast.LENGTH_SHORT).show();
            }
        });*/

        digitalTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MakeDigitalVisitingCardActivity.class));
                Toast.makeText(getActivity(), "Digital Visiting Card Maker", Toast.LENGTH_SHORT).show();
            }
        });

/*        cvTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Coming soon....!", Toast.LENGTH_SHORT).show();
            }
        });*/

       return view;
    }
}