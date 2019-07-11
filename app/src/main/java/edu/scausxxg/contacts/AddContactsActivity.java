package edu.scausxxg.contacts;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import edu.scausxxg.contacts.data.ContactsTable;
import edu.scausxxg.contacts.data.User;


/************************************************************
 *添加联系人操作界面
 ************************************************************/
public class AddContactsActivity extends AppCompatActivity {
    //姓名输入框
    private EditText nameEditText;
    //手机输入框
    private  EditText mobileEditText;
    //qq输入框
    private  EditText qqEditText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        setTitle("添加联系人");
        //从已设置的页面布局查找对应的控件
        nameEditText = findViewById(R.id.name);
        mobileEditText = findViewById(R.id.mobile);
        qqEditText = findViewById(R.id.qq);

    }
    /**
     * 创建菜单
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(Menu.NONE,1, Menu.NONE, "保存");
        menu.add(Menu.NONE,2, Menu.NONE, "返回");
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
                    User user = new User();
                    user.setName(nameEditText.getText().toString());
                    user.setMobile(mobileEditText.getText().toString());
                    user.setQq(qqEditText.getText().toString());
                    ContactsTable ct = new ContactsTable(AddContactsActivity.this);
                    if(ct.addData(user))
                    {
                        Toast.makeText(AddContactsActivity.this, "添加成功！",Toast.LENGTH_SHORT).show();
                        finish();
                    }else
                    {
                        Toast.makeText(AddContactsActivity.this, "添加失败！",Toast.LENGTH_SHORT).show();

                    }
                }else
                {
                    Toast.makeText(AddContactsActivity.this, "请先输入数据！",Toast.LENGTH_SHORT).show();
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