package com.example.projectcircle.util;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * @Description: HttpClient瀹炵幇绫��������
 * 
 */
public class MyHttpClient {

	public static final String BASE_URL = "http://www.gcquan.com/project/api/";
	/**
	 * 鐧诲綍鍜屾敞鍐��������
	 */
	public static final String login_url = BASE_URL + "findUser?tel=";
	public static final String sigin_url = BASE_URL + "postUser?tel=";
	/**
	 * 瀹屽杽淇℃伅
	 */
	public static final String completeall_url = BASE_URL + "postUser?id=";
	public static final String complete_url = BASE_URL + "updateEqu?uid=";
	public static final String completeD_url = BASE_URL + "updateDriver?uid=";
	public static final String completeUser_url = BASE_URL + "updateUser?id=";
	public static final String updateMyInfo_url = BASE_URL + "update?id=";
	public static final String uploadequheadimg_url = BASE_URL
			+ "uploadEquHeadimage";
	/**
	 * 鐢ㄦ埛璇︽儏 闄勮繎浜��������借繎缇ょ粍 璺ㄥ尯鍩熺兢缁��������芥柊浣嶇疆
	 */
	// 鐢ㄦ埛璇︽儏
	public final static String userdetail_url = BASE_URL + "UserDetail?id=";
	// 璁惧鐨勮鎯�
	public final static String equdetail_url = BASE_URL + "findByUid?uid=";
	// 鎸夌収type鐨勪笉鍚岀被鍨嬭В鏋愮殑userDetail
	public final static String userotherdetail_url = BASE_URL
			+ "userOtherDetail?id=";
	// 闄勮繎浜��������
	public final static String nearuser_url = BASE_URL + "nearUser?id=";
	// 绛涳拷1锟������������
	public final static String select_url = BASE_URL + "userFilter?address=";
	// 闄勮繎缇ょ粍
	public final static String neargroup_url = BASE_URL + "nearGroup?radius=";
	// 璺ㄥ尯鍩熺兢缁��������
	public final static String grouplist_url = BASE_URL + "api/groupList";

	public final static String NEAR_GROUP_LIST_URL = BASE_URL + "nearGroup";
	// 缇よ鎯��������絠d
	public final static String groupdetail_url = BASE_URL + "GroupDetail?id=";
	// 鏇存柊浣嶇疆
	public final static String updateP_url = BASE_URL + "updatePosition?id=";
	// 濂藉弸鍒楄〃鏄剧ず
	public static final String friend_url = BASE_URL + "findFriends?id=";
	public static final String findconRegiest_url = BASE_URL + "isRegiest?";
	/**
	 * 鍒涘缓缇ょ粍
	 */
	public final static String postgroup_url = BASE_URL + "postGroup?uid=";
	// 鏌愬尯鍩熺殑缇ょ粍鏁��������
	public final static String countgroup_url = BASE_URL + "countGroup?key=";
	// 鏌ユ壘缇ゆ垚鍛��������
	public final static String findmember_url = BASE_URL + "findMember?gid=";
	// 鍒犻櫎缇ゆ垚鍛��������
	public final static String delmember_url = BASE_URL + "deleteMember?uid=";
	// 鏌ユ壘鑷繁鍒涘缓鐨勭兢缁��������
	public final static String findmyselfgroup_url = BASE_URL
			+ "findCreateGroup?uid=";
	public final static String findmygroup_url = BASE_URL + "findGroup?uid=";
	/**
	 * 濂藉弸
	 */
	// 鐢宠濂藉弸鎺ュ彛
	public final static String applyfriend_url = BASE_URL + "askFriends?aid=";
	public final static String searchfriend_url = BASE_URL
			+ "searchFriends?key=";
	public final static String verifyfriend_url = BASE_URL + "askFriends?aid=";
	public final static String friendrequest_url = BASE_URL
			+ "verifyFriends?bid=";
	public final static String befriend_url = BASE_URL + "beFriends?id=";
	public final static String denyfriend_url = BASE_URL + "delFriends?id=";
	// 鏍规嵁鐢佃瘽鍙风爜鏌ユ壘鐢ㄦ埛
	public final static String finduserbytel_url = BASE_URL
			+ "findUserByTel?tel=";
	/**
	 * 宸ヤ綔鎺ュ彛
	 */
	// 鍙戣〃蹇冩儏,涓嶅惈鍥剧墖
	public final static String post_mood = BASE_URL + "postMood?uid=";
	// 鍙戣〃蹇冩儏涓殑鍥剧墖
	public final static String postmood_land = BASE_URL
			+ "postMoodLand?userid=";
	// 鍙戣〃蹇冩儏涓殑鍥剧墖
	public final static String postmood_img = BASE_URL + "postMoodImg";
	// 鍙戣〃璇勮
	public final static String postcomment_url = BASE_URL
			+ "postMoodComment?moodid=";
	// 鍙戝竷宸ョ▼浣滀笟
	public final static String postjob_url = BASE_URL + "postJob?uid=";
	// 宸ョ▼浣滀笟鍒楄〃鍜屽徃鏈洪渶姹傚垪琛��������
	public final static String joblist_url = BASE_URL + "findJobByType?type=";
	// 鎵� 鎵�浠�
	// public final static String joblist_url =
	// BASE_URL+"findJobByType?type=";
	// 鐘讹拷1锟����藉垪琛��������
	public final static String my_mood = BASE_URL + "userMood?id=";
	public final static String listmood_url = BASE_URL + "listMood?userid=";
	// 宸ョ▼浣滀笟璇︽儏鍜屽徃鏈洪渶姹傝鎯��������
	public final static String jobdetail_url = BASE_URL + "JobDetail?id=";
	// 鎵��������藉伐浣滄帴鍙��������
	public final static String alljob_url = BASE_URL + "findAllJob";
	// 鎵��������藉晢瀹舵帴鍙��������
	public final static String findshangjia_url = BASE_URL + "findByType?type=";
	// 鏍规嵁绫诲瀷鏌ユ壘鍟嗗璁惧
	public final static String findetype_url = BASE_URL
			+ "findBusiness?business=";
	// 绉诲姩鍟嗗绛涳拷1锟���
	public final static String moveshopselect_url = BASE_URL
			+ "userFilterByBusEqu?equ=";
	/**
	 * 鍥剧墖鍦板潃
	 */
	public static final String IMAGE_URL = "http://115.28.81.148/project/upload/";
	// 涓婁紶涓汉澶村儚
	public final static String headurl = BASE_URL + "uploadHeadimage";
	// 涓婁紶缇ょ粍澶村儚
	public final static String gheadurl = BASE_URL + "uploadGroupHeadimage";
	/**
	 * 鍙戯拷1锟����借亰澶╂秷鎭��������
	 */
	public final static String postmessages_url = BASE_URL
			+ "postMessages?userid=";
	// 鍒ゆ柇鐢ㄦ埛杈撳叆瀵嗙爜鏄惁姝ｇ‘
	public final static String pwdistrue_url = BASE_URL + "psIstrue?id=";

	public final static String PWD_CHANGE_URL = BASE_URL + "psIstrue";
	// 鎰忚鍙嶉鎺ュ彛
	public final static String postopinion_url = BASE_URL + "postOpinion?uid=";

	private static final String DEL_EQU_URL = BASE_URL + "deleteEqu";

	private static final String MODIFY_GROUP_INFO_URL = BASE_URL
			+ "updateGroup";

	private static final String DESTROY_GROUP_URL = BASE_URL + "destroyGroup";
	
	public static final String APP_URL_DOWNLOAD="http://www.gcquan.com/download/";

	/**
	 * 鐧诲綍鍜屾敞鍐��������
	 */
	// 鐧诲綍

	private static AsyncHttpClient client = new AsyncHttpClient();

	public void doLogin(String tel, String password,
			AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(login_url + tel + "&password=" + password, params, res);
			Log.i("login_url", login_url + tel + "&password=" + password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 娉ㄥ唽
	public void doSigin(String tel, String password, String username,
			String birthday, String address, String type, String equipment,
			String business, String accept, double lon, double lat,
			String rplace, String age, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(sigin_url + tel + "&password=" + password
					+ "&username=" + username + "&birthday=" + birthday
					+ "&address=" + address + "&type=" + type + "&equipment="
					+ equipment + "&business=" + business + "&accept=" + accept
					+ "&lon=" + lon + "&lat=" + lat + "&rplace=" + rplace
					+ "&age=" + age, params, res);
			Log.i("sigin_url", sigin_url + tel + "&password=" + password
					+ "&username=" + username + "&birthday=" + birthday
					+ "&address=" + address + "&type=" + type + "&equipment="
					+ equipment + "&business=" + business + "&accept=" + accept
					+ "&lon=" + lon + "&lat=" + lat + "&rplace=" + rplace
					+ "&age=" + age);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 瀹屽杽淇℃伅 鏈轰富
	 */
	public void uploadEqu(String uid, String type, String ename, String ebrand,
			String etype, String emodel, String enumber,
			AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(complete_url + uid + "&type=" + type + "&ename="
					+ ename + "&ebrand=" + ebrand + "&etype=" + etype
					+ "&emodel=" + emodel + "&enumber=" + enumber, params, res);
			Log.i("complete_url", complete_url + uid + "&type=" + type
					+ "&ename=" + ename + "&ebrand=" + ebrand + "&etype="
					+ etype + "&emodel=" + emodel + "&enumber=" + enumber);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 瑁呰浇杞��������藉畠
	public void CompMasterLoad(String uid, String type, String ename,
			String ebrand, String eweight, String emodel, String enumber,
			AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(complete_url + uid + "&type=" + type + "&ename="
					+ ename + "&ebrand=" + ebrand + "&eweight=" + eweight
					+ "&emodel=" + emodel + "&enumber=" + enumber, params, res);
			Log.i("complete_url", complete_url + uid + "&type=" + type
					+ "&ename=" + ename + "&ebrand=" + ebrand + "&eweight="
					+ eweight + "&emodel=" + emodel + "&enumber=" + enumber);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 骞虫澘杞��������
	public void CompMasterFlat(String uid, String type, String ename,
			String eload, String enumber, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(complete_url + uid + "&type=" + type + "&ename="
					+ ename + "&eload=" + eload + "&enumber=" + enumber,
					params, res);
			Log.i("complete_url", complete_url + uid + "&type=" + type
					+ "&ename=" + ename + "&eload=" + eload + "&enumber="
					+ enumber);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 瀹屽杽淇℃伅 鍏徃
	 */
	public void CompleteCompany(String uid, String companyname,
			String businessinfo, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(completeUser_url + uid + "&companyname=" + companyname
					+ "&businessinfo=" + businessinfo, params, res);
			Log.i("completeUser_url", completeUser_url + uid + "&companyname="
					+ companyname + "&businessinfo=" + businessinfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void CompleteCompany(String uid, String companyname,
			String businessinfo, String business, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(
					completeUser_url + uid + "&companyname=" + companyname
							+ "&businessinfo=" + businessinfo + "&business="
							+ business, params, res);
			Log.i("completeUser_url", completeUser_url + uid + "&companyname="
					+ companyname + "&businessinfo=" + businessinfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @deprecated 瀹屽杽淇℃伅 鍙告満
	 */
	public void CompleteDriver(String id, String type, String driveryears,
			String nequ, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(completeD_url + id + "&type=" + type + "&driveryears="
					+ driveryears + "&nequ=" + nequ, params, res);
			Log.i("completeD_url", "添加:" + completeD_url + id + "&type=" + type
					+ "&driveryears=" + driveryears + "&nequ=" + nequ);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @deprecated 此方法过期，请使用
	 *             {@link #CompleteDriver2(String, String, String, String, String, String, AsyncHttpResponseHandler)}
	 * @param id
	 * @param type
	 * @param driveryears
	 * @param nequ
	 * @param res
	 */
	public void CompleteDriver2(String id, String type, String driveryears,
			String nequ, String oequ, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(completeD_url + id + "&type=" + type + "&driveryears="
					+ driveryears + "&nequ=" + nequ + "&oequ=" + oequ, params,
					res);
			Log.i("completeD_url", "添加:" + completeD_url + id + "&type=" + type
					+ "&driveryears=" + driveryears + "&nequ=" + nequ);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void CompleteDriver2(String id, String type, String driveryears,
			String nequ, String oequ, String dbegin,
			AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(completeD_url + id + "&type=" + type + "&driveryears="
					+ driveryears + "&nequ=" + nequ + "&oequ=" + oequ
					+ "&dbegin=" + dbegin, params, res);
			Log.i("completeD_url", "添加:" + completeD_url + id + "&type=" + type
					+ "&driveryears=" + driveryears + "&nequ=" + nequ);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 鏇存柊涓汉淇℃伅
	 */
	public void updateMyInfo(String id, String username, String age,
			String sign, String info, String type, String equipment,
			String accept, String address, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(updateMyInfo_url + id + "&username=" + username
					+ "&age =" + age + "&sign=" + sign + "&info=" + info
					+ "&type=" + type + "&equipment=" + equipment + "&accept="
					+ accept + "&address=" + address, params, res);
			Log.i("updateMyInfo_url", updateMyInfo_url + id + "&username="
					+ username + "&age =" + age + "&sign=" + sign + "&info="
					+ info + "&type=" + type + "&equipment=" + equipment
					+ "&accept=" + accept + "&address=" + address);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateMyInfo(String id, String username, String age,
			String sign, String info, String type, String equipment,
			String accept, String address, String place, String hobby,
			AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(updateMyInfo_url + id + "&username=" + username
					+ "&age =" + age + "&sign=" + sign + "&info=" + info
					+ "&type=" + type + "&equipment=" + equipment + "&accept="
					+ accept + "&address=" + address + "&place=" + place
					+ "&hobby=" + hobby, params, res);
			Log.i("updateMyInfo_url", updateMyInfo_url + id + "&username="
					+ username + "&age =" + age + "&sign=" + sign + "&info="
					+ info + "&type=" + type + "&equipment=" + equipment
					+ "&accept=" + accept + "&address=" + address);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 涓婁紶澶村儚
	public void postHeadImage(String id, String headimage,
			AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			params.put("id", id);
			params.put("headimage", headimage);
			HttpUtil.post(headurl, params, res);
			// HttpUtil.post(headurl + id + "&headimage=" + headimage, params,
			// res);
			// System.out.println(headurl + id + "&headimage=" + headimage);
			Log.i("headurl", headurl + "id=" + id + ",headimage=" + headimage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 娣诲姞璁惧鐨勪笁寮犵収鐗�
	 */
	public void upLoadEquHeadImage(String uid, String headimage,
			AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			params.put("uid", uid);
			params.put("headimage", headimage);
			HttpUtil.post(uploadequheadimg_url, params, res);
			Log.i("uploadequheadimg_url", uploadequheadimg_url + "uid=" + uid
					+ ",headimage=" + headimage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 瀹屽杽淇℃伅 涓汉淇℃伅
	public void BaseInfo(String id, String sign, String place, String hobby,
			String info, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(completeUser_url + id + "&sign=" + sign + "&place="
					+ place + "&hobby=" + hobby + "&info=" + info, params, res);
			Log.i("completeUser_url", completeUser_url + id + "&sign=" + sign
					+ "&place=" + place + "&hobby=" + hobby + "&info=" + info);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 鐢ㄦ埛璇︽儏
	 */
	public void UserDetail(String id, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(userdetail_url + id, params, res);
			Log.i("userdetail_url", userdetail_url + id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 璁惧璇︽儏
	 */
	public void EquDetail(String uid, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(equdetail_url + uid, params, res);
			Log.i("equdetail_url", equdetail_url + uid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 鎸塼ype绫诲瀷涓嶅悓瑙ｆ瀽鐨剈serDetail
	 */
	public void UserDetail2(String id, String type, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(userotherdetail_url + id + "&type=" + type, params,
					res);
			Log.i("userdetail_url", userdetail_url + id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 闄勮繎浜��������
	 */
	public void UserFilter(String address, String type, String equ, double lat,
			double lon, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(select_url + address + "&type=" + type + "&equ=" + equ
					+ "&lat=" + lat + "&lon=" + lon, params, res);
			Log.i("select_url", select_url + address + "&type=" + type
					+ "&equ=" + equ + "&lat=" + lat + "&lon=" + lon);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 缁涙盯锟介崝鐔诲厴
	 */
	public void NearUser(String id, int radius, double lat, double lon,
			AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(nearuser_url + id + "&radius=" + radius + "&lat="
					+ lat + "&lon=" + lon, params, res);
			Log.i("nearuser_url", nearuser_url + id + "&radius=" + radius
					+ "&lat=" + lat + "&lon=" + lon);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 涓婁紶缇ょ粍澶村儚
	public void postGroupHeadImage(String id, String headimage,
			AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			params.put("uid", id);
			params.put("headimage", headimage);
			HttpUtil.post(gheadurl, params, res);
			Log.i("gheadurl", gheadurl + "uid==" + id + ",headimage=="
					+ headimage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 闄勮繎缇ょ粍
	 */
	public void NearGroup(int radius, double lat, double lon,
			AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(
					neargroup_url + radius + "&lat=" + lat + "&lon=" + lon,
					params, res);
			Log.i("neargroup_url", neargroup_url + radius + "&lat=" + lat
					+ "&lon=" + lon);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @deprecated 璺ㄥ尯鍩熺兢缁��������
	 */
	public void GroupList(AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(grouplist_url, params, res);
			Log.i("grouplist_url", grouplist_url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void GroupList2(String lat, String lon, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		params.put("lat", lat);
		params.put("lon", lon);
		try {
			HttpUtil.get(NEAR_GROUP_LIST_URL, params, res);
			Log.i("grouplist_url", "附近群组" + NEAR_GROUP_LIST_URL + params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 鏇存柊鍧愭爣
	 */
	public void updatePosition(String id, double lat, double lon,
			AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(updateP_url + id + "&lat=" + lat + "&lon=" + lon,
					params, res);
			Log.i("updateP_url", updateP_url + id + "&lat=" + lat + "&lon="
					+ lon);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 鍒涘缓缇ょ粍
	 */
	public void postGroup(String uid, String gname, String gaddress,
			String content, String glimit, double lat, double lon,
			AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(postgroup_url + uid + "&gname=" + gname + "&gaddress="
					+ gaddress + "&content=" + content + "&glimit=" + glimit
					+ "&lat=" + lat + "&lon=" + lon, params, res);
			Log.i("postgroup_url", postgroup_url + uid + "&gname=" + gname
					+ "&gaddress=" + gaddress + "&content=" + content
					+ "&glimit=" + glimit + "&lat=" + lat + "&lon=" + lon);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 缇よ鎯��������
	 */
	public void GroupDetails(String id, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(groupdetail_url + id, params, res);
			Log.i("groupdetail_url", groupdetail_url + id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 鏌愬尯鍩熺殑缇ょ粍鏁��������
	 */
	public void CountGroup(String key, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(countgroup_url + key, params, res);
			Log.i("countgroup_url", countgroup_url + key);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 鏌ユ壘缇ゆ垚鍛��������
	 */
	public void findMember(String id, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(findmember_url + id, params, res);
			Log.i("findmember_url", findmember_url + id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 鍒犻櫎缇ゆ垚鍛��������
	 */
	public static void deleteMember(String uid, String gid,
			AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(delmember_url + uid + "&gid=" + gid, params, res);
			Log.i("delmember_url", delmember_url + uid + "&gid=" + gid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 鍒ゆ柇閫氳褰曠殑鐢佃瘽鍙风爜鏄惁宸叉敞鍐屼负宸ョ▼鍦堢敤鎴��������
	 */
	public void contactIsRegiest(String tel, String id,
			AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			params.add("tel", tel);
			HttpUtil.post(findconRegiest_url + "&id=" + id, params, res);
			Log.i("findmyselfregist_url", findconRegiest_url + tel + "&id="
					+ id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 閺屻儲澹橀懛顏勭箒瀵よ櫣娈戠紘銈囩矋
	 */
	public void findCreatGroup(String uid, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(findmyselfgroup_url + uid, params, res);
			Log.i("findmyselfgroup_url", findmyselfgroup_url + uid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 鎴戠殑缇ょ粍鍒楄〃
	 */
	public void findGroup(String uid, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(findmygroup_url + uid, params, res);
			Log.i("findmygroup_url", findmygroup_url + uid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 鍙戝竷宸ヤ綔
	 */
	public void PostJob(String uid, String title, String location, String type,
			String detail, String name, String tel, Double jlat, Double jlon,
			AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(postjob_url + uid + "&title=" + title + "&location="
					+ location + "&type=" + type + "&detail=" + detail
					+ "&name=" + name + "&tel=" + tel + "&lat=" + jlat
					+ "&lon=" + jlon, params, res);
			Log.i("postjob_url", postjob_url + uid + "&title=" + title
					+ "&location=" + location + "&type=" + type + "&detail="
					+ detail + "&name=" + name + "&tel=" + tel + "&lat=" + jlat
					+ "&lon=" + jlon);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 鍙戝竷蹇冩儏鍐呭锛屼笉鍚浘鐗�
	 */
	public void PostMood(String uid, String context,
			AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.post(post_mood + uid + "&context=" + context// +"&headimage="+headimge
			, params, res);
			Log.i("post_mood", post_mood + uid + "&context=" + context);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 鍙戣〃璇勮
	 */
	public void PostComment(String moodid, String content, String userid,
			String commentid, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.post(postcomment_url + moodid + "&content=" + content
					+ "&userid=" + userid// +"&headimage="+headimge
					+ "&commentid=" + commentid, params, res);
			Log.i("postcomment_url", postcomment_url + moodid + "&content="
					+ content + "&userid=" + userid// +"&headimage="+headimge
					+ "&commentid=" + commentid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 鍙戝竷蹇冩儏鍐呭涓殑鍥剧墖
	 */
	public static void postMoodImg(String moodid, String moodimage2,
			AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			params.put("moodid", moodid);
			params.put("moodpicStr", moodimage2);
			HttpUtil.post(postmood_img, params, res);
			Log.i("postmood_img", postmood_img + moodid + "&moodpicStr="
					+ moodimage2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 鐐硅禐
	 */
	public void postMoodLand(String userid, String moodid,
			AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(postmood_land + userid + "&moodid=" + moodid, params,
					res);
			Log.i("postmood_land", postmood_land + userid + "&moodid=" + moodid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 濂藉弸鐨勭姸鎬佸垪琛��������
	 */
	public void listMood(String id, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(listmood_url + id, params, res);
			Log.i("listmood_url", listmood_url + id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 鑷繁鐨勭姸鎬佸垪琛��������
	 */
	public void userMood(String id, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(my_mood + id, params, res);
			Log.i("my_mood", my_mood + id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @deprecated 宸ヤ綔鍒楄〃
	 */
	public void JobList(String type, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(joblist_url + type, params, res);
			Log.i("joblist_url", joblist_url + type);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void JobList_(String lat, String lon, String type,
			AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		params.put("lat", lat);
		params.put("lon", lon);
		try {
			HttpUtil.get(joblist_url + type, params, res);
			Log.i("joblist_url", joblist_url + type);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @deprecated 鍙告満闇��������藉垪琛��������
	 */
	public void JobList1(String type, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();

		try {
			HttpUtil.get(joblist_url + type, params, res);
			Log.i("joblist_url", joblist_url + type);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void JobList1_(String lat, String lon, String type,
			AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		params.put("lat", lat);
		params.put("lon", lon);
		try {
			HttpUtil.get(joblist_url + type, params, res);
			Log.i("joblist_url", joblist_url + type);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 宸ヤ綔璇︽儏
	 */
	public void JobDetail(String id, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(jobdetail_url + id, params, res);
			Log.i("jobdetail_url", jobdetail_url + id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 鏌ユ壘鎵��������藉伐浣��������
	 */
	public void AllJobList(AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(alljob_url, params, res);
			Log.i("alljob_url", alljob_url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 鏌ユ壘鎵��������藉晢瀹��������
	 */
	public void AllBusiness(String type, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(findshangjia_url + type, params, res);
			Log.i("findshangjia_url", findshangjia_url + type);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 鏍规嵁绫诲瀷鏌ユ壘鍟嗗璁惧
	 */
	public void findEtype(String etype, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(findetype_url + etype, params, res);
			Log.i("findetype_url", findetype_url + etype);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 绉诲姩鍟嗗绛涳拷1锟�藉姛鑳��
	 */
	public void userFilterByBusEqu(String equ, String bus, Double lat,
			Double lon, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(moveshopselect_url + equ + "&bus=" + bus + "&lat="
					+ lat + "&lon=" + lon, params, res);
			Log.i("moveshopselect_url", moveshopselect_url + equ + "&bus="
					+ bus + "&lat=" + lat + "&lon=" + lon);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 鐢宠濂藉弸
	 */
	public void applyfriend(String aid, String bid, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(applyfriend_url + aid + "&bid=" + bid, params, res);
			Log.i("applyfriend_url", applyfriend_url + aid + "&bid=" + bid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 鏌ユ壘濂藉弸
	 */
	public void searchfriend(String key, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(searchfriend_url + key, params, res);
			Log.i("searchfriend_url", searchfriend_url + key);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 濂藉弸楠岃瘉
	 */
	public void VerifyFriend(String aid, String bid, String info,
			AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(verifyfriend_url + aid + "&bid=" + bid + "&info="
					+ info, params, res);
			Log.i("verifyfriend_url", verifyfriend_url + aid + "&bid=" + bid
					+ "&info=" + info);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 璇锋眰淇℃伅
	 */
	public void FriendRequestMessage(String bid, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(friendrequest_url + bid, params, res);
			Log.i("friendrequest_url", friendrequest_url + bid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 楠岃瘉鍚屾剰鎴愪负濂藉弸
	 */
	public void beFriends(Object accept_id, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(befriend_url + accept_id, params, res);
			Log.i("befriend_url", befriend_url + accept_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 鎷掔粷鎴愪负濂藉弸
	 */
	public void denyFriends(Object deny_id, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(denyfriend_url + deny_id, params, res);
			Log.i("denyfriend_url", denyfriend_url + deny_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 鏍规嵁鎵嬫満鍙锋煡鎵惧ソ鍙嬩俊鎭�
	 */
	public void findUserByTel(String tel, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(finduserbytel_url + tel, params, res);
			Log.i("finduserbytel_url", finduserbytel_url + tel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 濂藉弸鍒楄〃
	 */
	public void findfriend(String id, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(friend_url + id, params, res);
			Log.i("friend_url", friend_url + id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 鍙戯拷1锟����借亰澶╂秷鎭��������
	 * 
	 */
	public void postMessages(String userid, String sendid, String content,
			AsyncHttpResponseHandler res) {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(postmessages_url + userid + "&sendid=" + sendid
					+ "&content=" + content, params, res);
			Log.i("postmessages_url", postmessages_url + userid + "&sendid="
					+ sendid + "&content=" + content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void postMessages2Group(String userid, String gid, String content,
			AsyncHttpResponseHandler res) {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams();
		try {

			HttpUtil.get(postmessages_url + null + "&sendid=" + userid
					+ "&gid=" + gid + "&content=" + content, params, res);
			Log.i("postmessages_url", postmessages_url + null + "&sendid="
					+ userid + "&gid=" + gid + "&content=" + content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 鍔犲叆缇ょ粍
	 * 
	 * @param gid
	 *            缇ょ粍ID.
	 * @param uid
	 *            鐢ㄦ埛ID
	 * @param handler
	 *            澶勭悊杩斿洖
	 */
	public static void askAddGroup(String gid, String uid, String info,
			AsyncHttpResponseHandler handler) {

		RequestParams params = new RequestParams();
		params.put("gid", gid);
		params.put("uid", uid);
		params.put("info", info);
		Log.i("锟斤拷锟斤拷", BASE_URL + "askMember");
		HttpUtil.post(BASE_URL + "askMember", params, handler);
	}

	/**
	 * 鏌ヨ缇ゆ垚鍛��������
	 * 
	 * @deprecated
	 * @param gid
	 *            缇ょ粍ID
	 * @param handler
	 *            澶勭悊杩斿洖
	 */
	public static void verifyMember2Group(String gid,
			AsyncHttpResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("gid", gid);
		HttpUtil.post(BASE_URL + "verifyMember", params, handler);
	}

	public static void verifyMember2Group_(String gid,
			AsyncHttpResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("uid", gid);
		HttpUtil.post(BASE_URL + "verifyMember", params, handler);
	}

	/**
	 * 鍚屾剰鍔犲叆缇ょ粍
	 * 
	 * @param id
	 *            璇锋眰ID
	 * @param handler
	 */
	public static void beMember2GroupMem(String id,
			AsyncHttpResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("id", id);
		HttpUtil.post(BASE_URL + "beMember", params, handler);
	}

	/**
	 * 鍒犻櫎缇ょ粍鎴愬憳
	 * 
	 * @param gid
	 *            缇ょ粍ID
	 * @param uid
	 *            鐢ㄦ埛ID
	 * @param handler
	 */
	public static void deleteMember2Group(String gid, String uid,
			AsyncHttpResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("gid", gid);
		params.put("uid", uid);
		HttpUtil.post(BASE_URL + "deleteMember", params, handler);
	}

	/**
	 * 鏌ヨ鐢ㄦ埛璁惧
	 * 
	 * @param uid
	 *            鐢ㄦ埛ID
	 * @param handler
	 */
	public static void findUserEqu4User(String uid,
			AsyncHttpResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("uid", uid);
		HttpUtil.post(BASE_URL + "findUserEqu", params, handler);
	}

	// 鍒ゆ柇鏄惁鏄兢鎴愬憳
	public static final String isgroupmember_url = BASE_URL + "isMember?gid=";

	/**
	 * 鍒ゆ柇鏄惁鏄兢鎴愬憳
	 */
	public void isMember(String gid, String uid, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(isgroupmember_url + gid + "&uid=" + uid, params, res);
			Log.i("login_url", isgroupmember_url + gid + "&uid=" + uid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 淇敼瀵嗙爜涓獙璇佺敤鎴疯緭鍏ョ殑瀵嗙爜鏄惁姝ｇ‘
	public void doPwdIsTrue(String id, String pwd, String pwd_new,
			AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		params.put("id", id);
		params.put("pwd", pwd);
		params.put("npwd", pwd_new);
		try {
			new AsyncHttpClient().post(PWD_CHANGE_URL, params, res);
			// HttpUtil.get(pwdistrue_url + id + "&psw=" + psw, params, res);
			Log.i("pwdistrue_url", pwdistrue_url + id + "&psw=" + pwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 鎰忚鍙嶉
	public void doPostOpinion(String uid, String content,
			AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(postopinion_url + uid + "&content=" + content, params,
					res);
			Log.i("pwdistrue_url", postopinion_url + uid + "&content="
					+ content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除设备
	 * 
	 * @param id
	 * @param handler
	 */
	public static void deleteEqu(String id, AsyncHttpResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("id", id);
		client.post(DEL_EQU_URL, params, handler);
	}

	/**
	 * 
	 * @param id
	 * @param content
	 * @param gname
	 * @param handler
	 */
	public static void modifyGroupInfo(String id, String content, String gname,
			AsyncHttpResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("id", id);
		params.put("content", content);
		params.put("gname", gname);
		client.post(MODIFY_GROUP_INFO_URL + "?" + params, handler);
	}

	public static void destroyGroup(String id, AsyncHttpResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("id", id);
		client.post(DESTROY_GROUP_URL, params, handler);
	}

	public static void listVersion(AsyncHttpResponseHandler handler) {
		client.post(BASE_URL + "listVersion", handler);
	}

}