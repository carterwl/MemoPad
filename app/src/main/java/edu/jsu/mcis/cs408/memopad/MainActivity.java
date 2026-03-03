package edu.jsu.mcis.cs408.memopad;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MemoAdapter adapter;
    private ArrayList<Memo> memoList;
    private DatabaseHandler db;

    private EditText memoInput;
    private Button addButton;
    private Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHandler(this);

        memoInput = findViewById(R.id.memoInput);
        addButton = findViewById(R.id.addButton);
        deleteButton = findViewById(R.id.deleteButton);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        memoList = new ArrayList<>();
        adapter = new MemoAdapter(memoList);
        recyclerView.setAdapter(adapter);

        refreshList();


        addButton.setOnClickListener(v -> {
            String text = memoInput.getText().toString().trim();
            if (!text.isEmpty()) {
                db.addMemo(text);
                memoInput.setText("");
                refreshList();
            }
        });


        deleteButton.setOnClickListener(v -> {
            if (!memoList.isEmpty()) {
                Memo last = memoList.get(memoList.size() - 1);
                db.deleteMemo(last.getId());
                refreshList();
            }
        });
    }

    private void refreshList() {
        memoList.clear();
        memoList.addAll(db.getAllMemos());
        adapter.notifyDataSetChanged();
    }
}