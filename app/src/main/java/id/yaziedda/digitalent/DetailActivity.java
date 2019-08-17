package id.yaziedda.digitalent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    TextView tvTitle, tvPrice, tvDeskripsi;
    ImageView ivImage;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvTitle = findViewById(R.id.tv_title);
        tvPrice = findViewById(R.id.tv_price);
        tvDeskripsi = findViewById(R.id.tv_deskripsi);
        ivImage = findViewById(R.id.img);
        recyclerView = findViewById(R.id.recyclerView);

        Food model = (Food) getIntent().getSerializableExtra(Food.class.getName());

        setData(model);

        List<Food> list = populateData();
        setAdapter(list);
    }

    private void setData(Food model) {
        setTitle(model.getName());
        tvTitle.setText(model.getName());
        tvPrice.setText(model.getPrice());
        tvDeskripsi.setText(model.getDeskripsi());

        Glide.with(getApplicationContext())
                .load(model.getImage())
                .centerCrop()
                .placeholder(R.drawable.logo)
                .into(ivImage);
    }

    private List<Food> populateData() {
        List<Food> list = new ArrayList<>();

        Food model = new Food();
        model.setId(list.size()+1);
        model.setName("Coto Makassar");
        model.setPrice("Rp. 15.000");
        model.setImage("https://tfanews.com/wp-content/uploads/2019/04/Coto-makassar.jpg");
        model.setDeskripsi("Coto makassar atau coto mangkasara adalah makanan tradisional Makassar, Sulawesi Selatan. Makanan ini terbuat dari jeroan sapi yang direbus dalam waktu yang lama. Rebusan jeroan bercampur daging sapi ini kemudian diiris-iris lalu dibumbui dengan bumbu yang diracik secara khusus");
        list.add(model);

        model = new Food();
        model.setId(list.size()+1);
        model.setName("Soto Betawi");
        model.setPrice("Rp. 12.000");
        model.setImage("https://i1.wp.com/www.bisamasak.com/wp-content/uploads/2018/08/soto-betawi.jpg?fit=957%2C659&ssl=1");
        model.setDeskripsi("Soto Betawi merupakan soto yang populer di daerah Jakarta. Seperti halnya soto Madura dan soto sulung, soto Betawi juga menggunakan jeroan. Selain jeroan, seringkali organ-organ lain juga disertakan, seperti mata, terpedo, dan juga hati.\n");
        list.add(model);

        model = new Food();
        model.setId(list.size()+1);
        model.setName("Nasi Goreng Gila");
        model.setPrice("Rp. 22.000");
        model.setImage("https://i1.wp.com/resepkoki.id/wp-content/uploads/2016/10/Resep-Nasgor-Gila.jpg?fit=2314%2C2239&ssl=1");
        model.setDeskripsi("Konon, dahulu ada penjual nasi goreng yang bereksperimen dengan bahan makanan dan bumbu yang lebih beragam. Nasi goreng yang biasa-biasa saja diberi tambahan berbagai bahan istimewa untuk menghasilkan cita rasa baru. Setelah sang tukang nasi goreng mencoba kreasi masakannya, ia pun bergumam, “Gila, ternyata enak ya”. Reaksi tersebut akhirnya mencetuskan ide tentang penggunaan nama nasi gila. Menu makanan yang awalnya terkenal di Jakarta ini sekarang sudah populer di berbagai daerah lain di tanah air.");
        list.add(model);

        model = new Food();
        model.setId(list.size()+1);
        model.setName("Nasi Kebuli");
        model.setPrice("Rp. 35.000");
        model.setImage("https://i1.wp.com/resepkoki.id/wp-content/uploads/2017/03/Resep-Nasi-Kebuli.jpg?fit=1280%2C1651&ssl=1");
        model.setDeskripsi("Nasi kebuli adalah hidangan nasi berbumbu yang bercitarasa gurih yang ditemukan di Indonesia. Nasi ini dimasak bersama kaldu daging kambing, susu kambing, dan minyak samin, disajikan dengan daging kambing goreng dan kadang ditaburi dengan irisan kurma atau kismis.");
        list.add(model);

        model = new Food();
        model.setId(list.size()+1);
        model.setName("Ayam Geprek Bensu Mozzarella");
        model.setPrice("Rp. 21.000");
        model.setImage("https://awsimages.detik.net.id/community/media/visual/2017/05/16/27aa616b-6db6-41a8-bdcb-e4a45c08284d.jpg?w=700&q=90");
        model.setDeskripsi("erjun ke bisnis makanan tak lantas cuma bermodal nama tenar. Kalau memang produk yang dihasilkan pas-pasan, ya siap-siap saja 'say goodbye'.\n" +
                "\n" +
                "Ruben Onsu bisa dibilang salah satu selebritis yang terbilang sukses mencicipi 'lezatnya' bisnis makanan. Dengan menjajakan 'Ayam Geprek Bensu', suami Sarwendah itu sukses menginvasi lidah para penikmat kuliner di berbagai kota.\n" +
                "\n" +
                "Memang, nama Ruben sudah beken di dunia entertainment. Namun ini ibarat dua sisi mata uang. Jika suguhan ayam gepreknya jauh dari harapan sudah pasti suara bullyan orang-orang bakal nyaring terdengar. Namun ternyata, Ruben bisa membuktikan, modal yang dipegangnya bukan cuma popularitas. \n" +
                "\n" +
                "\n" +
                "Ada nilai-nilai entrepreneur, kreativitas dan kerja keras di sini. Hingga akhirnya kepak bisnis 'Ayam Geprek Bensu' tercermin lewat puluhan cabang yang berdiri kokoh di banyak tempat.");
        list.add(model);

        model = new Food();
        model.setId(list.size()+1);
        model.setName("Rendang Padang");
        model.setPrice("Rp. 37.000");
        model.setImage("https://i0.wp.com/resepkoki.id/wp-content/uploads/2017/11/Resep-Rendang-padang.jpg?fit=2837%2C3283&ssl=1");
        model.setDeskripsi("Rendang atau randang adalah masakan daging dengan bumbu rempah-rempah yang berasal dari Minangkabau. Masakan ini dihasilkan dari proses memasak yang dipanaskan berulang-ulang menggunakan santan sampai kuahnya kering sama sekali.");
        list.add(model);

        model = new Food();
        model.setId(list.size()+1);
        model.setName("Hanamasa Paket Berdua");
        model.setPrice("Rp. 150.000");
        model.setImage("https://nanyaharga.com/wp-content/uploads/2017/03/menu-hanamasa-1024x440.jpg");
        model.setDeskripsi("Hanamasa adalah restoran berkonsep all you can eat yang memungkinkan kita untuk makan sepuasnya. Restoran yang membuka cabang di beberapa kota besar di Indonesia ini menyajikan menu yakiniku dan syabu-syabu.");
        list.add(model);

        model = new Food();
        model.setId(list.size()+1);
        model.setName("Nasi Uduk Komplit");
        model.setPrice("Rp. 15.000");
        model.setImage("https://i0.wp.com/masakansehat.com/wp-content/uploads/2017/07/Nasi-Uduk-Komplit.jpg?fit=750%2C538");
        model.setDeskripsi("Nasi uduk atau dalam bahasa Belanda Rijst vermengd met onrust van de liefde atau disingkat Jaloerse rijst adalah nama makanan yang terbuat dari bahan dasar nasi putih yang diaron dan dikukus dengan santan, serta dibumbui dengan pala, kayu manis, jahe, daun serai dan merica.");
        list.add(model);

        model = new Food();
        model.setId(list.size()+1);
        model.setName("Soto Tangkar Karawang");
        model.setPrice("Rp. 25.000");
        model.setImage("https://awsimages.detik.net.id/community/media/visual/2018/10/15/99c86679-54b6-4e18-8daf-8310e5f4c5b3.jpeg?w=700&q=90");
        model.setDeskripsi("Soto berisi tangkar atau iga sapi ini berkuah gurih enak. Hingga kini masih banyak dijual di sekitar Jakarta. Makanan legendaris ini tetap manjakan lidah warga ibukota.\n" +
                "\n" +
                "Hari ini Jakarta berulangtahun ke-490, ibukota yang dihuni oleh berbagai suku. Betawi, suku Jakarta juga memberi kontribusi pada ragam kuliner Jakarta.\n" +
                "\n" +
                "Selain soto betawi, kota Jakarta juga memiliki soto tangkar yang tak kalah lezat. Meski sekilas terlihat hampir sama, soto tangkar menggunakan santan dengan warna kuning kemerahan karena kunyit dan cabai dipakai sebagai bumbu.");
        list.add(model);

        model = new Food();
        model.setId(list.size()+1);
        model.setName("Gudeg Jogja");
        model.setPrice("Rp. 25.000");
        model.setImage("https://2.bp.blogspot.com/-yCpaokWn1fk/VQzOz16m-oI/AAAAAAAAAqo/zyHxsSxRA-k/s1600/resep-gudeg-jogja-enak.jpg");
        model.setDeskripsi("Gudeg (ejaan bahasa Jawa: ꦒꦸꦝꦼꦒ꧀\u200B, Gudheg) adalah makanan khas Yogyakarta dan Jawa Tengah yang terbuat dari nangka muda yang dimasak dengan santan. Perlu waktu berjam-jam untuk membuat masakan ini. Warna coklat biasanya dihasilkan oleh daun jati yang dimasak bersamaan.");
        list.add(model);
        return list;
    }

    private void setAdapter(List<Food> list) {
        RecyclerViewAdapater adapter = new RecyclerViewAdapater(list, new RecyclerViewAdapater.OnItemClickListener() {
            @Override
            public void onItemClick(Food model) {
                setData(model);
            }
        }, true);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
