package com.example.krv15.jsonpractice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

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


public class MainActivity2 extends AppCompatActivity {

    RecyclerView rv;

    private String Url="http://getskill.talentedge.in/SAP/SAP_Integration.asmx/Centers";

    List<ExModel> DetailsModellist;

    OkHttpClient okHttpClient;
    Gson gson;
    Request request;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv=findViewById(R.id.rv);

        okHttpClient=new OkHttpClient();
        request=new Request.Builder().url(Url).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try{
                    JSONArray jsonArray=new JSONArray(response.body().string());
                    JSONObject jsonObject=jsonArray.getJSONObject(0);
                    DetailsModellist =new ArrayList<ExModel>();

                    for(int i=0;i<jsonObject.length();i++){

                        ExModel detailsModel=new ExModel();
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);

                        detailsModel.setCenterCode(jsonObject1.getString("CenterCode"));
                        detailsModel.setCenterName(jsonObject1.getString("CenterName"));
                        detailsModel.setDistrictCode(jsonObject1.getString("DistrictCode"));
                        detailsModel.setCityCode(jsonObject1.getString("CityCode"));

                        DetailsModellist.add(detailsModel);

                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            rv.setAdapter(new MainActivity2.Adapter());
                            rv.setLayoutManager(new LinearLayoutManager(MainActivity2.this));
                        }
                    });

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }
    public class Adapter extends RecyclerView.Adapter<MainActivity2.Adapter.VH> {

        @Override
        public MainActivity2.Adapter.VH onCreateViewHolder(ViewGroup parent, int viewType) {

            return new MainActivity2.Adapter.VH(getLayoutInflater().inflate(R.layout.inflate,null));
        }

        @Override
        public void onBindViewHolder(final VH holder, int position) {
            ExModel detailsModel=DetailsModellist.get(position);

            holder.tv.setText(detailsModel.getCenterName());
            holder.tv1.setText(detailsModel.getCenterCode());
            holder.tv2.setText(detailsModel.getDistrictCode());
            holder.tv3.setText(detailsModel.getCityCode());
            holder.tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent i=new Intent(MainActivity2.this,Getdetails.class);
//                    startActivity(i);
                }
            });
        }



        @Override
        public int getItemCount() {

            return DetailsModellist.size();
        }

        public class VH extends RecyclerView.ViewHolder {

            TextView tv,tv1,tv2,tv3;

            public VH(View itemView) {

                super(itemView);
                tv=itemView.findViewById(R.id.id);
                tv1=itemView.findViewById(R.id.name);
                tv2=itemView.findViewById(R.id.dcode);
                tv3=itemView.findViewById(R.id.ccode);
            }
        }
    }
}
