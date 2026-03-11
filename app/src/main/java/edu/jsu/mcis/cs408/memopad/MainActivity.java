package edu.jsu.mcis.cs408.memopad;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import edu.jsu.mcis.cs408.memopad.model.dao.DAOFactory;
import edu.jsu.mcis.cs408.memopad.model.dao.MemoDAO;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MemoAdapter adapter;
    private ArrayList<Memo> memoList;

    private DAOFactory daoFactory;
    private MemoDAO memoDao;

    private EditText memoInput;
    private Button addButton;
    private Button deleteButton;

    private Integer selectedId = null;

    private final MemoPadItemClickHandler itemClick = new MemoPadItemClickHandler();

    public MemoPadItemClickHandler getItemClick() {
        return itemClick;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        daoFactory = new DAOFactory(this, null, null, 1);
        memoDao = daoFactory.getMemoDao();

        memoInput = findViewById(R.id.memoInput);
        addButton = findViewById(R.id.addButton);
        deleteButton = findViewById(R.id.deleteButton);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        memoList = new ArrayList<>();
        adapter = new MemoAdapter(this, memoList);
        recyclerView.setAdapter(adapter);

        refreshList();

        addButton.setOnClickListener(v -> {
            String text = memoInput.getText().toString().trim();

            if (!text.isEmpty()) {
                memoDao.create(new Memo(text));
                memoInput.setText("");
                refreshList();
            }
        });

        deleteButton.setOnClickListener(v -> {
            if (selectedId != null) {
                memoDao.delete(selectedId);
                selectedId = null;
                refreshList();
            } else {
                Toast.makeText(this, "Select a memo first", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void refreshList() {
        memoList.clear();
        memoList.addAll(memoDao.list());
        adapter.notifyDataSetChanged();
    }

    private class MemoPadItemClickHandler implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int position = recyclerView.getChildLayoutPosition(v);

            Memo memo = adapter.getItem(position);
            selectedId = memo.getId();

            Toast.makeText(v.getContext(), String.valueOf(selectedId), Toast.LENGTH_SHORT).show();
        }
    }
}