package io.github.yaziedda.submission2.ui.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import io.github.yaziedda.submission2.R;
import io.github.yaziedda.submission2.model.TVShowResult;
import io.github.yaziedda.submission2.model.TVShowResult_Table;
import io.github.yaziedda.submission2.util.Static;

public class DetailTVShowActivity extends AppCompatActivity {

    private Menu menu;
    TVShowResult item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tvshow);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView title = findViewById(R.id.tv_title);
        TextView desc = findViewById(R.id.tv_desc);
        TextView release = findViewById(R.id.tv_release);
        ImageView img = findViewById(R.id.img);

        item = (TVShowResult) getIntent().getSerializableExtra(TVShowResult.class.getName());

        setTitle(item.getOriginalName());

        title.setText(item.getOriginalName());

        desc.setText(item.getOverview());
        release.setText(item.getFirstAirDate());

        Glide.with(getApplicationContext())
                .load(Static.getUrlPoster(Static.POSTER_w780, item.getPosterPath()))
                .placeholder(R.drawable.ic_launcher_background)
                .into(img);
        loadUI();
    }

    private void loadUI() {
        TVShowResult tvShowResult = SQLite.select().from(TVShowResult.class)
                .where(TVShowResult_Table.id.eq(item.getId()))
                .querySingle();

        if (tvShowResult != null){
            if (menu != null)
                menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_black_24dp));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        loadUI();
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_favorit:

                TVShowResult tvShowResult = SQLite.select().from(TVShowResult.class)
                        .where(TVShowResult_Table.id.eq(this.item.getId())).querySingle();

                if(tvShowResult != null){
                    tvShowResult.delete();
                    menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_black_24dp));
                    Toast.makeText(getApplicationContext(), R.string.movies_fav_delete_0, Toast.LENGTH_LONG).show();
                }else{
                    this.item.save();
                    menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_black_24dp));
                    Toast.makeText(getApplicationContext(), R.string.movies_fav_delete_1, Toast.LENGTH_LONG).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
