package com.example.customrecycle.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.customrecycle.R;
import com.example.customrecycle.base.BaseApp;
import com.example.customrecycle.frame.utils.KEY;
import com.example.customrecycle.frame.utils.PreferencesUtils;
import com.example.customrecycle.view.TextViewWithTTF;

import java.util.ArrayList;
import java.util.List;

public class OpenTabTitleAdapter extends BaseTabTitleAdapter {
	private List<String> titleList = new ArrayList<String>();

	public OpenTabTitleAdapter() {
			titleList.add("搜索");
			titleList.add("分类");
			titleList.add("精选");
			titleList.add("VIP");
			titleList.add("电影");
			titleList.add("电视剧");
			if (!PreferencesUtils.getBoolean(BaseApp.getContext(), KEY.FLAG_ISVISITOR)){
				titleList.add("设置");
			}
//			titleList.add("动漫");
	}

	@Override
	public int getCount() {
		return titleList.size();
	}

	/**
	 * 为何要设置ID标识。<br>
	 * 因为PAGE页面中的ITEM如果向上移到标题栏， <br>
	 * 它会查找最近的，你只需要在布局中设置 <br>
	 * android:nextFocusUp="@+id/title_bar1" <br>
	 * 就可以解决焦点问题哦.
	 */
	private List<Integer> ids = new ArrayList<Integer>() {
		{
			add(R.id.title_bar1);
			add(R.id.title_bar2);
			add(R.id.title_bar3);
			add(R.id.title_bar4);
			add(R.id.title_bar5);
			add(R.id.title_bar6);
			if (!PreferencesUtils.getBoolean(BaseApp.getContext(),KEY.FLAG_ISVISITOR)){
                add(R.id.title_bar7);
            }
		}
	};

	@Override
	public Integer getTitleWidgetID(int pos) {
		return ids.get(pos);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		parent.getContext();
		String title = titleList.get(position);
		if (convertView == null) {
			convertView = newTabIndicator(parent.getContext(), title, false);
			convertView.setId(ids.get(position)); // 设置ID.
		} else {
			// ... ...
		}
		return convertView;
	}

	/**
	 * 这里只是demo，你可以设置自己的标题栏.
	 */
	private View newTabIndicator(Context context, String tabName, boolean focused) {
		final String name = tabName;
		View viewC = View.inflate(context, R.layout.tab_view_indicator_item, null);
		TextViewWithTTF view = (TextViewWithTTF) viewC.findViewById(R.id.tv_tab_indicator);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		lp.setMargins(20, 0, 20, 0);
		view.setLayoutParams(lp);

		// mTabWidget.setPadding(getResources().getDimensionPixelSize(R.dimen.tab_left_offset),
		// 0, 0, 0);

		view.setText(name);

		if (focused == true) {
			Resources res = context.getResources();
			view.setTextColor(res.getColor(android.R.color.white));
			view.setTypeface(null, Typeface.BOLD);
			view.requestFocus();
		}
		return viewC;
	}
}
