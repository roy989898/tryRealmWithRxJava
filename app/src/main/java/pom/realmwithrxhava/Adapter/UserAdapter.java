package pom.realmwithrxhava.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pom.realmwithrxhava.Models.User;
import pom.realmwithrxhava.R;

/**
 * Created by Roy.Leung on 26/1/17.
 */

public class UserAdapter extends BaseAdapter {
    private Context mContext;
    private List<User> userList;

    public UserAdapter(Context context, List<User> userList) {
        this.mContext = context;
        this.userList = userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {

        if (userList == null)
            return 0;
        else
            return userList.size();
    }

    @Override
    public Object getItem(int i) {
        if (userList == null)
            return null;
        else
            return userList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        User user = userList.get(i);
        ViewHolder vh;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.user_adapter_layout, viewGroup, false);
            vh = new ViewHolder(view);
            view.setTag(vh);

        }
        vh = (ViewHolder) view.getTag();
        vh.tvAge.setText(user.getAge());
        vh.tvName.setText(user.getName());

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_age)
        TextView tvAge;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
