package com.example.yonathan.ballandhole;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import yuku.ambilwarna.AmbilWarnaDialog;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Setting.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Setting#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Setting extends Fragment implements View.OnClickListener {
    Button changeBallColor,changeCanvasColor,changeBallSize;
    int ballColor,canvasColor, defaultColor;
    EditText ballSize;
    private FragmentListener listener;

    public Setting() {
        // Required empty public constructor
    }
    public static Setting newInstance() {
        Setting fragment = new Setting();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_setting, container, false);
        this.changeBallColor=v.findViewById(R.id.bt_ballColor);
        this.changeCanvasColor=v.findViewById(R.id.bt_backColor);
        this.changeBallSize=v.findViewById(R.id.btn_gantiUkuran);
        this.ballSize= v.findViewById(R.id.et_ballSize);
        this.defaultColor= ResourcesCompat.getColor(getResources(), R.color.Hole, null);
        this.changeBallSize.setOnClickListener(this);
        this.changeCanvasColor.setOnClickListener(this);
        this.changeBallColor.setOnClickListener(this);

        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentListener) {
            this.listener = (FragmentListener) context;
        } else {
            throw new ClassCastException(context.toString() + "must implements FragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        if(v==this.changeBallColor){
            this.ballColor=openColorPicker();
        }
        else if(v==this.changeBallSize){
            this.ballSize.getText();
        }
        else if(v==this.changeCanvasColor){
            this.canvasColor=openColorPicker();
        }

    }

    public int openColorPicker(){
        final int[] warna = new int[1];
        AmbilWarnaDialog colorPicker= new AmbilWarnaDialog(this.getContext(), defaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
               warna[0] =color;
            }
        });
        colorPicker.show();
        return warna[0];
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
