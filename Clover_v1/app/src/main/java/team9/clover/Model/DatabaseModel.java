package team9.clover.Model;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Collections;

import team9.clover.LogInActivity;
import team9.clover.MainActivity;
import team9.clover.Module.Reuse;
import team9.clover.SplashActivity;

public class DatabaseModel {

    public static FirebaseUser USER;
    public static FirebaseFirestore firebaseFirestore = null;
    public static FirebaseAuth firebaseAuth = null;

    /*
     * Load người dùng hiện tại trên thiết bị
     * */
    public static void getCurrentUser() {
        USER = FirebaseAuth.getInstance().getCurrentUser();
    }


    /*
    * Thêm thông tin new user vào Firestore với key là email
    * */
    public static Task addNewUser(String email, UserModel newUser) {
        if (firebaseFirestore == null) firebaseFirestore = FirebaseFirestore.getInstance();
        return firebaseFirestore.collection(UserModel.class.getSimpleName()).document(email).set(newUser);
    }

    /*
    * Đăng kí new user này với Firebase authentication
    * */
    public static Task signUp(String email, String password) {
        if (firebaseAuth == null) firebaseAuth = FirebaseAuth.getInstance();
        return firebaseAuth.createUserWithEmailAndPassword(email, password);
    }

    /*
    * Đăng nhập bằng Firebase authentication
    * */
    public static Task signIn(String email, String password) {
        if (firebaseAuth == null) firebaseAuth = FirebaseAuth.getInstance();
        return firebaseAuth.signInWithEmailAndPassword(email, password);
    }

    /*
    * Dùng để gửi email reset password cho user
    * */
    public static Task resetPassword(String email) {
        if (firebaseAuth == null) firebaseAuth = FirebaseAuth.getInstance();
        return firebaseAuth.sendPasswordResetEmail(email);
    }

    /*
    * Đăng xuất khỏi current user
    * */
    public static void signOut() {
        if (USER != null) firebaseAuth.signOut();
    }
}