package com.example.a6laba;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class DetailPostActivity extends AppCompatActivity
{
    private com.example.a6laba.Post post;
    private Button backBtn, deleteBtn, editBtn;
    private ImageView postPhoto;
    private TextView titleText, descriptionText, linkText, price, currency, isOwner;


    Integer position;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_post);
        position = (Integer) getIntent().getSerializableExtra("position");
        try
        {
            ArrayList<com.example.a6laba.Post> mListPosts = Utility.getPostsList(getApplicationContext());
            post = mListPosts.get(position);
            secondServiceCall(post.selectedImagePath);
            backBtn = findViewById(R.id.detail_back_button);
            backBtn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    finish();
                }
            });

            deleteBtn = findViewById(R.id.detail_delete_button);
            deleteBtn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    try
                    {
                        Utility.deletePostInList(getApplicationContext(), position);
                        Intent intent = new Intent(com.example.a6laba.DetailPostActivity.this, PostExtraction.class);
                        startActivity(intent);
                    } catch (Exception e)
                    {
                        Toast.makeText(getApplicationContext(), "Sorry, I can't delete this post", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            editBtn = findViewById(R.id.detail_edit_button);
            editBtn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    try
                    {

                        Intent intent = new Intent(com.example.a6laba.DetailPostActivity.this, EditPostActivity.class);
                        intent.putExtra("position", position);
                        startActivity(intent);
                    } catch (Exception e)
                    {
                        Toast.makeText(getApplicationContext(), "Sorry, I can't edit this post", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            postPhoto = findViewById(R.id.detail_photo_card);


            titleText = findViewById(R.id.detail_title);
            titleText.setText(post.title);

            price = findViewById(R.id.priceTextView);
            price.setText(String.valueOf(post.price));

            currency = findViewById(R.id.currencyTextView);
            currency.setText(post.currency);

            isOwner = findViewById(R.id.isOwnerTextView);
            isOwner.setText(((post.owner) ? "Да" : "Нет"));

            descriptionText = findViewById(R.id.detail_description);
            descriptionText.setText(post.getPostDescription());

            linkText = findViewById(R.id.detail_link);
            linkText.setText(post.link);
            linkText.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(com.example.a6laba.DetailPostActivity.this, com.example.a6laba.WebViewActivity.class);
                    intent.putExtra("link", post.link);
                    startActivity(intent);
                }
            });


        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public void secondServiceCall(String url)
    {
        RequestQueue queue = Volley.newRequestQueue(this);

        ImageRequest ir = new ImageRequest(url, new Response.Listener<Bitmap>()
        {
            @Override
            public void onResponse(Bitmap response)
            {
                // callback
                postPhoto.setImageBitmap(response);
            }
        }, 0, 0, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.RGB_565, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
            }
        });
        queue.add(ir);
    }

}