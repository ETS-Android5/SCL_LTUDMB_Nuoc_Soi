package team9.clover.Module;

import android.app.Activity;
import android.content.Intent;
import android.widget.Adapter;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputLayout;

import org.apache.commons.validator.routines.EmailValidator;

import java.util.Collections;
import java.util.Comparator;

import team9.clover.Adapter.HomePageAdapter;
import team9.clover.ErrorActivity;
import team9.clover.Fragment.HomeFragment;
import team9.clover.Model.DatabaseModel;
import team9.clover.Model.HomePageModel;
import team9.clover.R;

public class Reuse {

    /*
     * Thiết lập animation khi start một activity
     * */
    public static void startActivity(Activity activity) {
        activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    /*
     * Dùng để thay đổi các fragment
     * */
    public static void setFragment(FragmentActivity activity, Fragment fragment, FrameLayout layout, int animFrom) {
        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        if (animFrom == -1) // from left
            fragmentTransaction.setCustomAnimations(R.anim.slide_from_left, R.anim.slide_to_right);
        else if (animFrom == 1) // from right
            fragmentTransaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left);

        fragmentTransaction.replace(layout.getId(), fragment);
        fragmentTransaction.commit();
    }

    /*
     * Kiểm tra email user nhập vào có hợp lệ không
     * */
    public static String emailValid(TextInputLayout til, Activity activity) {
        String email = til.getEditText().getText().toString();
        til.setErrorEnabled(true);
        if (email.isEmpty()) {
            til.setError(activity.getString(R.string.email_empty)); email = "";
        } else if (!EmailValidator.getInstance().isValid(email)) {
            til.setError(activity.getString(R.string.email_invalid)); email = "";
        } else {
            til.setErrorEnabled(false);
            til.setError("");
        }

        return email;
    }

    /*
     * Kiểm tra tên user nhập vào có hợp lệ không
     * */
    public static String fullNameValid(TextInputLayout til, Activity activity) {
        String fullName = til.getEditText().getText().toString();
        til.setErrorEnabled(true);
        if (fullName.isEmpty()) {
            til.setError(activity.getString(R.string.name_empty)); fullName = "";
        } else {
            til.setErrorEnabled(false);
            til.setError("");
        }

        return fullName;
    }

    /*
     * Kiểm tra password user nhập vào có hợp lệ không
     * */
    public static String passwordValid(TextInputLayout til, Activity activity) {
        String password = til.getEditText().getText().toString();
        til.setErrorEnabled(true);
        if (password.isEmpty()) {
            til.setError(activity.getString(R.string.password_empty)); password = "";
        } else if (password.length() < 8) {
            til.setError(activity.getString(R.string.password_short)); password = "";
        } else if (password.length() > 18) {
            til.setError(activity.getString(R.string.password_long)); password = "";
        } else {
            til.setErrorEnabled(false);
            til.setError("");
        }

        return password;
    }

    public static void goToErrorActivity(Activity activity) {
        activity.finishAffinity();
        activity.startActivity(new Intent(activity, ErrorActivity.class));
        Reuse.startActivity(activity);
    }
}
