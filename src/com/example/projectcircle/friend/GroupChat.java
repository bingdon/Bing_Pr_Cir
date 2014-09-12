package com.example.projectcircle.friend;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract.Data;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.projectcircle.LoginActivity;
import com.example.projectcircle.R;
import com.example.projectcircle.adpter.GroupChatAdapter;
import com.example.projectcircle.adpter.GroupGallaryAdapter;
import com.example.projectcircle.bean.UserInfo;
import com.example.projectcircle.util.MyHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;


@SuppressLint("CutPasteId")
public class GroupChat extends Activity  implements FriendSelectListenter{
	private static final String TAG = null;
	// private static final String TAG = "FriendPage";
	ListView listview;
	GroupChatAdapter myAdapter;
	/**
	 * 顶部Button
	 */
	Button add, new_, maybe, group;
	/**
	 * 搜索Button
	 */
	Button search;
	LinearLayout groupchat, addfriend, tellfriend;

	boolean isRefreshing = false;
	View headview2;
	LinearLayout group2;
	LinearLayout friend;
	protected Activity context;
	protected LayoutInflater anchor;
	public ArrayList<UserInfo> friendList;
	LayoutInflater inflater = null;

	String id;

	public ArrayList<UserInfo> usersList;
	private Button button_back;
	ArrayList<HashMap<String, Object>> listItem;
	private String username;

	private String headimage2;
	private List<String> gallarylist;
	private GroupGallaryAdapter adapter;
	private Gallery myGallery;
	private String headimage3;
	private Set<String> set;
	private ImageView group_button;
	private String group_chat_friend_id;
	private RelativeLayout gallerParent;
	private Gallery group_chat_gallery;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.group_chat);
		gallarylist =  new ArrayList<String>(); 		
		adapter = new GroupGallaryAdapter(this, gallarylist); 
		id = LoginActivity.id;
		findfriend(id);	
		groupChatSure();
		back();
		//gallary();
	}
//群聊中，选好人，按确定键，进入群聊界面
private void groupChatSure() {
		// TODO Auto-generated method stub
		group_button = (ImageView)findViewById(R.id.group_btn_sure);
		group_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent1 = new Intent(GroupChat.this, GroupChatContent.class);
				startActivity(intent1);	
				GroupChat.this.finish();
			}
		});
	}
	private void findfriend(String id) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i("返回：", response);
				parsefindfriend(response);
				initList();
			}

		};
		MyHttpClient client = new MyHttpClient();
		client.findfriend(id, res);
	}

	private void parsefindfriend(String response) {
		 try {
		 JSONObject result = new JSONObject(response);
		 JSONObject obj = result.getJSONObject("friends");
		 friendList = new ArrayList<UserInfo>();
		 JSONArray json = obj.getJSONArray("resultlist");
		 int length = json.length();
		 System.out.println("length==" + length);
		 for (int i = 0; i < length; i++) {
		 UserInfo user = new UserInfo();
		 obj = json.getJSONObject(i);
		 user.setId(obj.getString("id"));
//		 user.setTel(obj.getString("tel"));
		 user.setUsername(obj.getString("username"));
		 user.setType(obj.getString("type"));
		 user.setAddress(obj.getString("address"));
		 user.setEquipment(obj.getString("equipment"));
//		 user.setSign(obj.getString("sign"));
		 user.setHeadimage(obj.getString("headimage"));
		 user.setAccept(obj.getString("accept"));
//		 user.setLat(obj.getDouble("commercialLat"));
//		 user.setLon(obj.getDouble("commercialLon"));
		 friendList.add(user);
		 }
		 } catch (JSONException e) {
		 e.printStackTrace();
		 }

	}
	private void initList() {
		// TODO Auto-generated method stub
		listview = (ListView) findViewById(R.id.n_group_listView);
		myAdapter = new GroupChatAdapter(getList_friend(), this);
		listview.setAdapter(myAdapter); 
		myAdapter.setFriendSelectListener((FriendSelectListenter) this);
	
	}


	private ArrayList<HashMap<String, Object>> getList_friend() {
		 listItem = new ArrayList<HashMap<String, Object>>();
		// TODO Auto-generated method stub
		Log.i(TAG, "返回:friendList" + friendList);
		if(friendList != null){
		for (int i = 0; i < friendList.size(); i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("friendID", friendList.get(i).getId());
			map.put("username", friendList.get(i).getUsername());
			map.put("equipment", friendList.get(i).getEquipment());
			map.put("headimage", friendList.get(i).getHeadimage());
			map.put("address", friendList.get(i).getAddress());
			map.put("type", friendList.get(i).getType());
			//map.put("people", people_num);
			listItem.add(map);
		}
		}
		return listItem;
	}
	private void back() {
		// TODO Auto-generated method stub
		button_back = (Button) findViewById(R.id.n_group_left);
		button_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				GroupChat.this.setResult(RESULT_OK, getIntent());
				GroupChat.this.finish();
			}
		});
	}

	@Override
	public void onFriendSelectListenter(int position) {
		// TODO Auto-generated method stub
		 headimage2 = listItem.get(position).get("headimage")+"";
		 Log.i("看看能不能得到这个头像资源", headimage2);			
		  //添加资源  
		  gallarylist.add(headimage2); 	
		  Log.i("gallarylist去重复前", gallarylist+"");	
		  //删除选中的相同的头像
		  //如果两个好友头像相同的话，还不能删除，不能用这个方法，所以得删除相同的id，因为每个人的Id都不同
		  group_chat_friend_id = listItem.get(position).get("friendID")+"";
		 removeDuplicate(gallarylist);
		  Log.i("gallarylist去重复后", gallarylist+"");			 
	      myGallery = (Gallery) findViewById(R.id.group_chat_gallery); 
	      gallerParent =  (RelativeLayout) findViewById(R.id.gallery_parent); 
	      group_chat_gallery = (Gallery)findViewById(R.id.group_chat_gallery);
	     //alignGalleryToLeft(gallerParent,group_chat_gallery);
	     // myGallery.setSelection(gallarylist.size()/2);	    
	      myGallery.setAdapter(adapter); 	        
	}

	@Override
	public void onFriendNotSelectListenter(int position) {
			// TODO Auto-generated method stub
		 headimage3 = listItem.get(position).get("headimage")+"";
		 gallarylist.remove(headimage3);
		 removeDuplicate(gallarylist);
		  myGallery = (Gallery) findViewById(R.id.group_chat_gallery);  
	        myGallery.setAdapter(adapter); 		       
	}
	//删除下边相同的头像
	  public static void removeDuplicate(List list) {
		   for ( int i = 0 ; i < list.size() - 1 ; i ++ ) {
		     for ( int j = list.size() - 1 ; j > i; j -- ) {
		       if (list.get(j).equals(list.get(i))) {
		         list.remove(j);
		       }
		      }
		    }
		    System.out.println(list);
		}

		private void alignGalleryToLeft(View parentView, Gallery gallery) {
		int galleryWidth = parentView.getWidth();//得到Parent控件的宽度
		//在这边我们必须先从资源尺寸中得到子控件的宽度跟间距，因为:
		// 1. 在运行时，我们无法得到间距(因为Gallery这个类，没有这样的权限)
		// 2.有可能在运行得宽度的时候，item资源还没有准备好。
		
		int itemWidth = context.getResources().getDimensionPixelSize(R.id.group_chat_gallery);
		int spacing = context.getResources().getDimensionPixelSize(R.id.group_chat_gallery);
		//那么这个偏移量是多少，我们将左边的gallery，以模拟的第一项的左对齐
		int offset;
		if (galleryWidth <= itemWidth) {
		offset = galleryWidth / 2 - itemWidth / 2 - spacing;
		} else {
		offset = galleryWidth - itemWidth - 2 * spacing;
		}
		// 现在就可以根据更新的布局参数设置做对齐了。
		MarginLayoutParams mlp = (MarginLayoutParams) gallery.getLayoutParams();
		mlp.setMargins(-offset, mlp.topMargin, mlp.rightMargin, mlp.bottomMargin);
		}

}
