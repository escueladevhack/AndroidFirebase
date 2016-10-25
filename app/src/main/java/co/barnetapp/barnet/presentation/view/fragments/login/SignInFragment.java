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
import co.barnetapp.barnet.presentation.presenter.ISignInPresenter;
import co.barnetapp.barnet.presentation.presenter.SignInPresenter;

public class SignInFragment extends Fragment implements ISignInView {

    private ProgressDialog progressDialog;
    private ISignInPresenter signInPresenter;
    private OnSignInFragmentListener mListener;

    @Bind(R.id.txtEmail)
    EditText txtEmail;

    @Bind(R.id.txtPassword)
    EditText txtPassword;

    public SignInFragment() {
        // Required empty public constructor
    }

    public static SignInFragment newInstance() {
        SignInFragment fragment = new SignInFragment();
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
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        ButterKnife.bind(this, view);

        signInPresenter = new SignInPresenter(this);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSignInFragmentListener) {
            mListener = (OnSignInFragmentListener) context;
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

    @OnClick(R.id.btnSignIn)
    @Override
    public void clickSignIn() {
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();

        signInPresenter.signIn(email, password);
    }

    @OnClick(R.id.btnSignUp)
    @Override
    public void clickSignUp() {
        mListener.goToSignUp();
    }

    @Override
    public void showAuthSuccesful() {
        Toast.makeText(getContext(), getResources().getString(R.string.welcome), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAuthError(Exception error) {
        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    public interface OnSignInFragmentListener {

        void goToSignUp();

    }
}
