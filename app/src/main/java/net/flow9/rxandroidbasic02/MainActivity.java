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
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

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
    Observable<Memo> forJust;
    Observable<String> forDefer;

    private void initObservable() {
        // from 생성
        String fromData[] = {"aaa","bbb","ccc","ddd","eee"};
        forFrom = Observable.fromArray(fromData);

        // just 생성
        Memo memo1 = new Memo("Hello");
        Memo memo2 = new Memo("Android");
        Memo memo3 = new Memo("with");
        Memo memo4 = new Memo("Reactive X!");
        forJust = Observable.just(memo1, memo2, memo3, memo4);

        // defer 생성
        forDefer = Observable.defer(new Callable<ObservableSource<? extends String>>() {
            @Override
            public ObservableSource<? extends String> call() throws Exception {
                return Observable.just("monday","tuesday","wednesday");
            }
        });
    }

    // xml 의 onclick에 바인드됨
    public void doFrom(View view){
        // 원형
//        forFrom.subscribe(
//                new Consumer<String>() {  // onNext
//                    @Override
//                    public void accept(String str) throws Exception {
//                        data.add(str);
//                    }
//                },
//                new Consumer<Throwable>() { // onError
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        /*일단 아무것도 안함*/
//                    }
//                },
//                new Action() {   // onComplete
//                    @Override
//                    public void run() throws Exception {
//                        adapter.notifyDataSetChanged();
//                    }
//                }
//        );
        // 람다표현식
        forFrom.subscribe(
                str -> data.add(str),                  // 옵저버블(발행자:emitter)로 부터 데이터를 가져온다
                t   -> { /*일단 아무것도 안함*/ },
                ()  -> adapter.notifyDataSetChanged()  // 완료되면 리스트에 알린다.
        );
    }

    public void doJust(View view){
        forJust.subscribe(
                obj -> data.add(obj.content),
                t   -> { /*일단 아무것도 안함*/ },
                ()  -> adapter.notifyDataSetChanged()
        );
    }

    public void doDefer(View view){
        forDefer.subscribe(
                str -> data.add(str),                  // 옵저버블(발행자:emitter)로 부터 데이터를 가져온다
                t   -> { /*일단 아무것도 안함*/ },
                ()  -> adapter.notifyDataSetChanged()  // 완료되면 리스트에 알린다.
        );

    }
}
// just 생성자를 위한 클래스
class Memo {
    String content;
    public Memo(String content) {
        this.content = content;
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