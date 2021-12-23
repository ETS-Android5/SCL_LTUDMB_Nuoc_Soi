package team9.clover.Model;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import team9.clover.ErrorActivity;
import team9.clover.Module.CategoryAdapter;
import team9.clover.Adapter.HomePageAdapter;
import team9.clover.Module.Reuse;

public class DatabaseModel {

    public static FirebaseUser USER = null;
    public static FirebaseFirestore firebaseFirestore = null;
    public static FirebaseAuth firebaseAuth = null;

    public static List<CategoryModel> categoryModelList = new ArrayList<>();
    public static List<HomePageModel> homePageModelList = new ArrayList<>();

    public static void loadCarousel(HomePageAdapter adapter, Activity activity) {
        if (firebaseFirestore == null) firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection(CarouselModel.class.getSimpleName())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<CarouselModel> carouselModelList = new ArrayList<>();
                            for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                carouselModelList.add(snapshot.toObject(CarouselModel.class));
                            }

                            homePageModelList.add(new HomePageModel(carouselModelList));
                            adapter.notifyDataSetChanged();
                        } else {
                            activity.finish();
                            activity.startActivity(new Intent(activity, ErrorActivity.class));
                            Reuse.startActivity(activity);
                        }
                    }
                });
    }

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

    public static void loadCategory(CategoryAdapter adapter, Activity activity) {
        if (firebaseFirestore == null) firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection(CategoryModel.class.getSimpleName())
                .orderBy("index")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                categoryModelList.add(snapshot.toObject(CategoryModel.class));
                            }

                            // adapter báo cho RecyclerView => cập nhật giao diện
                            adapter.notifyDataSetChanged();
                        } else {
                            activity.finish();
                            activity.startActivity(new Intent(activity, ErrorActivity.class));
                            Reuse.startActivity(activity);
                        }
                    }
                });
    }

    /*
     * Đăng xuất khỏi current user
     * */
    public static void signOut() {
        if (USER != null) firebaseAuth.signOut();
    }
}


/*
*         for (int i = 0; i < 7; ++i) {
            firebaseStorage.getReference(CarouselModel.FIRESTORAGE+"/"+i+ ".jpg")
                    .getDownloadUrl()
                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                firebaseFirestore.collection(CarouselModel.class.getSimpleName())
                                        .add(new CarouselModel(task.getResult().toString()));

                            }
                        }
                    });
        }
* */