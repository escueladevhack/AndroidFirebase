package co.barnetapp.barnet.presentation.view.fragments.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.barnetapp.barnet.R;
import co.barnetapp.barnet.presentation.presenter.ISignUpPresenter;
import co.barnetapp.barnet.presentation.presenter.SignUpPresenter;

public class SignUpFragment extends Fragment implements ISignUpView {

    private ProgressDialog progressDialog;
    private ISignUpPresenter signUpPresenter;

    @Bind(R.id.txtRegisterName)
    EditText txtRegisterName;

    @Bind(R.id.txtRegisterEmail)
    EditText txtRegisterEmail;

    @Bind(R.id.txtRegisterPassword)
    EditText txtRegisterPassword;

    public OnSignUpFragmentListener mListener;

    public SignUpFragment() {
        // Required empty public constructor
    }

    public static SignUpFragment newInstance() {
        SignUpFragment fragment = new SignUpFragment();
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
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        ButterKnife.bind(this, view);

        signUpPresenter = new SignUpPresenter(this);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSignUpFragmentListener) {
            mListener = (OnSignUpFragmentListener) context;
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

    @Override
    public void showLoading() {
        progressDialog = ProgressDialog.show(getActivity(), "",
                getResources().getString(R.string.loading));
    }

    @Override
    public void hideLoading() {
        progressDialog.dismiss();
    }

    @OnClick(R.id.btnSignUp)
    @Override
    public void clickRegister() {
        String name = txtRegisterName.getText().toString();
        String email = txtRegisterEmail.getText().toString();
        String password = txtRegisterPassword.getText().toString();
        signUpPresenter.signUp(name, email, password);
    }

    @Override
    public void goHome() {

    }

    @Override
    public void showAuthMessage() {
        Toast.makeText(getContext(), getResources().getString(R.string.welcome), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorAuthMessage(Exception error) {
        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
    public interface OnSignUpFragmentListener {


    }
}
