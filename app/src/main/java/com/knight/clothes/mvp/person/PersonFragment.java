package com.knight.clothes.mvp.person;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.knight.clothes.R;
import com.knight.clothes.api.AppClient;
import com.knight.clothes.buses.UserBus;
import com.knight.clothes.dialogs.ChooseImage;
import com.knight.clothes.models.User;
import com.knight.clothes.utils.AppUtils;
import com.knight.clothes.utils.Router;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.rxjava3.functions.Consumer;

public class PersonFragment extends Fragment implements PersonContract.View {

    private LinearLayout llLoading;
    private LinearLayout llInformation;
    private LinearLayout llHistory;
    private LinearLayout llCoupons;
    private LinearLayout llAddress;
    private LinearLayout llTerms;
    private RelativeLayout rlAvatarProfile;
    private CircleImageView circleAvatar;
    private Button btnAuth;
    private TextView txtUserName;
    private View view;

    private PersonContract.Presenter presenter;

    private void init(View view) {
        this.view = view;
        llAddress = view.findViewById(R.id.ll_address_person);
        llInformation = view.findViewById(R.id.ll_information_person);
        llHistory = view.findViewById(R.id.ll_history_person);
        llTerms = view.findViewById(R.id.ll_terms_person);
        llCoupons = view.findViewById(R.id.ll_coupon_person);
        txtUserName = view.findViewById(R.id.txt_name_profile);
        llLoading = view.findViewById(R.id.ll_load_person);
        circleAvatar = view.findViewById(R.id.ci_circleAvatar);
        rlAvatarProfile = view.findViewById(R.id.rl_profile_image);
        btnAuth = view.findViewById(R.id.btn_auth_person);
        presenter = new PersonPresenter(getContext(), this);
        presenter.requestData();

        UserBus.subscribe(new Consumer<User>() {
            @Override
            public void accept(User user) throws Throwable {
                setData(user);
            }
        });
    }

    private boolean logged() {
        return UserBus.current() != null && UserBus.current().getName() != null;
    }

    private void listener() {
        llAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.navigator(logged() ? Router.ADDRESS : Router.LOGIN, getActivity(), null);
            }
        });
        llInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.navigator(logged() ? Router.INFORMATION : Router.LOGIN, getActivity(), null);
            }
        });
        rlAvatarProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (logged()) {
                    DialogFragment dialogFragment = new ChooseImage(getActivity(), presenter);
                    dialogFragment.show(getFragmentManager(), "dialog");
                }
            }
        });
        llHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.navigator(logged() ? Router.ORDER_HISTORY : Router.LOGIN, getActivity(), null);
            }
        });
        llTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.navigator(Router.TERMS, getActivity(), null);
            }
        });
        llCoupons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.navigator(logged() ? Router.COUPONS : Router.LOGIN, getActivity(), null);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person, container, false);
        init(view);
        listener();
        return view;
    }

    @Override
    public void showProgress() {
        llLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        llLoading.setVisibility(View.GONE);
    }

    @Override
    public void changeButtonAuth(boolean needLogin) {
        if (needLogin) {
            btnAuth.setText(getResources().getString(R.string.login_vi));
            btnAuth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Router.navigator(Router.LOGIN, getActivity(), null);
                }
            });
        } else {
            btnAuth.setText(getResources().getString(R.string.logout_vi));
            btnAuth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppUtils.showDialogConfirm(getResources().getString(R.string.confirm_logout), getActivity(), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AppUtils.hideDialog(getFragmentManager(), getResources().getString(R.string.dialog_confirm));
                            presenter.logout();
                        }
                    }, getFragmentManager());
                }
            });
        }
    }

    @Override
    public void setData(User user) {
        if (user != null && user.getName() != null) {
            changeButtonAuth(false);
            txtUserName.setText(user.getName());
            String url = "";
            if (user.getAvatar().startsWith("http")) url = user.getAvatar();
            else url = AppClient.url() + user.getAvatar();
            Glide.with(getContext()).load(url).into(circleAvatar);
        } else {
            changeButtonAuth(true);
            txtUserName.setText(R.string.person_name);
            circleAvatar.setImageResource(R.drawable.image_profile);
        }
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Log.e("ERR", throwable.getMessage());
        Snackbar.make(this.view.findViewById(R.id.fragment_person), getString(R.string.error_data),
                Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}