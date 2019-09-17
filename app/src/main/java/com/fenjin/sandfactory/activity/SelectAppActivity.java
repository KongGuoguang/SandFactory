package com.fenjin.sandfactory.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fenjin.data.bean.AppInfo;
import com.fenjin.sandfactory.R;
import com.fenjin.sandfactory.viewmodel.SelectAppViewModel;

import java.util.ArrayList;
import java.util.List;

public class SelectAppActivity extends BaseActivity {

    private SelectAppViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_app);
        viewModel = ViewModelProviders.of(this).get(SelectAppViewModel.class);

        PackageManager pm = getPackageManager();

        // Return a List of all packages that are installed on the device.

        List<PackageInfo> packages = pm.getInstalledPackages(0);

        List<String> packageNames = viewModel.dataRepository.getPackageNames();

        final List<AppInfo> appInfoList = new ArrayList<>();

        for (PackageInfo packageInfo : packages) {

            // 判断系统/非系统应用
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {// 非系统应用
                if (packageNames.contains(packageInfo.packageName)) {
                    continue;
                }

                AppInfo appInfo = new AppInfo();
                appInfo.setPackageName(packageInfo.packageName);
                appInfo.setAppIcon(packageInfo.applicationInfo.loadIcon(pm));
                appInfo.setAppName(packageInfo.applicationInfo.loadLabel(pm).toString());

                appInfoList.add(appInfo);
            }

        }

        Adapter adapter = new Adapter(appInfoList);

        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra("name", appInfoList.get(i).getPackageName());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    public void onClick(View view) {
        finish();
    }


    private class Adapter extends BaseAdapter {

        List<AppInfo> items;

        public Adapter(List<AppInfo> items) {
            this.items = items;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int i) {
            return items.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            AppInfo appInfo = items.get(i);
            Holder holder;
            if (view == null) {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_app_list, viewGroup, false);
                holder = new Holder(view);
                view.setTag(holder);
            } else {
                holder = (Holder) view.getTag();
            }

            holder.logo.setImageDrawable(appInfo.getAppIcon());
            holder.name.setText(appInfo.getAppName());

            return view;
        }


        private class Holder {
            TextView name;
            ImageView logo;

            Holder(View view) {
                logo = view.findViewById(R.id.icon);
                name = view.findViewById(R.id.name);
            }
        }
    }
}
