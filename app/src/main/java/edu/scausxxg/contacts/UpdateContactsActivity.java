package edu.scausxxg.contacts;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import edu.scausxxg.contacts.data.ContactsTable;
import edu.scausxxg.contacts.data.MyDB;
import edu.scausxxg.contacts.data.User;


/***************************************
 *修改联系人操作界面
 ****************************************/
public class UpdateContactsActivity extends AppCompatActivity {
    /** Called when the activity is first created. */
    //姓名输入框
    private  EditText nameEditText;
    //手机输入框
    private  EditText mobileEditText;
    //qq
    private  EditText qqEditText;

    //修改的联系人
    private User user;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        setTitle("修改联系人");

        //TODO 1、从已设置的页面布局查找对应的控件
        nameEditText = findViewById(R.id.name);
        mobileEditText = findViewById(R.id.mobile);
        qqEditText = findViewById(R.id.qq);

        //TODO 2、将要修改的联系人数据赋值到用户界面显示
        ContactsTable contactsTable = new ContactsTable(this);
        Bundle data = getIntent().getExtras();
        user = contactsTable.getUserByID(data.getInt("user_ID"));

        nameEditText.setText(user.getName());
        mobileEditText.setText(user.getMobile());
        qqEditText.setText(user.getQq());

    }
    /**
     * 创建菜单
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, 1, Menu.NONE, "保存");
        menu.add(Menu.NONE, 2, Menu.NONE, "返回");
        return super.onCreateOptionsMenu(menu);
    }
    /**
     * 菜单事件
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()) {
            case 1://保存
                if(!nameEditText.getText().toString().equals(""))
                {
                    user.setName(nameEditText.getText().toString());
                    user.setMobile(mobileEditText.getText().toString());
                    user.setQq(qqEditText.getText().toString());
                    ContactsTable ct= new ContactsTable(this);
					
                    //修改数据库联系人信息
                    if(ct.updateUser(user))
                    {
                        Toast.makeText(UpdateContactsActivity.this, "修改成功！",
                                Toast.LENGTH_SHORT).show();
                    }else
                    {
                        Toast.makeText(UpdateContactsActivity.this, "修改失败！",
                                Toast.LENGTH_SHORT).show();
                    }
                }else
                {
                    Toast.makeText(UpdateContactsActivity.this, "数据不能为空！",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case 2://返回
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}