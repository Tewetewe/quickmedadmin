package com.example.adminquickmed;

import java.util.ArrayList;

//public class UkiranData {
public class UkiranData{
    private static String[] ukiranNames = {
            "Karang Babi",
            "Karang Barong",
            "Karang Boma",
            "Karang Daun",
            "Karang Gajah",
            "Karang Garuda",
            "Karang Goak",
            "Karang Naga",
            "Karang Rangde",
            "Karang Singa",
            "Karang Tapel"
    };
    private static String[] ukiranDetails = {
            "Ide/konsep di ambil dari cerita mencari ujung dan pangkal dari lingga Ciwa, yang pada intinya tidak pernah ketemu, atinya kekuasaan Tuhan tidak manusiapun yang mampu menyelaminya. Dewa Wisnu berubah menjadi seekor celeng/babi kemudian distilir menjadi karang celeng/babi.",
            "ide/konsep diambil dari cerita calonarang simbol kekuatan Ciwa untuk mengimbangi kekuatan Durga, kemudian distilir dan dikombinasikan dengan keketusan dan pepatran.",
            "ide/konsep diambil dari cerita Bomantara, tentang kelahiran boma sebagai pelindung hutan belantara. Adapun ciri dari karang boma adalah gabungan dari muka raksasa dengan ornamen keketusan dan pepatran.",
            "ide/konsep dari seikat daun berbagai macam ukuran, kemudian distilir menjadi bentuk karang daun, yang dikombinasikan dengan keketusan, pepatran dan kekarangan.",
            "ide/konsep dari binatang gajah yang mempunyai kekuatan besar di dunia, kemudain diambil bentuk kepalanya dan distilir/dirubah menjadi bentuk karang gajah yang ditempatkan pada dasar bangunan di bagian sudut dan ditengah, sebagai lambang kekuatan/kekokohan sebuah bangunan.Sebagai pelengkap dihias dengan ornamen keketusan dan pepatran.",
            "ide/konsep diambil dari cerita sang jarat karu yang mempunyai putra seekor burung garuda, kemudian distilir dan dikombinasikan dengan keketusan dan kekarangan, menjadi ornamen karang garuda.",
            "ide/konsep diambil dari muka burung yang kemudian distilir menjadi karang goak yang dikombinasikan dengan keketusan dan pepatran. Ditempatkan pada sudut dan tengah bangunan pada bagian atas bangunan dekat leher bangunan.",
            "ide/konsep dari cerita sang jarat karu yang mempunyai anak tiri berupa 100 seekor ular/naga yang diasuhnya, bersama anaknya seekor burung garuda, naga ini distilir menjadi ornamen karang naga yang tempatnya diatas sebagai simbol kemakmuran sandang, pangan dan papan.",
            "ide/konsep diambil dari cerita calonarang yang mempunyai kekuatan magis, simbol dari saktinya Ciwa yaitu Dwi Durga, kemudian distilir menjadi ornamen karang rangda, dikombinasikan dengan keketusan dan pepatran.",
            "ide/konsep diambil dari raja hutan/singa, kemudian distilir dan dikombinasikan dengan keketusan dan pepatran menjadi ornamen karang singa.",
            "ide/konsep diambil dari bentuk muka/ tapeng, sebagai penutup muka, dimana bentuk muka distilir/digubah menjadi bentuk karang tapel dan dikombinasikan dengan keketusan dan pepatran, ditempatkan pada sudut dan tengah bangunan bagian pinggang bangunan."
    };
    private static int[] ukiranImages = {
            R.drawable.karang_babi,
            R.drawable.karang_barong,
            R.drawable.karang_boma,
            R.drawable.karang_daun,
            R.drawable.karang_gajah,
            R.drawable.karang_garuda,
            R.drawable.karang_goak,
            R.drawable.karang_naga,
            R.drawable.karang_rangde,
            R.drawable.karang_singa,
            R.drawable.karang_tapel
    };
    //    static ArrayList<Ukiran> getListData() {
//        ArrayList<Ukiran> list = new ArrayList<>();
//        for (int position = 0; position < ukiranNames.length; position++) {
//            Ukiran ukiran = new Ukiran();
//            ukiran.setName(ukiranNames[position]);
//            ukiran.setDetail(ukiranDetails[position]);
//            ukiran.setPhoto(ukiranImages[position]);
//            list.add(ukiran);
//        }
//        return list;
    static ArrayList<Ukiran> getListData(){
        ArrayList<Ukiran> list = new ArrayList<>();
        for (int position = 0; position < ukiranNames.length; position++){
            Ukiran ukiran = new Ukiran();
            ukiran.setName(ukiranNames[position]);
            ukiran.setDetail(ukiranDetails[position]);
            ukiran.setPhoto(ukiranImages[position]);
            list.add(ukiran);
        }
        return list;
    }
}

//

//    }
//}
