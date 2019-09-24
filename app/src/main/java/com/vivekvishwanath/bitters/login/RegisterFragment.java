package com.vivekvishwanath.bitters.login;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vivekvishwanath.bitters.R;
import com.vivekvishwanath.bitters.apis.FirebaseAuthDao;

public class RegisterFragment extends Fragment {

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
                    , editTextPassword.getText().toString(), editTextName.getText().toString()
                    , new FirebaseAuthDao.RegistrationCallback() {
                        @Override
                        public void onRegistrationResult(boolean result) {
                            if (result) {
                                Snackbar.make(getView(), R.string.successful_registration
                                        , Snackbar.LENGTH_LONG).show();
                                getActivity().onBackPressed();
                            } 
                        }
                    });
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
    
    private boolean checkFields() {
        if (TextUtils.isEmpty(editTextName.getText().toString())
                || TextUtils.isEmpty(editTextEmail.getText().toString())
                || TextUtils.isEmpty(editTextPassword.getText().toString())
                || TextUtils.isEmpty(editTextRetypePassword.getText().toString())) {
            Toast.makeText(getContext(), "Make sure all fields are complete",
                    Toast.LENGTH_LONG).show();
            return false;
        } else if (!editTextPassword.getText().toString()
                .equals(editTextRetypePassword.getText().toString())) {
            Toast.makeText(getContext(), "Passwords have to match",
                    Toast.LENGTH_LONG).show();
            return false;
        } else if(editTextPassword.getText().toString().length() < 8
                || editTextPassword.getText().toString().length() > 64) {
            Toast.makeText(getContext(), "Password has to be between 8 and 64 characters",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
