package com.vivekvishwanath.bitters.login;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.vivekvishwanath.bitters.R;
import com.vivekvishwanath.bitters.apis.FirebaseAuthDao;

public class RegisterFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextRetypePassword;
    private Button buttonRegister;
    private Context context;

    private View.OnClickListener registerListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            buttonRegisterClicked();
        }
    };

    private void buttonRegisterClicked() {
        if (checkFields()) {
            FirebaseAuthDao.registerAccount(editTextEmail.getText().toString()
                    , editTextPassword.getText().toString(), editTextName.getText().toString());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (FirebaseAuthDao.getAccountCreated()) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Snackbar.make(getView(), R.string.successful_registration
                                            , Snackbar.LENGTH_LONG).show();
                                    getActivity().onBackPressed();
                                }
                            });
                            return;
                        }
                    }
                }
            }).start();
        }
    }

    public RegisterFragment() {
        // Required empty public constructor
    }

    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
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
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();

        editTextName = view.findViewById(R.id.edit_text_name_register);
        editTextEmail = view.findViewById(R.id.edit_text_email_register);
        editTextPassword = view.findViewById(R.id.edit_text_password_register);
        editTextRetypePassword = view.findViewById(R.id.edit_text_retype_password_register);

        buttonRegister = view.findViewById(R.id.button_register);
        buttonRegister.setOnClickListener(registerListener);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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

    private boolean checkFields() {
        if (TextUtils.isEmpty(editTextName.getText().toString())
                || TextUtils.isEmpty(editTextEmail.getText().toString())
                || TextUtils.isEmpty(editTextPassword.getText().toString())
                || TextUtils.isEmpty(editTextRetypePassword.getText().toString())) {
            return false;
        } else if (!editTextPassword.getText().toString()
                .equals(editTextRetypePassword.getText().toString())) {
            return false;
        }
        return true;
    }
}
