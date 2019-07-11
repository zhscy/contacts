package edu.scausxxg.contacts;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.RawContacts;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Contacts.Data;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import edu.scausxxg.contacts.data.ContactsTable;
import edu.scausxxg.contacts.data.User;


/***************************************
 *主界面
 ****************************************/
public class MyContactsActivity extends AppCompatActivity {
    //结果列表
    private  ListView listView;
    //ListView 列表适配器
    private BaseAdapter  listViewAdapter;
    //通讯录用户
    private User users[];
    //当前选择
    private  int selecteItem=0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("通讯录");
        listView = (ListView) findViewById(R.id.listView);
        loadContacts();
    }
	
	//导入到手机电话薄，知识点涉及到ContentProvider
    public  void importPhone(String name,String phone)
    {
        //系统通信录ContentProvider的URI
        Uri  phoneURL=android.provider.ContactsContract.Data.CONTENT_URI;
        ContentValues values = new ContentValues();
        //首先向RawContacts.CONTENT_URI执行一个空值插入，目的是获取系统返回的rawContactId
        Uri rawContactUri = this.getContentResolver().
                insert(RawContacts.CONTENT_URI, values);
        long rawContactId = ContentUris.parseId(rawContactUri);
        //往data表插入姓名数据
        values.clear();
        values.put(Data.RAW_CONTACT_ID, rawContactId);
        values.put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE);
        values.put(StructuredName.GIVEN_NAME, name);
        this.getContentResolver().insert(phoneURL, values);
        //往data表插入电话数据
        values.clear();
        values.put(Data.RAW_CONTACT_ID, rawContactId);
        values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
        values.put(Phone.NUMBER, phone);
        values.put(Phone.TYPE, Phone.TYPE_MOBILE);
        this.getContentResolver().insert(phoneURL, values);
    }
	
	
    /**
     * 加载联系人列表
     */
    private void  loadContacts()
    {
        //获取所有通讯录联系人
        ContactsTable ct = new ContactsTable(this);
        users=ct.getAllUser();
        //listView列表实现适配器
        listViewAdapter = new BaseAdapter() {
            @Override
            public View getView(int position,View convertView, ViewGroup parent) {
                if(convertView == null)
                {
                    TextView textView =
                            new TextView(MyContactsActivity.this);
                    textView.setTextSize(22);
                    convertView=textView;
                }
                String mobile = users[position].getMobile() == null ? ""
                                                                       :users[position].getMobile();
                ((TextView)convertView).setText(users[position]
                        .getName()+"---"+mobile);
                if(position == selecteItem)
                {
                    convertView.setBackgroundColor(Color.YELLOW);
                }else
                {
                    convertView.setBackgroundColor(0);
                }
                return convertView;
            }
            @Override
            public long getItemId(int position) {
                return position;
            }
            @Override
            public Object getItem(int position) {
                return users[position];
            }
            @Override
            public int getCount() {
                return users.length;
            }
        };
        //设置listView控件的适配器
        listView.setAdapter(listViewAdapter);

        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3)
            {
                //记录点击列
                selecteItem=arg2;
                //刷新列表
                listViewAdapter.notifyDataSetChanged();
            }

        });
    }

    /**
     * 创建菜单
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, 1, Menu.NONE, "添加");
        menu.add(Menu.NONE, 2, Menu.NONE, "编辑");
        menu.add(Menu.NONE, 3, Menu.NONE, "查看信息");
        menu.add(Menu.NONE, 4, Menu.NONE, "删除");
        menu.add(Menu.NONE, 5, Menu.NONE, "查询");
        menu.add(Menu.NONE, 6, Menu.NONE, "导入到手机电话薄");
        menu.add(Menu.NONE, 7, Menu.NONE, "退出");
        return super.onCreateOptionsMenu(menu);
    }
    /**
     * 菜单事件
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case 1://添加
                Intent intent = new Intent(MyContactsActivity.this,AddContactsActivity.class);
                startActivity(intent);
                break;
            case 2://修改
                if(users[selecteItem].get_id()>0)//根据数据库ID判断当前记录是否可以操作
                {
                    intent = new Intent(MyContactsActivity.this,UpdateContactsActivity.class);
                    intent.putExtra("user_ID", users[selecteItem].get_id());
                    startActivity(intent);
                }else
                {
                    Toast.makeText(this, "无结果记录，无法操作!",Toast.LENGTH_SHORT).show();
                }
                break;
            case 3://查看信息
                if(users[selecteItem].get_id()>0)
                {
                    intent = new Intent(MyContactsActivity.this,ContactsMessageActivity.class);
                    intent.putExtra("user_ID", users[selecteItem].get_id());
                    startActivity(intent);
                }else
                {
                    Toast.makeText(this, "无结果记录，无法操作!",Toast.LENGTH_SHORT).show();
                }
                break;
            case 4://删除
                if(users[selecteItem].get_id()>0)
                {
                    delete();
                }else
                {
                    Toast.makeText(this, "无结果记录，无法操作!",Toast.LENGTH_SHORT).show();
                }
                break;
            case 5://查询
                new FindDialog(this).show();
                break;
            case 6://导入到手机电话薄
                if(users[selecteItem].get_id()>0)
                {
                    importPhone(users[selecteItem].getName(),users[selecteItem].getMobile());
                    Toast.makeText(this, "已经成功导入‘"+users[selecteItem].getName()+"’到手机电话薄!",Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(this, "无结果记录，无法操作!",Toast.LENGTH_SHORT).show();
                }
                break;
            case 7://退出
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //重新加载数据
        ContactsTable ct=new ContactsTable(this);
        users=ct.getAllUser();
        //刷新列表
        listViewAdapter.notifyDataSetChanged();
    }
    /**
     * 查询
     */
    public class FindDialog extends Dialog{

        public FindDialog(Context context) {
            super(context);

        }
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_find);
            setTitle("联系人查询");
            Button find=(Button)findViewById(R.id.find);
            Button cancel=(Button)findViewById(R.id.cancel);
            find.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText value=(EditText)findViewById(R.id.value);
                    ContactsTable ct=new ContactsTable(MyContactsActivity.this);
                    users=ct.findUserByKey(value.getText().toString());

//                    for(int i = 0;i < users.length; i++)
//                    {
//                        System.out.println("姓名是"+users[i].getName()+
//                                "，电话是" +users[i].getMobile());
//                    }
                    listViewAdapter.notifyDataSetChanged();
                    selecteItem = 0;
                    dismiss();//对话框的方法
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
    }
    /**
     * 删除联系人
     */
    public  void delete()
    {
        Builder alert = new Builder(this);
        alert.setTitle("系统信息");
        alert.setMessage("是否要删除联系人？");
        alert.setPositiveButton("是",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        ContactsTable ct = new ContactsTable(MyContactsActivity.this);
                        //删除联系人信息
                        if(ct.deleteByUser(users[selecteItem]))
                        {
                            //重新获取数据
                            users=ct.getAllUser();
                            //刷新列表
                            listViewAdapter.notifyDataSetChanged();
                            selecteItem = 0;
                            Toast.makeText(MyContactsActivity.this, "删除成功！",
                                    Toast.LENGTH_SHORT).show();
                        }else
                        {
                            Toast.makeText(MyContactsActivity.this, "删除失败！",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        alert.setNegativeButton("否",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });
        alert.show();
    }

}