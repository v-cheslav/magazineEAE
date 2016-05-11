//package magazine.utils;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by pvc on 15.11.2015.
// */
//public class TemporaryPhotoAddresses {
//    private static TemporaryPhotoAddresses temporaryPhotoAddresses;;
//
//    private ArrayList<String> addresses = new ArrayList<>();
//
//    private TemporaryPhotoAddresses() {
//    }
//
//    public static TemporaryPhotoAddresses getInstance() {
//        if (temporaryPhotoAddresses == null)
//            synchronized (TemporaryPhotoAddresses.class) {
//                if (temporaryPhotoAddresses == null){
//                    temporaryPhotoAddresses = new TemporaryPhotoAddresses();
//                }
//            }
//        return temporaryPhotoAddresses;
//    }
//
//    public ArrayList<String> getAddresses() {
//        return addresses;
//    }
//
//    public void setAddresses(ArrayList<String> addresses) {
//        this.addresses = addresses;
//    }
//}
