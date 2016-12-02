package com.slut.recorder.create.note;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.slut.recorder.App;
import com.slut.recorder.R;
import com.slut.recorder.utils.BitmapUtils;
import com.slut.recorder.utils.ConvertUtils;
import com.slut.recorder.utils.ResUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.richeditor.RichEditor;

public class NoteCreateActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    EditText title;
    @BindView(R.id.content)
    RichEditor content;
    @BindView(R.id.bold)
    TextView bold;
    @BindView(R.id.italic)
    TextView italic;
    @BindView(R.id.head)
    TextView head;
    @BindView(R.id.underline)
    TextView underline;

    private static final String IMAGE_TYPE = "image/*";
    private static final int REQUEST_PIC_GALLERY = 1000;//图库选取图片请求码
    private static final int REQUEST_PIC_CAMERA = 2000;//照相机选取图片请求码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_create);
        App.getInstances().addActivity(this);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        content.setPadding(4, 0, 4, 0);
        content.setPlaceholder(ResUtils.getString(R.string.hint_note_create_content));
    }

    private void initListener(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @OnClick(R.id.italic)
    void onItalicClick() {
        content.setItalic();
    }

    @OnClick(R.id.left)
    void onLeftClick() {
        content.setAlignLeft();
    }

    @OnClick(R.id.center)
    void onCenterClick() {
        content.setAlignCenter();
    }

    @OnClick(R.id.right)
    void onRightClick() {
        content.setAlignRight();
    }

    @OnClick(R.id.indent)
    void onIndentClick() {
        content.setIndent();
    }

    @OnClick(R.id.outdent)
    void onOutDentClick() {
        content.setOutdent();
    }

    @OnClick(R.id.numbers)
    void onNumbersClick() {
        content.setNumbers();
    }

    @OnClick(R.id.link)
    void onLinkClick() {
        content.insertLink("http://www.baidu.com","百度");
    }

    @OnClick(R.id.todo)
    void onTodoClick() {
        content.insertTodo();
    }

    @OnClick(R.id.bold)
    void onBoldClick() {
        content.setBold();
    }

    @OnClick(R.id.underline)
    void onUnderLineClick() {
        content.setUnderline();
    }

    @OnClick(R.id.blockquote)
    void onBlockQuoteClick() {
        content.setBlockquote();
    }

    @OnClick(R.id.head)
    void onHeadClick() {
        PopupMenu popupMenu = new PopupMenu(this, head);
        popupMenu.inflate(R.menu.pop_note_create_head);
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.h1:
                        content.setHeading(1);
                        break;
                    case R.id.h2:
                        content.setHeading(2);
                        break;
                    case R.id.h3:
                        content.setHeading(3);
                        break;
                    case R.id.h4:
                        content.setHeading(4);
                        break;
                    case R.id.h5:
                        content.setHeading(5);
                        break;
                    case R.id.h6:
                        content.setHeading(6);
                        break;

                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_create, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.gallery:
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(IMAGE_TYPE);
                startActivityForResult(intent, REQUEST_PIC_GALLERY);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_PIC_GALLERY:
                    if (data != null) {
                        Uri uri = data.getData();
                        if (uri != null) {
                            content.insertImage(ConvertUtils.getImageAbsolutePath(uri), "");
                        }
                    }
                    break;
            }
        }
    }
}
