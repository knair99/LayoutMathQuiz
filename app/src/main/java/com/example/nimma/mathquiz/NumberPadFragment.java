package com.example.nimma.mathquiz;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class NumberPadFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_numberpad, container, false);
    }

    //Fragment to fragment communication
    //1. declare an interface
    OnAnswerInput mCallBack;

    //2. define this as an interface
    public interface OnAnswerInput{
        //Make a method that needs to be implemented in the interface user (the activity)
        public void sendInput(String strInput);
    }

    //3. On the attach event, make sure the activity to which this attaches
    //implements the callback interface
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //Make sure the call back is implemented, or throw exception
        try{
            mCallBack = (OnAnswerInput) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "must implement the interface OnAnswerInput");
        }

     }

    //4. Next, use the interface method to send the data across
    public void fragmentBtnOnClickNum(View view, String btnText) {
        //All we need to do here is send the digit pressed to QuestionFragment
        mCallBack.sendInput(btnText);
    }

    //5. Try not to leak
    @Override
    public void onDetach() {
        mCallBack = null;
        super.onDetach();
        //Rest of  the steps in QuestionActivity and Question fragment
    }

}