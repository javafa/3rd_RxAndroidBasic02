package net.flow9.rxandroidbasic02;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textResult;
    private Button btnJust;
    private Button btnFrom;
    private Button btnDefer;
    // 목록
    private RecyclerView listView;
    private List<String> data = new ArrayList<>(); // 빈 목록
    private CustomAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


    }

    private void initView() {
        textResult = (TextView) findViewById(R.id.textResult);
        btnJust = (Button) findViewById(R.id.btnJust);
        btnFrom = (Button) findViewById(R.id.btnFrom);
        btnDefer = (Button) findViewById(R.id.btnDefer);
        // 목록 세팅
        listView = (RecyclerView) findViewById(R.id.listView);
        adapter = new CustomAdapter(data);
        listView.setAdapter(adapter);
        listView.setLayoutManager(new LinearLayoutManager(this));

        btnJust.setOnClickListener(this);
        btnFrom.setOnClickListener(this);
        btnDefer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnFrom:
                break;
            case R.id.btnJust:
                break;
            case R.id.btnDefer:
                break;
        }
    }
}

class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.Holder> {
    List<String> data;

    public CustomAdapter(List<String> data) {
        this.data = data;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.textView.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView textView;

        public Holder(View itemView) {
            super(itemView);
            initView(itemView);
        }
        private void initView(View itemView) {
            textView = (TextView) itemView.findViewById(R.id.textView);
        }
    }
}