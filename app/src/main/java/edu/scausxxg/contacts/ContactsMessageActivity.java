package edu.scausxxg.contacts;


import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import edu.scausxxg.contacts.data.ContactsTable;
import edu.scausxxg.contacts.data.User;

/***************************************
 *查看联系人信息操作界面
 ****************************************/

public class ContactsMessageActivity extends AppCompatActivity {
    /** Called when the activity is first created. */
    //姓名输入框
    private TextView nameTextView;
    //手机输入框
    private  TextView mobileTextView;
    //qq
    private  TextView qqTextView;
    //修改的联系人
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        setTitle("联系人信息");

        //TODO 1、从已设置的页面布局查找对应的控件
        nameTextView = findViewById(R.id.name);
        mobileTextView = findViewById(R.id.mobile);
        qqTextView = findViewById(R.id.qq);

        //TODO 2、将要修改的联系人数据赋值到用户界面显示
        ContactsTable contactsTable = new ContactsTable(this);
        Bundle data = getIntent().getExtras();
        user = contactsTable.getUserByID(data.getInt("user_ID"));

        nameTextView.setText(user.getName());
        mobileTextView.setText(user.getMobile());
        qqTextView.setText(user.getQq());

    }
    /**
     * 创建菜单
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, 1, Menu.NONE, "返回");
        return super.onCreateOptionsMenu(menu);
    }
    /**
     * 菜单事件
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case 1://返回
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
