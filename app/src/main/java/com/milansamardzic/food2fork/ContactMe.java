package com.milansamardzic.food2fork;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by ms on 1/18/15.
 */
public class ContactMe extends Fragment implements View.OnClickListener{

    EditText subject;
    EditText email;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.contact_me, container, false);

        Button btn = (Button) rootView.findViewById(R.id.sendEmail);
        btn.setOnClickListener(this);
        subject = (EditText) rootView.findViewById(R.id.tvSubject);
        email = (EditText) rootView.findViewById(R.id.tvEmail);

        return rootView;
    }

    @Override
    public void onClick(View v) {

        String subj = subject.getText().toString();
        String em = email.getText().toString();
        if (subj!="" && em!="") {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL, new String[]{"milan.samardzic@gmail.com"});
            i.putExtra(Intent.EXTRA_SUBJECT, subj);
            i.putExtra(Intent.EXTRA_TEXT, em);
            try {
                startActivity(Intent.createChooser(i, "Send mail..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
            }

        }
        else{
            Toast.makeText(getActivity(), "Please fill subject and email text", Toast.LENGTH_SHORT).show();
        }
    }
}