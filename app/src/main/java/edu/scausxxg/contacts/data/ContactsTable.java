package edu.scausxxg.contacts.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.Vector;

/**
 * 联系人表操作
 */
public class ContactsTable {
    private MyDB mydb;//数据库管理

    public ContactsTable(Context context)
    {
        mydb = new  MyDB(context);
        if(!mydb.isTableExits(MyDB.TABLE_NAME))
        {
            String  createTableSql ="CREATE TABLE IF NOT EXISTS " +
                    MyDB.TABLE_NAME + "(  _id  integer primary key  AUTOINCREMENT ,  " +
                    "name  VARCHAR," +
                    "mobile  VARCHAR,"+
                    "qq  VARCHAR)";
            //创建表
            mydb.creatTable(createTableSql);
        }
    }

    /**
     * 添加数据到联系人表
     * @param user
     * @return
     */
	 // TODO 1、往表中添加数据
    public  boolean  addData(User user)
    {
        ContentValues values = new ContentValues();

        values.put("name", user.getName());
        values.put("mobile", user.getMobile());
        values.put("qq", user.getQq());

        return mydb.save(MyDB.TABLE_NAME,values);
    }
	 /**
     * 修改联系人信息
     */
	// TODO 2、更新表中的数据
    public  boolean updateUser(User user)
    {
        ContentValues values = new ContentValues();
        values.put("name", user.getName());
        values.put("mobile", user.getMobile());
        values.put("qq", user.getQq());

        String whereClause = "_id = ?";
        String[] whereArgs = {""+ user.get_id()};
	   
        return mydb.update(MyDB.TABLE_NAME, values, whereClause, whereArgs);
    }
    /**
     * 删除联系人
     * @param user
     * @return
     */
	// TODO 3、删除表中的数据 
    public  boolean  deleteByUser(User user)
    {
        String whereClause = "_id = ?";
        String[] whereArgs = {""+ user.get_id()};
		
		return mydb.delete(MyDB.TABLE_NAME, whereClause, whereArgs);
    }

    /**
     * 获取联系人表数据
     * @return
     */
    public  User[] getAllUser()
    {
        Vector<User> v = new Vector<User>();
        Cursor cursor = null;
        try {
            cursor = mydb.find("select * from " + MyDB.TABLE_NAME , null);
            while (cursor.moveToNext()) {
                User temp = new User();
                temp.set_id(cursor.getInt(cursor.getColumnIndex("_id")));
                temp.setName(cursor.getString(cursor.getColumnIndex("name")));
                temp.setMobile(cursor.getString(cursor.getColumnIndex("mobile")));
                temp.setQq(cursor.getString(cursor.getColumnIndex("qq")));
                v.add(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            mydb.closeConnection();
        }
        if (v.size() > 0) {
            return v.toArray(new User[] {});
        }else
        {
            User[]  users=new User[1];
            User  user=new User();
            user.setName("无结果");
            users[0]=user;
            return users;
        }
    }
    /**
     * 根据数据库ID主键来获取联系人
     * @param id
     * @return
     */
    public User getUserByID(int id)
    {
        Cursor cursor = null;
        try {
            cursor = mydb.find(
                    "select * from " + MyDB.TABLE_NAME +"   where  "
                            +"_id=?" , new String[]{id+""});
            User temp = new User();
            cursor.moveToNext();
            temp.set_id(cursor.getInt(cursor.getColumnIndex("_id")));
            temp.setName(cursor.getString(cursor.getColumnIndex("name")));
            temp.setMobile(cursor.getString(cursor.getColumnIndex("mobile")));
            temp.setQq(cursor.getString(cursor.getColumnIndex("qq")));
            return temp;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            mydb.closeConnection();
        }
        return null;
    }
    /**
     * 获取联系人表数据
     * @return
     */
    public  User[] findUserByKey(String key)
    {
        Vector<User> v = new Vector<User>();
        Cursor cursor = null;
        try {
            cursor = mydb.find(
                    "select * from " + MyDB.TABLE_NAME +"   where  "
                            +"name like '%"+key+"%' " +
                            " or "+"mobile like '%"+key+"%' " +
                            " or "+"qq like  '%"+key+"%' "
                    , null);
            while (cursor.moveToNext()) {
                User temp = new User();
                temp.set_id(cursor.getInt(cursor.getColumnIndex("_id")));
                temp.setName(cursor.getString( cursor.getColumnIndex("name")));
                temp.setMobile(cursor.getString(cursor.getColumnIndex("mobile")));
                temp.setQq(cursor.getString(cursor.getColumnIndex("qq")));
                v.add(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            mydb.closeConnection();
        }
        if (v.size() > 0) {
            return v.toArray(new User[] {});
        }else
        {
            User[]  users=new User[1];
            User  user=new User();
            user.setName("无结果");
            users[0]=user;
            return users;
        }
    }

}
