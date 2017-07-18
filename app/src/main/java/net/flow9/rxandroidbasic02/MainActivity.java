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

import io.reactivex.Observable;
import io.reactivex.Observer;

public class MainActivity extends AppCompatActivity {

    private TextView textResult;
    // 목록
    private RecyclerView listView;
    private List<String> data = new ArrayList<>(); // 빈 목록
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initObservable();
    }

    private void initView() {
        textResult = (TextView) findViewById(R.id.textResult);
        // 목록 세팅
        listView = (RecyclerView) findViewById(R.id.listView);
        adapter = new CustomAdapter(data);
        listView.setAdapter(adapter);
        listView.setLayoutManager(new LinearLayoutManager(this));
    }

    Observable<String> forFrom;
    private void initObservable() {
        String fromData[] = {"aaa","bbb","ccc","ddd","eee"};
        forFrom = Observable.fromArray(fromData);
    }

    // xml 의 onclick에 바인드됨
    public void doFrom(View view){
        forFrom.subscribe(
                str -> data.add(str),                  // 옵저버블(발행자:emitter)로 부터 데이터를 가져온다
                t   -> { /*일단 아무것도 안함*/ },
                ()  -> adapter.notifyDataSetChanged()  // 완료되면 리스트에 알린다.
        );
    }

    public void doJust(View view){

    }

    public void doDefer(View view){

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