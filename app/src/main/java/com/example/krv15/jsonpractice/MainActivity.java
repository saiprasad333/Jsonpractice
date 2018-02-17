package com.example.krv15.jsonpractice;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends BaseActivity {


    RecyclerView rv;

    private String Url="http://www.json-generator.com/api/json/get/caiiHWvFFe?indent=2";

    List<DetailsModel> DetailsModellist;

    OkHttpClient okHttpClient;
    Gson gson;
    Request request;

    Toolbar toolbar;
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void intitializeViews() {
        toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setIcon(0);

        CollapsingToolbarLayout ctl=findViewById(R.id.collapse);
        ctl.setTitle("Example");

        swipeRefreshLayout =findViewById(R.id.swipelayout);

//        swipeRefreshLayout.post(new Runnable() {
//            @Override
//            public void run() {
//              swipeRefreshLayout.setRefreshing(true);
//            }
//        });

        rv= (RecyclerView) this.findViewById(R.id.rv);

        okHttpClient =new OkHttpClient();

        request =new Request.Builder().url(Url).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                //Log.e("res",response.body().string());

                try {


                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONArray jsonArray=jsonObject.getJSONArray("rootnode");

                    DetailsModellist =new ArrayList<DetailsModel>();



                    for(int i=0;i<jsonArray.length();i++){

//                        DetailsModellist= (List<DetailsModel>) gson.fromJson(jsonObject.getJSONArray("rootnode").
//                                        getJSONObject(i).toString(), new TypeToken<List<DetailsModel>>(){}.getType());

                        DetailsModel detailsModel=new DetailsModel();
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);

                        detailsModel.setId(jsonObject1.getString("Id"));
                        detailsModel.setAge(jsonObject1.getString("Age"));
                        detailsModel.setName(jsonObject1.getString("Name"));
                        detailsModel.setSurname(jsonObject1.getString("Surname"));

                        DetailsModellist.add(detailsModel);

                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            rv.setAdapter(new Adapter());
                            rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        }
                    });


                } catch (JSONException e) {

                    e.printStackTrace();

                }

            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }


    public class Adapter extends RecyclerView.Adapter<Adapter.VH> {

      @Override
      public VH onCreateViewHolder(ViewGroup parent, int viewType) {
          View view= LayoutInflater.from(MainActivity.this).inflate(R.layout.inflate,parent,false);
          return new VH(view);
      }

      @Override
      public void onBindViewHolder(VH holder, int position) {

          DetailsModel detailsModel=DetailsModellist.get(position);
          holder.tv1.setText("Name :"+detailsModel.getName());
//          holder.tv2.setText("SurName :"+detailsModel.getSurname());
//          holder.tv.setText("Id :"+detailsModel.getId());
//          holder.tv3.setText("Age :"+detailsModel.getAge());
//          holder.tv.setOnClickListener(new View.OnClickListener() {
//              @Override
//              public void onClick(View view) {
//                  Intent i=new Intent(MainActivity.this,Getdetails.class);
//              }
//          });
      }

      @Override
      public int getItemCount() {

          return DetailsModellist.size();
      }

      public class VH extends RecyclerView.ViewHolder {

          TextView tv,tv1,tv2,tv3;

          public VH(View itemView) {

              super(itemView);
 //             tv=itemView.findViewById(R.id.id);
              tv1=itemView.findViewById(R.id.name);
//              tv2=itemView.findViewById(R.id.ccode);
//              tv3=itemView.findViewById(R.id.dcode);
          }
      }
  }
}
