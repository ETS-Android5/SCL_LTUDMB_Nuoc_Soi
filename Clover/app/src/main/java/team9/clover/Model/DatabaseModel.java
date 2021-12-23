package team9.clover.Model;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import team9.clover.ErrorActivity;
import team9.clover.Adapter.CategoryAdapter;
import team9.clover.Adapter.HomePageAdapter;
import team9.clover.Module.Reuse;
import team9.clover.R;

public class DatabaseModel {

    public static FirebaseUser USER = null;
    public static FirebaseFirestore firebaseFirestore = null;
    public static FirebaseAuth firebaseAuth = null;

    public static List<CategoryModel> categoryModelList = new ArrayList<>();
    public static List<HomePageModel> homePageModelList = new ArrayList<>();


    private static void loadGridProduct(HomePageAdapter adapter, Activity activity) {
        firebaseFirestore.collection(ProductModel.class.getSimpleName())
                .whereEqualTo("screen", (long) HomePageModel.GRID_PRODUCT_VIEW_TYPE)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<ProductModel> productModelList = new ArrayList<>();
                            for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                productModelList.add(ProductModel.castFromFirestore(snapshot));
                            }

                            homePageModelList.add(new HomePageModel(HomePageModel.GRID_PRODUCT_VIEW_TYPE, R.drawable.icon_discount, "Khuyến mãi hôm nay", productModelList));
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

    /*
    * Load icon, title trên thanh category toolbar
    * */
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
    * Load ảnh carousel lên homepage
    * */
    public static void loadHomePage(HomePageAdapter adapter, Activity activity) {
        if (firebaseFirestore == null) firebaseFirestore = FirebaseFirestore.getInstance();
        loadCarousel(adapter, activity);
    }

    private static void loadCarousel(HomePageAdapter adapter, Activity activity) {
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
                            loadBanner(adapter, activity);
                        } else {
                            activity.finish();
                            activity.startActivity(new Intent(activity, ErrorActivity.class));
                            Reuse.startActivity(activity);
                        }
                    }
                });
    }

    private static void loadBanner(HomePageAdapter adapter, Activity activity) {
        firebaseFirestore.collection(BannerModel.class.getSimpleName())
                .document("0")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    BannerModel bannerModel = task.getResult().toObject(BannerModel.class);
                    homePageModelList.add(new HomePageModel(bannerModel));
                    loadSliderProduct(adapter, activity);
                } else {
                    activity.finish();
                    activity.startActivity(new Intent(activity, ErrorActivity.class));
                    Reuse.startActivity(activity);
                }
            }
        });
    }

    private static void loadSliderProduct(HomePageAdapter adapter, Activity activity) {
        firebaseFirestore.collection(ProductModel.class.getSimpleName())
                .whereEqualTo("screen", (long) HomePageModel.SLIDER_PRODUCT_VIEW_TYPE)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<ProductModel> productModelList = new ArrayList<>();
                            for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                productModelList.add(ProductModel.castFromFirestore(snapshot));
                            }

                            homePageModelList.add(new HomePageModel(HomePageModel.SLIDER_PRODUCT_VIEW_TYPE, R.drawable.icon_cart_check, "Bán nhiều nhất", productModelList));
                            loadGridProduct(adapter, activity);
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

    public static void setData() {


        int start = 9; // index của sản [hẩm bắt đầu ghi
        long screen = 4; // các sản phẩm này sẽ hiễn thị màn hình nào

        firebaseFirestore = FirebaseFirestore.getInstance();

        ArrayList<Long> categorys = new ArrayList<>();
        categorys.add((long) 1);
        categorys.add((long) 1);
        categorys.add((long) 1);
        categorys.add((long) 1);
        categorys.add((long) 6);

        ArrayList<String> titles = new ArrayList<>();
        titles.add("two-tone checkered trench coat");
        titles.add("logo-print sleeveless dress");
        titles.add("belted-waist midi trench coat");
        titles.add("check cape coat");
        titles.add("Vintage Check Ramsey sneakers");


        for (int j = 0; j < categorys.size(); ++j, ++start) {

            long category = categorys.get(j);
            String title = titles.get(j),
                    price = "550.000 đ",
                    cutPrice = "820.000 đ",
                    description = "Tiến Dũng là trụ cột ở hàng thủ của Việt Nam, thường xuyên đá cặp cùng Quế Ngọc Hải và Đỗ Duy Mạnh trong sơ đồ ba trung vệ. Anh được HLV Park triệu tập ở mọi giải đấu, góp phần quan trọng làm nên chiến tích ở U23 châu Á 2018, vô địch AFF Cup 2018 và vào vòng loại thứ ba World Cup 2022 - khu vực châu Á.";
            ArrayList<String> image = new ArrayList<>();
            ArrayList<String> size = new ArrayList<>(Arrays.asList("XS", "S", "L"));
            ArrayList<String> bodyName = new ArrayList<>(Arrays.asList("Vai", "Lưng", "Dài áo"));
            ArrayList<Long> measure = new ArrayList<>();
            measure.add((long) 1);
            measure.add((long) 1);
            measure.add((long) 1);
            measure.add((long) 1);
            measure.add((long) 1);
            measure.add((long) 1);
            measure.add((long) 1);
            measure.add((long) 1);
            measure.add((long) 1);


            ArrayList<String> info = new ArrayList<>();
            info.add("Chất liêu: vài flannel");
            info.add("Chất vải mềm mại, không quá dày");
            info.add("Chi tiết trước & sau thêu KTS sắc nét");
            info.add("Mạc vải đỏ đô được may bên hông túi áo");
            info.add("Zipper HKK 2 chiều");
            ProductModel productModel = new ProductModel(screen, category, title, price, cutPrice, description, image, size, bodyName, measure, info);
            int finalStart = start;
            firebaseFirestore.collection(ProductModel.class.getSimpleName()).document(Integer.toString(start)).set(productModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
                        for (int i = 0; i < 5; ++i) {
                            int finalI = i;
                            firebaseStorage.getReference(ProductModel.FIRESTORAGE + "/" + finalStart + "/" + i + ".jpg")
                                    .getDownloadUrl()
                                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            if (task.isSuccessful()) {
                                                firebaseFirestore.collection(ProductModel.class.getSimpleName()).document(Integer.toString(finalStart)).update(String.format("image.%d", finalI), task.getResult().toString());
                                            }
                                        }
                                    });
                        }
                    }
                }
            });
        }
    }
}